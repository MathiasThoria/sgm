package modelo;

import java.util.ArrayList;

public class UsuariosSistema {
	private ArrayList<Usuario> usuariosSistema;

	public UsuariosSistema(ArrayList<Usuario> coleccionUsuario) {		
		this.usuariosSistema = coleccionUsuario;
	}
	public UsuariosSistema() {
		this.usuariosSistema = new ArrayList<Usuario>();
	}

	public ArrayList<Usuario> getColeccionUsuarioSistema() {
		return usuariosSistema;
	}

	public void setColeccionUsuarioSistema(ArrayList<Usuario> coleccionUsuario) {
		this.usuariosSistema = coleccionUsuario;
	}
	
	public void agregarUsuario(Usuario u) {
		usuariosSistema.add(u);
	}
}
