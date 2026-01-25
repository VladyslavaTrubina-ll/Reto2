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

public class prueba {

	static Sala S1 = new Sala("Sala Principal", 70);
	static Sala S2 = new Sala("Sala Premium", 55);
	static Sala S3 = new Sala("Sala 3D", 56);
	static Sala S4 = new Sala("Sala VIP", 50);
	static Sala S5 = new Sala("Sala Familiar", 80);
	static GestorCine gestorCine = new GestorCine();

	public static void main(String args[]) {
		// 1. Conexión + Login
		if (!gestorCine.conexionrealizada()) {
			return;
		}

		String respuesta;
		do {
			//metodos de  visualizacion aquí
			mostrarpeliculas(gestorCine.controlador);

			// logica en GestorCine
			String peliculaElegida = gestorCine.elegirpelicula(gestorCine.controlador);

			// visualización
			ArrayList<FechaSesion> fechas = mostarfecha(peliculaElegida);

			// Logica en GestorCine
			FechaSesion fechaelegida = gestorCine.elegirfecha(gestorCine.controlador, fechas);
			ArrayList<OrarioPrecioSalaSesion> orariopreciosala = mostrarorariopreciosala(fechaelegida);
			OrarioPrecioSalaSesion orarioelegido = gestorCine.elegirorario(gestorCine.controlador, orariopreciosala);
			ArrayList<EspectadoresSesion> printespectadores = mostrarespectadores(
		            gestorCine.controlador, fechaelegida, orarioelegido);
			

			

			System.out.println("Selecionar mas peliculas?");
			respuesta = gestorCine.controladorentrada.leerCadena();
			int espectadoresActuales = cinepieno(printespectadores);
	        
	        //  Seleccionar numero de asientos
	        gestorCine.selecionarnumerositios(printespectadores, orarioelegido);
	        
	        // Preguntar si quiere anadir mas películas
	        System.out.println("\n¿Seleccionar mas peliculas? (si/no)");
	        respuesta = gestorCine.controladorentrada.leerCadena();

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
		if (sitiosdisponibles == S1.getSitios() || sitiosdisponibles == S2.getSitios()
				|| sitiosdisponibles == S3.getSitios() || sitiosdisponibles == S4.getSitios()
				|| sitiosdisponibles == S5.getSitios()) {
			System.out.println("no hay sitios disponibles");

		}
		return sitiosdisponibles;
	}

}
