package vista;

import java.util.Scanner;
import modelo. *;
import controlador. *;
import java.util.ArrayList;
/*
import java.util.Scanner;
import modelo.ClienteAcesso;
import modelo.Sesion;
import modelo.EspectadoresSesion;
import modelo.FechaSesion;
import modelo.GestorCine;
import modelo.OrarioPrecioSalaSesion;
import controlador.ControladorEntradaYSalida;
import java.util.ArrayList;
import controlador.ControladorDB;
import modelo.Pelicula;
import modelo.Sala;
import modelo.Carrito;
import controlador.Imprimir;
import modelo.GestorTicket; */


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

		// TODO realizar 1. Bienvenida
		Imprimir.bienvenida();

		// TODO Tienes cuenta?

		boolean esRegistrado = false;
		
		while (!esRegistrado) {
			System.out.print("¿Tienes cuenta? (si/no): ");
			String respuesta = gestorCine.controladorEntrada.leerCadena();
			if (respuesta.equalsIgnoreCase("si")) {
				esRegistrado = true;
			} else if (respuesta.equalsIgnoreCase("no")) {
				System.out.println("Por favor, regístrate para continuar.");
				
				// No -> TODO 1.1 Registrar
				//registrarCliente();
				
				esRegistrado = false;
				// Vuelve a preguntar si tiene cuenta
			} else {
				System.out.println("Respuesta no válida. Por favor, responde 'si' o 'no'.");
			}
		}
		// Si -> continuar
		
		// 2. Login
		ClienteAcesso cliente = gestorCine.login();
		
		// 3. Compra
		// 4.Eligir entradas
		
		// 4.1 Mostrar peliculas
		imprimir.imprimirPeliculas(gestorCine.controlador);      
		//  4.2 Qieres elegir pelicula?
		
		boolean vasEligPeli = false;
		
		while (!vasEligPeli) {
			System.out.print("¿Quieres elegir pelicula? (si/no): ");
			String elegirPeli = gestorCine.controladorEntrada.leerCadena();
		
			if (elegirPeli.equalsIgnoreCase("no")) {
			// 4.2 no -> 4.3 Hay algo en carrito?
				if (carrito.getSesiones().isEmpty()) {
					// 4.3 no -> salir?
					System.out.println("¿Quieres salir? (si/no)");
					String salir = gestorCine.controladorEntrada.leerCadena();
				
					// salir si -> fin programa
					if (salir.equalsIgnoreCase("si")) {
							System.out.println("Hasta luego!");
							return; // Termina el programa
					}
					} else {
       	 			}
			} else if (elegirPeli.equalsIgnoreCase("si")) {
				Pelicula peliEligida = gestorCine.elegirPelicula(gestorCine.controlador);
				
				ArrayList<FechaSesion> fechas = gestorCine.controlador.obtenerfechasporperli(peliEligida.getNombre());
				imprimir.imprimirFecha(gestorCine.controlador, peliEligida.getNombre());
				FechaSesion elegirFecha = gestorCine.elegirFecha(gestorCine.controlador, fechas); //ToDO verificar
				System.out.println("nooo");
			} else {
				System.out.println("Respuesta no válida. Por favor, responde 'si' o 'no'.");
			}
		}
	}
}


