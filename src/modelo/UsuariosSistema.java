package modelo;

import java.util.ArrayList;

public class UsuariosSistema {
	private ArrayList<UsuarioSistema> usuariosSistema;

	public UsuariosSistema(ArrayList<UsuarioSistema> coleccionUsuario) {		
		this.usuariosSistema = coleccionUsuario;
	}
	public UsuariosSistema() {
		this.usuariosSistema = new ArrayList<UsuarioSistema>();
	}

	public ArrayList<UsuarioSistema> getColeccionUsuarioSistema() {
		return usuariosSistema;
	}

	public void setColeccionUsuarioSistema(ArrayList<UsuarioSistema> coleccionUsuario) {
		this.usuariosSistema = coleccionUsuario;
	}
	
	public void agregarUsuario(UsuarioSistema u) {
		usuariosSistema.add(u);
	}
	public void agregarUsuario(int id, String perfil, String contraseña) {
		usuariosSistema.add(new UsuarioSistema(id,perfil,contraseña));
	}
	
	public String obtenerUsuarios() {
		String resultado = "";
		
		for (UsuarioSistema u : usuariosSistema) {
			resultado+=u.toString()+ "\n";
		}		
		return resultado;		
	}
}
