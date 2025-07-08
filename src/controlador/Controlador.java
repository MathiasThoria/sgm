package controlador;

import logica.ManejadorDatos;
/**
 * Responsabilidades: 
 * - orquestar entre vista y logica  
 **/
public class Controlador {	
    private ManejadorDatos manejadorDatos;

    public Controlador() throws Exception {
        manejadorDatos = new ManejadorDatos();
    }

    public void actualizarUsuariosDesdeArchivo() {
        manejadorDatos.parserDatosToUsuarios();
    }   

    public String obtenerUsuariosComoString() {
    	actualizarUsuariosDesdeArchivo();
    	return manejadorDatos.obtenerUsuariosComoString();
    }
    public String obtenerUsuarioPorIdComoString(int id) {      
    	actualizarUsuariosDesdeArchivo();
    	return manejadorDatos.obtenerUsuarioPorIdComoString(id);    	
    }
    
}

