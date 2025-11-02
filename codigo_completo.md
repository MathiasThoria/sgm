# Código fuente completo del proyecto

## Archivo: src/logica/Controlador.java
```java
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
    
    public String obtenerUsuarioPorId(int id) {   	
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
    public int procesarMensajesDeUsuarios() {    	
    	int res= manejadorMensajes.procesarEnvioMensajes(manejadorDatos.getColeccionUsuario());
    	if ( res > 0 )
    		//deprecated
    		//manejadorBD.persistirMensajesNuevos(manejadorMensajes.getMensajesSinPersistir()); 
    		manejadorBD.persistirMensajesNuevos();
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
    
}

```

## Archivo: src/logica/ManejadorBD.java
```java
package logica;

import java.util.ArrayList;
import java.util.Iterator;



import modelo.Fecha;
import modelo.Historias;
import modelo.Usuarios;
import modelo.UsuariosSistema;
import modelo.MensajeEnviado;
import persistencia.HistoriasBD;
import persistencia.UsuarioSistemaBD;

public class ManejadorBD {
	private Historias historias;
	private HistoriasBD historiasBD;
	private UsuariosSistema usuariosSistema;
	private UsuarioSistemaBD usuarioSistemaBD;
	
	public ManejadorBD(UsuariosSistema usuariosSistema, Historias historias) {
		this.historiasBD= new HistoriasBD();
		this.usuarioSistemaBD = new UsuarioSistemaBD();
		this.usuariosSistema=usuariosSistema;
		this.historias=historias;
		
	}	
	
	/*
	 * Persiste todos los mensajes posteriores al ultimo id de BD
	 * */
	public int persistirMensajesNuevos() {	
		int ultimo = ultimoIdHistorias();	
		for(MensajeEnviado m : historias.getMensajesEnviados()) {			
			if (m.getId()>ultimo) {				
				historiasBD.alta(
			            m.getFechaEnvio(),
			            m.getIdUsuario(),
			            m.getCorreo(),
			            m.getTitulosYDias()
			    );
			
			}		
		}	
		return 1;
	}
	

	
	/*
	 * Obtiene toda la tabla de historias en Bd y la carga al modelo 
	 * */
	public void cargarModelo() {	

		String mensajesNuevos = historiasBD.obtener();	
		MensajeEnviado msj=null;
		
		// parseo desde mensajesNuevos -> modelo Historias
		String[] listaMensajes = mensajesNuevos.split("\n");
		for(String m : listaMensajes) {
			if (!m.equals("")) {
				String[] columnasMensaje = m.split("\\|"); // tengo que "escapar" el | porque si no lo toma como REGEX (es un OR) 

				msj = new MensajeEnviado(Integer.parseInt(columnasMensaje[0].trim()),
						new Fecha(columnasMensaje[1]),
						Integer.parseInt(columnasMensaje[2].trim()),
						columnasMensaje[3],
						columnasMensaje[4],
						columnasMensaje[4]);
						
				historias.agregarMensaje(msj);
			}
		}
		
	/* (Deprecated)
	 * 	// Guardo el último id generado en la BD
		if (msj!=null)
			historias.setUltimoId(msj.getId());
	*/	
		
		String usuarios= usuarioSistemaBD.obtener();
		
		String[] listaUsuarios = usuarios.split("\n");
		for(String u : listaUsuarios) {
			if (!u.equals("")) {
				String[] columnasUsuarios = u.split("\\|");
				usuariosSistema.agregarUsuario(Integer.parseInt(columnasUsuarios[0]),
						columnasUsuarios[1],
						columnasUsuarios[2]);
				
			}
		}
		
	}
		
	public void borrarTodasHistoriasBD() {
		historiasBD.borrarTodo();
	}
	
	public int ultimoIdHistorias() {
		return historiasBD.obtenerUltimoId();
	}
	
	

	public void agregarUsuarioSistema(int id, String perfil, String contraseña) {
		usuarioSistemaBD.alta(id, perfil, contraseña);		
	}
	public void bajaUsuarioSistema(int id) {
		usuarioSistemaBD.baja(id);
	}
	public boolean modificarUsuarioSistema(int id, String perfil, String constraseña) {
		String respuesta ="";
		boolean res=false;
		respuesta = usuarioSistemaBD.modificar(id, perfil, constraseña);
		if (!respuesta.equals("")) 
			res=true;
		
		return res;
	}
	
	public boolean eliminarMensajeEnviado(int id) {	
		boolean res=false;		
		if (historiasBD.existeId(id))
			res=historiasBD.baja(id);		
		return res;
	}
	public String obtenerPerfilUsuarioSistema(int id) {
		return usuarioSistemaBD.obtenerPerfil(id);
	}
	
	
	
	
	
	
	
	
	
	
	/* (Deprecated)
	 * 
	 * Este metodo hace uso de la coleccion "buffer" mensajesNuevos, que realiza una copia
	 * de cada mensaje enviado
	 */
	public int persistirMensajesNuevos(Historias nuevos) {
		int persistidos=0;
		boolean ok=false;
		//borrar mensajes persistidos
		if (!nuevos.getMensajesEnviados().isEmpty()){
			//usamos iterator para poder borrar
			// Revisar si es necesario usar el iterator o se puede simplificar para que la defensa sea sencilla
			Iterator<MensajeEnviado> it = nuevos.getMensajesEnviados().iterator();
			while (it.hasNext()) {
			    MensajeEnviado mensaje = it.next();
			    ok = historiasBD.alta(
			            mensaje.getFechaEnvio(),
			            mensaje.getIdUsuario(),
			            mensaje.getCorreo(),
			            mensaje.getTitulosYDias()
			    );
			    if (ok) {
			        persistidos++;
			        it.remove();
			    } else {
			        System.out.println("Error en guardado en BD de mensaje " + mensaje.toString());
			    }
			}

		}		
		return persistidos;
	}
	/*
	 * deprecated
	 */
	public int actualizarMensajesNuevosAModelo() {	
		
		//para verificar que solo traigo mensajes que no estan en memoria, obtengo solo los posteriores al ultimo id en memoria.
		String mensajesNuevos = historiasBD.obtenerMayorDeId(historias.buscarUltimoId());
		
		//System.out.println(mensajesNuevos);
		
		// parseo desde mensajesNuevos -> modelo Historias
		String[] listaMensajes = mensajesNuevos.split("\n");
		for(String m : listaMensajes) {
			if (!m.equals("")) {
				String[] columnasMensaje = m.split(","); 
				MensajeEnviado msj = new MensajeEnviado(Integer.parseInt(columnasMensaje[0].trim()),
						new Fecha(columnasMensaje[1]),
						Integer.parseInt(columnasMensaje[2].trim()),
						columnasMensaje[3],
						columnasMensaje[4],
						columnasMensaje[4]);
						
				historias.agregarMensaje(msj);
			}
		}		
		return 1;
	}
	public boolean verificarContraseñaUsuarioSistema(int id, String contraseña) {
		return usuarioSistemaBD.verificarPass(id,contraseña);		
	}
	
}
```

