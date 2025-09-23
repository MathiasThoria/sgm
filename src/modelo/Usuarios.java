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
	public String buscarUsuario(int id) {
		String resultado="";
		for (Usuario u : coleccionUsuario) {
			if (u.getId() == id)
				resultado=u.toString();
		}
		return resultado;
	}
	
	private Usuario obtenerUsuario(int id) {
		Usuario resultado = new Usuario();
		for (Usuario u : coleccionUsuario) {
			if (u.getId() == id)
				resultado=u;
		}
		return resultado;
	}
	
	public String toString() {
		 String s="";    	 
         for (Usuario u : coleccionUsuario) {
             s+= u.toString();             
         }
         return s;
		
	}
	public String obtenerDatosUsuarios() {
		String s="";    	 
        for (Usuario u : coleccionUsuario) {
            s+= u.obtenerDatosUsuario();            
        }
        return s;
	}
	
	public void borrarTodo() {
		coleccionUsuario.clear();
	}
	
	public void agregarPrestamoAUsuario(int id, Prestamo prestamo) {
		obtenerUsuario(id).getListaPrestamos().agregarPrestamo(prestamo);
	}
}
