package vista;

import java.util.Scanner;
import modelo.ClienteAcesso;
import modelo.Entrada;
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
import modelo.GestorCarrito;

public class prueba {

	private static GestorCine gestorCine = new GestorCine();
	static GestorCarrito gestorcarrito = new GestorCarrito();

	public static void main(String args[]) {
		// 1. Conexion + Login
		if (!gestorCine.conexionrealizada()) {
			return;
		}
		ClienteAcesso cliente = gestorCine.login();
		gestorcarrito.setCliente(cliente);
		String respuesta;
		do {
			// metodos de visualizacion aquí
			mostrarpeliculas(gestorCine.controlador);

			String peliculaElegida = gestorCine.elegirpelicula(gestorCine.controlador);

			ArrayList<FechaSesion> fechas = mostarfecha(peliculaElegida);

			// Logica en GestorCine
			FechaSesion fechaelegida = gestorCine.elegirfecha(gestorCine.controlador, fechas);
		
			ArrayList<OrarioPrecioSalaSesion> orariopreciosala = mostrarorariopreciosala(fechaelegida);
	
			System.out.println("Volver a la selecion de peliculas?");
			
			respuesta = gestorCine.controladorentrada.leerCadena();
			 
			OrarioPrecioSalaSesion orarioelegido = gestorCine.elegirorario(gestorCine.controlador, orariopreciosala);
			
			ArrayList<EspectadoresSesion> printespectadores = mostrarespectadores(gestorCine.controlador, fechaelegida,
					orarioelegido);

			 int espectadoresActuales = cinepieno(printespectadores);

			// Seleccionar numero de asientos

			int posti = gestorCine.selecionarnumerositios(printespectadores, orarioelegido);

			// Preguntar si quiere anadir mas películas
			System.out.println("\n¿Seleccionar mas peliculas? (si/no)");
			respuesta = gestorCine.controladorentrada.leerCadena();
//genero un entrada con los varios datos//
			double descuento = 0.0;
			double precioentrada = orariopreciosala.get(0).getPrecio();
			String orarioentrada = orarioelegido.getOrario();
			
			
 Entrada nuevaentrada =	gestorCine.generarEntrada(peliculaElegida, orarioentrada, posti, precioentrada,descuento);
 double preciototentrada = nuevaentrada.calcolarpreciototal(precioentrada, posti);
			gestorcarrito.anadirentrada(nuevaentrada);
			gestorcarrito.preciototal(nuevaentrada);
			gestorcarrito.calculoscarrito(nuevaentrada);
			gestorcarrito.resumencarrito(nuevaentrada);

		} while (respuesta.contains("si"));
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

	public static ArrayList<OrarioPrecioSalaSesion> mostrarorariopreciosala(FechaSesion fecha) {
		ArrayList<FechaSesion> unafecha = new ArrayList<>();
		unafecha.add(fecha);
		ArrayList<OrarioPrecioSalaSesion> orariopreciosala = gestorCine.controlador.obtenerhorariopreciosala(unafecha);
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
