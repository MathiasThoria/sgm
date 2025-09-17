package modelo;

import java.util.LinkedList;

public class Historias {
	private LinkedList<MensajeEnviado> mensajesEnviados;
	private int ultimoId;
	
	public Historias() {
		mensajesEnviados=new LinkedList<MensajeEnviado>();
		this.ultimoId = 0;
	}
	/*Agrega mensaje a coleccion. Modifica id a autonumerico*/
	public void agregarMensaje(MensajeEnviado mensaje) {
		
		
		
		this.mensajesEnviados.add(mensaje);
	}
	
	
	public Historias buscarMensajesPorUsuario(int idUsuario) {
	    Historias resultado = new Historias();
	    for (MensajeEnviado msg : mensajesEnviados) {
	        if (msg.getIdUsuario() == idUsuario) {
	            resultado.mensajesEnviados.add(msg); // Acceso directo porque si uso agregar me modifica id (asigna automaticamente)
	        }
	    }
	    return resultado;
	}

	public MensajeEnviado buscarMensajePorId(int id) {
		for (MensajeEnviado msg : mensajesEnviados) {
			if (msg.getId() == id) {
				return msg;
			}
		}
		return null;
	}
	public LinkedList<MensajeEnviado> getMensajesEnviados(){
		return mensajesEnviados;
	}
	public int buscarUltimoId() {
		int resultado=0;
		for (MensajeEnviado msg : mensajesEnviados) {
	        if (msg.getIdUsuario() > resultado) {
	            resultado = msg.getIdUsuario(); 
	        }
	    }
	    return resultado;
	}

	public String toString() {
		String s = "";
		for (MensajeEnviado msg : mensajesEnviados) {
			s += msg.toString() + "\n";
		}
		return s;
	}
	public void borrarTodo() {
		mensajesEnviados.clear();
	}
	public void setUltimoId(int i) {
		ultimoId = i; 
	}
	
	public int getUltimoId() {
		return ultimoId;
	}
	
}
