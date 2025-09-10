package modelo;

import java.util.LinkedList;

public class Usuarios {
	private LinkedList<Usuario> coleccionUsuario;

	public Usuarios(LinkedList<Usuario> coleccionUsuario) {		
		this.coleccionUsuario = coleccionUsuario;
	}
	public Usuarios() {
		this.coleccionUsuario = new LinkedList<Usuario>();
	}

	public LinkedList<Usuario> getColeccionUsuario() {
		return coleccionUsuario;
	}

	public void setColeccionUsuario(LinkedList<Usuario> coleccionUsuario) {
		this.coleccionUsuario = coleccionUsuario;
	}
	
	public void agregarUsuario(Usuario u) {
		coleccionUsuario.add(u);
	}
	public boolean existeIdUsuario(int id) {
		for (Usuario u : coleccionUsuario) {
			if (u.getId() == id)
				return true;
		}
		return false;
	}
	public Usuario buscarUsuario(int id) {
		for (Usuario u : coleccionUsuario) {
			if (u.getId() == id)
				return u;
		}
		return null;
	}
	
	public String toString() {
		 String s="";    	 
         for (Usuario u : coleccionUsuario) {
             s+="Usuario: " + u.getNombre() + " | ID: " + u.getId();
             
             for (Prestamo p : u.getListaPrestamos().getListaPrestamos()) {
            	 s+=" \n  → " + p.getTituloObra() + " (" + p.getFechaPrestamo() + " → " + p.getFechaDevolucion() + ")";
             }
             s+="\n";
         }
         return s;
		
	}
	public void borrarTodo() {
		coleccionUsuario.clear();
	}
}
