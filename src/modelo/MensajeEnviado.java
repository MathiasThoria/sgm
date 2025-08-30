package modelo;

import java.util.Map;

public class MensajeEnviado {
	private int id;	
	private Fecha fechaEnvio;
	private int idUsuario;
	private String correo;
	
	// Representa diccionario con k libro y v dias de atraso
	// k - codigoDeBarrasEjemplar
	// v - cantidad de dias de atraso a la fecha de enviado el mensaje	
	Map<String,Integer> prestamosALaFecha; 
	//almacenamos ejemplar o publicacion??	
	//consideramos almacenar individualmente los id para tener registro posterior, sin necesidad de que exista instancia.
	
	String textoMensaje; // necesario?
	
	public MensajeEnviado(int id,Fecha fechaEnvio, int idUsuario,String correo, Map<String, Integer> prestamosALaFecha,
			String textoMensaje) {
		this.id=id;
		this.fechaEnvio = fechaEnvio;
		this.idUsuario = idUsuario;
		this.correo=correo;
		this.prestamosALaFecha = prestamosALaFecha;
		this.textoMensaje = textoMensaje;
	}
	
	public void setId(int id) {
		this.id=id;
	}
	
	public int getId() {
		return this.id;
	}

	public Fecha getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Fecha fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public void setCorreo(String correo) {
		this.correo=correo;
	}
	public String getCorreo() {
		return this.correo;
	}
	
	public Map<String, Integer> getPrestamosALaFecha() {
		return prestamosALaFecha;
	}

	public void setPrestamosALaFecha(Map<String, Integer> prestamosALaFecha) {
		this.prestamosALaFecha = prestamosALaFecha;
	}

	public String getTextoMensaje() {
		return textoMensaje;
	}

	public void setTextoMensaje(String textoMensaje) {
		this.textoMensaje = textoMensaje;
	}
	
}
