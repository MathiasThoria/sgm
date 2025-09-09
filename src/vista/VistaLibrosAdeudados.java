package vista;

import controlador.Controlador;
import java.util.Scanner;

public class VistaLibrosAdeudados {
	private Controlador controlador;
		
		public VistaLibrosAdeudados(Controlador controlador) {
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
					case 1:
					break;
					default:
					break;		
				}
				
			}while(opcion!=0);
		}
		
		public void mostrarMenu() {
			System.out.println("========MENU LIBROS ADEUDADOS==========\n");
			System.out.println("0.Salir");		
			System.out.println("1.");
			System.out.println("2.");
			System.out.println("3.");
			System.out.println("4.");
			System.out.println("5.");
			System.out.println("Seleccione una opcion:");
		}
}
