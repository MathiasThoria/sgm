package logica;
import modelo.*;
import persistencia.HistoriasBD;
/**
 * Responsabilidades: 
 * - logica entre vista y modelo
 * - mantener instancias de modelo
 * - mantener instancias de manejadores
 * - logica entre modelo y persistencia. 
 * 
 * Manejadores: 
 *  - centralizan operaciones con persistencia (BD y XLS) y mensajeria
 *  - Los manejadores tienen inyeccion de dependencia (le "paso" usuarios y historias para que lo tnegan como atributo)
 *    Al instanciar el controlador se actualiza el modelo desde BD y xls
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
   
    // ------ MODELO ---------- // 
    public String obtenerUsuarios() {    	
    	return usuarios.obtenerDatosUsuarios();
    }
    
    public String obtenerLibrosDeUsuarioPorId(int id) {   	
    	return usuarios.obtenerLibrosDeUsuario(id);    	
    }
    public boolean existeUsuario(int id) {    	
    	return usuarios.existeIdUsuario(id);    
    }
    public String obtenerHistoricoMensajes() {    	
    	return historias.toString();
    }    
    public String obtenerHistoricoMensajesPorIdUsuario(int id) {    	
    	return historias.obtenerMensajesPorIdUsuario(id);
    }
    public String obtenerUsuariosDelSistema() {
    	return usuariosSistema.obtenerUsuarios();
    }
    public String obtenerUsuarioDelSistema(int id) {
    	return usuariosSistema.obtenerUsuarioPorId(id);
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
    // ---------- MENSAJES ------------//
    public String procesarMensajesDeUsuarios() {    	
        String res = manejadorMensajes.procesarEnvioMensajes(manejadorDatos.getColeccionUsuario());
        
        // Si se devolvieron mensajes, persistirlos
        if (!res.isEmpty()) {
            manejadorBD.persistirMensajesNuevos();
        }
        return res;
    }
    
    
    // ------------ BD ----------------//
    public void borrarTodasHistoriasBD() {
    	manejadorBD.borrarTodasHistoriasBD();
    	historias.borrarTodo();
    	
    }
    public void actualizarModeloDesdeBD() {
    	manejadorBD.cargarModelo();
    }
    public boolean verificarContraseñaUsuarioSistema(int id, String contraseña) {
		return manejadorBD.verificarContraseñaUsuarioSistema(id,contraseña);		
	}    
    public String obtenerPerfilUsuarioSistema(int id) {
    	return manejadorBD.obtenerPerfilUsuarioSistema(id);
    }
    
    
    // ------------- DATOS XLS ------------//
    
    public void actualizarModeloDesdeDatosXls() {
    	
    	if (manejadorDatos.getArchivoXls().getCadena().isEmpty())
    		System.out.println("Advertencia:Archivo Xls vacío. Se asume que no hay deudores."); 
    	manejadorDatos.cargarModelo();
    }    
    
    
    // -------------- MODELO Y BD ----------------//
    /* 
     * id autonimerico manejado por el sistema
     */
    public String altaUsuarioSistema(String perfil, String contraseña) {    	
    	String confirmacion="";
    	int id=usuariosSistema.obtenerUltimoId()+1;    	
    	
    	//Antes de agregar comprueba existencia de id y los dos tipos de perfiles posibles: operador y administrador
    	if (usuariosSistema.existeId(id)) {
    		confirmacion="Ya existe esa id";
    	}else {
    		// esto deberia ser un enum
    		if (perfil.equalsIgnoreCase("administrador")||perfil.equalsIgnoreCase("operador")) {
    			usuariosSistema.agregarUsuario(id,perfil,contraseña);
            	manejadorBD.agregarUsuarioSistema(id,perfil,contraseña);
            	confirmacion="Usuario agregado:"
            			+ "\nId:" + id 
            			+ "\nPerfil:" + perfil
            			+ "\nContraseña:" + contraseña;
            			
    		}else {
    			confirmacion="El perfil debe ser administrador u operador. Intente nuevamente.";
    		}
    			
    	}
    	
    	return confirmacion;
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
    public ManejadorDatos getManejadorDatos() {
        return manejadorDatos;
    }

    public void setManejadorDatos(ManejadorDatos manejadorDatos) {
        this.manejadorDatos = manejadorDatos;
    }

    public ManejadorMensajes getManejadorMensajes() {
        return manejadorMensajes;
    }

    public void setManejadorMensajes(ManejadorMensajes manejadorMensajes) {
        this.manejadorMensajes = manejadorMensajes;
    }

    public ManejadorBD getManejadorBD() {
        return manejadorBD;
    }

    public void setManejadorBD(ManejadorBD manejadorBD) {
        this.manejadorBD = manejadorBD;
    }

    public Historias getHistorias() {
        return historias;
    }

    public void setHistorias(Historias historias) {
        this.historias = historias;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    public UsuariosSistema getUsuariosSistema() {
        return usuariosSistema;
    }

    public void setUsuariosSistema(UsuariosSistema usuariosSistema) {
        this.usuariosSistema = usuariosSistema;
    }
}

