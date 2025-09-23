package modelo;

import java.util.Map;

public class MensajeEnviado {
	private int id;	//es la unica forma de distinguir un mensaje de otro, si es que se puedee mandar varios mail por dia
	private Fecha fechaEnvio;
	private int idUsuario;
	private String correo;	

	private String titulosYDias; 
	
	//private String textoMensaje; // necesario?
	
	public MensajeEnviado(int id, 
			Fecha fechaEnvio, 
			int idUsuario, 
			String correo,
			String titulosYDias,
			String textoMensaje) {
		this.id=id;
		this.fechaEnvio = fechaEnvio;
		this.idUsuario = idUsuario;
		this.correo=correo;
		this.titulosYDias = titulosYDias;
		//this.textoMensaje = textoMensaje;
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
	
	public String getTitulosYDias() {
		return titulosYDias;
	}
	public void setTitulosYDias(String tyd) {
		this.titulosYDias=tyd;
	}
	@Override
	public String toString() {
	    return id + "|" 
	         + fechaEnvio + "|" 
	         + idUsuario + "|" 
	         + correo + "|" 
	         + titulosYDias + "\n";	         
	}

}
