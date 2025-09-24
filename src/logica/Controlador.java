package logica;
import modelo.*;
import persistencia.HistoriasBD;
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
    public boolean existeUsuario(int id) {    	
    	return usuarios.existeIdUsuario(id);    
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
    	historias.borrarTodo();
    	
    }
    public void actualizarModeloDesdeBD() {
    	manejadorBD.cargarModelo();
    }
    public void actualizarModeloDesdeDatosXls() {
    	manejadorDatos.cargarModelo();
    }

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
    public boolean eliminarMensajeEnviado(int id) {
    	boolean res=false;
    	if (historias.eliminarMensajeEnviado(id) && manejadorBD.eliminarMensajeEnviado(id))
    		res=true;
    	return res;
    }
    public boolean verificarContraseñaUsuarioSistema(int id, String contraseña) {
		return manejadorBD.verificarContraseñaUsuarioSistema(id,contraseña);		
	}
    public String obtenerPerfilUsuarioSistema(int id) {
    	return manejadorBD.obtenerPerfilUsuarioSistema(id);
    }
    
    public String obtenerConstancia(int id) {
    	
    	Fecha fecha = new Fecha();
    	fecha.establecerComoHoy();
    	
    	String constancia = "Constancia de No Deudor\n\n" +
                "Por la presente se certifica que el Sr./Sra. ______________________     " +  
                " no registra deudas pendientes a la fecha " + fecha + ".\n\n" +
                "Atentamente,\n" +
                "I.N.E.T.";
    	
    	return constancia;
    }
    
}

