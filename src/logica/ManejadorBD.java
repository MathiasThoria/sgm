package logica;

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
	
	public int actualizarModelo() {
		/*
		 * select de historias con id mensaje > al ultimo del modelo
		 * 
		 * */
		
		//System.out.println(historiasBD.obtenerMayorDeId(10));
		
		
		
		return 1;
	}
}
