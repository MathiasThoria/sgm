package vista;
import java.util.Scanner;

import logica.Controlador;

public class VistaUsuariosDelSistema {
	private Controlador controlador;
	
	public VistaUsuariosDelSistema(Controlador controlador) {
		this.controlador = controlador;
	}
	
	public void menu() {
		int opcion= 0;		
		Scanner sc=new Scanner(System.in);		
		do {			
			mostrarMenu();
			opcion = sc.nextInt();
            sc.nextLine();
			switch(opcion) {
			case 0:
				System.out.println("Saliendo.");
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
				System.out.println("Ingrese una opcion valida.");
				break;

			}
		
		}while(opcion!=0);	
		
	}
	
	
	public void mostrarMenu() {
		
		System.out.println("========MENU USUARIOS DEL SISTEMA==========\n");
		System.out.println("0.Salir");		
		System.out.println("1.Alta Usuario");
		System.out.println("2.Baja Usuario");
		System.out.println("3.Modificar Usuario");
		System.out.println("4.Mostrar Usuarios");		
		System.out.print("Seleccione una opcion: ");
	}
	
	public int obtenerOpcion() {
	   	Scanner sc=new Scanner(System.in);
        int opcion = sc.nextInt();
        //sc.nextLine();
        //sc.close();
        return opcion; 
	}
	
	public void mostrarUsuarios() {
		System.out.println(controlador.obtenerUsuariosDelSistema());
	}
	public void altaUsuario() {
		int id=0;
		String perfil="";
		String contraseña="";
		Scanner sc = new Scanner(System.in);		
		/*
		System.out.println("Ingrese id:");
		id=sc.nextInt();
		sc.nextLine();
		*/
		
		System.out.println("Ingrese perfil:");
		perfil=sc.nextLine();
		System.out.println("Ingrese contraseña:");
		contraseña=sc.nextLine();
		
		System.out.println(controlador.altaUsuarioSistema(perfil,contraseña));
		
	}
	public void bajaUsuario() {
		Scanner sc = new Scanner (System.in);
		int id=0;
		boolean ok=false;
	
		System.out.println("Ingrese id de usuario:");			
		id=sc.nextInt();
		sc.nextLine();
		ok=controlador.eliminarUsuarioSistema(id);
		if (ok)				
			System.out.println("Usuario Eliminado.");
		else
			System.out.println("Error en la eliminacion de usuario. Verifique id.");
	}
	
	public void modificarUsuario() {
		Scanner sc = new Scanner (System.in);
		int id=0;
		boolean ok=false;
		String perfil="", contraseña="";
		String usuarioStr="";
		
		System.out.println("Ingrese id de usuario:");			
		id=sc.nextInt();
		sc.nextLine();
		
		
		usuarioStr=controlador.obtenerUsuarioDelSistema(id);
		
		if (usuarioStr.equals(""))
			System.out.println("Id no encontrado.");
		else {
			System.out.println("Se modificará la informacion el siguiente usuario:");
			System.out.println(controlador.obtenerUsuarioDelSistema(id));
			
			System.out.println("Ingrese perfil de usuario(administrado u operador):");			
			perfil=sc.nextLine();
			System.out.println("Ingrese contraseña de usuario:");			
			contraseña=sc.nextLine();
			
			if (controlador.modificarUsuarioDelSistema(id,perfil,contraseña))
				System.out.println("Se han modificado los datos.");
			else 
				System.out.println("No se ha logrado modificar los datos.");
		}
	}
}
