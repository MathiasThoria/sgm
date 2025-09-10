package test;

import logica.*;
import modelo.*;

public class testManejadorDatos {

	public static void main(String[] args) throws Exception {
		ManejadorDatos md = new ManejadorDatos(new Usuarios());
		md.parserDatosToUsuarios();
		System.out.println(md.getColeccionUsuario().toString());

	}

}
