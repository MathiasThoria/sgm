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
