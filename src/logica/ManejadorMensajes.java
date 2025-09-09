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
    private ServicioMensajeria sm;
    
    public ManejadorMensajes() {
        this.historialMensajes = new Historias();
        this.sm = new ServicioMensajeria();
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
                    boolean enviado = this.sm.enviar(textoMensaje, usuario.getEmail());
                    
                    if (enviado) {
                        // Registrar en historial si fue exitoso
                        MensajeEnviado mensaje = new MensajeEnviado(
                            0, // ID se asigna automáticamente en Historias
                            obtenerFechaActual(),
                            usuario.getId(),
                            usuario.getEmail(),
                            titulosYDias,
                            textoMensaje
                        );
                        
                        historialMensajes.agregarMensaje(mensaje);
                        
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
        
        // Resumen final
        resultado += "\n=== RESUMEN ===\n";
        resultado += "Mensajes enviados: " + enviados + "\n";
        resultado += "Errores: " + errores + "\n";
      
        
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
        
        return prestamosAtrasados + ". Con un total de " + diasTotal + "de atraso.\n";
    }
    
    /**
     * Genera el texto del mensaje personalizado para cada usuario
     */
    private String generarTextoMensaje(Usuario usuario, String titulosYDias) {
        String mensaje = "Estimado/a " + usuario.getNombre() + " " + usuario.getApellido() + ",\n\n";
        mensaje += "Le escribimos desde la Biblioteca para recordarle que tiene los siguientes materiales vencidos:\n\n";
        mensaje += titulosYDias;
        /*
        for (Prestamo prestamo : usuario.getListaPrestamos().getListaPrestamos()) {
            String codigoBarras = prestamo.getCodigoBarrasEjemplar();
            if (prestamosAtrasados.containsKey(codigoBarras)) {
                int diasAtraso = prestamosAtrasados.get(codigoBarras);
                mensaje += "• " + prestamo.getTituloObra() +
                          " (Código: " + codigoBarras + ")" +
                          " - " + diasAtraso + " día(s) de atraso" +
                          " - Vencía: " + prestamo.getFechaDevolucion() + "\n";
            }
        }*/
        
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
        if (historialMensajes.getMensajesEnviados().isEmpty()) {
            return "No hay mensajes enviados en el historial.";
        }
        
        String resultado = "=== HISTORIAL DE MENSAJES ENVIADOS ===\n\n";
        
        for (MensajeEnviado mensaje : historialMensajes.getMensajesEnviados()) {
            resultado += "ID: " + mensaje.getId() +
                        " | Fecha: " + mensaje.getFechaEnvio() +
                        " | Usuario ID: " + mensaje.getIdUsuario() +
                        " | Email: " + mensaje.getCorreo() +
                        " | Libros: " + mensaje.getTitulosYDias() + "\n";
        }
        
        return resultado;
    }
    
    /**
     * Buscar mensajes por usuario específico
     */
    public String obtenerMensajesPorIdUsuario(int idUsuario) {
        Historias mensajesUsuario = historialMensajes.buscarMensajesPorUsuario(idUsuario);
        
        if (mensajesUsuario.getMensajesEnviados().isEmpty()) {
            return "No se encontraron mensajes para el usuario ID: " + idUsuario;
        }
        
        String resultado = "=== MENSAJES DEL USUARIO ID: " + idUsuario + " ===\n\n";
        
        for (MensajeEnviado mensaje : mensajesUsuario.getMensajesEnviados()) {
            resultado += "Fecha: " + mensaje.getFechaEnvio() +
                        " | Email: " + mensaje.getCorreo() +
                        " | Libros atrasados: " + mensaje.getTitulosYDias() + "\n";
        }
        
        return resultado;
    }
    
    
    
    
}