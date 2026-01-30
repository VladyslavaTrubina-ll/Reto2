package controlador;

import java.util.ArrayList;

import modelo.FechaSesion;
import modelo.OrarioPrecioSalaSesion;
import modelo.Pelicula;

public class Imprimir {

	public static void imprimirPeliculas(ControladorDB controlador) {
		System.out.println("pelicuals disponibles");
		System.out.println("---------------------");
		ArrayList<Pelicula> peliculas = controlador.obtenerPelis();
		for (int i = 0; i < peliculas.size(); i++) {
			Pelicula p = peliculas.get(i);
			System.out.println(p.toString());
		}
	}
	
	public static ArrayList<FechaSesion> mostarFecha(String titulo) {
		ArrayList<FechaSesion> fechas = gestorCine.controlador.obtenerfechasporperli(titulo);
		for (int i = 0; i < fechas.size(); i++) {
			System.out.println((1 + i) + ". " + (fechas.get(i)));
		}
		return fechas;
	}
	
	public static ArrayList<OrarioPrecioSalaSesion> imprimirHoraPrecioYSala(FechaSesion fecha, String titulo) {
		
		ArrayList<FechaSesion> unafecha = new ArrayList<>();
		unafecha.add(fecha);
		ArrayList<OrarioPrecioSalaSesion> horaPrecioSala = gestorCine.controlador.obtenerhorariopreciosala(unafecha,titulo);
		for (int i = 0; i < horaPrecioSala.size(); i++) {
			System.out.println((1 + i) + ". " + (horaPrecioSala.get(i)));
		}
		return horaPrecioSala;
	}
}
