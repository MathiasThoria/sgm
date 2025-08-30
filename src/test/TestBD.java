package test;
import persistencia.UsuarioSistemaBD;

import modelo.UsuarioSistema;

public class TestBD {
	
	public static void main(String[] args) throws Exception {
	
		UsuarioSistema usu1 = new UsuarioSistema(1,"biblioteca","sarasa");
		System.out.println(usu1.toString());
		// ALTA
		System.out.println(UsuarioSistemaBD.Alta(usu1.getId(),usu1.getPerfil(),usu1.getContraseña()));
		System.in.read();
		// MODIFICA
		System.out.println(UsuarioSistemaBD.Modificar(usu1.getId(),"modificado",usu1.getContraseña()));
		// BAJA
		System.out.println(UsuarioSistemaBD.Baja(usu1.getId()));
	}
	
}
