package logica;

import java.util.ArrayList;

import com.mysql.cj.protocol.x.SyncFlushDeflaterOutputStream;

import modelo.Fecha;
import modelo.Historias;
import modelo.Usuarios;
import modelo.MensajeEnviado;
import persistencia.HistoriasBD;

public class ManejadorBD {
	Historias historias;
	Usuarios usuarios;
	HistoriasBD historiasBD;
	
	public ManejadorBD(Usuarios usuarios, Historias historias) {
		historiasBD= new HistoriasBD();
		this.usuarios=usuarios;
		this.historias=historias;
		
	}
	
	
	public int persistirMensajesNuevos(Historias nuevos) {
		int persistidos=0;
		boolean ok=false;
		//borrar mensajes persistidos
		if (!nuevos.getMensajesEnviados().isEmpty()){
			for (MensajeEnviado mensaje : nuevos.getMensajesEnviados()) {			
				ok = historiasBD.alta(mensaje.getFechaEnvio(), 
						mensaje.getIdUsuario(),
						mensaje.getCorreo(),
						mensaje.getTitulosYDias());
				if (ok) {				
					persistidos++;
					//falta borrar mensajes de Historias
					nuevos.getMensajesEnviados().remove(mensaje);
				}else { 
					System.out.println("Error en guardado en BD de mensaje " + mensaje.toString());
				}
			}
		}		
		return persistidos;
	}
	/*
	 * lee la BD y actualiza el modelo
	 */
	public int actualizarModelo() {	
		
		//para verificar que solo traigo mensajes que no estan en memoria, obtengo solo los posteriores al ultimo id en memoria.
		String mensajesNuevos = historiasBD.obtenerMayorDeId(historias.buscarUltimoId());
		
		//System.out.println(mensajesNuevos);
		
		// parseo desde mensajesNuevos -> modelo Historias
		String[] listaMensajes = mensajesNuevos.split("\n");
		for(String m : listaMensajes) {
			if (!m.equals("")) {
				String[] columnasMensaje = m.split(","); 
				MensajeEnviado msj = new MensajeEnviado(Integer.parseInt(columnasMensaje[0].trim()),
						new Fecha(columnasMensaje[1]),
						Integer.parseInt(columnasMensaje[2].trim()),
						columnasMensaje[3],
						columnasMensaje[4],
						columnasMensaje[4]);
						
				historias.agregarMensaje(msj);
			}
		}
		
		return 1;
	}
	
	public void borrarTodasHistoriasBD() {
		historiasBD.borrarTodo();
	}
}
