package controlador;

import logica.ManejadorDatos;
import modelo.Usuarios;

public class Controlador {	
    private ManejadorDatos manejadorDatos;

    public Controlador() throws Exception {
        manejadorDatos = new ManejadorDatos();
    }

    public Usuarios obtenerUsuariosDesdeArchivo() {
        return manejadorDatos.parserDatosToUsuarios();
    }
    public ManejadorDatos getManejadorDatos() {
    	return manejadorDatos;
    }
}

