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
        	System.out.println("Ingrese usuario:");
            usuario=sc.nextInt();
            sc.nextLine();
            System.out.println("Ingrese contraseña:");
            pass=sc.nextLine();       	
        	
        	contraseñaOk=controlador.verificarContraseñaUsuarioSistema(usuario, pass);
	        if(contraseñaOk) {
	        	perfilUsuario=controlador.obtenerPerfilUsuarioSistema(usuario);
	        	System.out.println("Ha ingresado con perfil " + perfilUsuario);
	        	
	        	do{ 
	            	System.out.println();
	                System.out.println("\n====== MENÚ PRINCIPAL ======");	              
	                System.out.println("0. Salir");
	                System.out.println("1. Deudores");	                
	                System.out.println("2. Mensajeria");  
	                
	                if (perfilUsuario.equals("administrador"))
	                	System.out.println("3. Usuarios del Sistema");        
	                System.out.println("4. Emitir Constancia");          
	                System.out.print("Seleccione una opción: ");
	                
	                
	                
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
	                    	if (perfilUsuario.equals("administrador")) {	                    		
	                    		VistaUsuariosDelSistema usuariosDelSistemaMenu = new VistaUsuariosDelSistema(controlador);
	                    		usuariosDelSistemaMenu.menu();
	                    	}else
	                    		System.out.println(" Opcion inválida");
	                    	break;       
	                    case 5:
	                    	emitirConstancia();
	                    	break;
	                    case 0:
	                    	System.out.println(" Hasta luego!");
	                    	break;
	                    default:
	                        System.out.println(" Opción inválida");
	                        break;
	                }
	            }while(opcion!=0);
	        	
	        }else {
	        	System.out.println("Usuario o contraseña incorrecta.");
	        }
	        
    	}while(!contraseñaOk);
        
        
    }

    // es imposible traer datos de Usuarios de la Biblioteca que nunca fueron deudores
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
    
    
}