## Archivo: src/logica/ManejadorDatos.java
```java
package logica;
import persistencia.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import modelo.*;

/**
 * Responsabilidades: 
 * - Consumir el String devuelto por ServicioDatos para convertirlo en instancias del Modelo
 * - 
 */
public class ManejadorDatos {
	private Usuarios coleccionUsuario;
	private ServicioDatos archivoXls;	
	
	
	public ManejadorDatos(Usuarios usuarios) throws Exception {
		archivoXls = new ServicioDatos();//queda en cadena el archivo cargado
		
		coleccionUsuario = usuarios;	
		
	}
	
	public Usuarios getColeccionUsuario() {
		return coleccionUsuario;
	}
	
	public Usuarios parserDatosToUsuarios() {
			
		String datosCrudos = archivoXls.getCadena();
	    String[] filas = datosCrudos.split(";");
	   
	    boolean encabezado=true;
	    for (String fila : filas) {
	        if (encabezado) {	        
	        	encabezado=false;
	        }else {	
	        
		        String[] campos = fila.split(",");      
	            try {	            
		        	
					// falta chequear que campos no esten vaciois porq sino tira
					// Exception en los cambios de tipo 			
	            	
		            Usuario usuario = new Usuario(
		            		parseNumero(campos[3]), //id
		            		campos[4], //apellido
		            		campos[5], //nombre
		            		campos[6], //email
		            		campos[8], //coidgoBarras (ci)
		            		new Prestamos());
	
		            // Parsear datos del préstamo
		            
		            Prestamo prestamo = new Prestamo(
		            		new Fecha(campos[0]), 
		            		new Fecha(campos[1]), 
		            		(int)Double.parseDouble(campos[2]),
		            		campos[8],
		            		campos[9],
		            		parseNumero(campos[10]),
		            		parseNumero(campos[11]),
		            		parseNumero(campos[12]),
		            		campos[14],
		            		campos[15],
		            		Boolean.parseBoolean(campos[16]));
	
		            // Si el usuario no existe agrega a coleccion
		            // Si el usuario existe solo agrega prestamo		            
			            
		            if (!coleccionUsuario.existeIdUsuario(usuario.getId())) {
		            	usuario.getListaPrestamos().agregarPrestamo(prestamo);		            
		            	coleccionUsuario.agregarUsuario(usuario);
		            }else
		            	coleccionUsuario.agregarPrestamoAUsuario(usuario.getId(),prestamo);
	
		        } catch (Exception e) {
		            System.out.println("Error al procesar fila: " + fila);
		            e.printStackTrace();
		        }

	        }
	    }

	    return coleccionUsuario;

	}
	
	
	public LocalDate parseFecha(String fechaStr) {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    //System.out.println(fechaStr);
	    try {
	        return LocalDate.parse(fechaStr, formatter);
	    } catch (DateTimeParseException e) {
	        e.printStackTrace();
	        return null; 
	    }	    
	}
	
	public int parseNumero(String valor) {
				
		//cuando una celda viene vacia no es "" sino " "
		if(!valor.equals(" ")) {			
			return (int)Double.parseDouble(valor);
		}else {
			return 0;
		}
			
	}
	
	 public String obtenerUsuariosComoString() {    	 
    	return coleccionUsuario.obtenerDatosUsuarios();
    }
	 
    public String obtenerUsuarioPorIdComoString(int id) {      
    	String s="";
        String encontrado = coleccionUsuario.obtenerUsuarioPorId(id);
        if (!encontrado.isEmpty()) {
            s=encontrado;
        } else {
            s="Usuario no encontrado.";
        } 
        return s;
    }
	public void cargarModelo() {
		parserDatosToUsuarios();
	}
	
	public ServicioDatos getArchivoXls() {
		return archivoXls;
	}
	public void setArchivoXls(ServicioDatos s) {
		this.archivoXls = s;
	}
	
}
```

## Archivo: src/logica/ManejadorMensajes.java
```java
package logica;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import modelo.*;
import persistencia.ServicioMensajeria;

/**
 * Responsabilidades:
 * - Procesar usuarios deudores y enviar mensajes
 * - Generar plantilla de texto de mensajes automáticamente
 * - Coordinar con ServicioMensajeria
 * - Registrar historial de mensajes enviados
 */


public class ManejadorMensajes {
    private Historias historialMensajes;    
    private Historias mensajesSinPersistir; // este atributo sirve para actualizar la BD. luego de realizar persistencia se vacia.
    private ServicioMensajeria sm;
    
    public ManejadorMensajes(Historias historias) {
        this.historialMensajes = historias;
        this.sm = new ServicioMensajeria();
        this.mensajesSinPersistir = new Historias();
    }
    
    public Historias getHistorialMensajes() {
        return historialMensajes;
    }
    
    /**
     * Método principal: procesa todos los usuarios y envía mensajes
     * @param usuarios Colección de usuarios deudores
     * @return Resumen de envíos realizados
     */ 
    public int procesarEnvioMensajes(Usuarios usuarios) {
        String resultado = "=== PROCESO DE ENVÍO DE MENSAJES ===\n\n";
        
        int enviados = 0;
        int errores = 0;
        
        for (Usuario usuario : usuarios.getColeccionUsuario()) {
            try {
                // Calcular días de retraso para cada préstamo -- en caso que retard no funcione
            	// comprueba que xls tenga retard actualizado
                String titulosYDias = calcularPrestamosAtrasados(usuario);
                
                // Solo enviar si tiene préstamos atrasados
                if (!titulosYDias.isEmpty()) {
                    // Generar texto del mensaje
                    String textoMensaje = generarTextoMensaje(usuario, titulosYDias);
                    
                    // Intentar envío
                    //boolean enviado = this.sm.enviar(textoMensaje, usuario.getEmail());
                    boolean enviado=true;
                    if (enviado) {                        
                    	
                    	historialMensajes.setUltimoId(historialMensajes.getUltimoId()+1);
                    	
                        MensajeEnviado mensaje = new MensajeEnviado(
                            historialMensajes.buscarUltimoId()+1, 
                            obtenerFechaActual(),
                            usuario.getId(),
                            usuario.getEmail(),
                            titulosYDias,
                            textoMensaje
                        );
                        
                        historialMensajes.agregarMensaje(mensaje);
                        mensajesSinPersistir.agregarMensaje(mensaje);
                        
                        resultado += " ENVIADO: " + usuario.getNombre() + 
                                   " (" + usuario.getEmail();
                        enviados++;
                        
                    } else {
                        resultado += " ERROR: No se pudo enviar a " + usuario.getNombre() + 
                                   " (" + usuario.getEmail() + ")\n";
                        errores++;
                    }
                } else {
                    resultado += " SIN ATRASOS: " + usuario.getNombre() + 
                               " - No tiene préstamos vencidos\n";
                }
                
            } catch (Exception e) {
                resultado += " EXCEPCIÓN: Error procesando usuario " + usuario.getNombre() + 
                           " - " + e.getMessage() + "\n";
                errores++;
            }
        }
        System.out.println(resultado);
        return enviados;
    }
    
    /**
     * Calcula qué préstamos están atrasados y por cuántos días
     * Devuelve String con titulo y dias de atraso
     */
    private String calcularPrestamosAtrasados(Usuario usuario) {
    	
        String prestamosAtrasados = "";
        Fecha fechaActual = obtenerFechaActual();
        long diasTotal=0, diasDiferencia=0;
        
        for (Prestamo prestamo : usuario.getListaPrestamos().getListaPrestamos()) {
            // Calcular días de diferencia usando tu método de Fecha
            diasDiferencia = fechaActual.diferencia(prestamo.getFechaDevolucion());
            
            // Si la diferencia es positiva, está atrasado
            if (diasDiferencia > 0) {
                if (!prestamosAtrasados.isEmpty())
                	prestamosAtrasados+=",";
                
            	prestamosAtrasados += prestamo.getTituloObra();
                diasTotal+=diasDiferencia;           
                		
            }
        }
        if (!prestamosAtrasados.isEmpty())
        	prestamosAtrasados+= ". Con un total de " + diasTotal + " de atraso.\n";
        
        return prestamosAtrasados;
    }
    
    /**
     * Genera el texto del mensaje personalizado para cada usuario
     */
    private String generarTextoMensaje(Usuario usuario, String titulosYDias) {
        String mensaje = "Estimado/a " + usuario.getNombre() + " " + usuario.getApellido() + ",\n\n";
        mensaje += "Le escribimos desde la Biblioteca para recordarle que tiene los siguientes materiales vencidos:\n\n";
        mensaje += titulosYDias;
    
        mensaje += "\nPor favor, acérquese a la biblioteca a la brevedad para regularizar su situación.\n";
        mensaje += "Recuerde que los retrasos pueden generar sanciones según el reglamento.\n\n";
        mensaje += "Saludos cordiales,\n";
        mensaje += "Equipo de Biblioteca\n";
        mensaje += "---\n";
        mensaje += "Este es un mensaje automático, por favor no responder.";
        
        return mensaje;
    }
    
    /**
     * Obtiene la fecha actual usando tu clase Fecha personalizada
     */
    private Fecha obtenerFechaActual() {
        LocalDate hoy = LocalDate.now();
        return new Fecha(hoy.getDayOfMonth(), hoy.getMonthValue(), hoy.getYear());
    }
    
    /**
     * Obtener historial de mensajes como String formateado
     */
    public String obtenerHistoricoMensajes() {
    	String resultado="";
    	String mensajesUsuario = historialMensajes.obtenerMensajes();        
    	if (!mensajesUsuario.isEmpty()) {
            resultado=mensajesUsuario;
        }else {
        	resultado = "No hay mensajes enviados en el historial.";
        }
        return resultado;
    }
    
    /**
     * Buscar mensajes por usuario específico
     */
    public String obtenerMensajesPorIdUsuario(int idUsuario) {
        String mensajesUsuario = historialMensajes.obtenerMensajesPorUsuario(idUsuario);
        String resultado;
        if (!mensajesUsuario.isEmpty()) {
        	resultado = mensajesUsuario;
        }else {
        	resultado = "No se encontraron mensajes para el usuario ID: " + idUsuario;
        }        
        return resultado;
    }
    
    public Historias getMensajesSinPersistir() {
    	return mensajesSinPersistir;
    }
    
    
}```

