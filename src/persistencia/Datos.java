package persistencia;
import java.util.Map;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.HashMap;

import java.io.FileInputStream;
import java.time.LocalDate;
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

/**
 * Clase Singleton con el objetivo de cargar archivo xls
 */
public class Datos {
	String rutaArchivo = "datos.xls";
	private static Datos instancia;
	private Workbook libro;
	private Sheet hoja;
	
	//Mapea atributos y posicion	 
	private static Map<String, Integer> mapaColumnaPosicion = new HashMap<>();
	//iniciliza atributo estatico al cargar la clase
	static {
	    int pos = 0;
	    //ojo incremento. pasa y despues suma
	    mapaColumnaPosicion.put("fechaPrestamo", pos++);
	    mapaColumnaPosicion.put("fechaDevolucion", pos++);
	    mapaColumnaPosicion.put("diasRetraso", pos++);

	    mapaColumnaPosicion.put("idUsuario", pos++);
	    mapaColumnaPosicion.put("apellidoUsuario", pos++);
	    mapaColumnaPosicion.put("nombreUsuario", pos++);
	    mapaColumnaPosicion.put("emailUsuario", pos++);
	    mapaColumnaPosicion.put("codigoBarrasUsuario", pos++);

	    mapaColumnaPosicion.put("cotaEjemplar", pos++);
	    mapaColumnaPosicion.put("codigoBarrasEjemplar", pos++);
	    mapaColumnaPosicion.put("idFichaBibliografica", pos++);
	    mapaColumnaPosicion.put("idBulletin", pos++);
	    mapaColumnaPosicion.put("idNotice", pos++);

	    mapaColumnaPosicion.put("tituloObra", pos++);
	    mapaColumnaPosicion.put("tipoDocumento", pos++);
	    mapaColumnaPosicion.put("prestamoCorto", pos++);
	}
	
	//Mapea atributos y columnas del XLS
	private static final Map<String, String> mapaColumnaAtributo = new HashMap<>(); 
	//iniciliza atributo estatico al cargar la clase
	static{
		 mapaColumnaAtributo.put("fechaPrestamo", "aff_pret_date");
		 mapaColumnaAtributo.put("fechaDevolucion", "aff_pret_retour");
		 mapaColumnaAtributo.put("diasRetraso", "retard");

		 mapaColumnaAtributo.put("idUsuario", "id_empr");
		 mapaColumnaAtributo.put("apellidoUsuario", "empr_nom");
		 mapaColumnaAtributo.put("nombreUsuario", "empr_prenom");
		 mapaColumnaAtributo.put("emailUsuario", "empr_mail");
		 mapaColumnaAtributo.put("codigoBarrasUsuario", "empr_cb");

		 mapaColumnaAtributo.put("cotaEjemplar", "expl_cote");
		 mapaColumnaAtributo.put("codigoBarrasEjemplar", "expl_cb");
		 mapaColumnaAtributo.put("idFichaBibliografica", "expl_notice");
		 mapaColumnaAtributo.put("idBulletin", "expl_bulletin");
		 mapaColumnaAtributo.put("idNotice", "idnot");

		 mapaColumnaAtributo.put("tituloObra", "tit");
		 mapaColumnaAtributo.put("tipoDocumento", "tdoc_libelle");
		 mapaColumnaAtributo.put("prestamoCorto", "short_loan_flag");
	};
	/**
	 * Constructor privado por singleton. Solo se ejecuta desde getInstancia.
	 * @throws Exception
	 */
	
	private Datos() throws Exception{
        FileInputStream archivo = new FileInputStream(rutaArchivo);
        libro = new HSSFWorkbook(archivo);
        hoja = libro.getSheetAt(0);
        archivo.close();
        //mapeo de nombres de atributos y posicion    
    	
	}
	
	/**
	 * Si no existe instancia se llama a constructor
	 * @return instancia estatica
	 * @throws Exception
	 */
	public static Datos getInstancia() throws Exception {
	        if (instancia == null) {
	            instancia = new Datos();
	        }
	        return instancia;
	}
	
	/**
	 * Metodo para actualizar en memoria el archivo Xls. Actualiza la instancia llamando 
	 * de nuevo al constructor.
	 * @throws Exception
	 */
	public static void actualizarArchivo() throws Exception {
        instancia = new Datos();
	}
	
	/**
	 * Devuelve el archivo xls en un arreglo de String donde cada elemento es una fila y
	 * los campos estan separados por coma
	 * 
	 * @return Arreglo de String. Celdas separadas por coma.
	 */
	public String[] getArchivoToArrString() {
	    int cantidadFilas = hoja.getLastRowNum() + 1; // filas desde 0
	    String[] lineas = new String[cantidadFilas];

	    for (int i = 0; i < cantidadFilas; i++) {
	        Row fila = hoja.getRow(i);
	        String linea = "";
	        if (fila != null) {
	            for (Cell celda : fila) {
	                linea += celda.toString() + ",";
	            }
	            linea = linea.trim();
	        }
	        lineas[i] = linea;
	    }

	    return lineas;
	}
	
	
	public static String getValorFromFilaAtributo(String fila, String atributo) {
		//Integer permite uso de null en la variable   
		Integer colIndex = mapaColumnaPosicion.get(atributo);	
		//System.out.println(atributo);
		//System.out.println(mapaColumnaPosicion.get(atributo));
		if (colIndex != null) {   
			String[] partes = fila.split(",");
			if(colIndex <= partes.length)				
				return partes[colIndex].trim();
		}	   
		return "";		
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
		
		if(!valor.isEmpty()) {			
			return (int)Double.parseDouble(valor);
		}else {
			return 0;
		}
			
	}
	
	
	
}