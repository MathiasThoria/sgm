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
    private ManejadorBD manejadorBD; //aca estan las instancias de modeloBD
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
        
        actualizarModeloDesdeDatosXls();        
        actualizarModeloDesdeBD(); 
    }
   
   
    public String obtenerUsuarios() {    	
    	return usuarios.obtenerDatosUsuarios();
    }
    
    public String obtenerUsuarioPorId(int id) {   	
    	return usuarios.obtenerLibrosDeUsuario(id);    	
    }
    
    public int procesarMensajesDeUsuarios() {    	
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
    
    public String obtenerUsuariosDelSistema() {
    	return usuariosSistema.obtenerUsuarios();
    }
    public void altaUsuarioSistema(int id, String perfil, String contraseña) {    	
    	usuariosSistema.agregarUsuario(id,perfil,contraseña);
    	manejadorBD.agregarUsuarioSistema(id,perfil,contraseña);
    }
    public boolean eliminarUsuarioSistema(int id) {
    	boolean res=false;
    	if (usuariosSistema.existeId(id)) {
    		usuariosSistema.eliminarUsuario(id);
    		manejadorBD.bajaUsuarioSistema(id);
    		res=true;
    	}
    	return res;
    }
    public String obtenerUsuarioDelSistema(int id) {
    	return usuariosSistema.obtenerUsuarioPorId(id);
    }
    public boolean modificarUsuarioDelSistema(int id, String perfil, String contraseña) {
    	boolean res=false;
    	
    	if (usuariosSistema.modificarUsuario(id,perfil,contraseña) && 
    			manejadorBD.modificarUsuarioSistema(id,perfil,contraseña))
    		res=true;
    	return res;
    }
    
}

