package vista;

import java.util.Scanner;

import controlador.Controlador;
	
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
						listarHistoricoMensajes();
					break;
					case 2:
						procesarMensajes();
					break;
					case 3:
						listarHistoricoMensajesPorUsuario();
					break;
					default:
					break;		
				}
				
			}while(opcion!=0);
		}
		
		public void mostrarMenu() {
			System.out.println();
			System.out.println("========MENU MENSAJERIA==========\n");
			System.out.println("0.Salir");		
			System.out.println("1. Listar historico de mensajes");
			System.out.println("2. Enviar mensajes de correo a cada Deudor");
			System.out.println("3. Listar mensajes por id de Deudor");
			System.out.println("4.");
			System.out.println("5.");
			System.out.print("Seleccione una opcion: ");
		}
		
	    private void procesarMensajes() {
	    	System.out.println("Mensajes enviados:" + controlador.procesarMensajesDeUsuarios());
	    }
	    
	    private void listarHistoricoMensajes() {
	    	System.out.println(controlador.obtenerHistoricoMensajes());
	    }
	    private void listarHistoricoMensajesPorUsuario() {
	    	Scanner sc=new Scanner(System.in);
	    	int id=0;
	    	System.out.print("Ingrese Id de Deudor:");
	    	id=sc.nextInt();
	    	sc.nextLine();
	    	
	    	System.out.println(controlador.obtenerHistoricoMensajesPorIdUsuario(id));
	    	
	    }

}
