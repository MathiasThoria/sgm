package test;
import modelo.Prestamo;
import modelo.Prestamos;
import persistencia.XlsParser;

public class TestPrestamos {

	public static void main(String[] args)throws Exception {
		XlsParser parser =XlsParser.getInstancia();
        
        //Creamos Prestamo desde archvio y agregamos a lista
		String[] archivo = parser.getArchivoToArrString();
		Prestamo p = new Prestamo(archivo[1]);
        Prestamos lista= new Prestamos();
        lista.addPrestamo(p);
        System.out.println(".............................................");        
        System.out.println(lista.toString());
        
        //Creamos Prestamo desde ejemplo y agregamos a lista
        String filaEjemplo = "25/10/2018,27/10/2018,2,123,Soria,Mathias,mathi@mail.com,CB123," +
                "QA.123,CB456,987,456,789,Java Básico,Libro,false";
        Prestamo p2 = new Prestamo(filaEjemplo);
        lista.addPrestamo(p2);
        
        System.out.println(".............................................");        
        System.out.println(lista.toString());
	}

}
