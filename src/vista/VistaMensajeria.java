package vista;

import java.util.Scanner;

import logica.Controlador;
	
	public class VistaMensajeria {
		private Controlador controlador;
		
		
		public VistaMensajeria(Controlador controlador) {
			this.controlador=controlador;		
		}
		
		public void menu() {
			int opcion=0;
			Scanner sc=new Scanner(System.in);			
			do {
				mostrarMenu();
				opcion = leerEntero(sc);
				
				switch(opcion) {
					case 0:
						System.out.println("\n╔════════════════════════════════════════╗");
	                    System.out.println("║           Saliendo de Mensajería       ║");
	                    System.out.println("╚════════════════════════════════════════╝");
					break;
					case 1:
						procesarMensajes();
					break;
					case 2:
						listarHistoricoMensajes();						
					break;
					case 3:
						listarHistoricoMensajesPorUsuario();
					break;
					case 4:
						borrarMensajeEnviado();
					break;
					case 5:
						borrarTodasHistoriasBD();
					break;
					default:
					    System.out.println("\n╔════════════════════════════════════════╗");
	                    System.out.println("║            Opción inválida             ║");
	                    System.out.println("╚════════════════════════════════════════╝");
					break;		
				}
				
			}while(opcion!=0);
		}
		
		public void mostrarMenu() {
			System.out.println("\n╔═════════════════════════════════════════╗");
	        System.out.println("║           MENÚ MENSAJERÍA               ║");
	        System.out.println("╠═════════════════════════════════════════╣");
	        System.out.println("║ 0. Salir                                ║");
	        System.out.println("║ 1. Enviar mensajes a cada Deudor        ║");
	        System.out.println("║ 2. Mostrar todos los mensajes enviados  ║");
	        System.out.println("║ 3. Mostrar mensajes por ID de Deudor    ║");
	        System.out.println("║ 4. Eliminar mensaje enviado             ║");
	        System.out.println("║ 5. Borrar todos los mensajes            ║");
	        System.out.println("╚═════════════════════════════════════════╝");
	        System.out.print("  > Seleccione una opción: ");
		}
		
	    private void procesarMensajes() {
	    	String resultado = controlador.procesarMensajesDeUsuarios();

	        if (resultado.isEmpty()) {
	            System.out.println("\n╔════════════════════════════════╗");
	            System.out.println("║    No se enviaron mensajes     ║");
	            System.out.println("╚════════════════════════════════╝");
	        } else {
	            String[] lineas = resultado.split("\\|");
	            
	            System.out.println("\n╔══════════════════════════════════════════════════════════════════════════╗");
	            System.out.println("║                           MENSAJES ENVIADOS                              ║");
	            System.out.println("╠══════════════════════════════════════════════════════════════════════════╣");

	            for (String linea : lineas) {
	                if (!linea.trim().isEmpty()) {
	                    System.out.printf("║ > %-70s ║%n", linea.trim());
	                }
	            }

	            System.out.println("╚════════════════════════════════════════════════════════════════════════╝");
	        }
	    }

	    
	    private void listarHistoricoMensajes() {
	        String historial = controlador.obtenerHistoricoMensajes();
	        if (historial.isEmpty()) {
	            System.out.println("\n╔════════════════════════════════╗");
	            System.out.println("║    No hay mensajes enviados    ║");
	            System.out.println("╚════════════════════════════════╝");
	        } else {
	            drawTopTitulo();
	            drawTitulo("HISTORICO DE MENSAJES");
	            drawEncabezado();
	            drawMiddle();

	            int maxTitulo = 40; // ancho de TITULOS Y DIAS

	            String[] lineas = historial.split("\n");
	            for (String linea : lineas) {
	                if (!linea.trim().isEmpty()) {
	                    String[] campos = linea.split("\\|");
	                    if (campos.length >= 5) {
	                        String titulo = campos[4].trim();
	                        int len = titulo.length();
	                        int start = 0;
	                        boolean primeraLinea = true;
	                        //imprime lineas de titulo hasta que se agote el titulo. va dando "saltos" de a +40 caraceres 
	                        while (start < len) {
	                        	//recorta el string desde start hasta +40
	                            String sub = titulo.substring(start, Math.min(start + maxTitulo, len));
	                            if (primeraLinea) {
	                                System.out.printf("║%-3s║%-11s║%-8s║%-30s║%-40s║%n",
	                                        campos[0].trim(),
	                                        campos[1].trim(),
	                                        campos[2].trim(),
	                                        campos[3].trim(),
	                                        sub);
	                                primeraLinea = false;
	                            } else {
	                                // líneas adicionales solo muestran la columna larga
	                                System.out.printf("║%-3s║%-11s║%-8s║%-30s║%-40s║%n",
	                                        "", "", "", "", sub);
	                            }
	                            start += maxTitulo;
	                        }
	                        drawMiddle();
	                    }
	                }
	            }
	            drawBottom();
	        }
	    }

	    
	    private void listarHistoricoMensajesPorUsuario() {
	        Scanner sc = new Scanner(System.in);
	        System.out.print("\n  > Ingrese ID del Deudor: ");
	        int id = leerEntero(sc);
	        

	        String datos = controlador.obtenerHistoricoMensajesPorIdUsuario(id);
	        if (datos.isEmpty() || datos.contains("no encontrado")) {
	            System.out.println("\n╔════════════════════════════════════════╗");
	            System.out.println("║  [ERROR] Usuario no encontrado         ║");
	            System.out.println("╚════════════════════════════════════════╝");
	        } else {
	            drawTopTitulo();
	            drawTitulo("MENSAJES DE:"+ id);	            
	            drawEncabezado();
	            drawMiddle();
	  
	            int maxTitulo = 40;

	            String[] lineas = datos.split("\n");
	            for (String linea : lineas) {
	                if (!linea.trim().isEmpty()) {
	                    String[] campos = linea.split("\\|");
	                    if (campos.length >= 5) {
	                        String titulo = campos[4].trim();
	                        int len = titulo.length();
	                        int start = 0;

	                        boolean primeraLinea = true;
	                        while (start < len) {
	                            String sub = titulo.substring(start, Math.min(start + maxTitulo, len));
	                            if (primeraLinea) {
	                                System.out.printf("║%-3s║%-11s║%-8s║%-30s║%-40s║%n",
	                                        campos[0].trim(),
	                                        campos[1].trim(),
	                                        campos[2].trim(),
	                                        campos[3].trim(),
	                                        sub);
	                                primeraLinea = false;
	                            } else {
	                                // líneas adicionales solo muestran la columna larga
	                                System.out.printf("║%-3s║%-11s║%-8s║%-30s║%-40s║%n",
	                                        "", "", "", "", sub);
	                            }
	                            start += maxTitulo;
	                        }
	                    }
	                    drawMiddle();
	                }	                
	            }
	            drawBottom();
	        }
	    }
	    
	    public void borrarTodasHistoriasBD() {
	        controlador.borrarTodasHistoriasBD();
	        System.out.println("\n╔════════════════════════════════════════╗");
	        System.out.println("║        Todos los mensajes fueron       ║");
	        System.out.println("║                borrados                ║");
	        System.out.println("╚════════════════════════════════════════╝");
	    }
	    
	    private void borrarMensajeEnviado() {
	        Scanner sc = new Scanner(System.in);
	        System.out.print("\n  > Ingrese ID del mensaje: ");
	        int id = leerEntero(sc);
	        
	        boolean eliminado = controlador.eliminarMensajeEnviado(id);

	        System.out.println("\n╔══════════════════════════════════╗");
	        if (eliminado)
	            System.out.println("║  Mensaje eliminado correctamente ║");
	        else
	            System.out.println("║  No se pudo eliminar el mensaje  ║");
	        System.out.println("╚══════════════════════════════════╝");
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
	                System.out.println(" >");
	            }
	        }
	        
	        return numero;
	    } 
	    
	    
	 // Dibuja el título centrado en la tabla
	    private void drawTitulo(String titulo) {
	        int totalWidth = 3 + 11 + 8 + 30 + 40 + 4; // suma de anchos + bordes ║
	        int padding = (totalWidth - titulo.length()) / 2;
	        System.out.printf("║%s%s%s║%n", repeat(' ', padding), titulo, repeat(' ', totalWidth - padding - titulo.length()));
	    }

	    // Dibuja la línea superior de la tabla
	    private void drawTopTitulo() {
	        System.out.printf("╔%s═%s═%s═%s═%s╗%n",
	            repeat('═', 3),   // ID
	            repeat('═', 11),  // FECHA ENVIO
	            repeat('═', 8),   // ID USER
	            repeat('═', 30),  // CORREO
	            repeat('═', 40)   // TITULOS Y DIAS
	        );
	    }

	    // Dibuja el encabezado con nombres de columna
	    private void drawEncabezado() {
	        System.out.printf("╠%s╦%s╦%s╦%s╦%s╣%n",
	            repeat('═', 3),
	            repeat('═', 11),
	            repeat('═', 8),
	            repeat('═', 30),
	            repeat('═', 40)
	        );
	        System.out.printf("║%-3s║%-11s║%-8s║%-30s║%-40s║%n",
	            "ID", "FECHA ENVIO", "ID USER", "CORREO", "TITULOS Y DIAS");
	    }

	    // Dibuja la línea intermedia de la tabla (entre encabezado y filas)
	    private void drawMiddle() {
	        System.out.printf("╠%s╬%s╬%s╬%s╬%s╣%n",
	            repeat('═', 3),
	            repeat('═', 11),
	            repeat('═', 8),
	            repeat('═', 30),
	            repeat('═', 40)
	        );
	    }

	    // Dibuja la línea inferior de la tabla
	    private void drawBottom() {
	        System.out.printf("╚%s╩%s╩%s╩%s╩%s╝%n",
	            repeat('═', 3),
	            repeat('═', 11),
	            repeat('═', 8),
	            repeat('═', 30),
	            repeat('═', 40)
	        );
	    }


	    private String repeat(char c, int n) {
	        String s = "";
	        for (int i = 0; i < n; i++) {
	            s += c;
	        }
	        return s;
	    }	
		
	    
	    
}
