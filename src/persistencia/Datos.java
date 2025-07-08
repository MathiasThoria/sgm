package persistencia;
import java.util.Map;
import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class Datos {
	private String rutaArchivo = "datos.xls";
	private String cadena;

	public Datos()throws Exception{
		
	    FileInputStream archivo = new FileInputStream(rutaArchivo);
	    Workbook libro = new HSSFWorkbook(archivo);
        Sheet hoja = libro.getSheetAt(0);
        
        
        int cantidadFilas = hoja.getLastRowNum(); // filas desde 0
	    System.out.println(hoja.getLastRowNum() );
        cadena = "";
        
	    //for (int i = 0; i < cantidadFilas; i++) {
        for(Row fila : hoja) {
	        //Row fila = hoja.getRow(i);	        
	        if (fila != null) {
		        int ultimaCelda = fila.getLastCellNum();		

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
	    
        archivo.close();
	}
	
	public String getCadena() {
	    return cadena;
	}

	

	
}
