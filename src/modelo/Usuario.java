package modelo;

import persistencia.Datos;

public class Usuario {
	private int idUsuario;                // id_empr
	private String apellidoUsuario;       // empr_nom
	private String nombreUsuario;         // empr_prenom
	private String emailUsuario;          // empr_mail
	private String codigoBarrasUsuario;  // empr_cb
	private Prestamos listaPrestamos;
	
	public Usuario(int idUsuario, String apellidoUsuario, String nombreUsuario, String emailUsuario,
			String codigoBarrasUsuario, Prestamos listaPrestamos) {		
		this.idUsuario = idUsuario;
		this.apellidoUsuario = apellidoUsuario;
		this.nombreUsuario = nombreUsuario;
		this.emailUsuario = emailUsuario;
		this.codigoBarrasUsuario = codigoBarrasUsuario;
	}
	
	public Usuario(String fila) {
	    this.idUsuario = Datos.parseNumero(Datos.getValorFromFilaAtributo(fila, "idUsuario"));
	    this.apellidoUsuario = Datos.getValorFromFilaAtributo(fila, "apellidoUsuario");
	    this.nombreUsuario = Datos.getValorFromFilaAtributo(fila, "nombreUsuario");
	    this.emailUsuario = Datos.getValorFromFilaAtributo(fila, "emailUsuario");
	    this.codigoBarrasUsuario = Datos.getValorFromFilaAtributo(fila, "codigoBarrasUsuario");
	    
	    //Para crear un usuario es necesario por lo menos un prestamo 
	    //No es responsabilidad de Usuario si existen mas prestamos (se controla de afuera)
	    this.listaPrestamos=new Prestamos();
	    this.listaPrestamos.addPrestamo(new Prestamo(fila));	    
	}
	
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getApellidoUsuario() {
		return apellidoUsuario;
	}
	public void setApellidoUsuario(String apellidoUsuario) {
		this.apellidoUsuario = apellidoUsuario;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getEmailUsuario() {
		return emailUsuario;
	}
	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}
	public String getCodigoBarrasUsuario() {
		return codigoBarrasUsuario;
	}
	public void setCodigoBarrasUsuario(String codigoBarrasUsuario) {
		this.codigoBarrasUsuario = codigoBarrasUsuario;
	}	
	public Prestamos getListaPrestamos() {
		return this.listaPrestamos;
	}

	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", apellidoUsuario=" + apellidoUsuario + ", nombreUsuario="
				+ nombreUsuario + ", emailUsuario=" + emailUsuario + ", codigoBarrasUsuario=" + codigoBarrasUsuario
				+ ", listaPrestamos=" + listaPrestamos + "]";
	}
	
	
	
}
