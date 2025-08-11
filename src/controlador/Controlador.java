package controlador;

import logica.*;
/**
 * Responsabilidades: 
 * - orquestar entre vista y logica  
 **/
public class Controlador {	
    private ManejadorDatos manejadorDatos;
    private ManejadorMensajes manejadorMensajes;

    public Controlador() throws Exception {
        this.manejadorDatos = new ManejadorDatos();
        this.manejadorMensajes = new ManejadorMensajes();
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
    public String procesarMensajesDeUsuarios() {
    	return manejadorMensajes.procesarEnvioMensajes(manejadorDatos.getColeccionUsuario());
    }
    
}

