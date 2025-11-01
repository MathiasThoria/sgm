package vista;
import java.util.Scanner;

import logica.Controlador;

public class VistaDeudores {

	private Controlador controlador;
	
	public VistaDeudores(Controlador controlador) {
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
					System.out.println("Saliendo de Menu Deudores.");
				break;
				case 1:
					mostrarUsuarios();
				break;
				case 2:
					buscarUsuarioPorId();	
				break;
				default:
					System.out.println("Opcion inválida.");
				break;		
			}
			
		}while(opcion!=0);
	}
	
	public void mostrarMenu() {
		System.out.println();
		System.out.println("========MENU DEUDORES==========\n");
		System.out.println("0.Salir");		
		System.out.println("1.Mostrar Todos los Deudores.");
		System.out.println("2.Mostrar Libros de un Deudor.");		
		System.out.print("Seleccione una opcion: ");
	}
	private void mostrarUsuarios() {
		String vista= controlador.obtenerUsuarios();
		if (vista.isEmpty())
			System.out.println("\nSin Deudores.");
		else
			System.out.println(vista);
	}
    private void buscarUsuarioPorId() {
    	Scanner sc=new Scanner(System.in);
        System.out.print("Ingrese el ID del usuario: ");
        int idBuscado = Integer.parseInt(sc.nextLine());
        
        System.out.println(controlador.obtenerUsuarioPorId(idBuscado));
       
    }
}
