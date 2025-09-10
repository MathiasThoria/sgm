import vista.Vista;
import logica.*;


public class Main {
    public static void main(String[] args) throws Exception {
        Controlador controlador = new Controlador();
    	new Vista(controlador).iniciar();
    }
}
