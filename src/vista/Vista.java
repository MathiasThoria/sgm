package vista;

import controlador.Controlador;
import modelo.*;

import java.util.Scanner;

public class Vista {
    private Controlador controlador;
    private Scanner sc;

    public Vista() throws Exception {
        controlador = new Controlador();
        sc = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion = -1;
        while (opcion != 3) {
            mostrarMenu();
            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1:
                    mostrarUsuarios();
                    break;
                case 2:
                    buscarUsuarioPorId();
                    break;
                case 3:
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
        Usuarios usuarios = controlador.obtenerUsuariosDesdeArchivo();
        for (Usuario u : usuarios.getColeccionUsuario()) {
            System.out.println("Usuario: " + u.getNombre() + " | ID: " + u.getId());
            
            for (Prestamo p : u.getListaPrestamos().getListaPrestamos()) {
                System.out.println("   → " + p.getTituloObra() + " (" + p.getFechaPrestamo() + " → " + p.getFechaDevolucion() + ")");
            }
            System.out.println();
        }
    }

    private void buscarUsuarioPorId() {
        System.out.print("Ingrese el ID del usuario: ");
        int idBuscado = Integer.parseInt(sc.nextLine());

        Usuario encontrado = controlador.getManejadorDatos().getColeccionUsuario().buscarUsuario(idBuscado);
        if (encontrado != null) {
            System.out.println("Usuario: " + encontrado.getNombre());
            for (Prestamo p : encontrado.getListaPrestamos().getListaPrestamos()) {
                System.out.println("   → " + p.getTituloObra());
            }
        } else {
            System.out.println("Usuario no encontrado.");
        }
    }

    public static void main(String[] args) throws Exception {
        new Vista().iniciar();
    }
}