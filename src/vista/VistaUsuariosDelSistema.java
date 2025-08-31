package vista;
import java.util.Scanner;

import controlador.Controlador;

public class VistaUsuariosDelSistema {
	private Controlador controlador;
	
	public VistaUsuariosDelSistema(Controlador controlador) {
		this.controlador = controlador;
	}
	
	public void menu() {
		int opcion= 0;
		do {
			mostrarMenu();
			opcion=obtenerOpcion();
			
			switch(opcion) {
			case 1:
				
				break;
			case 2:
	
				break;
			case 3:
				
				break;
			case 4:
				
				break;
			case 5:
				
				break;
			default:
				System.out.println("Ingrese una opcion valida.");
		

			}
		
		}while(opcion!=0);
			
		
	}
	
	
	public void mostrarMenu() {
		
		System.out.println("========MENU USUARIOS DEL SISTEMA==========\n");
		System.out.println("0.Salir");		
		System.out.println("1.Alta Usuario");
		System.out.println("2.Baja Usuario");
		System.out.println("3.Modificar Usuario");
		System.out.println("4.Mostrar Usuarios");
		System.out.println("5.");
		System.out.println("Seleccione una opcion:");
	}
	
	public int obtenerOpcion() {
	   	Scanner sc=new Scanner(System.in);
        int opcion = sc.nextInt();
        //sc.nextLine();
        //sc.close();
        return opcion; 
	}
	
	
}
