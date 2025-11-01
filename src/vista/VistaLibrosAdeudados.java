package vista;
/*
 * DEPRECATED
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */

import java.util.Scanner;

import logica.Controlador;

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
					case 0:
						System.out.println("Saliendo de Menu Libros Adeudados");						
					break;
					case 1:
						mostrarLibrosAdeudados();
					break;
					case 2:
						//en construccion
					break;
					case 3:
						//en construccion
					break;
					default:
						System.out.println("Ingrese una opcion válida");
					break;		
				}
				
			}while(opcion!=0);
		}
		
		public void mostrarMenu() {
			System.out.println();
			
			System.out.println("========MENU LIBROS ADEUDADOS==========\n");
			System.out.println("0.Salir");		
			System.out.println("1.Mostrar Libros Adeudados");
			System.out.println("2.Buscar libro por titulo");
			System.out.println("3.Listar Deudores por libro");
			System.out.print("Seleccione una opcion: ");
		}
		public void mostrarLibrosAdeudados() {
			System.out.println("Lista de libros adeudados:");
			//System.out.println(controlador.obtenerListaLibros());
		}
}