## Archivo: src/Main.java
```java
import vista.Vista;
import logica.*;


public class Main {
    public static void main(String[] args) throws Exception {
        Controlador controlador = new Controlador();
    	Vista v = new Vista(controlador);
    	v.iniciar();
    }
}
```

## Archivo: src/modelo/Fecha.java
```java
package modelo;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class Fecha {
	private int dia;
	private int mes;
	private int anio;
		
	public Fecha(int dia, int mes, int anio) {		
		this.dia = dia;
		this.mes = mes;
		this.anio = anio;
	}	
	public Fecha() {
		
	}
	public Fecha(Fecha f) {
		this(f.dia,f.mes,f.anio);
	}
	
	public Fecha(String f) {
		String[] arrf = new String[3]; 
		if (f.contains("/")) {
			arrf=f.split("/");
		}else {
			arrf=f.split("-");
		}		
		
		dia=Integer.parseInt(arrf[0].trim());
		mes=Integer.parseInt(arrf[1].trim());
		anio=Integer.parseInt(arrf[2].trim());
		
	}
	
	public int getDia() {
		return dia;
	}
	public void setDia(int dia) {
		this.dia = dia;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public int getAnio() {
		return anio;
	}
	public void setAnio(int anio) {
		this.anio = anio;
	}
	public String toString() {
		return dia + "/" + mes + "/" + anio;
	}
    public Date toSqlDate() {
        LocalDate local = LocalDate.of(anio, mes, dia);
        return Date.valueOf(local);
    }
	public long diferencia(Fecha otra) {
		LocalDate f1 = LocalDate.of(this.anio, this.mes, this.dia);		
		LocalDate f2 = LocalDate.of(otra.anio, otra.mes, otra.dia);		
		return ChronoUnit.DAYS.between(f2,f1);
	}
	public Fecha obtenerFechaActual() {
	    LocalDate hoy = LocalDate.now();
	    return new Fecha(hoy.getDayOfMonth(), hoy.getMonthValue(), hoy.getYear());
	}
	
	public void establecerComoHoy() {
	    LocalDate hoy = LocalDate.now();
	    this.dia = hoy.getDayOfMonth();
	    this.mes = hoy.getMonthValue(); 
	    this.anio = hoy.getYear();
	}

}
```

## Archivo: src/modelo/Historias.java
```java
package modelo;

import java.util.LinkedList;

public class Historias {
	private LinkedList<MensajeEnviado> mensajesEnviados;
	private int ultimoId;
	
	public Historias() {
		mensajesEnviados=new LinkedList<MensajeEnviado>();
		this.ultimoId = 0;
	}
	
	public void agregarMensaje(MensajeEnviado mensaje) {		
		this.mensajesEnviados.add(mensaje);
	}
	
	public String obtenerMensajes(){
		String resultado="";
		for (MensajeEnviado msg : mensajesEnviados) {	     
            resultado+=msg.getId() + "|";
            resultado+=msg.getFechaEnvio() + "|";
            resultado+=msg.getIdUsuario() + "|";
            resultado+=msg.getCorreo() + "|";
            resultado+=msg.getTitulosYDias() + "\n";        
	    }				
		return resultado;
	}
	public String obtenerMensajesPorUsuario(int idUsuario) {
	    String resultado = "";
	    for (MensajeEnviado msg : mensajesEnviados) {
	        if (msg.getIdUsuario() == idUsuario) {
	            resultado+=msg.getId() + "|";
	            resultado+=msg.getFechaEnvio() + "|";
	            resultado+=msg.getIdUsuario() + "|";
	            resultado+=msg.getCorreo() + "|";
	            resultado+=msg.getTitulosYDias() + "\n";
	        }
	    }
	    return resultado;
	}

	public MensajeEnviado buscarMensajePorId(int id) {
		for (MensajeEnviado msg : mensajesEnviados) {
			if (msg.getId() == id) {
				return msg;
			}
		}
		return null;
	}
	
	public String obtenerMensajesPorIdUsuario(int id) {
		String resultado="";
		
		for (MensajeEnviado msg : mensajesEnviados) {
			if (msg.getIdUsuario() == id) {
				resultado+=msg.toString();
			}
		}
		
		return resultado;
	}
	
	public LinkedList<MensajeEnviado> getMensajesEnviados(){
		return mensajesEnviados;
	}
	
	public int buscarUltimoId() {
		int resultado=0;
		for (MensajeEnviado msg : mensajesEnviados) {
	        if (msg.getId() > resultado) {
	            resultado = msg.getId(); 
	        }
	    }
	    return resultado;
	}

	public String toString() {
		String s = "";
		for (MensajeEnviado msg : mensajesEnviados) {
			s += msg.toString();
		}
		return s;
	}
	public void borrarTodo() {
		mensajesEnviados.clear();
	}
	public void setUltimoId(int i) {
		ultimoId = i; 
	}
	
	public int getUltimoId() {
		return ultimoId;
	}
	public boolean existeIdMensaje(int id) {
		boolean res = false;
		for (MensajeEnviado msg : mensajesEnviados) {
			if (msg.getId()==id)
				res=true;
		}
		return res;
	}
	public boolean eliminarMensajeEnviado(int id) {
		boolean res = false;		
		if (existeIdMensaje(id)) {
			mensajesEnviados.remove(buscarMensajePorId(id));
			res=true;
		}
		return res;
		
		
	}
	
}
```

## Archivo: src/modelo/MensajeEnviado.java
```java
package modelo;

import java.util.Map;

public class MensajeEnviado {
	private int id;	//es la unica forma de distinguir un mensaje de otro, si es que se puedee mandar varios mail por dia
	private Fecha fechaEnvio;
	private int idUsuario;
	private String correo;	
	private String titulosYDias; 	
	
	
	public MensajeEnviado(int id, 
			Fecha fechaEnvio, 
			int idUsuario, 
			String correo,
			String titulosYDias,
			String textoMensaje) {
		this.id=id;
		this.fechaEnvio = fechaEnvio;
		this.idUsuario = idUsuario;
		this.correo=correo;
		this.titulosYDias = titulosYDias;
		
	}
	
	public void setId(int id) {
		this.id=id;
	}
	
	public int getId() {
		return this.id;
	}

	public Fecha getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Fecha fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public void setCorreo(String correo) {
		this.correo=correo;
	}
	public String getCorreo() {
		return this.correo;
	}
	
	public String getTitulosYDias() {
		return titulosYDias;
	}
	public void setTitulosYDias(String tyd) {
		this.titulosYDias=tyd;
	}
	@Override
	public String toString() {
	    return id + "|" 
	         + fechaEnvio + "|" 
	         + idUsuario + "|" 
	         + correo + "|" 
	         + titulosYDias + "\n";	         
	}

}
```

