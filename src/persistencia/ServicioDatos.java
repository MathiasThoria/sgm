package persistencia;
import java.util.Map;
import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ServicioDatos {
	private String rutaArchivo = "datos.xls";
	private String cadena = "";

	public ServicioDatos()throws Exception{
		
	    FileInputStream archivo = new FileInputStream(rutaArchivo);
	    Workbook libro = new HSSFWorkbook(archivo);
        Sheet hoja = libro.getSheetAt(0);
        
        
        int cantidadFilas = hoja.getLastRowNum(); // filas desde 0
	    //System.out.println(hoja.getLastRowNum() );
        
        
        
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
					        cadena += celda.toString();
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
