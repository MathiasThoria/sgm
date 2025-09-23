package modelo;

public class UsuarioSistema {
	private int id;
	private String perfil; 
	private String contraseña;
	
	public UsuarioSistema(int id, String perfil, String contraseña) {		
		this.id = id;
		this.perfil = perfil;
		this.contraseña = contraseña;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	@Override
	public String toString() {
		return id + "|" + perfil + "|" + contraseña + "\n";
	}
}
