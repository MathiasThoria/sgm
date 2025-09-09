package vista;
import controlador.Controlador;
import java.util.Scanner;

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
		System.out.println("1.Mostrar Todos los Deudores y sus préstamos");
		System.out.println("2.Buscar Deudor por Id");
		System.out.println("3.");
		System.out.println("4.");
		System.out.println("5.");
		System.out.print("Seleccione una opcion: ");
	}
	private void mostrarUsuarios() {
	       System.out.println(controlador.obtenerUsuariosComoString());
	}
    private void buscarUsuarioPorId() {
    	Scanner sc=new Scanner(System.in);
        System.out.print("Ingrese el ID del usuario: ");
        int idBuscado = Integer.parseInt(sc.nextLine());
        
        System.out.println(controlador.obtenerUsuarioPorIdComoString(idBuscado));
       
    }
}