## Archivo: src/modelo/Prestamo.java
```java
package modelo;

import java.time.LocalDate;



public class Prestamo {
	private Fecha fechaPrestamo;      // aff_pret_date
	private Fecha fechaDevolucion;    // aff_pret_retour
	private int retraso;              // retard

	private String cotaEjemplar;          // expl_cote - representa ubicacion del libro en estanteria 
	private String codigoBarrasEjemplar;  // expl_cb
	//la "notice" es el documetno referente de los ejemplares "exemplaire" expl_ 
	private int idFichaBibliografica;     // expl_notice  
	private int idBulletin;               // expl_bulletin
	private int idNotice;                 // idnot

	private String tituloObra;            // tit
	private String tipoDocumento;         // tdoc_libelle
	private boolean prestamoCorto;        // short_loan_flag
	
	
	public Prestamo(Fecha fechaPrestamo, 
			Fecha fechaDevolucion, 
			int diasRetraso,
			String cotaEjemplar, 
			String codigoBarrasEjemplar, 
			int idFichaBibliografica, 
			int idBulletin, 
			int idNotice,
			String tituloObra, 
			String tipoDocumento, 
			boolean prestamoCorto) {
		
		this.fechaPrestamo = fechaPrestamo;
		this.fechaDevolucion = fechaDevolucion;
		this.retraso = diasRetraso;

		this.cotaEjemplar = cotaEjemplar;
		this.codigoBarrasEjemplar = codigoBarrasEjemplar;
		this.idFichaBibliografica = idFichaBibliografica;
		this.idBulletin = idBulletin;
		this.idNotice = idNotice;
		this.tituloObra = tituloObra;
		this.tipoDocumento = tipoDocumento;
		this.prestamoCorto = prestamoCorto;
	}


		
	
	public Fecha getFechaPrestamo() {
		return fechaPrestamo;
	}
	public void setFechaPrestamo(Fecha fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}
	public Fecha getFechaDevolucion() {
		return fechaDevolucion;
	}
	public void setFechaDevolucion(Fecha fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}
	public int getDiasRetraso() {
		return retraso;
	}
	public void setDiasRetraso(int retraso) {
		this.retraso = retraso;
	}
	
	public String getCotaEjemplar() {
		return cotaEjemplar;
	}
	public void setCotaEjemplar(String cotaEjemplar) {
		this.cotaEjemplar = cotaEjemplar;
	}
	public String getCodigoBarrasEjemplar() {
		return codigoBarrasEjemplar;
	}
	public void setCodigoBarrasEjemplar(String codigoBarrasEjemplar) {
		this.codigoBarrasEjemplar = codigoBarrasEjemplar;
	}
	public int getIdFichaBibliografica() {
		return idFichaBibliografica;
	}
	public void setIdFichaBibliografica(int idFichaBibliografica) {
		this.idFichaBibliografica = idFichaBibliografica;
	}
	public int getIdBulletin() {
		return idBulletin;
	}
	public void setIdBulletin(int idBulletin) {
		this.idBulletin = idBulletin;
	}
	public int getIdNotice() {
		return idNotice;
	}
	public void setIdNotice(int idNotice) {
		this.idNotice = idNotice;
	}
	public String getTituloObra() {
		return tituloObra;
	}
	public void setTituloObra(String tituloObra) {
		this.tituloObra = tituloObra;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public boolean isPrestamoCorto() {
		return prestamoCorto;
	}
	public void setPrestamoCorto(boolean prestamoCorto) {
		this.prestamoCorto = prestamoCorto;
	}
	@Override
	public String toString() {
	    return fechaPrestamo + "|" 
	         + fechaDevolucion + "|" 
	         + retraso + "|" 
	         + cotaEjemplar + "|" 
	         + codigoBarrasEjemplar + "|" 
	         + idFichaBibliografica + "|" 
	         + idBulletin + "|" 
	         + idNotice + "|" 
	         + tituloObra + "|" 
	         + tipoDocumento + "|" 
	         + prestamoCorto + "\n";
	}


}
```

## Archivo: src/modelo/Prestamos.java
```java
package modelo;

import java.util.LinkedList;

public class Prestamos {
	private LinkedList<Prestamo> listaPrestamos;

	public Prestamos() {
		this.listaPrestamos= new LinkedList<>();
	}
	
	public LinkedList<Prestamo> getListaPrestamos() {
		return listaPrestamos;
	}

	public void setListaPrestamos(LinkedList<Prestamo> listaPrestamos) {
		this.listaPrestamos = listaPrestamos;
	}
	
	public void agregarPrestamo(Prestamo p){
		listaPrestamos.add(p);
	}
	
	public String toString() {
		String aux="";
		for(Prestamo p : listaPrestamos) {
			aux+=p.toString();			
		}
		return aux;
	}
	
}
```

## Archivo: src/modelo/Usuario.java
```java
package modelo;



public class Usuario {
	private int idUsuario;         // id_empr
	private String apellido;       // empr_nom
	private String nombre;         // empr_prenom
	private String email;          // empr_mail
	private String codigoBarras;   // empr_cb
	private Prestamos listaPrestamos;
	
	public Usuario(int idUsuario,
			String apellidoUsuario,
			String nombreUsuario,
			String emailUsuario,
			String codigoBarrasUsuario,
			Prestamos listaPrestamos) {		
		this.idUsuario = idUsuario;
		this.apellido = apellidoUsuario;
		this.nombre = nombreUsuario;
		this.email = emailUsuario;
		this.codigoBarras = codigoBarrasUsuario;
		this.listaPrestamos = listaPrestamos;
	}
	public Usuario() {
	}

	public int getId() {
		return idUsuario;
	}
	public void setId(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellidoUsuario) {
		this.apellido = apellidoUsuario;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombreUsuario) {
		this.nombre = nombreUsuario;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String emailUsuario) {
		this.email = emailUsuario;
	}
	public String getCodigoBarras() {
		return codigoBarras;
	}
	public void setCodigoBarras(String codigoBarrasUsuario) {
		this.codigoBarras = codigoBarrasUsuario;
	}	
	public Prestamos getListaPrestamos() {
		return this.listaPrestamos;
	}
	@Override
	public String toString() {		
	    return idUsuario + "|" 
	         + apellido + "|" 
	         + nombre + "|" 
	         + email + "|" 
	         + codigoBarras + "|" 
	         + listaPrestamos + "\n";
	}
	
	/*
	 * No tiene listaDePrestamos
	 */
	public String obtenerDatosUsuario() {		
	    return idUsuario + "|" 
	         + apellido + "|" 
	         + nombre + "|" 
	         + email + "|" 
	         + codigoBarras + "\n";
	}
	public String obtenerPrestamosUsuario() {
		return getListaPrestamos().toString();
	}
}
```

## Archivo: src/modelo/UsuarioSistema.java
```java
package modelo;

public class UsuarioSistema {
	private int id;
	private String perfil; 
	private String contraseña;
	
	public UsuarioSistema(int id, String perfil, String contraseña) {		
		this.id = id;
		this.perfil = perfil;
		this.contraseña = contraseña;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	@Override
	public String toString() {
		return id + "|" + perfil + "|" + contraseña + "\n";
	}
}
```

## Archivo: src/modelo/Usuarios.java
```java
package modelo;

import java.util.LinkedList;

public class Usuarios {
	private LinkedList<Usuario> coleccionUsuario;

	public Usuarios(LinkedList<Usuario> coleccionUsuario) {		
		this.coleccionUsuario = coleccionUsuario;
	}
	public Usuarios() {
		this.coleccionUsuario = new LinkedList<Usuario>();
	}

	public LinkedList<Usuario> getColeccionUsuario() {
		return coleccionUsuario;
	}

	public void setColeccionUsuario(LinkedList<Usuario> coleccionUsuario) {
		this.coleccionUsuario = coleccionUsuario;
	}
	
	public void agregarUsuario(Usuario u) {
		coleccionUsuario.add(u);
	}
	public boolean existeIdUsuario(int id) {
		for (Usuario u : coleccionUsuario) {
			if (u.getId() == id)
				return true;
		}
		return false;
	}
	public String obtenerUsuarioPorId(int id) {
		Usuario u = obtenerUsuario(id);
		String datos="";
		if (u!=null)
			datos = u.toString();
		else
			datos = "No encontrado";
		return datos;
	}
	
	private Usuario obtenerUsuario(int id) {
		Usuario resultado = new Usuario();
		boolean encontrado=false;
		for (Usuario u : coleccionUsuario) {
			if (u.getId() == id) {
				resultado=u;
				encontrado = true;
			}
		}
		if (!encontrado)
			resultado=null;
		return resultado;
	}
	
	public String toString() {
		 String s="";    	 
         for (Usuario u : coleccionUsuario) {
             s+= u.toString();             
         }
         return s;
		
	}
	public String obtenerDatosUsuarios() {
		String s="";    	 
        for (Usuario u : coleccionUsuario) {
            s+= u.obtenerDatosUsuario();            
        }
        return s;
	}
	
	public String obtenerLibrosDeUsuario(int id) {
		Usuario u = obtenerUsuario(id);
		String datos;
		if (u!=null)
			datos=u.obtenerPrestamosUsuario();
		else
			datos = "Usuario no encontrado.";
		return datos;
	}
	
	public void borrarTodo() {
		coleccionUsuario.clear();
	}
	
	public void agregarPrestamoAUsuario(int id, Prestamo prestamo) {
		obtenerUsuario(id).getListaPrestamos().agregarPrestamo(prestamo);
	}
}
```

