package modelo;

import java.util.LinkedList;

public class Historias {
	LinkedList<MensajeEnviado> mensajesEnviados;
	
	public Historias() {
		mensajesEnviados=new LinkedList<MensajeEnviado>();
	}
	/*Agrega mensaje a coleccion. Modifica id a autonumerico*/
	public void agregarMensaje(MensajeEnviado mensaje) {
		mensaje.setId(this.mensajesEnviados.size()+1);
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

	public String toString() {
		String s = "";
		for (MensajeEnviado msg : mensajesEnviados) {
			s += msg.toString() + "\n";
		}
		return s;
	}
}
