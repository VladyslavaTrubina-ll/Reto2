package vista;

import java.util.Scanner;
import modelo.*;
import controlador.*;
import java.util.ArrayList;


public class Launcher {
	static GestorTicket gestorTicket = new GestorTicket();
	private static GestorCine gestorCine = new GestorCine();
	static Carrito carrito = new Carrito();
	static Imprimir imprimir = new Imprimir();

	public static void main(String args[]) {
		// 0. Connexion a la base de datos
		if (!gestorCine.conexionRealizada()) {
			return;
		}
		while (true) {
			mainProceso();
		}
	}

	public static void mainProceso() {
	    // 1. Bienvenida
	    Imprimir.bienvenida();

	    // 2. Login o Registro
	    boolean esRegistrado = false;
	    while (!esRegistrado) {
	        String tienesCuenta = gestorCine.controladorEntrada.leerSiNo("¿Tienes cuenta?");
	        
	        if (tienesCuenta.equalsIgnoreCase("si")) {
	            esRegistrado = true;
	        } else if (tienesCuenta.equalsIgnoreCase("no")) {
	            registrarCliente();
	        }
	    }

	    // 3. Login
	    gestorCine.login();

	    // 4. Proceso principal de compra
	    boolean procesoPrincipal = true;

	    while (procesoPrincipal) {
	    	
	        boolean eligiendoPeliculas = true;

	        while (eligiendoPeliculas) {
	        	ArrayList<Pelicula> pelis = gestorCine.controlador.obtenerPelis();
	        	imprimir.imprimirPeliculas(pelis);  
	            // 4.1 ¿Quieres elegir película?
				//System.out.println("----------------------------------");
	            String elegirPeli = gestorCine.controladorEntrada.leerSiNo("\n¿Quieres elegir película?");

	            if (elegirPeli.equalsIgnoreCase("si")) {
	                // 4.2 Elegir película
	                Pelicula peliElegida = gestorCine.elegirPelicula(pelis);

	                // 5. Elegir fecha
	                
	                ArrayList<String> fechas = gestorCine.controlador.obtenerFechasPorPerli(peliElegida.getNombre());
	                ArrayList<String> fechasUnicos = imprimir.imprimirFechas(fechas, peliElegida.getNombre());
	                
	                String fechaElegida = gestorCine.elegirFecha(fechasUnicos);
	                Sesion sesionElegida = null;
	                int numEspectadores = 0;
	                
	                boolean eligiendoSesion = true;
	                while (eligiendoSesion) {
	                	// 6. Elegir horario/sala/precio
	                	ArrayList<Sesion> sesiones = gestorCine.controlador.obtenerSesionesPorPerli(fechaElegida, peliElegida);
	                	//TODO imprimir sesiones y mejorar print
	                	imprimir.imprimirHoraPrecioYSala(sesiones);
	                	String volverSiNo = gestorCine.controladorEntrada.leerSiNo("¿Volver?");
	                	if (volverSiNo.equalsIgnoreCase("si")) {
	                		eligiendoSesion = false;
	                		
	                	} else if (volverSiNo.equalsIgnoreCase("no")) {
	                		Sesion sesionSeleccionada = gestorCine.elegirSesion(sesiones);
	                		
	                		// 8. Verificar y seleccionar número de espectadores
	                		int numEspectadoresSelect = gestorCine.seleccionarNumEspectadores(sesionSeleccionada);
	                		
	                		if (numEspectadoresSelect <= 0) {
	                			System.out.println("No hay sillas libres, elige otra sesión.");
	                			
	                		} else {
	                			sesionElegida = sesionSeleccionada;
	                			numEspectadores = numEspectadoresSelect;
	                			eligiendoSesion = false;
	                		}
	                		
	                	}
	                }
	                if (sesionElegida != null) {
	                	// 9. Generar entrada(Sesion, numEntadas/numEspectadores) TODO new classe 
	                	/*	Sesion nuevaEntrada = gestorCine.generarEntrada(
	                			peliElegida,
	                			fechaElegida.getFecha(),
	                			horarioElegido.getSala(),
	                			horarioElegido.getOrario(),
	                			numEspectadores,
	                			horarioElegido.getPrecio()
	                			);
	                	 */
	                	// 10. Añadir al carrito 
	                	carrito.anadirEntrada(sesionElegida, numEspectadores);
	                	System.out.println(" Test Entrada añadida al carrito");
	                
	                	// 11. ¿Quieres elegir más películas?
	                	String masPeliculas = gestorCine.controladorEntrada.leerSiNo("¿Quieres elegir más películas?");
	                	if (masPeliculas.equalsIgnoreCase("si")) {
	                		continue;
	                	}
	                	if (masPeliculas.equalsIgnoreCase("no")) {
	                		// Mostrar resumen de compra y pagar
	                		procesoPagar();
	                		eligiendoPeliculas = false;
	                	}
	                }
	                
	            } else if (elegirPeli.equalsIgnoreCase("no")) {
	                // 12. ¿Hay algo en carrito?
	                if (carrito.getSesiones().isEmpty()) {
	                    // 12.1 Carrito vacío
	                    System.out.println("Carrito vacío, no puedes comprar");
	                    eligiendoPeliculas = false;
	                } else {
	                    // 12.2 Carrito con entradas
	                    System.out.println("Tienes entradas en carrito");
	                    String deseaComprar = gestorCine.controladorEntrada.leerSiNo("¿Quieres comprar?");
	                    
	                    if (deseaComprar.equalsIgnoreCase("si")) {
	                        // 13. Mostrar resumen y procesar pago
	                        procesoPagar();
	                        eligiendoPeliculas = false;
	                    } else {
                    	// Cancelar compra - limpiar carrito
                    	carrito.vaciar();
                    	System.out.println("Compra cancelada. Carrito vaciado.");
                    	eligiendoPeliculas = false;
	                    }
	                }
	            }
	        }

	        // 14. ¿Quieres salir?
	        String salir = gestorCine.controladorEntrada.leerSiNo("¿Quieres salir?");
	        if (salir.equalsIgnoreCase("si")) {
	            System.out.println("¡Hasta luego!");
	            procesoPrincipal = false;
	        } else {
	            procesoPrincipal = true; // Vuelve al inicio
	        }
	    }
	}
	public static void registrarCliente() {
		System.out.println("=== Registro para nuevo cliente ===");
		// System.out.print("Escribe tu DNI: ");
		String dni = gestorCine.controladorEntrada.leerCadena("Escribe tu DNI: ");
		
		String nombre = ControladorEntradaYSalida
		.letraMalluscula(gestorCine.controladorEntrada.leerCadena("Escribe tu nombre: "));
		
		String apellidos = ControladorEntradaYSalida
		.letraMalluscula(gestorCine.controladorEntrada.leerCadena("Escribe tus apellidos: "));
		String email = "";
		boolean emailValido = false;
		
		while (!emailValido) {
			
			email = gestorCine.controladorEntrada.leerCadena("Escribe tu email: ");
			
			// ^.+ qulquer simvolo antes @
			if (email.matches("^.+@gmail\\.com$")) {
				emailValido = true;
			} else {
				System.out.println("Email no válido. Por favor, escribe un email de Gmail.");
				emailValido = false;
			}
		}
		
		String contrasena = gestorCine.controladorEntrada.leerCadena("Escribe tu contraseña: ");
		
		try {
			gestorCine.controlador.insertarUsuario(dni, nombre, apellidos, email, contrasena);
			System.out.println("Registrado corectamente!");
		} catch (Exception e) {
			System.out.println("Error en el registro. Por favor, inténtalo de nuevo.");
			e.printStackTrace();
		}
	}

