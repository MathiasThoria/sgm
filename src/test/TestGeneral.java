package test;
import modelo.Prestamo;

public class TestGeneral {
    public static void main(String[] args) {
        String filaEjemplo = "25/10/2018,27/10/2018,2,123,Soria,Mathias,mathi@mail.com,CB123," +
                "QA.123,CB456,987,456,789,Java Básico,Libro,false";

        Prestamo p = new Prestamo(filaEjemplo);
        System.out.println(p);
    }
}