/*
int espectadoresActuales = 0;
boolean comprando = true;

do {
	
	
				boolean seleccionSesion = false;
				while (!seleccionSesion) {
					imprimir.imprimirHoraPrecioYSala(elegirFecha, peliEligida.getNombre());
					System.out.print("¿Quieres Volver? (si/no): ");
					String volver = gestorCine.controladorEntrada.leerCadena();
					if (volver.equalsIgnoreCase("si")) {
								break;
							} else {
								gestorCine.elegirSesion();
							}
							boolean haySillas = gestorCine.verificarSiHaySillas(); //ToDO tiene estar en otro sitio?
							if (!haySillas) {
								System.out.println("No hay sillas disponibles para esta sesión.");
								continue; // Volver a imprimirHoraPrecioYSala
							}
						}
						// salir no -> volver a 4.1 Mostrar peliculas
						continue;
					} else {
						// 4.3 si -> comprar?
						System.out.println("¿Quieres comprar lo que hay en el carrito? (si/no)");
						String comprar = gestorCine.controladorEntrada.leerCadena();
						if (comprar.equalsIgnoreCase("si")) {
							// 13 no -> Mostrar resumen de compra
							carrito.resumen(cliente.getNombre(), cliente.getApellidos(), carrito);
							// 14. Pagar?
							boolean compraconfirmada = gestorCine.confirmarcompra(carrito);
							if (!compraconfirmada) {
								System.out.println("Compra anulada");
								carrito.vaciar();
								continue;
							}
							// 17. Insertar compra en DB
							gestorCine.controlador.insertarCompra(cliente.getDni(), carrito.getSesiones().size(),
							carrito.getPrecioTotal(), carrito.getDescuento());
							System.out.println("impression de ticket...");
							System.out.println("Quieres guardar el ticket?");
							String guardarticket = gestorCine.controladorEntrada.leerCadena();
							if (guardarticket.equalsIgnoreCase("Si")) {
								GestorTicket.salvaCompra(cliente, carrito);
							}
							carrito.vaciar();
							System.out.println("¿Quieres salir? (si/no)");
							String salir = gestorCine.controladorEntrada.leerCadena();
							if (salir.equalsIgnoreCase("si")) {
								break;
							}
							continue;
						}
						// salir no -> volver a 4.1 Mostrar peliculas
						continue;
					}
				}
				//TODO aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaasta haqui test
				
				//  4.2 si -> 5. elegir pelicula
*/				
/* 				Pelicula PeliculaElegida = gestorCine.elegirpelicula(gestorCine.controlador);
				String peliculaElegida = PeliculaElegida.getNombre();
				
				
				// 6. mostrarán las fechas en las que se puede ver esa película
				ArrayList<FechaSesion> fechas = gestorCine.controlador.obtenerfechasporperli(peliculaElegida);
				for (int i = 0; i < fechas.size(); i++) {
					System.out.println((1 + i) + ". " + (fechas.get(i)));
				}
				
				// 7. Elegir fecha
				// Logica en GestorCine
				FechaSesion fechaElegida = gestorCine.elegirfecha(gestorCine.controlador, fechas);
				fechaElegida.getFecha();
				
				// 8. Mostrar sesiones (Hora,Sala y Precio)
				ArrayList<OrarioPrecioSalaSesion> horarioPrecioYSala = gestorCine.controlador
				.obtenerhorariopreciosala(fechas, peliculaElegida);
				for (int i = 0; i < horarioPrecioYSala.size(); i++) {
					System.out.println((1 + i) + ". " + (horarioPrecioYSala.get(i)));
				}
				System.out.print("Volver a la selecion de peliculas?");
				
				// volver?
				String respuesta = gestorCine.controladorEntrada.leerCadena();
				if (respuesta.equalsIgnoreCase("si")) {
					continue; // Se dice SI, salta tutto il resto e ricomincia
				}
				
				// 9. Elegir sesión
				OrarioPrecioSalaSesion horarioElegido = gestorCine.elegirHorario(gestorCine.controlador, horarioPrecioYSala);
				
				
				// 10. Hay sillas libres?
				ArrayList<EspectadoresSesion> sitiosDisponibles = Imprimir.imprimirEspectadores(
					gestorCine.controlador, fechaElegida, horarioElegido);
					
					if (sitiosDisponibles.isEmpty()) {
						System.out.println("No hay sitios disponbles para esta sesion");
						continue;
					}
					
					// 11. Escribir el numero de entradas
					int posti = gestorCine.seleccionarNumEspectadores(sitiosDisponibles, horarioElegido);
					sitiosDisponibles.get(0).setEspectadores(posti);
					
					// generar entrada (sesion + numEntradas) TODO class Entrada
					
					// genero un entrada con los varios datos//
					
					//ya esta en carrito
					double descuento = 0.0;
					double precioentrada = horarioElegido.getPrecio();
					String orarioentrada = horarioElegido.getOrario();
					
					Sesion nuevaentrada = gestorCine.generarEntrada(PeliculaElegida, fechaElegida.getFecha(),
					horarioElegido.getSala(), orarioentrada, posti, precioentrada);
					
					// 12. entrada anadida en carrito + TODO mostrará un mensaje con la selección realizada
					carrito.anadirEntrada(nuevaentrada, posti);
					System.out.println("Entrada añadida al carrito.");
					
					// 13. TODO Eligir mas peliculas?
					System.out.println("\n¿Seleccionar mas peliculas? (si/no)");
					
					// 13 si -> volver a 4.1 Mostrar peliculas
					String maspeli = gestorCine.controladorEntrada.leerCadena();
					if (maspeli.equalsIgnoreCase("si")) {
						continue;
					}
					
					// 13 no -> Mostrar resumen de compra
					carrito.resumen(cliente.getNombre(), cliente.getApellidos(), carrito);
					
					// 14. Pagar?
					boolean compraconfirmada = gestorCine.confirmarcompra(carrito);
					
					
					if (!compraconfirmada) {
						// 14 Pagar? no -> Borrar?
						
						// Borrar si -> cancelar compra
						System.out.println("Compra anulada");
						carrito.vaciar();
						//  Borrar no -> contiuar
						
						// siempre volver a 4.1 Mostrar peliculas
						// siempre volver a 4.1 Mostrar peliculas
						
					} else {
						// 14 Pagar? si ->
						
						// 15. Generar ticket (new Ticket)
						
						// 16. Hacer pago (user input dinero)
						
						// 17. Insertar compra en DB
						gestorCine.controlador.insertarCompra(cliente.getDni(), carrito.getSesiones().size(),
						carrito.getPrecioTotal(), carrito.getDescuento());
						
						// 17.1 idTicket = Insert ticket (compra)
						
						// 17.2 for cada entrada in carrito -> idEntrada = Insert entrada (idTicket, entrada)
						
						// 17.3 Update ticket con numEntradas
						
						// 17.4 Update sesiones con numEspectadores
						
						
						// Imprimir Ticket
						
						System.out.println("impression de ticket...");
						//String sesion =	gestorCine.controlador.obtenerSesion(fechaElegida, horarioElegido);
						//System.out.println("sesion elegida " + sesion + "");
						
						// 18. Guardar ticket?
						// 18 no -> salir?
						
						// 18 si -> 19. guardar ticket
						System.out.println("Quieres guardar el ticket?");
						String guardarticket = gestorCine.controladorEntrada.leerCadena();
						if (guardarticket.equalsIgnoreCase("Si")) {
							GestorTicket.salvaCompra(cliente, carrito);
						}
						
						// 19. Vaciar carrito
						carrito.vaciar();
						
						// 20. salir?
						System.out.println("¿Quieres salir? (si/no)");
						String salir = gestorCine.controladorEntrada.leerCadena();
						if (salir.equalsIgnoreCase("si")) {
							break;
						}
					}
				}
				
				
				public static void registrarCliente() {
					System.out.println("=== Registro para nuevo cliente ===");
					System.out.print("Escribe tu DNI: ");
					String dni = gestorCine.controladorEntrada.leerCadena();
					System.out.print("Escribe tu nombre: ");
					String nombre = ControladorEntradaYSalida.letraMalluscula(gestorCine.controladorEntrada.leerCadena());
					System.out.print("Escribe tus apellidos: ");
					String apellidos = ControladorEntradaYSalida.letraMalluscula(gestorCine.controladorEntrada.leerCadena());
					String email = "";
					boolean emailValido = false;
					
					while (!emailValido) {
					System.out.print("Escribe tu email: ");
					email = gestorCine.controladorEntrada.leerCadena();
				
					// ^.+ qulquer simvolo antes @
					if (email.matches("^.+@gmail\\.com$")) {
						emailValido = true;
					} else {
						System.out.println("Email no válido. Por favor, escribe un email de Gmail.");
						emailValido = false;
					}
					}
				
					System.out.print("Escribe tu contraseña: ");
					String contrasena = gestorCine.controladorEntrada.leerCadena();
				
					try {
						gestorCine.controlador.insertarUsuario(dni, nombre, apellidos, email, contrasena);
						System.out.println("Registrado corectamente!");
					} catch (Exception e) {
						System.out.println("Error en el registro. Por favor, inténtalo de nuevo.");
						e.printStackTrace();
					}
				}
	/* public static int salaLlena(ArrayList<EspectadoresSesion> espectadores) {
		int sitiosdisponibles = espectadores.get(0).getEspectadores();
		if (sitiosdisponibles == gestorCine.S1.getSitios() || sitiosdisponibles == gestorCine.S2.getSitios()
				|| sitiosdisponibles == gestorCine.S3.getSitios() || sitiosdisponibles == gestorCine.S4.getSitios()
				|| sitiosdisponibles == gestorCine.S5.getSitios()) {
			System.out.println("no hay sitios disponibles");

		}
		return sitiosdisponibles;
	}*/





// tipos de seciones de codigo:

// 1. backend: connexion DB, new Carrito
// 2. mensajes a usuario
// 3. logica de negocio: elegir pelicula, elegir fecha, elegir horario = mensaje_menu + mensaje + input (verificar)
// 4. breakpoints: volver a 4.1, salir, comprar, pagar, guardar ticket = mesage + input (verificar) + cambiar de estado