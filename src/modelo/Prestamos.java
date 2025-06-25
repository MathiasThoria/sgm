package modelo;

import java.util.LinkedList;

public class Prestamos {
	private LinkedList<Prestamo> listaPrestamos;

	public Prestamos() {
		this.listaPrestamos= new LinkedList<>();
	}
	
	public LinkedList<Prestamo> getListaPrestamos() {
		return listaPrestamos;
	}

	public void setListaPrestamos(LinkedList<Prestamo> listaPrestamos) {
		this.listaPrestamos = listaPrestamos;
	}
	
	public void addPrestamo(Prestamo p){
		listaPrestamos.add(p);
	}
	
	public String toString() {
		return listaPrestamos.toString();	
	}
	
}
