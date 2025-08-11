package test;
import persistencia.ServicioMensajeria;

public class TestEnviarMensaje {
	
	public static void main(String [] args) {
		String textoMensaje = "Hola mi mensaje";
		String destino ="mathiassoriapiro@gmail.com";
		System.out.println(persistencia.ServicioMensajeria.enviar(textoMensaje,destino));
		
	}
}
