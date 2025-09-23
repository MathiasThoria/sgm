package vista;

import java.util.Scanner;

import logica.Controlador;
	
	public class VistaMensajeria {
	private Controlador controlador;
		
		public VistaMensajeria(Controlador controlador) {
			this.controlador=controlador;		
		}
		
		public void menu() {
			int opcion=0;
			Scanner sc=new Scanner(System.in);			
			do {
				mostrarMenu();
				opcion = sc.nextInt();
	            sc.nextLine();
				switch(opcion) {
					case 0:
						System.out.println("Saliendo de menú Mensajería.");
					break;
					case 1:
						procesarMensajes();
					break;
					case 2:
						listarHistoricoMensajes();						
					break;
					case 3:
						listarHistoricoMensajesPorUsuario();
					break;
					case 4:
						borrarMensajeEnviado();
					break;
					case 10:
						borrarTodasHistoriasBD();
					default:
					break;		
				}
				
			}while(opcion!=0);
		}
		
		public void mostrarMenu() {
			System.out.println();
			System.out.println("========MENU MENSAJERIA==========\n");
			System.out.println("0.Salir");		
			System.out.println("1. Enviar mensajes de correo a cada Deudor");
			System.out.println("2. Mostrar todos los mensajes enviados");
			System.out.println("3. Mostrar mensajes por id de Deudor");
			System.out.println("4. Eliminar mensaje enviado");
			System.out.println("10. Borrar todas las historias de la BD");
			
			System.out.print("Seleccione una opcion: ");
		}
		
	    private void procesarMensajes() {
	    	System.out.println("Mensajes enviados:" + controlador.procesarMensajesDeUsuarios());
	    }
	    
	    private void listarHistoricoMensajes() {
	    	System.out.println("---------Historico de Mensajes Enviados--------");
	    	System.out.println(controlador.obtenerHistoricoMensajes());
	    }
	    
	    private void listarHistoricoMensajesPorUsuario() {
	    	Scanner sc=new Scanner(System.in);
	    	int id=0;
	    	System.out.print("Ingrese Id de Deudor:");
	    	id=sc.nextInt();
	    	sc.nextLine();
	    	System.out.println("-------Lista de mensajes por usuario-------");
	    	System.out.println(controlador.obtenerHistoricoMensajesPorIdUsuario(id));
	    	
	    }
	    
	    public void borrarTodasHistoriasBD() {
	    	controlador.borrarTodasHistoriasBD();
	    }
	    
	    public void borrarMensajeEnviado() {
	    	Scanner sc = new Scanner(System.in);
	    	int id=0;
	    	System.out.println("Ingrese id de mensaje:");
	    	id=sc.nextInt();
	    	sc.nextLine();
	    	if (controlador.eliminarMensajeEnviado(id))
	    		System.out.println("Mensaje eliminado satisfactoriamente.");
	    	else
	    		System.out.println("No se ha podido eliminar el mensaje.");
	    }
}
