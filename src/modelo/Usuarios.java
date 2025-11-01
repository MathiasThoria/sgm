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
	public String obtenerUsuarioPorId(int id) {
		Usuario u = obtenerUsuario(id);
		String datos="";
		if (u!=null)
			datos = u.toString();
		else
			datos = "No encontrado";
		return datos;
	}
	
	private Usuario obtenerUsuario(int id) {
		Usuario resultado = new Usuario();
		boolean encontrado=false;
		for (Usuario u : coleccionUsuario) {
			if (u.getId() == id) {
				resultado=u;
				encontrado = true;
			}
		}
		if (!encontrado)
			resultado=null;
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
	
	public String obtenerLibrosDeUsuario(int id) {
		Usuario u = obtenerUsuario(id);
		String datos;
		if (u!=null)
			datos=u.obtenerPrestamosUsuario();
		else
			datos = "Usuario no encontrado.";
		return datos;
	}
	
	public void borrarTodo() {
		coleccionUsuario.clear();
	}
	
	public void agregarPrestamoAUsuario(int id, Prestamo prestamo) {
		obtenerUsuario(id).getListaPrestamos().agregarPrestamo(prestamo);
	}
}
