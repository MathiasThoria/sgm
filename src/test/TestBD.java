package test;
import persistencia.UsuarioSistemaBD;

import modelo.UsuarioSistema;

public class TestBD {
	
	public static void main(String[] args) throws Exception {
	
		UsuarioSistema usu1 = new UsuarioSistema(2,"iblioteca","arasa");
		
		UsuarioSistemaBD usuBD= new UsuarioSistemaBD();
		
		
		System.out.println(usu1.toString());
		// ALTA
		System.out.println(usuBD.alta(usu1.getId(),usu1.getPerfil(),usu1.getContraseña()));
		
		// MODIFICA
		System.out.println(usuBD.modificar(usu1.getId(),"modificado",usu1.getContraseña()));
		// Mostrar Usuarios
		System.out.println(usuBD.mostrar());
		
		// BAJA
		System.out.println(usuBD.baja(usu1.getId()));

		
	}
	
}
