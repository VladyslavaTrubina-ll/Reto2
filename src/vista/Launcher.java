package vista;

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
import modelo.GestorTicket;

public class Launcher {
	static GestorTicket gestorTicket = new GestorTicket();
	private static GestorCine gestorCine = new GestorCine();
	static Carrito carrito = new Carrito();
static Imprimir imprmir = new Imprimir();
	public static void main(String args[]) {

		// 0. Connexion a la base de datos

		if (!gestorCine.conexionRealizada()) {
			return;
		}

		// TODO realizar 1. Bienvenida

		// TODO Tienes cuenta?

		// No -> TODO 1.1 Registrar

		// Si -> continuar

		// 2. Login
		ClienteAcesso cliente = gestorCine.login();

		// 3. Compra


		int espectadoresActuales = 0;
		boolean comprando = true;
		
		while (comprando) {

			// 4.Eligir entradas

			// 4.1 Mostrar peliculas
		   Imprimir.imprimirPeliculas(gestorCine.controlador);      

			//  4.2 Qieres elegir pelicula?

			// 4.2 no -> 4.3 Hay algo en carrito?

			// 4.3 no -> salir?

			// salir si -> fin programa

			// salir no -> volver a 4.1 Mostrar peliculas

			// 4.3 si -> comprar?

			//  4.2 si -> 5. elegir pelicula

			Pelicula PeliculaElegida = gestorCine.elegirpelicula(gestorCine.controlador);
			String peliculaElegida = PeliculaElegida.getNombre();


			// 6. mostrarán las fechas en las que se puede ver esa película
			ArrayList<FechaSesion> fechas = Imprimir.mostarFecha(peliculaElegida); 

			// 7. Elegir fecha
			// Logica en GestorCine
			FechaSesion fechaElegida = gestorCine.elegirfecha(gestorCine.controlador, fechas);
			 fechaElegida.getFecha();

			 // 8. Mostrar sesiones (Hora,Sala y Precio)
			ArrayList<OrarioPrecioSalaSesion> horarioPrecioYSala = Imprimir.imprimirHoraPrecioYSala(fechaElegida, peliculaElegida);
			System.out.print("Volver a la selecion de peliculas?");

			// volver?
			String respuesta = gestorCine.controladorEntrada.leerCadena();
			if (respuesta.equalsIgnoreCase("si")) {
				continue; // Se dice SI, salta tutto il resto e ricomincia
			}

			// 9. Elegir sesión
			OrarioPrecioSalaSesion horarioElegido = gestorCine.elegirHorario(gestorCine.controlador, horarioPrecioYSala);


			// 10. Hay sillas libres?
			ArrayList<EspectadoresSesion> sitiosDisponibles = imprimirEspectadores(gestorCine.controlador, fechaElegida,
					horarioElegido);

			if (espectadoresActuales == salaLlena(sitiosDisponibles)) {
				System.out.println("No hay sitios disponbles para esta sesion");
				imprimirPeliculas(gestorCine.controlador);
				// Volver a empezar el bucle principal
			}

			// 11. Escribir el numero de entradas
			int posti = gestorCine.seleccionarNumEspectadores(sitiosDisponibles, horarioElegido);
			sitiosDisponibles.get(0).setEspectadores(posti);

			// generar entrada (sesion + numEntradas) TODO class Entrada

			// genero un entrada con los varios datos//

			//ya esta en carrito
			/*double descuento = 0.0;
			double precioentrada = horarioPrecioYSala.get(0).getPrecio();
			String orarioentrada = horarioElegido.getOrario();*/

			Sesion nuevaentrada = gestorCine.generarEntrada(PeliculaElegida, fechaElegida.getFecha(),
					horarioPrecioYSala.get(0).getSala(), orarioentrada, posti, precioentrada);

			// 12. entrada anadida en carrito + TODO mostrará un mensaje con la selección realizada
			carrito.anadirEntrada(nuevaentrada, posti);
			
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
				imprimirPeliculas(gestorCine.controlador);

			} else {
				// 14 Pagar? si ->

				// 15. Generar ticket (new Ticket)

				// 16. Hacer pago (user input dinero)

				// 17. Insertar compra en DB
				gestorCine.controlador.insertarCompra(carrito.getDni(), carrito.getSesiones().size(),
						carrito.getPrecioTotal(), carrito.getDescuento());

				// 17.1 idTicket = Insert ticket (compra)

				// 17.2 for cada entrada in carrito -> idEntrada = Insert entrada (idTicket, entrada)

				// 17.3 Update ticket con numEntradas

				// 17.4 Update sesiones con numEspectadores


				// Imprimir Ticket

				System.out.println("impression de ticket...");
				String sesion =	gestorCine.controlador.obtenerSesion(fechaElegida, horarioElegido);
				System.out.println("sesion elegida " + sesion + "");
				
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
			}
		}
	}

	/*public static ArrayList<OrarioPrecioSalaSesion> imprimirHoraPrecioYSala(FechaSesion fecha, String titulo) {
		
		ArrayList<FechaSesion> unafecha = new ArrayList<>();
		unafecha.add(fecha);
		ArrayList<OrarioPrecioSalaSesion> orariopreciosala = gestorCine.controlador.obtenerhorariopreciosala(unafecha,titulo);
		for (int i = 0; i < orariopreciosala.size(); i++) {
			System.out.println((1 + i) + ". " + (orariopreciosala.get(i)));
		}
		return orariopreciosala;
	}*/

	
	/*public static ArrayList<EspectadoresSesion> imprimirEspectadores(ControladorDB controlador, FechaSesion fecha,
			OrarioPrecioSalaSesion horarioElegido) {
		ArrayList<FechaSesion> unafecha = new ArrayList<>();
		unafecha.add(fecha);
		ArrayList<OrarioPrecioSalaSesion> unorario = new ArrayList<>();
		unorario.add(horarioElegido);
		ArrayList<EspectadoresSesion> numespectadores = controlador.obtenerespectadoresporsesion(unafecha, unorario);
		if (numespectadores.isEmpty()) {
			System.out.println("No hay espectadores para esta sesión.");
		}
		return numespectadores;
	}*/

	public static int salaLlena(ArrayList<EspectadoresSesion> espectadores) {
		int sitiosdisponibles = espectadores.get(0).getEspectadores();
		if (sitiosdisponibles == gestorCine.S1.getSitios() || sitiosdisponibles == gestorCine.S2.getSitios()
				|| sitiosdisponibles == gestorCine.S3.getSitios() || sitiosdisponibles == gestorCine.S4.getSitios()
				|| sitiosdisponibles == gestorCine.S5.getSitios()) {
			System.out.println("no hay sitios disponibles");

		}
		return sitiosdisponibles;
	}

}

// tipos de seciones de codigo:

// 1. backend: connexion DB, new Carrito
// 2. mensajes a usuario
// 3. logica de negocio: elegir pelicula, elegir fecha, elegir horario = mensaje_menu + mensaje + input (verificar)
// 4. breakpoints: volver a 4.1, salir, comprar, pagar, guardar ticket = mesage + input (verificar) + cambiar de estado