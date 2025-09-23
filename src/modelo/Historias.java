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
	
	public String obtenerMensajes(){
		String resultado="";
		for (MensajeEnviado msg : mensajesEnviados) {	     
            resultado+=msg.getId() + "|";
            resultado+=msg.getFechaEnvio() + "|";
            resultado+=msg.getIdUsuario() + "|";
            resultado+=msg.getCorreo() + "|";
            resultado+=msg.getTitulosYDias() + "\n";        
	    }				
		return resultado;
	}
	public String obtenerMensajesPorUsuario(int idUsuario) {
	    String resultado = "";
	    for (MensajeEnviado msg : mensajesEnviados) {
	        if (msg.getIdUsuario() == idUsuario) {
	            resultado+=msg.getId() + "|";
	            resultado+=msg.getFechaEnvio() + "|";
	            resultado+=msg.getIdUsuario() + "|";
	            resultado+=msg.getCorreo() + "|";
	            resultado+=msg.getTitulosYDias() + "\n";
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
	
	public String obtenerMensajesPorIdUsuario(int id) {
		String resultado="";
		
		for (MensajeEnviado msg : mensajesEnviados) {
			if (msg.getIdUsuario() == id) {
				resultado+=msg.toString();
			}
		}
		
		return resultado;
	}
	
	public LinkedList<MensajeEnviado> getMensajesEnviados(){
		return mensajesEnviados;
	}
	
	public int buscarUltimoId() {
		int resultado=0;
		for (MensajeEnviado msg : mensajesEnviados) {
	        if (msg.getId() > resultado) {
	            resultado = msg.getId(); 
	        }
	    }
	    return resultado;
	}

	public String toString() {
		String s = "";
		for (MensajeEnviado msg : mensajesEnviados) {
			s += msg.toString();
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