	public static void procesoPagar() {
	    System.out.println("\n=== RESUMEN DE COMPRA ===");
	    carrito.resumen(gestorCine.clienteLogueado.getNombre(), gestorCine.clienteLogueado.getApellidos(), carrito);

	    String confirmar = gestorCine.controladorEntrada.leerSiNo("¿Confirmar compra?");

	    if (confirmar.equalsIgnoreCase("si")) {
	        // Procesar pago
	        double precioConIva = Math.round(carrito.getPrecioTotal() * 1.21 * 100.0) / 100.0;
	     //   double cambio = procesarPagoDinero(precioConIva);
	    //    carrito.setCambio(cambio);
	        
	        // 15. Insertar compra en BD
	        gestorCine.controlador.insertarCompra(
	            gestorCine.clienteLogueado.getDni(),
	            carrito.getSesiones().size(),
	            carrito.getPrecioTotal(),
	            carrito.getDescuento()
	        );

	        System.out.println("\nCompra realizada con éxito");
	        //TODO guardar a BD  update en sesion espectadores con getCantidadesEntradas (class Entrada)
	        
	        // 16. ¿Guardar ticket?
	        String guardarTicket = gestorCine.controladorEntrada.leerSiNo("¿Guardar ticket?");
	        if (guardarTicket.equalsIgnoreCase("si")) {
	            GestorTicket.salvaCompra(gestorCine.clienteLogueado, carrito);
	        }

	        // 17. Vaciar carrito
	        carrito.vaciar();
	    } else {
	        // No confirmar - liberar reserva automaticamente
	        carrito.vaciar();
	        System.out.println("\nCompra no confirmada. Reserva cancelada.");
	    }
	}
}

// 4. breakpoints: volver a 4.1, salir, comprar, pagar, guardar ticket = mesage + input (verificar) + cambiar de estado