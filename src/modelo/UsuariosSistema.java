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
	public int obtenerUltimoId() {
		int resultado = 0;		
		for (UsuarioSistema u : usuariosSistema) {
			if (resultado<u.getId())
				resultado=u.getId();
		}		
		return resultado;		
	}
	public boolean existeId(int id) {
		boolean resultado = false;		
		for (UsuarioSistema u : usuariosSistema) {
			if (id==u.getId())
				resultado=true;
		}		
		return resultado;
	}
	public UsuarioSistema obtenerUsuario(int id) {
		UsuarioSistema resultado=null;		
		for (UsuarioSistema u : usuariosSistema) {
			if (id==u.getId())
				resultado=u;
		}		
		return resultado;
	}
	
	public String obtenerUsuarioPorId(int id) {
		UsuarioSistema u = obtenerUsuario(id);
		String datos="";
		if (u!=null)
			datos=obtenerUsuario(id).toString();		
		return datos;
	}
	
	public boolean eliminarUsuario(int id) {
		boolean resultado=false;
		if (existeId(id)) {
			usuariosSistema.remove(obtenerUsuario(id));
			resultado=true;
		}
		return resultado;
	}
	public boolean modificarUsuario(int id, String perfil, String contraseña) {
		boolean resultado=false;
		if (existeId(id)) {
			if (perfil.equalsIgnoreCase("operador")||perfil.equalsIgnoreCase("administrador")) {
				obtenerUsuario(id).setPerfil(perfil);
				obtenerUsuario(id).setContraseña(contraseña);
				resultado=true;
			}			
		}
		return resultado;
	}
}
