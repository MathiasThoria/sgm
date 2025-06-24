package modelo.persistencia;
import java.util.Map;
import java.util.HashMap;

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

/**
 * Clase Singleton con el objetivo de cargar archivo xls
 */
public class XlsParser {
	String rutaArchivo = "archivo.xls";
	private static XlsParser instancia;
	private Workbook libro;
	private Sheet hoja;
	/**
	 * Mapea atributos y posicion
	 */
	private Map<String, Integer> mapaAtributos = new HashMap<>();
	/**
	 * Constructor privado por singleton. Solo se ejecuta desde getInstancia.
	 * @throws Exception
	 */
	
	private XlsParser() throws Exception{
        FileInputStream archivo = new FileInputStream(rutaArchivo);
        libro = new HSSFWorkbook(archivo);
        hoja = libro.getSheetAt(0);
        archivo.close();
        //mapeo de nombres de atributos y posicion
        mapaAtributos.put("nombre", 0);
        mapaAtributos.put("edad", 1);
        mapaAtributos.put("apellido", 2);    		
    	
	}
	
	/**
	 * Si no existe instancia se llama a constructor
	 * @return instancia estatica
	 * @throws Exception
	 */
	public static XlsParser getInstancia() throws Exception {
	        if (instancia == null) {
	            instancia = new XlsParser();
	        }
	        return instancia;
	}
	
	/**
	 * Metodo para actualizar en memoria el archivo Xls. Actualiza la instancia llamando 
	 * de nuevo al constructor.
	 * @throws Exception
	 */
	public static void actualizarArchivo() throws Exception {
        instancia = new XlsParser();
	}
	
	/**
	 * Devuelve el archivo xls en un arreglo de String donde cada elemento es una fila y
	 * los campos estan separados por coma
	 * 
	 * @return Arreglo de String. Celdas separadas por coma.
	 */
	public String[] getFilasToString() {
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
	
	
	public String getAtributoFromFila(String fila, String atributo) {
		//Integer permite uso de null en la variable   
		Integer colIndex = mapaAtributos.get(atributo.toLowerCase());	    
		
		if (colIndex != null) {   
			String[] partes = fila.split(",");
			if(colIndex <= partes.length)
				return partes[colIndex].trim();
		}
	   
		return "";
		
	}
	
	
}