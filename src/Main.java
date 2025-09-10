import vista.Vista;
import logica.*;
import vista.*;
public class Main {
    public static void main(String[] args) throws Exception {
        Controlador controlador = new Controlador();
    	new Vista(controlador).iniciar();
    }
}
