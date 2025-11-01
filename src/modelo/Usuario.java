package modelo;



public class Usuario {
	private int idUsuario;         // id_empr
	private String apellido;       // empr_nom
	private String nombre;         // empr_prenom
	private String email;          // empr_mail
	private String codigoBarras;   // empr_cb
	private Prestamos listaPrestamos;
	
	public Usuario(int idUsuario,
			String apellidoUsuario,
			String nombreUsuario,
			String emailUsuario,
			String codigoBarrasUsuario,
			Prestamos listaPrestamos) {		
		this.idUsuario = idUsuario;
		this.apellido = apellidoUsuario;
		this.nombre = nombreUsuario;
		this.email = emailUsuario;
		this.codigoBarras = codigoBarrasUsuario;
		this.listaPrestamos = listaPrestamos;
	}
	public Usuario() {
	}

	public int getId() {
		return idUsuario;
	}
	public void setId(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellidoUsuario) {
		this.apellido = apellidoUsuario;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombreUsuario) {
		this.nombre = nombreUsuario;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String emailUsuario) {
		this.email = emailUsuario;
	}
	public String getCodigoBarras() {
		return codigoBarras;
	}
	public void setCodigoBarras(String codigoBarrasUsuario) {
		this.codigoBarras = codigoBarrasUsuario;
	}	
	public Prestamos getListaPrestamos() {
		return this.listaPrestamos;
	}
	@Override
	public String toString() {		
	    return idUsuario + "|" 
	         + apellido + "|" 
	         + nombre + "|" 
	         + email + "|" 
	         + codigoBarras + "|" 
	         + listaPrestamos + "\n";
	}
	
	/*
	 * No tiene listaDePrestamos
	 */
	public String obtenerDatosUsuario() {		
	    return idUsuario + "|" 
	         + apellido + "|" 
	         + nombre + "|" 
	         + email + "|" 
	         + codigoBarras + "\n";
	}
	public String obtenerPrestamosUsuario() {
		return getListaPrestamos().toString();
	}
}
