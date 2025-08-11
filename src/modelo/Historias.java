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
	
}
