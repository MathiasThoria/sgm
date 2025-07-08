package persistencia;

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

	

