import vista.Vista;
import logica.*;


public class Main {
    public static void main(String[] args) throws Exception {
        Controlador controlador = new Controlador();
    	Vista v = new Vista(controlador);
    	v.iniciar();
    }
}