## Archivo: src/modelo/UsuariosSistema.java
```java
package modelo;

import java.util.ArrayList;

public class UsuariosSistema {
	private ArrayList<UsuarioSistema> usuariosSistema;

	public UsuariosSistema(ArrayList<UsuarioSistema> coleccionUsuario) {		
		this.usuariosSistema = coleccionUsuario;
	}
	public UsuariosSistema() {
		this.usuariosSistema = new ArrayList<UsuarioSistema>();
	}

	public ArrayList<UsuarioSistema> getColeccionUsuarioSistema() {
		return usuariosSistema;
	}

	public void setColeccionUsuarioSistema(ArrayList<UsuarioSistema> coleccionUsuario) {
		this.usuariosSistema = coleccionUsuario;
	}
	
	public void agregarUsuario(UsuarioSistema u) {
		usuariosSistema.add(u);
	}
	public void agregarUsuario(int id, String perfil, String contraseña) {
		usuariosSistema.add(new UsuarioSistema(id,perfil,contraseña));
	}
	
	public String obtenerUsuarios() {
		String resultado = "";
		
		for (UsuarioSistema u : usuariosSistema) {
			resultado+=u.toString()+ "\n";
		}		
		return resultado;		
	}
	public int obtenerUltimoId() {
		int resultado = 0;		
		for (UsuarioSistema u : usuariosSistema) {
			if (resultado<u.getId())
				resultado=u.getId();
		}		
		return resultado;		
	}
	public boolean existeId(int id) {
		boolean resultado = false;		
		for (UsuarioSistema u : usuariosSistema) {
			if (id==u.getId())
				resultado=true;
		}		
		return resultado;
	}
	public UsuarioSistema obtenerUsuario(int id) {
		UsuarioSistema resultado=null;		
		for (UsuarioSistema u : usuariosSistema) {
			if (id==u.getId())
				resultado=u;
		}		
		return resultado;
	}
	
	public String obtenerUsuarioPorId(int id) {
		UsuarioSistema u = obtenerUsuario(id);
		String datos="";
		if (u!=null)
			datos=obtenerUsuario(id).toString();		
		return datos;
	}
	
	public boolean eliminarUsuario(int id) {
		boolean resultado=false;
		if (existeId(id)) {
			usuariosSistema.remove(obtenerUsuario(id));
			resultado=true;
		}
		return resultado;
	}
	public boolean modificarUsuario(int id, String perfil, String contraseña) {
		boolean resultado=false;
		if (existeId(id)) {
			if (perfil.equalsIgnoreCase("operador")||perfil.equalsIgnoreCase("administrador")) {
				obtenerUsuario(id).setPerfil(perfil);
				obtenerUsuario(id).setContraseña(contraseña);
				resultado=true;
			}			
		}
		return resultado;
	}
}
```

## Archivo: src/persistencia/HistoriasBD.java
```java
package persistencia;

import modelo.Fecha;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/* HistoriasBD se encarga de preparar Querys y Update de la tabla historias, y ejecutarlas, usando la conexion
 * ofrecida por ServicioBD. Se opta por repetir codigo para simplificar lectura.
 * */
public class HistoriasBD {
	private ServicioBD cn;
	
	public HistoriasBD() {
		cn=new ServicioBD();
	}	
	// id es autonumerico en BD
	public boolean alta(Fecha fechaEnvio, int idUsuario, String correo, String titulosYDias) {
        String sql = "INSERT INTO historias (fechaEnvio,idUsuario,correo,titulosYDias) VALUES (?,?,?,?)";		
        boolean ok = false;
        
        cn.conectar();
        
        try {
            PreparedStatement statement = cn.getConexion().prepareStatement(sql);
            statement.setDate(1, fechaEnvio.toSqlDate());
            statement.setInt(2, idUsuario);
            statement.setString(3, correo);
            statement.setString(4, titulosYDias);
            
            ok = statement.executeUpdate() > 0;
            statement.close();
            
        } catch (SQLException e) {
            System.out.println("Error en alta: " + e.getMessage());
        }
        
        cn.cerrarConexion();
        return ok;
    }
	
	public String obtener() {
        String sql = "SELECT * FROM historias";
        String resultado = "";
        
        cn.conectar();
        
        try {
            Statement statement = cn.getConexion().createStatement();
            ResultSet rs = statement.executeQuery(sql);
            int columnas = rs.getMetaData().getColumnCount();
            
            while (rs.next()) {
                for (int i = 1; i <= columnas; i++) {
                    resultado += rs.getString(i);
                    
                    if (i < columnas) {
                        resultado += "|";
                    }
                }
                resultado += "\n";
            }
            rs.close();
            statement.close();
            
        } catch (SQLException e) {
            System.out.println("Error en obtener: " + e.getMessage());
            resultado = null;
        }
        
        cn.cerrarConexion();
        return resultado;
    }
	
	/*
	 * Devuelve mensajes posteriores a un id
	 * */
	public String obtenerMayorDeId(int id) {
        String sql = "SELECT * FROM historias WHERE id > ?";
        String resultado = "";
        
        cn.conectar();
        
        try {
            PreparedStatement statement = cn.getConexion().prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            int columnas = rs.getMetaData().getColumnCount();
            
            while (rs.next()) {
                for (int i = 1; i <= columnas; i++) {
                    resultado += rs.getString(i);
                    if (i < columnas) {
                        resultado += ", ";
                    }
                }
                resultado += "\n";
            }
            rs.close();
            statement.close();
            
        } catch (SQLException e) {
            System.out.println("Error en obtenerMayorDeId: " + e.getMessage());
            resultado = null;
        }
        
        cn.cerrarConexion();
        return resultado;
    }
    
    public boolean borrarTodo() {
        String sql = "TRUNCATE TABLE historias";
        boolean ok = false;
        
        cn.conectar();
        
        try {
            Statement statement = cn.getConexion().createStatement();
            ok = statement.executeUpdate(sql) >= 0; // DELETE puede afectar 0 filas y ser exitoso
            statement.executeUpdate("ALTER TABLE historias AUTO_INCREMENT = 1"); // para resetear autoincrement
            statement.close();
            
        } catch (SQLException e) {
            System.out.println("Error en borrarTodo: " + e.getMessage());
        }
        
        cn.cerrarConexion();
        return ok;
    }


	public int obtenerUltimoId() {
		String sql= "SELECT MAX(id) FROM historias;";
		boolean ok = false;        
        cn.conectar();
        int resultado=0;
        
        try {
            Statement statement = cn.getConexion().createStatement();            
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                resultado = rs.getInt(1); 
            }
            
            rs.close();
            statement.close();
            cn.cerrarConexion();
            
        } catch (SQLException e) {
            System.out.println("Error en obtenerMayorDeId: " + e.getMessage());            
        }
        
        cn.cerrarConexion();        
		return resultado; 
	}
	
	public boolean baja(int id) {
	    String sql = "DELETE FROM historias WHERE id = ?";
	    boolean ok = false;

	    cn.conectar();

	    try (PreparedStatement ps = cn.getConexion().prepareStatement(sql)) {
	        ps.setInt(1, id);
	        int filas = ps.executeUpdate();
	        ok = (filas > 0);
	    } catch (SQLException e) {
	        System.out.println("Error en baja: " + e.getMessage());
	    }

	    cn.cerrarConexion();
	    return ok;
	}
	
	public boolean existeId(int id) {
	    String sql = "SELECT 1 FROM historias WHERE id = ? LIMIT 1";
	    boolean existe = false;

	    cn.conectar();

	    try (PreparedStatement ps = cn.getConexion().prepareStatement(sql)) {
	        ps.setInt(1, id);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                existe = true;
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Error en existeId: " + e.getMessage());
	    }

	    cn.cerrarConexion();
	    return existe;
	}


}
```

## Archivo: src/persistencia/ServicioBD.java
```java
package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;



/* ServicioBD se encarga de conexion a la BD. 
*/
public class ServicioBD {
	private final String Controlador = "com.mysql.cj.jdbc.Driver";
	private final String Url = "jdbc:mysql://localhost:3306/sgm";
	private final String User = "root"; 
	private final String Pass = "root"; 
	private Connection conexion;
		
	public ServicioBD() {
		conexion=null;
	}
	
	public Connection conectar() {		
		try {
			conexion = DriverManager.getConnection(Url,User,Pass);

		} catch(SQLException e) {
			System.out.println("Error al establecer la conexion.");
		}
		return conexion;
	}

	public void cerrarConexion() {
		try {
			if(conexion!= null) {
				conexion.close();
			}
		} catch (Exception e2) {
			System.out.println("Error no se pudo cerrar la conexión.");
		}
	}
	public Connection getConexion() {
		return conexion;
	}
	
	

}
```

