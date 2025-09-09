package vista;

import controlador.Controlador;
import modelo.*;

import java.util.Scanner;

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
                default:
                    System.out.println(" Opción inválida");
            }
        }while(opcion!=0);
    }

    private void mostrarMenu() {
        System.out.println("\n====== MENÚ PRINCIPAL ======");
       /* System.out.println("0. Salir");
        System.out.println("1. Mostrar todos los usuarios y sus préstamos");
        System.out.println("2. Buscar usuario por ID");
        System.out.println("3. Procesar Mensajes");
        System.out.println("4. Obtener historico mensajes");
        System.out.println("5. Administracion de usuarios del sistema");*/
        System.out.println("0. Salir");
        System.out.println("1. Deudores");
        System.out.println("2. Libros Adeudados");
        System.out.println("3. Mensajeria");
        System.out.println("4. Usuarios del Sistema");        
        System.out.print("Seleccione una opción: ");
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
    private void procesarMensajes() {
    	System.out.println(controlador.procesarMensajesDeUsuarios());
    }
    
    private void listarHistoricoMensajes() {
    	System.out.println(controlador.obtenerHistoricoMensajes());
    }

    
    
    
    
    public static void main(String[] args) throws Exception {
        new Vista().iniciar();
    }
    
}