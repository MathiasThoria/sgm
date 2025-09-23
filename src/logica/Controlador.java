package logica;
import modelo.*;
/**
 * Responsabilidades: 
 * - orquestar entre vista y modelo
 * - mantener instancias de modelo
 * - mantener instancias de manejadores
 * 
 * Los manejadores tienen inyeccion de dependencia (le "paso" usuarios y historias para que lo tnegan como atributo)
 * Al instanciar el controlador se actualiza el modelo desde BD y xls
 **/
public class Controlador {	
    private ManejadorDatos manejadorDatos;
    private ManejadorMensajes manejadorMensajes;
    private ManejadorBD manejadorBD;
    private Historias historias;
    private Usuarios usuarios;
    private UsuariosSistema usuariosSistema;
    
    public Controlador() throws Exception {
        
        this.historias= new Historias();
        this.usuarios = new Usuarios();
        this.usuariosSistema = new UsuariosSistema();
        this.manejadorDatos = new ManejadorDatos(usuarios);
        this.manejadorMensajes = new ManejadorMensajes(historias);
        this.manejadorBD = new ManejadorBD(usuariosSistema,historias);        
        
        // Esto podria estar en cada manejador, pero para que sea explicito:
        
        actualizarModeloDesdeDatosXls();
        
        actualizarModeloDesdeBD(); 
    }
   
   
    public String obtenerUsuarios() {    	
    	return usuarios.toString();
    }
    
    public String obtenerUsuarioPorId(int id) {   	
    	return usuarios.obtenerUsuarioPorId(id);    	
    }
    
    public int procesarMensajesDeUsuarios() {
    	/*hacer metodo en BD para traer ultimo id
    	 * recorrer mensajes a partir de ese id
    	 * persisitir > a ultimo
    	 * */
    	int res= manejadorMensajes.procesarEnvioMensajes(manejadorDatos.getColeccionUsuario());
    	if ( res > 0 )
    		//manejadorBD.persistirMensajesNuevos(manejadorMensajes.getMensajesSinPersistir()); 
    		manejadorBD.persistirMensajesNuevos();
    	return res;
    }
    
    public String obtenerHistoricoMensajes() {    	
    	return historias.toString();
    }
    
    public String obtenerHistoricoMensajesPorIdUsuario(int id) {    	
    	return historias.obtenerMensajesPorIdUsuario(id);
    }
    
    public void borrarTodasHistoriasBD() {
    	manejadorBD.borrarTodasHistoriasBD();
    	
    }
    public void actualizarModeloDesdeBD() {
    	manejadorBD.cargarModelo();
    }
    public void actualizarModeloDesdeDatosXls() {
    	manejadorDatos.cargarModelo();
    }
    public void borrarModelo() {
    	historias.borrarTodo();
    }
    /*public String obtenerUsuariosDelSistema() {
    	//usuariosDelSistema.
    }*/
}

