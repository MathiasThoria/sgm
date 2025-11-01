package persistencia;
import java.util.Map;
import java.io.FileInputStream;

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
		
	    FileInputStream archivo = new FileInputStream(rutaArchivo);
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
	}
	
	public String getCadena() {
	    return cadena;
	}	
}
