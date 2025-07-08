package logica;
import persistencia.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import modelo.*;

/**
 * Responsabilidades: 
 * - Cargar datos del xls al string
 * - Devolver String formateados de las colecciones 
 */
public class ManejadorDatos {
	Usuarios coleccionUsuario;
	Datos archivoXls;
	
	public ManejadorDatos() throws Exception {
		archivoXls = new Datos();//queda en cadena el archivo cargado		
		coleccionUsuario = new Usuarios();//crea lista vacia	
		//coleccionUsuario = parserDatosToUsuarios();
	}
	
	public Usuarios getColeccionUsuario() {
		return coleccionUsuario;
	}
	
	public Usuarios parserDatosToUsuarios() {
		String datosCrudos = archivoXls.getCadena();
	    String[] filas = datosCrudos.split(";");
	    coleccionUsuario=new Usuarios();
	    
	    for (String fila : filas) {
	        if (!fila.isEmpty()) { 
	
		        String[] campos = fila.split(",");
		     
	
		        try {
		            /* Parsear datos del usuario		          
					 * Asignar la posicion directamente es endeble
					 * Queda para hacer un mapping como tenias antes, que vincule como teniamos en XlsParser anterior
					 *    
					    "fechaPrestamo", 0);
					    "fechaDevolucion", 1);
					    "diasRetraso", 2);
				
					    "idUsuario", 3);
					    "apellidoUsuario", 4);
					    "nombreUsuario", 5);
					    "emailUsuario", 6);
					    "codigoBarrasUsuario", 7);
				
					    "cotaEjemplar", 8);
					    "codigoBarrasEjemplar", 9);
					    "idFichaBibliografica", 10);
					    "idBulletin", 11);
					    "idNotice", 12);
				
					    "tituloObra", 13);
					    "tipoDocumento", 14);
					    "prestamoCorto", 15);
					}
					 * 
					 * */
		        	
					// falta chequear que campos no esten vaciois porq sino tira
					// Exception en los cambios de tipo  
					//

		            Usuario usuario = new Usuario(
		            		parseNumero(campos[3]),
		            		campos[4],
		            		campos[5],
		            		campos[6],
		            		campos[7],
		            		new Prestamos());
	
		            // Parsear datos del préstamo
		            
		            
		            
		            Prestamo prestamo = new Prestamo(
		            		parseFecha(campos[0]), 
		            		parseFecha(campos[1]), 
		            		Boolean.parseBoolean(campos[2]),
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
		            //

		            //usuario.getListaPrestamos().agregarPrestamo(prestamo);
		            // ojo hay que cheeuquar tambien si existe el prestamo de ese usuario
		            //  el prestamo que quiero agregar
		            if (!coleccionUsuario.existeIdUsuario(usuario.getId())) {
		            	usuario.getListaPrestamos().agregarPrestamo(prestamo);		            
		            	coleccionUsuario.agregarUsuario(usuario);
		            }else
		            	coleccionUsuario.buscarUsuario(usuario.getId())
		            	.getListaPrestamos()
		            	.agregarPrestamo(prestamo);
	
		        } catch (Exception e) {
		            System.out.println("Error al procesar fila: " + fila);
		            e.printStackTrace();
		        }
	        }
	    }

	    return coleccionUsuario;

	}
	
	
	public static LocalDate parseFecha(String fechaStr) {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    //System.out.println(fechaStr);
	    try {
	        return LocalDate.parse(fechaStr, formatter);
	    } catch (DateTimeParseException e) {
	        e.printStackTrace();
	        return null; 
	    }	    
	}
	
	public static int parseNumero(String valor) {
		//System.out.println("-"+valor+valor.equals(" "));
		
		//cuando una celda viene vacia no es "" sino " "
		if(!valor.equals(" ")) {			
			return (int)Double.parseDouble(valor);
		}else {
			return 0;
		}
			
	}
	
	 public String obtenerUsuariosComoString() {
    	 
    	 String s="";    	 
         for (Usuario u : coleccionUsuario.getColeccionUsuario()) {
             s+="Usuario: " + u.getNombre() + " | ID: " + u.getId();
             
             for (Prestamo p : u.getListaPrestamos().getListaPrestamos()) {
            	 s+=" \n  → " + p.getTituloObra() + " (" + p.getFechaPrestamo() + " → " + p.getFechaDevolucion() + ")";
             }
             
         }
         return s;
    }
    public String obtenerUsuarioPorIdComoString(int id) {      
    	String s="";
        Usuario encontrado = coleccionUsuario.buscarUsuario(id);
        if (encontrado != null) {
            s+="Usuario: " + encontrado.getNombre();
            for (Prestamo p : encontrado.getListaPrestamos().getListaPrestamos()) {
            	s+="\n   → " + p.getTituloObra();
            }
        } else {
            s+="Usuario no encontrado.";
        }
        return s;
    }
	
	
}
