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
    public void setHistorialMensajes(Historias h) {
        this.historialMensajes = h;
    }

    public void setMensajesSinPersistir(Historias h) {
        this.mensajesSinPersistir = h;
    }

    public ServicioMensajeria getServicioMensajeria() {
        return sm;
    }

    public void setServicioMensajeria(ServicioMensajeria sm) {
        this.sm = sm;
    }
    public Historias getHistorialMensajes() {
        return historialMensajes;
    }
    
    /**
     * Método principal: procesa todos los usuarios y envía mensajes
     * @param usuarios Colección de usuarios deudores
     * @return Resumen de envíos realizados
     */ 
    public String procesarEnvioMensajes(Usuarios usuarios) {
        String resultado = "";
        
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
                    
                    //Intentar envío - mock
                    boolean enviado = this.sm.enviar(textoMensaje, usuario.getEmail());
                    //boolean enviado=true;
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
                        
                        resultado += "|" + usuario.getNombre() + 
                                   " (" + usuario.getEmail() + ")";
                        enviados++;
                        
                    } else {
                        resultado += "|ERROR: No se pudo enviar a " + usuario.getNombre() + 
                                   " (" + usuario.getEmail() + ")";
                        errores++;
                    }
                } else {
                    resultado += "|SIN ATRASOS: " + usuario.getNombre() + 
                               " - No tiene préstamos vencidos";
                }
                
            } catch (Exception e) {
                resultado += " EXCEPCIÓN: Error procesando usuario " + usuario.getNombre() + 
                           " - " + e.getMessage();
                errores++;
            }
        }
        
        return resultado;
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
    
    
}