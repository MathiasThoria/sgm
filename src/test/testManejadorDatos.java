package test;

import logica.ManejadorDatos;

public class testManejadorDatos {

	public static void main(String[] args) throws Exception {
		ManejadorDatos md = new ManejadorDatos();
		md.parserDatosToUsuarios();
		System.out.println(md.getColeccionUsuario().toString());

	}

}
