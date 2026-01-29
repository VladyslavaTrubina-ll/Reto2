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

import modelo.GestorTicket;

public class prueba {
	static GestorTicket gestorTicker = new GestorTicket();
	private static GestorCine gestorCine = new GestorCine();
	static Carrito carrito = new Carrito();

	public static void main(String args[]) {
		// 1. Conexion + Login
		if (!gestorCine.conexionrealizada()) {
			return;
		}
		ClienteAcesso cliente = gestorCine.login();
		carrito.setCliente(cliente);
		int espectadoresActuales = 0;
		boolean comprando = true;
		while (comprando) {
			// metodos de visualizacion aquí
			mostrarpeliculas(gestorCine.controlador);

			Pelicula PeliculaElegida = gestorCine.elegirpelicula(gestorCine.controlador);
			String peliculaElegida = PeliculaElegida.getTitulo();

			ArrayList<FechaSesion> fechas = mostarfecha(peliculaElegida);

			// Logica en GestorCine
			FechaSesion fechaelegida = gestorCine.elegirfecha(gestorCine.controlador, fechas);
			 fechaelegida.getFecha();

			ArrayList<OrarioPrecioSalaSesion> orariopreciosala = mostrarorariopreciosala(fechaelegida, peliculaElegida);

			System.out.println("Volver a la selecion de peliculas?");

			String respuesta = gestorCine.controladorentrada.leerCadena();
			if (respuesta.equalsIgnoreCase("si")) {
				continue; // ← Se dice SI, salta tutto il resto e ricomincia
			}
			OrarioPrecioSalaSesion orarioelegido = gestorCine.elegirorario(gestorCine.controlador, orariopreciosala);

			ArrayList<EspectadoresSesion> sitiosdisponibles = mostrarespectadores(gestorCine.controlador, fechaelegida,
					orarioelegido);

			if (espectadoresActuales == cinepieno(sitiosdisponibles)) {
				System.out.println("No hay sitios disponbles para esta sesion");
				mostrarpeliculas(gestorCine.controlador);
			}

			// Seleccionar numero de asientos

			int posti = gestorCine.selecionarnumerositios(sitiosdisponibles, orarioelegido);
			sitiosdisponibles.get(0).setEspectadores(posti);

			// Preguntar si quiere anadir mas películas

			// genero un entrada con los varios datos//
			double descuento = 0.0;
			double precioentrada = orariopreciosala.get(0).getPrecio();
			String orarioentrada = orarioelegido.getOrario();

			Sesion nuevaentrada = gestorCine.generarEntrada(PeliculaElegida, fechaelegida.getFecha(),
					orariopreciosala.get(0).getSala(), orarioentrada, posti, precioentrada);
			carrito.anadirEntrada(nuevaentrada, posti);
			System.out.println("\n¿Seleccionar mas peliculas? (si/no)");
			String maspeli = gestorCine.controladorentrada.leerCadena();
			if (maspeli.equalsIgnoreCase("si")) {
				continue;
			}
			carrito.resumen(cliente.getNombre(), cliente.getApellidos(), carrito);
			boolean compraconfirmada = gestorCine.confirmarcompra(carrito);
			if (!compraconfirmada) {
				System.out.println("Compra anulada");
				carrito.vaciar();
				mostrarpeliculas(gestorCine.controlador);

			} else {
				System.out.println("impression de ticket...");
			String sesion =	gestorCine.controlador.obtenersesion(fechaelegida, orarioelegido);
				System.out.println("sesion elegida " + sesion + "");
				gestorCine.controlador.insertarCompra(carrito.getCliente().getDni(), carrito.getSesiones().size(),
						carrito.getPrecioTotal(), carrito.getDescuento());

				System.out.println("Quieres guardar el ticket?");
				String guardarticket = gestorCine.controladorentrada.leerCadena();
				if (guardarticket.equalsIgnoreCase("Si")) {
					GestorTicket.salvaCompra(cliente, carrito);
					carrito.vaciar();
				} else {
					carrito.vaciar();
				}
			}
		}
	}

	public static void mostrarpeliculas(ControladorDB controlador) {
		System.out.println("pelicuals disponibles");
		System.out.println("---------------------");
		ArrayList<Pelicula> peliculas = controlador.obtenerpelis();
		for (int i = 0; i < peliculas.size(); i++) {
			Pelicula p = peliculas.get(i);
			System.out.println(p.toString());
		}

	}

	public static ArrayList<FechaSesion> mostarfecha(String titulo) {
		ArrayList<FechaSesion> fechas = gestorCine.controlador.obtenerfechasporperli(titulo);
		for (int i = 0; i < fechas.size(); i++) {
			System.out.println((1 + i) + ". " + (fechas.get(i)));
		}
		return fechas;
	}

	public static ArrayList<OrarioPrecioSalaSesion> mostrarorariopreciosala(FechaSesion fecha, String titulo) {
		
		ArrayList<FechaSesion> unafecha = new ArrayList<>();
		unafecha.add(fecha);
		ArrayList<OrarioPrecioSalaSesion> orariopreciosala = gestorCine.controlador.obtenerhorariopreciosala(unafecha,titulo);
		for (int i = 0; i < orariopreciosala.size(); i++) {
			System.out.println((1 + i) + ". " + (orariopreciosala.get(i)));
		}
		return orariopreciosala;
	}

	public static ArrayList<EspectadoresSesion> mostrarespectadores(ControladorDB controlador, FechaSesion fecha,
			OrarioPrecioSalaSesion orarioelegido) {
		ArrayList<FechaSesion> unafecha = new ArrayList<>();
		unafecha.add(fecha);
		ArrayList<OrarioPrecioSalaSesion> unorario = new ArrayList<>();
		unorario.add(orarioelegido);
		ArrayList<EspectadoresSesion> numespectadores = controlador.obtenerespectadoresporsesion(unafecha, unorario);
		if (numespectadores.isEmpty()) {
			System.out.println("No hay espectadores para esta sesión.");
		}
		return numespectadores;
	}

	public static int cinepieno(ArrayList<EspectadoresSesion> espectadores) {
		int sitiosdisponibles = espectadores.get(0).getEspectadores();
		if (sitiosdisponibles == gestorCine.S1.getSitios() || sitiosdisponibles == gestorCine.S2.getSitios()
				|| sitiosdisponibles == gestorCine.S3.getSitios() || sitiosdisponibles == gestorCine.S4.getSitios()
				|| sitiosdisponibles == gestorCine.S5.getSitios()) {
			System.out.println("no hay sitios disponibles");

		}
		return sitiosdisponibles;
	}

}