## Archivo: src/persistencia/ServicioDatos.java
```java
package persistencia;
import java.util.Map;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
/*
 * ServicioDatos encapsula la carga del XLS en un String. 
 * cadena almacena una Sting con campos separados por coma y lineas por punto y coma.
 */
public class ServicioDatos {
	private String rutaArchivo = "datos.xls";
	private String cadena = "";

	public ServicioDatos()throws Exception{
		try(FileInputStream archivo = new FileInputStream(rutaArchivo)){
		    
		    Workbook libro = new HSSFWorkbook(archivo);
	        Sheet hoja = libro.getSheetAt(0);
	        int cantidadFilas = hoja.getLastRowNum(); // filas desde 0
		    for (int i = 1; i < cantidadFilas; i++) {	    	
		        Row fila = hoja.getRow(i);	        
		        if (fila != null) {
		        	int ultimaCelda = fila.getLastCellNum();
		        	
		        	//comprobar si fila tiene todos los campos vacios
		        	boolean todaVacia = true;
		        	for (Cell c : fila) {
		        		if (c.getCellType() != CellType.BLANK) {
		        			todaVacia = false;
		        		}
		        	}	        	
		        	
		        	if (!todaVacia) {
				        for (int j = 0; j < ultimaCelda; j++) {
						    Cell celda = fila.getCell(j);
						    
						    if (celda != null) {
						        String valor = "";
						        if (celda.getCellType() == CellType.NUMERIC) {
						            // los tipos de celda numericos arrojan expresiones exponenciales (1234E10)
						            valor = String.valueOf((long) celda.getNumericCellValue());
						        } else {
						            valor = celda.toString();
						        }
	
						        cadena += valor;
						        			    	
					
						        if (j < ultimaCelda - 1) 
						            cadena += ",";
						        else
						        	cadena += ";";					        
						    }
						}
		        	}
		        }
			}
	        archivo.close();
	        
		}catch (FileNotFoundException e) {
	        System.out.println("Archivo xls no encontrado en: /" + rutaArchivo);
	     
	    } catch (IOException e) {
	        System.out.println("Error al leer el archivo xls" + e.getMessage());
	     
	    }
		
        	
	}
	
	public String getCadena() {
	    return cadena;
	}	
}
```

## Archivo: src/persistencia/ServicioMensajeria.java
```java
package persistencia;
import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.io.File;
import java.io.IOException;

/* Se encarga de conexion y envio de mail 
 * 
 */
public class ServicioMensajeria {
	private String micorreo = "tallerINET2025@gmail.com";
	private String clave = "wjvj lssx adib dinn";	
	private String destino;
	private String asunto="";
	private String textoMensaje="";	
	private String cuerpoHtml="";
	private String rutaAdjunto="";
	
	
	public ServicioMensajeria() {		
	}
	
	public boolean enviar(String textoMensaje, String destino) {	
		Properties propiedades = new Properties();
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.host", "smtp.gmail.com");
        propiedades.put("mail.smtp.port", "465");
        propiedades.put("mail.smtp.ssl.enable", "true");
        propiedades.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        
        Session sesion = Session.getInstance(propiedades, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(micorreo, clave);
            }
        });
        try {
            Message mail = new MimeMessage(sesion);
            mail.setFrom(new InternetAddress(micorreo));
            mail.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destino));
            mail.setSubject(asunto);
            
            // Solo texto simple - sin multipart
            mail.setText(textoMensaje);
            
            Transport.send(mail);
            System.out.println("Correo enviado correctamente a: " + destino);
            return true;
            
        } catch (MessagingException e) {
            System.err.println("Error al enviar correo: " + e.getMessage());
            return false;
        }
        

	}
}
```

## Archivo: src/persistencia/UsuarioSistemaBD.java
```java
package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/* UsuariosSistemaBD se encarga de preparar Querys y Update de la tabla usuariosistema, y ejecutarlas, usando la conexion
 * ofrecida por ServicioBD. Se opta por repetir codigo para simplificar lectura.
 * */
public class UsuarioSistemaBD {
	
	private ServicioBD cn;
		
	public UsuarioSistemaBD() {	
			cn=new ServicioBD(); 
	}
	
	public String obtener() {
        String sql = "SELECT * FROM usuariosistema";
        String resultado = "";
        
        cn.conectar();
        
        try {
            Statement statement = cn.getConexion().createStatement();
            ResultSet rs = statement.executeQuery(sql);
            int columnas = rs.getMetaData().getColumnCount();
            
            while (rs.next()) {
                for (int i = 1; i <= columnas; i++) {
                    resultado += rs.getString(i);
                    if (i < columnas) {
                        resultado += "|";
                    }
                }
                resultado += "\n";
            }
            rs.close();
            statement.close();
            
        } catch (SQLException e) {
            System.out.println("Error en obtener: " + e.getMessage());
            resultado = null;
        }
        
        cn.cerrarConexion();
        return resultado;
    }
	
	
	public String alta(int id, String perfil, String contraseña) {
		String retorno = "";
		
		cn.conectar();		
		
		String sql = "INSERT INTO usuariosistema VALUES (?,?,?)";
		try (PreparedStatement statement = cn.getConexion().prepareStatement(sql)){
			statement.setInt(1, id);
			statement.setString(2, perfil);
			statement.setString(3, contraseña);
			statement.executeUpdate();
			retorno = "Usuario del sistema dado de alta con éxito";
		} catch (SQLException e) {
			retorno = e.getMessage();
		}
		
		cn.cerrarConexion();
		return retorno;
	}
	
	public String baja(int id) {
		String retorno = "";
		cn.conectar();
				
		String sql = "DELETE FROM usuariosistema WHERE id = ?";
		try (PreparedStatement statement = cn.getConexion().prepareStatement(sql)){
			statement.setInt(1, id);
			statement.executeUpdate();
			retorno = "Usuario del sistema dado de baja con éxito";
		} catch (SQLException e) {
			retorno = e.getMessage();
		}
		
		
		cn.cerrarConexion();
		return retorno;
	}
	
	public String modificar(int id, String perfil, String contraseña) {
		String retorno = "";
		cn.conectar();
		
		
		String sql = "UPDATE usuariosistema SET perfil = ?, contrasenia = ? WHERE id = ?";
		try (PreparedStatement statement = cn.getConexion().prepareStatement(sql)){
			statement.setString(1, perfil);
			statement.setString(2, contraseña);
			statement.setInt(3, id);
			statement.executeUpdate();
			retorno = "Usuario del sistema modificado con éxito";
		} catch (SQLException e) {
			retorno = e.getMessage();
		}		
		
		cn.cerrarConexion();
		return retorno;
	}
	
	public boolean verificarPass(int id, String contraseña) {
		boolean res=false;
		String sql = "SELECT * FROM usuariosistema WHERE id = ? AND contrasenia = ?";
		cn.conectar();
		try (PreparedStatement ps = cn.getConexion().prepareStatement(sql)) {
		    ps.setInt(1, id);      
		    ps.setString(2, contraseña);
		    try (ResultSet rs = ps.executeQuery()) {
		        if (rs.next()) 
		            res=true;		        
		    }catch(SQLException e) {
				System.out.println(e);
			}
		}catch(SQLException e) {
			System.out.println(e);
		}
		cn.cerrarConexion();
		return res;
	}
	
	public String obtenerPerfil(int id) {
		String res="";
		String sql = "SELECT perfil FROM usuariosistema WHERE id = ?";
		cn.conectar();
		try (PreparedStatement ps = cn.getConexion().prepareStatement(sql)) {
		    ps.setInt(1, id);
		    try (ResultSet rs = ps.executeQuery()) {
		    	if (rs.next())
		    		res=rs.getString("perfil");		        
		    }catch(SQLException e) {
				System.out.println(e);
			}
		}catch(SQLException e) {
			System.out.println(e);
		}
		cn.cerrarConexion();
		return res;
	}
}

```

## Archivo: src/test/TestBD.java
```java
package test;
import persistencia.UsuarioSistemaBD;

import modelo.UsuarioSistema;

public class TestBD {
	
	public static void main(String[] args) throws Exception {
	
		UsuarioSistema usu1 = new UsuarioSistema(2,"iblioteca","arasa");
		
		UsuarioSistemaBD usuBD= new UsuarioSistemaBD();
		
		
		System.out.println(usu1.toString());
		// ALTA
		System.out.println(usuBD.alta(usu1.getId(),usu1.getPerfil(),usu1.getContraseña()));
		
		// MODIFICA
		System.out.println(usuBD.modificar(usu1.getId(),"modificado",usu1.getContraseña()));
		// Mostrar Usuarios
		System.out.println(usuBD.obtener());
		
		// BAJA
		System.out.println(usuBD.baja(usu1.getId()));

		
	}
	
}
```

## Archivo: src/test/TestDatos.java
```java
package test;
import persistencia.*;

public class TestDatos {

	public static void main(String[] args) throws Exception {
		ServicioDatos nd = new ServicioDatos();		
		System.out.println(nd.getCadena());
	}

}
```

## Archivo: src/test/TestEnviarMensaje.java
```java
package test;
import persistencia.ServicioMensajeria;

public class TestEnviarMensaje {
	
	public static void main(String [] args) {
		String textoMensaje = "Hola mi mensaje";
		String destino ="eltartamudo01@gmail.com";
		ServicioMensajeria sm = new ServicioMensajeria();
		System.out.println(sm.enviar(textoMensaje,destino));
		
	}
}
```

