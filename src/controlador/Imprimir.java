package controlador;

import java.util.ArrayList;

import modelo.EspectadoresSesion;
import modelo.FechaSesion;
import modelo.GestorCine;
import modelo.OrarioPrecioSalaSesion;
import modelo.Pelicula;


public class Imprimir {

	public static void bienvenida(){
		
		System.out.println("=================================");
		System.out.println("	  Bienvenido a cine ");
		System.out.println("   Haz login con tu cuenta para comprar entradas");
		System.out.println("=================================");
	}

	public  void imprimirPeliculas(ArrayList<Pelicula> peliculas) {
		System.out.println("\nPeliculas disponibles");
		System.out.println("---------------------");

		if (peliculas.isEmpty()) {
        System.out.println("No hay películas disponibles.");
        return;
    	}
		for (int i = 0; i < peliculas.size(); i++) {
			Pelicula p = peliculas.get(i);
			System.out.printf("[%d] %s - %d min%n", (i + 1), p.getNombre(), p.getDuracion());
		}
	}
	public  ArrayList<EspectadoresSesion> imprimirEspectadores(ControladorDB controlador, FechaSesion fecha,
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
	}
	
	public  ArrayList<FechaSesion> imprimirFecha(ControladorDB controlador, String titulo) {
		ArrayList<FechaSesion> fechas = controlador.obtenerfechasporperli(titulo);
		System.out.println("\nFechas disponibles para: " + titulo);
		for (int i = 0; i < fechas.size(); i++) {
			System.out.printf("[%d] %s%n", (1 + i), fechas.get(i));
		}
		return fechas;
	}
	
	public  ArrayList<OrarioPrecioSalaSesion> imprimirHoraPrecioYSala(FechaSesion fecha, String titulo, ControladorDB controlador) {
		
		ArrayList<FechaSesion> unafecha = new ArrayList<>();
		unafecha.add(fecha);
		ArrayList<OrarioPrecioSalaSesion> horaPrecioSala = controlador.obtenerhorariopreciosala(unafecha,titulo);
		for (int i = 0; i < horaPrecioSala.size(); i++) {
			System.out.println((1 + i) + ". " + (horaPrecioSala.get(i)));
		}
		return horaPrecioSala;
	}

	
}
