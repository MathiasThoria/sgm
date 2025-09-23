package logica;

import java.util.ArrayList;
import java.util.Iterator;



import modelo.Fecha;
import modelo.Historias;
import modelo.Usuarios;
import modelo.UsuariosSistema;
import modelo.MensajeEnviado;
import persistencia.HistoriasBD;
import persistencia.UsuarioSistemaBD;

public class ManejadorBD {
	private Historias historias;
	private HistoriasBD historiasBD;
	private UsuariosSistema usuariosSistema;
	private UsuarioSistemaBD usuarioSistemaBD;
	
	public ManejadorBD(UsuariosSistema usuariosSistema, Historias historias) {
		this.historiasBD= new HistoriasBD();
		this.usuarioSistemaBD = new UsuarioSistemaBD();
		this.usuariosSistema=usuariosSistema;
		this.historias=historias;
		
	}	
	
	/*
	 * Persiste todos los mensajes posteriores al ultimo id de BD
	 * */
	public int persistirMensajesNuevos() {	
		int ultimo = ultimoIdHistorias();	
		for(MensajeEnviado m : historias.getMensajesEnviados()) {			
			if (m.getId()>ultimo) {				
				historiasBD.alta(
			            m.getFechaEnvio(),
			            m.getIdUsuario(),
			            m.getCorreo(),
			            m.getTitulosYDias()
			    );
			//System.out.println(m.getTitulosYDias()); String esta completo
			}		
		}	
		return 1;
	}
	

	
	/*
	 * Obtiene toda la tabla de historias en Bd y la carga al modelo 
	 * */
	public void cargarModelo() {	

		String mensajesNuevos = historiasBD.obtener();	
		MensajeEnviado msj=null;
		
		// parseo desde mensajesNuevos -> modelo Historias
		String[] listaMensajes = mensajesNuevos.split("\n");
		for(String m : listaMensajes) {
			if (!m.equals("")) {
				String[] columnasMensaje = m.split("\\|"); // tengo que "escapar" el | porque si no lo toma como REGEX (es un OR) 
				
				/*for(String s : columnasMensaje)
					System.out.print(s);*/
				
				msj = new MensajeEnviado(Integer.parseInt(columnasMensaje[0].trim()),
						new Fecha(columnasMensaje[1]),
						Integer.parseInt(columnasMensaje[2].trim()),
						columnasMensaje[3],
						columnasMensaje[4],
						columnasMensaje[4]);
						
				historias.agregarMensaje(msj);
			}
		}
	/* (Deprecated)
	 * 	// Guardo el último id generado en la BD
		if (msj!=null)
			historias.setUltimoId(msj.getId());
	*/	
		
		String usuarios= usuarioSistemaBD.obtener();
		
		String[] listaUsuarios = usuarios.split("\n");
		for(String u : listaUsuarios) {
			if (!u.equals("")) {
				String[] columnasUsuarios = u.split(",");
				usuariosSistema.agregarUsuario(Integer.parseInt(columnasUsuarios[0]),
						columnasUsuarios[1],
						columnasUsuarios[2]);
				
			}
		}
		
	}
		
	public void borrarTodasHistoriasBD() {
		historiasBD.borrarTodo();
	}
	
	public int ultimoIdHistorias() {
		return historiasBD.obtenerUltimoId();
	}
	
	

	public void agregarUsuarioSistema(int id, String perfil, String contraseña) {
		usuarioSistemaBD.alta(id, perfil, contraseña);		
	}
	public void bajaUsuarioSistema(int id) {
		usuarioSistemaBD.baja(id);
	}
	public boolean modificarUsuarioSistema(int id, String perfil, String constraseña) {
		String respuesta ="";
		boolean res=false;
		respuesta = usuarioSistemaBD.modificar(id, perfil, constraseña);
		if (!respuesta.equals("")) 
			res=true;
		
		return res;
	}
	
	public boolean eliminarMensajeEnviado(int id) {	
		boolean res=false;		
		if (historiasBD.existeId(id))
			res=historiasBD.baja(id);		
		return res;
	}
	
	
	
	
	
	
	
	
	
	
	
	/* (Deprecated)
	 * 
	 * Este metodo hace uso de la coleccion "buffer" mensajesNuevos, que realiza una copia
	 * de cada mensaje enviado
	 */
	public int persistirMensajesNuevos(Historias nuevos) {
		int persistidos=0;
		boolean ok=false;
		//borrar mensajes persistidos
		if (!nuevos.getMensajesEnviados().isEmpty()){
			//usamos iterator para poder borrar
			// Revisar si es necesario usar el iterator o se puede simplificar para que la defensa sea sencilla
			Iterator<MensajeEnviado> it = nuevos.getMensajesEnviados().iterator();
			while (it.hasNext()) {
			    MensajeEnviado mensaje = it.next();
			    ok = historiasBD.alta(
			            mensaje.getFechaEnvio(),
			            mensaje.getIdUsuario(),
			            mensaje.getCorreo(),
			            mensaje.getTitulosYDias()
			    );
			    if (ok) {
			        persistidos++;
			        it.remove();
			    } else {
			        System.out.println("Error en guardado en BD de mensaje " + mensaje.toString());
			    }
			}

		}		
		return persistidos;
	}
	/*
	 * deprecated
	 */
	public int actualizarMensajesNuevosAModelo() {	
		
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
	
	
}
