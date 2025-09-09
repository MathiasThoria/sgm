package logica;

/**
 * Responsabilidades: 
 * - orquestar entre vista y logica
 * 
 *   Por ahora la actualizacion del archiov xls al modelo se hace 1 vez al inciiar el programa (en constructor)
 **/
public class Controlador {	
    private ManejadorDatos manejadorDatos;
    private ManejadorMensajes manejadorMensajes;
    private ManejadorBD manejadorBD;

    public Controlador() throws Exception {
        this.manejadorDatos = new ManejadorDatos();
        this.manejadorMensajes = new ManejadorMensajes();
        this.manejadorBD = new ManejadorBD();
        // Esto podria estar en cada manejador, pero para que sea explicito:
        actualizarUsuariosDesdeArchivo();
        actualizarHistoriasDesdeBD();
    }

    public void actualizarUsuariosDesdeArchivo() {
        manejadorDatos.parserDatosToUsuarios();
    }   
    public void actualizarHistoriasDesdeBD() {
    	manejadorBD.actualizarModelo();
    }
    public String obtenerUsuariosComoString() {
    	
    	return manejadorDatos.obtenerUsuariosComoString();
    }
    public String obtenerUsuarioPorIdComoString(int id) {      
    	
    	return manejadorDatos.obtenerUsuarioPorIdComoString(id);    	
    }
    public int procesarMensajesDeUsuarios() {
    	/*
    	 * Para persistir los nuuevos mensajes tenemos 3 opciones:
    	 * 1.Ejecutar UPDATE con mensajes ya exisitentes y nuevos, BD niega los duplicados por id.
    	 * 2.Consultar BD y ejecutar UPDATE de diferencia
    	 * 3.Hacer la solicitud de persisitencia dentro de ManejadorMensajes (mala separacion de responsabilidades, mejor en controlador)
    	 * 4.Devolver desde el proceso procesarEnvioMensajes un Historial con mensajes nuevos. (mayor acoplamiento)
    	 * 5.Almacenar en ManejadorMensajes un atributo de tipo Historial que contenga mensajes nuevos.
    	 *     	 *
    	 * Se opta por la ultima que conserva separacion de responsabilidades y comunicacion limpia en capa. Dificultad: puede 
    	 * ser confuso mantener un estado en manejadorDeMensajes.
    	 * 
    	 * 6. Posibilidad de manejar id null de cada nuevo mensaje en Historias como filtro.
    	 * (id es null en nuevos porque lo maneja autoincremental la BD) 
    	*/
    	int res= manejadorMensajes.procesarEnvioMensajes(manejadorDatos.getColeccionUsuario());
    	if ( res > 0 )
    		manejadorBD.persistirMensajesNuevos(manejadorMensajes.getMensajesSinPersistir()); //mensaje persistido->mensaje borrado de mensajesSinPersistir    	
    	return res;
    }
    public String obtenerHistoricoMensajes() {
    	
    	return manejadorMensajes.obtenerHistoricoMensajes();
    }
    
    public String obtenerHistoricoMensajesPorIdUsuario(int id) {
    	
    	return manejadorMensajes.obtenerMensajesPorIdUsuario(id);
    }
    
}

