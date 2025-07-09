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
        String opcion = "";
        while (!opcion.equals("3")) {
            mostrarMenu();
            opcion = sc.nextLine();

            switch (opcion) {
                case "1":
                    mostrarUsuarios();
                    break;
                case "2":
                    buscarUsuarioPorId();
                    break;
                case "3":
                    System.out.println(" Hasta luego!");
                    break;
                default:
                    System.out.println(" Opción inválida");
            }
        }
    }

    private void mostrarMenu() {
        System.out.println("\n====== MENÚ PRINCIPAL ======");
        System.out.println("1. Mostrar todos los usuarios y sus préstamos");
        System.out.println("2. Buscar usuario por ID");
        System.out.println("3. Salir");
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

    public static void main(String[] args) throws Exception {
        new Vista().iniciar();
    }
}