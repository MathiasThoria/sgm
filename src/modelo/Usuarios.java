package modelo;

import java.util.LinkedList;

public class Usuarios {
	LinkedList<Usuario> coleccionUsuario;

	public Usuarios(LinkedList<Usuario> coleccionUsuario) {
		super();
		this.coleccionUsuario = coleccionUsuario;
	}

	public LinkedList<Usuario> getColeccionUsuario() {
		return coleccionUsuario;
	}

	public void setColeccionUsuario(LinkedList<Usuario> coleccionUsuario) {
		this.coleccionUsuario = coleccionUsuario;
	}
	
}
