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
					System.out.println("\n╔════════════════════════════════╗");
                    System.out.println("║        Saliendo de menu        ║");
                    System.out.println("╚════════════════════════════════╝");
				break;
				case 1:
					mostrarUsuarios();
				break;
				case 2:
					buscarUsuarioPorId();	
				break;
				default:
					System.out.println("\n╔════════════════════════════════╗");
                    System.out.println("║        Opción inválida         ║");
                    System.out.println("╚════════════════════════════════╝");
				break;		
			}
			
		}while(opcion!=0);
	}
	
	public void mostrarMenu() {
	    System.out.println("\n╔════════════════════════════════════════════╗");
	    System.out.println("║           MENU DEUDORES                    ║");
	    System.out.println("╠════════════════════════════════════════════╣");
	    System.out.println("║  1. Mostrar Todos los Deudores             ║");
	    System.out.println("║  2. Buscar Libros de un Deudor             ║");
	    System.out.println("║  0. Volver al Menu Principal               ║");
	    System.out.println("╚════════════════════════════════════════════╝");
	    System.out.print("  > Seleccione una opcion: ");
	}
	private void mostrarUsuarios() {
		String vista= controlador.obtenerUsuarios();
		if (vista.isEmpty())
			System.out.println("\nSin Deudores.");
		else {
			System.out.println("\n╔════════════════════════════════════════════════════════════════════════════════╗");
		    System.out.println("║                               LISTADO DE DEUDORES                              ║");
		    System.out.println("╠════════╦══════════════════════╦══════════════════════╦═════════════════════════╣");
		    System.out.printf("║ %-6s ║ %-20s ║ %-20s ║ %-23s ║%n", "ID", "APELLIDO", "NOMBRE", "EMAIL");
		    System.out.println("╠════════╬══════════════════════╬══════════════════════╬═════════════════════════╣");
		    
		    String[] lineas = vista.split("\n");
		    for (String linea : lineas) {
		        if (!linea.trim().isEmpty()) {
		            String[] campos = linea.split("\\|");
		            if (campos.length >= 4) {
		                System.out.printf("║ %-6s ║ %-20s ║ %-20s ║ %-20s ║%n",
		                    campos[0].trim(),
		                    campos[1].trim(),
		                    campos[2].trim(),
		                    campos[3].trim()
		                );
		            }
		        }
		    }
		    System.out.println("╚════════╩══════════════════════╩══════════════════════╩═════════════════════════╝");
		}
			
	}
	
	private void buscarUsuarioPorId() {
	    Scanner sc = new Scanner(System.in);
	    System.out.print("\n  > Ingrese el ID del usuario: ");
	    int idBuscado = Integer.parseInt(sc.nextLine());
	    
	    String datos = controlador.obtenerLibrosDeUsuarioPorId(idBuscado);
	    
	    if (datos.contains("no encontrado") || datos.isEmpty()) {
	        System.out.println("\n╔════════════════════════════════════════════╗");
	        System.out.println("║  [ERROR] Usuario no encontrado             ║");
	        System.out.println("╚════════════════════════════════════════════╝\n");
	        return;
	    }
	    
	    // Deserializar préstamos
	    String[] lineas = datos.split("\n");
	    
	    System.out.println("\n╔══════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
	    System.out.println("║                              PRESTAMOS DEL USUARIO ID:            " + idBuscado + "                                    ║");
	    System.out.println("╠═══════════════╦═══════════════╦═════════╦════════════════════════════════════════════════════════════════╣");
	    System.out.printf("║ %-13s ║ %-13s ║ %-7s ║ %-62s ║%n", 
	        "F. PRESTAMO", "F. DEVOLUCION", "ATRASO", "TITULO");
	    System.out.println("╠═══════════════╬═══════════════╬═════════╬════════════════════════════════════════════════════════════════╣");
	    
	    int contador = 0;
	    for (String linea : lineas) {
	        if (!linea.trim().isEmpty()) {
	            String[] campos = linea.split("\\|");
	            if (campos.length >= 9) {
	                System.out.printf("║ %-13s ║ %-13s ║ %-7s ║ %-62s ║%n",
	                    campos[0].trim(),  // fecha prestamo
	                    campos[1].trim(),  // fecha devolucion
	                    campos[2].trim(),  // dias atraso
	                    campos[8].trim()   // titulo
	                );
	                contador++;
	            }
	        }
	    }
	    
	    System.out.println("╚═══════════════╩═══════════════╩═════════╩════════════════════════════════════════════════════════════════╝");
	    System.out.println("  Total de prestamos: " + contador + "\n");
	}
}
