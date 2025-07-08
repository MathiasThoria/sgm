package test;
import modelo.Prestamo;
import persistencia.XlsParser;

public class TestPrestamo {
    public static void main(String[] args) throws Exception {
    	XlsParser parser =XlsParser.getInstancia();
    	String[] archivo = parser.getArchivoToArrString();  
        String filaEjemplo = "25/10/2018,27/10/2018,2,123,Soria,Mathias,mathi@mail.com,CB123," +
                "QA.123,CB456,987,456,789,Java Básico,Libro,false";

        Prestamo p = new Prestamo(archivo[1]);
        System.out.println(p);
    }
}
