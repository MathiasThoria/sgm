package vista;

import java.util.Scanner;
import logica.Controlador;

public class VistaUsuariosDelSistema {

    private Controlador controlador;

    public VistaUsuariosDelSistema(Controlador controlador) {
        this.controlador = controlador;
    }

    public void menu() {
        int opcion = 0;
        Scanner sc = new Scanner(System.in);

        do {
            mostrarMenu();
            opcion = leerEntero(sc);
            
            switch (opcion) {
                case 0:
                    showMessageBox("Saliendo del menú");
                    break;
                case 1:
                    altaUsuario();
                    break;
                case 2:
                    bajaUsuario();
                    break;
                case 3:
                    modificarUsuario();
                    break;
                case 4:
                    mostrarUsuarios();
                    break;
                default:
                    showMessageBox("Opción inválida");
                    break;
            }
        } while (opcion != 0);
    }

    public void mostrarMenu() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║          MENÚ USUARIOS DEL SISTEMA        ║");
        System.out.println("╠════════════════════════════════════════════╣");
        System.out.println("║  1. Alta Usuario                           ║");
        System.out.println("║  2. Baja Usuario                           ║");
        System.out.println("║  3. Modificar Usuario                       ║");
        System.out.println("║  4. Mostrar Usuarios                        ║");
        System.out.println("║  0. Salir                                   ║");
        System.out.println("╚════════════════════════════════════════════╝");
        System.out.print("> Seleccione una opción: ");
    }

    public void mostrarUsuarios() {
        String usuarios = controlador.obtenerUsuariosDelSistema();
        if (usuarios.isEmpty()) {
            showMessageBox("No hay usuarios");
            return;
        }

        drawTopTitulo();
        drawTitulo("USUARIOS DEL SISTEMA");
        drawEncabezado();
        drawMiddle();

        String[] lineas = usuarios.split("\n");
        for (String linea : lineas) {
            if (!linea.trim().isEmpty()) {
                String[] campos = linea.split("\\|");
                if (campos.length >= 3) {
                    System.out.printf("║ %-3s ║ %-20s ║ %-20s ║%n",
                            campos[0].trim(),
                            campos[1].trim(),
                            campos[2].trim()
                    );
                }
            }
        }

        drawBottom();
    }

    public void altaUsuario() {
        Scanner sc = new Scanner(System.in);
        System.out.print("> Ingrese perfil: ");
        String perfil = sc.nextLine();
        System.out.print("> Ingrese contraseña: ");
        String contraseña = sc.nextLine();

        String resultado = controlador.altaUsuarioSistema(perfil, contraseña);
        showMessageBox(resultado);
    }

    public void bajaUsuario() {
        Scanner sc = new Scanner(System.in);
        System.out.print("> Ingrese ID de usuario: ");
        int id = leerEntero(sc);
        

        boolean ok = controlador.eliminarUsuarioSistema(id);
        if (ok)
            showMessageBox("Usuario eliminado.");
        else
            showMessageBox("Error en la eliminación. Verifique ID.");
    }

    public void modificarUsuario() {
        Scanner sc = new Scanner(System.in);
        System.out.print("> Ingrese ID de usuario: ");
        int id = leerEntero(sc);       

        String usuarioStr = controlador.obtenerUsuarioDelSistema(id);
        if (usuarioStr.equals("")) {
            showMessageBox("ID no encontrado");
            return;
        }

        showMessageBox("Se modificará la información del siguiente usuario:\n" + usuarioStr);

        System.out.print("> Ingrese perfil de usuario (administrador u operador): ");
        String perfil = sc.nextLine();
        System.out.print("> Ingrese contraseña de usuario: ");
        String contraseña = sc.nextLine();

        boolean ok = controlador.modificarUsuarioDelSistema(id, perfil, contraseña);
        if (ok)
            showMessageBox("Se han modificado los datos.");
        else
            showMessageBox("No se ha logrado modificar los datos.");
    }

    // =========================
    // Métodos auxiliares para bordes y tabla
    // =========================
    private void drawTopTitulo() {
        System.out.println("╔═══════════════════════════════════════════════════╗");
    }

    private void drawEncabezado() {
    	System.out.println("╠═════╦══════════════════════╦══════════════════════╣");
        System.out.printf("║ %-3s ║ %-20s ║ %-20s ║%n", "ID", "PERFIL", "CONTRASEÑA");
    }

    private void drawMiddle() {
        System.out.println("╠═════╬══════════════════════╬══════════════════════╣");
    }

    private void drawBottom() {
        System.out.println("╚═════╩══════════════════════╩══════════════════════╝");
    }

    private void drawTitulo(String titulo) {
        int totalWidth = 3 + 3 + 20 + 3 + 20 + 2; // ID + PERFIL + CONTRASEÑA + separadores
        int padding = (totalWidth - titulo.length()) / 2;
        System.out.printf("║%s%s%s║%n", repeat(' ', padding), titulo, repeat(' ', totalWidth - padding - titulo.length()));
    }

    private void showMessageBox(String mensaje) {        
        System.out.println("\n╔" + repeat('═',72) + "╗");        
        System.out.printf("║ %-70s ║%n", mensaje);        
        System.out.println("╚" + repeat('═',72) + "╝\n");
    }
    private String repeat(char c, int n) {
        String s = "";
        for (int i = 0; i < n; i++) {
            s += c;
        }
        return s;
    }
    private int leerEntero(Scanner sc) {
        int numero = 0;
        boolean valido = false;
        
        while (!valido) {
            try {                
                numero = Integer.parseInt(sc.nextLine());
                valido = true;  
            } catch (NumberFormatException e) {
                System.out.println("\n╔════════════════════════════════════════╗");
                System.out.println("║  [ERROR] Debe ingresar un número       ║");
                System.out.println("╚════════════════════════════════════════╝");    
                System.out.print("  > ");
            }
        }
        
        return numero;
    }
}
