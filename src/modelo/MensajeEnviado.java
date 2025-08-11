package modelo;

public class MensajeEnviado {
	Usuario usuario;
	String textoMensaje;
	Fecha fechaEnvio;
	
	
	public MensajeEnviado(Usuario usuario, String textoMensaje, Fecha fechaEnvio) {		
		this.usuario = usuario;
		this.textoMensaje = textoMensaje;
		this.fechaEnvio = fechaEnvio;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public String getTextoMensaje() {
		return textoMensaje;
	}
	public void setTextoMensaje(String textoMensaje) {
		this.textoMensaje = textoMensaje;
	}
	public Fecha getFechaEnvio() {
		return fechaEnvio;
	}
	public void setFechaEnvio(Fecha fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}
	
}
