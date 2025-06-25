package test;

import persistencia.Datos;
import modelo.Usuario;

public class TestUsuario {

	public static void main(String[] args) throws Exception{
		
		Datos parser =Datos.getInstancia();
    	String[] archivo = parser.getArchivoToArrString(); 
    	
    	Usuario u = new Usuario (archivo[1]);
    	System.out.println(u);
    	   	
    	
	}

}
