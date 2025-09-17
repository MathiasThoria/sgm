package vista;
import java.util.Scanner;

import logica.Controlador;

public class VistaUsuariosDelSistema {
	private Controlador controlador;
	
	public VistaUsuariosDelSistema(Controlador controlador) {
		this.controlador = controlador;
	}
	
	public void menu() {
		int opcion= 0;		
		Scanner sc=new Scanner(System.in);		
		do {			
			mostrarMenu();
			opcion = sc.nextInt();
            sc.nextLine();
			switch(opcion) {
			case 1:
				//en construccion
				break;
			case 2:
				//en construccion
				break;
			case 3:
				//en construccion
				break;
			case 4:
				mostrarUsuarios();
				break;
			case 5:
				//en construccion
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
		System.out.print("Seleccione una opcion: ");
	}
	
	public int obtenerOpcion() {
	   	Scanner sc=new Scanner(System.in);
        int opcion = sc.nextInt();
        //sc.nextLine();
        //sc.close();
        return opcion; 
	}
	
	public void mostrarUsuarios() {
	//	System.out.println(controlador.obtenerUsuariosDelSistema());
	}
}
