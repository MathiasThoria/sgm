package test;
import persistencia.ServicioMensajeria;

public class TestEnviarMensaje {
	
	public static void main(String [] args) {
		String textoMensaje = "Hola mi mensaje";
		String destino ="eltartamudo01@gmail.com";
		ServicioMensajeria sm = new ServicioMensajeria();
		System.out.println(sm.enviar(textoMensaje,destino));
		
	}
}
