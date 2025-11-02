package vista;

import java.util.Scanner;

import logica.Controlador;

public class Vista {
    private Controlador controlador;  

    public Vista(Controlador controlador) throws Exception {    	
    	this.controlador = controlador;
    }

    public void iniciar() {
    	Scanner sc=new Scanner(System.in);
        int opcion = 0;
        int usuario=0;
        String pass="";
        String perfilUsuario="";
        boolean contraseñaOk=false;        
        
        do {        	
        	System.out.print("\n╔════════════════════════════════╗\n");
            System.out.print("║        INICIO DE SESIÓN        ║\n");
            System.out.print("╚════════════════════════════════╝\n");

            System.out.print("  > Usuario: ");
            usuario = sc.nextInt();
            sc.nextLine();
            System.out.print("  > Contraseña: ");
            pass = sc.nextLine();
        	
        	contraseñaOk=controlador.verificarContraseñaUsuarioSistema(usuario, pass);
	        if(contraseñaOk) {
	        	perfilUsuario=controlador.obtenerPerfilUsuarioSistema(usuario);
	        	System.out.println("\n╔══════════════════════════════════════════╗");
	        	System.out.printf("║  Ha ingresado con perfil: %-10s  ║%n", perfilUsuario);
                System.out.println("╚══════════════════════════════════════════╝");
	        	
	        	do{ 
	            	mostrarMenuPrincipal(perfilUsuario);
	                opcion = sc.nextInt();
	                sc.nextLine();
	
	                switch (opcion) {
	                    case 1:
	                        VistaDeudores vDeudores= new VistaDeudores(controlador);
	                        vDeudores.menu();
	                        break;	                    
	                    case 2:
	                    	VistaMensajeria vMensajeria = new VistaMensajeria(controlador);
	                    	vMensajeria.menu();                    
	                        break;
	                    case 3:
	                    	if (perfilUsuario.equals("administrador")) {	                    		
	                    		VistaUsuariosDelSistema usuariosDelSistemaMenu = new VistaUsuariosDelSistema(controlador);
	                    		usuariosDelSistemaMenu.menu();
	                    	}else
	                    		mostrarOpcionInvalida();
	                    	break;       
	                    case 4:
	                    	emitirConstancia();
	                    	break;
	                    case 0:
	                    	System.out.println("\n╔════════════════════════════════╗");
	                        System.out.println("║          Hasta luego!          ║");
	                        System.out.println("╚════════════════════════════════╝");
	                    	break;
	                    default:
	                        mostrarOpcionInvalida();
	                        break;
	                }
	            }while(opcion!=0);
	        	
	        }else {
	        	System.out.println("Usuario o contraseña incorrecta.");
	        }
	        
    	}while(!contraseñaOk);
        
        
    }

    // Solo se conocen los Usuarios que alguna vez se atrasaron en sus prestamos (para nosotros "Deudores") 
    // Si no existe usuario, significa que no es Deudor. Se asume que su inexistencia en xls es pago de deuda
    public void emitirConstancia() {
    	Scanner sc=new Scanner(System.in);
    	int id=0;
    	System.out.println("Ingrese id de usuario:");
    	id=sc.nextInt();
    	System.out.println();
    	if (!controlador.existeUsuario(id))
    		System.out.println(controlador.obtenerConstancia(id));
    	else
    		System.out.println("El usuario es deudor. No se puede emitir constancia.");
    	
    }
    private void mostrarMenuPrincipal(String perfilUsuario) {
        System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║         MENÚ PRINCIPAL         ║");
        System.out.println("╠════════════════════════════════╣");
        System.out.println("║ 1. Deudores                    ║");
        System.out.println("║ 2. Mensajería                  ║");
        if (perfilUsuario.equals("administrador"))
            System.out.println("║ 3. Usuarios del Sistema        ║");
        System.out.println("║ 4. Emitir Constancia           ║");
        System.out.println("║ 0. Salir                       ║");
        System.out.println("╚════════════════════════════════╝");
        System.out.print("  > Seleccione una opción: ");
    }
    private void mostrarOpcionInvalida() {
    	System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║        Opción inválida         ║");
        System.out.println("╚════════════════════════════════╝");
    }
    
}