## Archivo: src/test/TestFecha.java
```java
package test;

import modelo.Fecha;

public class TestFecha {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Fecha f1 = new Fecha(12,12,1912);
		Fecha f2 = new Fecha(20,12,1911);
		System.out.println(f1);
		System.out.println(f1.diferencia(f2));
		Fecha s=new Fecha("12/1/1923");
		System.out.println(s);
	}

}
```

## Archivo: src/test/testManejadorDatos.java
```java
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
```

## Archivo: src/test/XlsParserTest.java
```java
package test;

import java.io.FileInputStream;
import java.util.Scanner;
//Poor Obfuscated Implementation
//Horrible Spreadsheet Format
/*
jdk 8
poi 4.1.2

*/
import org.apache.poi.ss.usermodel.Workbook; 
import org.apache.poi.ss.usermodel.Sheet;   
import org.apache.poi.ss.usermodel.Row;     
import org.apache.poi.ss.usermodel.Cell;    
import org.apache.poi.hssf.usermodel.HSSFWorkbook; 

//HELP: https://poi.apache.org/components/spreadsheet/quick-guide.html#Iterator



public class XlsParserTest {


	public static void main(String[] args)throws Exception {
		int op=0;
        do{
	        Scanner teclado = new Scanner(System.in);
	        imprimirMenu();

	        System.out.println("Ingresar opcion:");
	        op=teclado.nextInt();
	        teclado.nextLine();

	        switch(op) {
	        	case 0:
	        		System.out.println("Chau!");
	        	break;
	        	case 1:
	        		imprimirTodaLaHoja();
	        	break;
	        	case 2:
	        		imprimirUnaCelda();
	        	break;
	        	case 3:
	        		imprimirIsbn();
	        	break;
	        	default:
	        		System.out.println("En construccion...");
	        	break;
	        }
	    }while (op!=0);
	}

	public static void imprimirMenu() {
		String [] op= {"Salir"
				,"Imprimir toda la hoja"
				,"Imprimir una celda en concreto"
				,"Imprimir isbn desde String a Double"
				,"Imprimir formato fecha"};
		System.out.println("--------------------------------------");
		for(int i=0;i<op.length;i++) {
			System.out.println(i + ". " + op[i]);
		}
	}

	public static void imprimirTodaLaHoja() throws Exception{

		FileInputStream archivo = new FileInputStream("archivo.xls");
        Workbook libro = new HSSFWorkbook(archivo);

        Sheet hoja = libro.getSheetAt(0);

        for (Row fila : hoja) {
            for (Cell celda : fila) {
                System.out.print(celda.toString() + "\t");
            }
            System.out.println();
        }
        libro.close();
        archivo.close();
	}

	public static void imprimirUnaCelda() throws Exception{
		FileInputStream archivo = new FileInputStream("archivo.xls");
        Workbook libro = new HSSFWorkbook(archivo);
        Sheet hoja = libro.getSheetAt(0);
        Row fila = hoja.getRow(5);
        Cell celda = fila.getCell(50);

        String valor="";

        if (celda!=null) {
        	//Usar una implementacion toString de Object puede traer problemillas
        	//Confirmar tipo para usar metodos parser de POI
        	//Las celdas tienen formatos expresados en un ENUM
        	switch(celda.getCellType()) {
        	case STRING:
        		valor = celda.getStringCellValue();
        	break;
        	case NUMERIC:
        		valor = String.valueOf(celda.getNumericCellValue());
        	break;
        	case BOOLEAN:
        		valor = String.valueOf(celda.getBooleanCellValue());
        	break;
        	case FORMULA:
        		valor = celda.getCellFormula();
        	break;
        	default:
        		valor = "Sin tipo.";
        	break;
        	}
        	System.out.println(valor);
        }
        libro.close();
        archivo.close();
	}
	public static void imprimirIsbn() throws Exception{
		FileInputStream archivo = new FileInputStream("archivo.xls");
        Workbook libro = new HSSFWorkbook(archivo);
        Sheet hoja = libro.getSheetAt(0);
        Row fila = hoja.getRow(4);
        Cell celda = fila.getCell(4);

        Long isbn=Long.parseLong(celda.getStringCellValue().replaceAll("-",""));
        System.out.println(isbn);

		libro.close();
        archivo.close();
	}
}

	

```

## Archivo: src/vista/VistaDeudores.java
```java
package vista;
import java.util.Scanner;

import logica.Controlador;

public class VistaDeudores {

	private Controlador controlador;
	
	public VistaDeudores(Controlador controlador) {
		this.controlador=controlador;		
	}
	
	public void menu() {
		int opcion=0;
		Scanner sc=new Scanner(System.in);
		do {
			mostrarMenu();
			opcion = sc.nextInt();
	        sc.nextLine();
			switch(opcion) {
				case 0:
					System.out.println("Saliendo de Menu Deudores.");
				break;
				case 1:
					mostrarUsuarios();
				break;
				case 2:
					buscarUsuarioPorId();	
				break;
				default:
					System.out.println("Opcion inválida.");
				break;		
			}
			
		}while(opcion!=0);
	}
	
	public void mostrarMenu() {
		System.out.println();
		System.out.println("========MENU DEUDORES==========\n");
		System.out.println("0.Salir");		
		System.out.println("1.Mostrar Todos los Deudores.");
		System.out.println("2.Mostrar Libros de un Deudor.");		
		System.out.print("Seleccione una opcion: ");
	}
	private void mostrarUsuarios() {
		String vista= controlador.obtenerUsuarios();
		if (vista.isEmpty())
			System.out.println("\nSin Deudores.");
		else
			System.out.println(vista);
	}
    private void buscarUsuarioPorId() {
    	Scanner sc=new Scanner(System.in);
        System.out.print("Ingrese el ID del usuario: ");
        int idBuscado = Integer.parseInt(sc.nextLine());
        
        System.out.println(controlador.obtenerUsuarioPorId(idBuscado));
       
    }
}
```

## Archivo: src/vista/Vista.java
```java
package vista;

import java.util.Scanner;

import logica.Controlador;

public class Vista {
    private Controlador controlador;  

    public Vista(Controlador controlador) throws Exception {    	
    	this.controlador = controlador;
    }

    public void iniciar() {
    	Scanner sc=new Scanner(System.in);
        int opcion = 0;
        int usuario=0;
        String pass="";
        String perfilUsuario="";
        boolean contraseñaOk=false;        
        
        do {        	
        	System.out.println("Ingrese usuario:");
            usuario=sc.nextInt();
            sc.nextLine();
            System.out.println("Ingrese contraseña:");
            pass=sc.nextLine();       	
        	
        	contraseñaOk=controlador.verificarContraseñaUsuarioSistema(usuario, pass);
	        if(contraseñaOk) {
	        	perfilUsuario=controlador.obtenerPerfilUsuarioSistema(usuario);
	        	System.out.println("Ha ingresado con perfil " + perfilUsuario);
	        	
	        	do{ 
	            	System.out.println();
	                System.out.println("\n====== MENÚ PRINCIPAL ======");	              
	                System.out.println("0. Salir");
	                System.out.println("1. Deudores");	                
	                System.out.println("2. Mensajeria");  
	                
	                if (perfilUsuario.equals("administrador"))
	                	System.out.println("3. Usuarios del Sistema");        
	                System.out.println("4. Emitir Constancia");          
	                System.out.print("Seleccione una opción: ");
	                
	                
	                
	                opcion = sc.nextInt();
	                sc.nextLine();
	
	                switch (opcion) {
	                    case 1:
	                        VistaDeudores vDeudores= new VistaDeudores(controlador);
	                        vDeudores.menu();
	                        break;	                    
	                    case 2:
	                    	VistaMensajeria vMensajeria = new VistaMensajeria(controlador);
	                    	vMensajeria.menu();                    
	                        break;
	                    case 3:
	                    	if (perfilUsuario.equals("administrador")) {	                    		
	                    		VistaUsuariosDelSistema usuariosDelSistemaMenu = new VistaUsuariosDelSistema(controlador);
	                    		usuariosDelSistemaMenu.menu();
	                    	}else
	                    		System.out.println(" Opcion inválida");
	                    	break;       
	                    case 4:
	                    	emitirConstancia();
	                    	break;
	                    case 0:
	                    	System.out.println(" Hasta luego!");
	                    	break;
	                    default:
	                        System.out.println(" Opción inválida");
	                        break;
	                }
	            }while(opcion!=0);
	        	
	        }else {
	        	System.out.println("Usuario o contraseña incorrecta.");
	        }
	        
    	}while(!contraseñaOk);
        
        
    }

    // es imposible traer datos de Usuarios de la Biblioteca que nunca fueron deudores
    public void emitirConstancia() {
    	Scanner sc=new Scanner(System.in);
    	int id=0;
    	System.out.println("Ingrese id de usuario:");
    	id=sc.nextInt();
    	System.out.println();
    	if (!controlador.existeUsuario(id))
    		System.out.println(controlador.obtenerConstancia(id));
    	else
    		System.out.println("El usuario es deudor. No se puede emitir constancia.");
    	
    }
    
    
}```

