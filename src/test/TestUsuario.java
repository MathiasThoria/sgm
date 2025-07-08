package test;

import persistencia.XlsParser;
import modelo.Usuario;

public class TestUsuario {

	public static void main(String[] args) throws Exception{
		
		XlsParser parser =XlsParser.getInstancia();
    	String[] archivo = parser.getArchivoToArrString(); 
    	
    	Usuario u = new Usuario (archivo[1]);
    	System.out.println(u);
    	   	
    	
	}

}
