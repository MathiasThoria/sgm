package vista;

import modelo.*;

import java.util.Scanner;

import logica.Controlador;

public class Vista {
    private Controlador controlador;
    

    public Vista() throws Exception {    	
    	controlador = new Controlador();       
        
    }

    public void iniciar() {
    	Scanner sc=new Scanner(System.in);
        int opcion = 0;
        do{
            mostrarMenu();
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    VistaDeudores vDeudores= new VistaDeudores(controlador);
                    vDeudores.menu();
                    break;
                case 2:
                    VistaLibrosAdeudados vLibrosAdeudados= new VistaLibrosAdeudados(controlador);
                    vLibrosAdeudados.menu();
                    break;
                case 3:
                	VistaMensajeria vMensajeria = new VistaMensajeria(controlador);
                	vMensajeria.menu();                    
                    break;
                case 4:
                	VistaUsuariosDelSistema usuariosDelSistemaMenu = new VistaUsuariosDelSistema(controlador);
                	usuariosDelSistemaMenu.menu();
                	break;                
                case 0:
                	System.out.println(" Hasta luego!");
                	break;
                default:
                    System.out.println(" Opción inválida");
                    break;
            }
        }while(opcion!=0);
    }

    private void mostrarMenu() {
    	System.out.println();
        System.out.println("\n====== MENÚ PRINCIPAL ======");
      
        System.out.println("0. Salir");
        System.out.println("1. Deudores");
        System.out.println("2. Libros Adeudados");
        System.out.println("3. Mensajeria");
        System.out.println("4. Usuarios del Sistema");        
        System.out.print("Seleccione una opción: ");
    }
    
    public static void main(String[] args) throws Exception {
        new Vista().iniciar();
    }
    
}