## Archivo: src/vista/VistaLibrosAdeudados.java
```java
package vista;
/*
 * DEPRECATED
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */

import java.util.Scanner;

import logica.Controlador;

public class VistaLibrosAdeudados {
	private Controlador controlador;
		
		public VistaLibrosAdeudados(Controlador controlador) {
			this.controlador=controlador;		
		}
		
		public void menu() {
			int opcion=0;
			Scanner sc=new Scanner(System.in);
			do {
				mostrarMenu();
				opcion = sc.nextInt();
	            sc.nextLine();
				switch(opcion) {
					case 0:
						System.out.println("Saliendo de Menu Libros Adeudados");						
					break;
					case 1:
						mostrarLibrosAdeudados();
					break;
					case 2:
						//en construccion
					break;
					case 3:
						//en construccion
					break;
					default:
						System.out.println("Ingrese una opcion válida");
					break;		
				}
				
			}while(opcion!=0);
		}
		
		public void mostrarMenu() {
			System.out.println();
			
			System.out.println("========MENU LIBROS ADEUDADOS==========\n");
			System.out.println("0.Salir");		
			System.out.println("1.Mostrar Libros Adeudados");
			System.out.println("2.Buscar libro por titulo");
			System.out.println("3.Listar Deudores por libro");
			System.out.print("Seleccione una opcion: ");
		}
		public void mostrarLibrosAdeudados() {
			System.out.println("Lista de libros adeudados:");
			//System.out.println(controlador.obtenerListaLibros());
		}
}
```

## Archivo: src/vista/VistaMensajeria.java
```java
package vista;

import java.util.Scanner;

import logica.Controlador;
	
	public class VistaMensajeria {
	private Controlador controlador;
		
		public VistaMensajeria(Controlador controlador) {
			this.controlador=controlador;		
		}
		
		public void menu() {
			int opcion=0;
			Scanner sc=new Scanner(System.in);			
			do {
				mostrarMenu();
				opcion = sc.nextInt();
	            sc.nextLine();
				switch(opcion) {
					case 0:
						System.out.println("Saliendo de menú Mensajería.");
					break;
					case 1:
						procesarMensajes();
					break;
					case 2:
						listarHistoricoMensajes();						
					break;
					case 3:
						listarHistoricoMensajesPorUsuario();
					break;
					case 4:
						borrarMensajeEnviado();
					break;
					case 5:
						borrarTodasHistoriasBD();
					default:
					break;		
				}
				
			}while(opcion!=0);
		}
		
		public void mostrarMenu() {
			System.out.println();
			System.out.println("========MENU MENSAJERIA==========\n");
			System.out.println("0.Salir");		
			System.out.println("1. Enviar mensajes de correo a cada Deudor");
			System.out.println("2. Mostrar todos los mensajes enviados");
			System.out.println("3. Mostrar mensajes por id de Deudor");
			System.out.println("4. Eliminar mensaje enviado");
			System.out.println("5. Borrar todos los mensajes");
			
			System.out.print("Seleccione una opcion: ");
		}
		
	    private void procesarMensajes() {
	    	System.out.println("Mensajes enviados:" + controlador.procesarMensajesDeUsuarios());
	    }
	    
	    private void listarHistoricoMensajes() {
	    	System.out.println("---------Historico de Mensajes Enviados--------");
	    	System.out.println(controlador.obtenerHistoricoMensajes());
	    }
	    
	    private void listarHistoricoMensajesPorUsuario() {
	    	Scanner sc=new Scanner(System.in);
	    	int id=0;
	    	System.out.print("Ingrese Id de Deudor:");
	    	id=sc.nextInt();
	    	sc.nextLine();
	    	System.out.println("-------Lista de mensajes por usuario-------");
	    	System.out.println(controlador.obtenerHistoricoMensajesPorIdUsuario(id));
	    	
	    }
	    
	    public void borrarTodasHistoriasBD() {
	    	controlador.borrarTodasHistoriasBD();
	    }
	    
	    public void borrarMensajeEnviado() {
	    	Scanner sc = new Scanner(System.in);
	    	int id=0;
	    	System.out.println("Ingrese id de mensaje:");
	    	id=sc.nextInt();
	    	sc.nextLine();
	    	if (controlador.eliminarMensajeEnviado(id))
	    		System.out.println("Mensaje eliminado satisfactoriamente.");
	    	else
	    		System.out.println("No se ha podido eliminar el mensaje.");
	    }
}
```

## Archivo: src/vista/VistaUsuariosDelSistema.java
```java
package vista;
import java.util.Scanner;

import logica.Controlador;

public class VistaUsuariosDelSistema {
	private Controlador controlador;
	
	public VistaUsuariosDelSistema(Controlador controlador) {
		this.controlador = controlador;
	}
	
	public void menu() {
		int opcion= 0;		
		Scanner sc=new Scanner(System.in);		
		do {			
			mostrarMenu();
			opcion = sc.nextInt();
            sc.nextLine();
			switch(opcion) {
			case 0:
				System.out.println("Saliendo.");
				break;
			case 1:
				altaUsuario();
				break;
			case 2:
				bajaUsuario();
				break;
			case 3:
				modificarUsuario();
				break;
			case 4:
				mostrarUsuarios();
				break;	
			default:
				System.out.println("Ingrese una opcion valida.");
				break;

			}
		
		}while(opcion!=0);	
		
	}
	
	
	public void mostrarMenu() {
		
		System.out.println("========MENU USUARIOS DEL SISTEMA==========\n");
		System.out.println("0.Salir");		
		System.out.println("1.Alta Usuario");
		System.out.println("2.Baja Usuario");
		System.out.println("3.Modificar Usuario");
		System.out.println("4.Mostrar Usuarios");		
		System.out.print("Seleccione una opcion: ");
	}
	
	public int obtenerOpcion() {
	   	Scanner sc=new Scanner(System.in);
        int opcion = sc.nextInt();
        //sc.nextLine();
        //sc.close();
        return opcion; 
	}
	
	public void mostrarUsuarios() {
		System.out.println(controlador.obtenerUsuariosDelSistema());
	}
	public void altaUsuario() {
		int id=0;
		String perfil="";
		String contraseña="";
		Scanner sc = new Scanner(System.in);		
		/*
		System.out.println("Ingrese id:");
		id=sc.nextInt();
		sc.nextLine();
		*/
		
		System.out.println("Ingrese perfil:");
		perfil=sc.nextLine();
		System.out.println("Ingrese contraseña:");
		contraseña=sc.nextLine();
		
		System.out.println(controlador.altaUsuarioSistema(perfil,contraseña));
		
	}
	public void bajaUsuario() {
		Scanner sc = new Scanner (System.in);
		int id=0;
		boolean ok=false;
	
		System.out.println("Ingrese id de usuario:");			
		id=sc.nextInt();
		sc.nextLine();
		ok=controlador.eliminarUsuarioSistema(id);
		if (ok)				
			System.out.println("Usuario Eliminado.");
		else
			System.out.println("Error en la eliminacion de usuario. Verifique id.");
	}
	
	public void modificarUsuario() {
		Scanner sc = new Scanner (System.in);
		int id=0;
		boolean ok=false;
		String perfil="", contraseña="";
		String usuarioStr="";
		
		System.out.println("Ingrese id de usuario:");			
		id=sc.nextInt();
		sc.nextLine();
		
		
		usuarioStr=controlador.obtenerUsuarioDelSistema(id);
		
		if (usuarioStr.equals(""))
			System.out.println("Id no encontrado.");
		else {
			System.out.println("Se modificará la informacion el siguiente usuario:");
			System.out.println(controlador.obtenerUsuarioDelSistema(id));
		
			System.out.println("Ingrese perfil de usuario(administrado u operador):");			
			perfil=sc.nextLine();
			System.out.println("Ingrese contraseña de usuario:");			
			contraseña=sc.nextLine();
			
			if (controlador.modificarUsuarioDelSistema(id,perfil,contraseña))
				System.out.println("Se han modificado los datos.");
			else 
				System.out.println("No se ha logrado modificar los datos.");
		}
	}
}
```

