package controlador;

import java.util.ArrayList;

import modelo.EspectadoresSesion;
import modelo.FechaSesion;
import modelo.OrarioPrecioSalaSesion;
import modelo.Pelicula;

public class Imprimir {
	
	public static void bienvenida(){
		
		System.out.println("=================================");
		System.out.println("	  Bienvenido a cine ");
		System.out.println("   Hace login con tu cuenta para comprar entradas");
		System.out.println("=================================");
	}

	public static void imprimirPeliculas(ControladorDB controlador) {
		System.out.println("pelicuals disponibles");
		System.out.println("---------------------");
		ArrayList<Pelicula> peliculas = controlador.obtenerPelis();
		for (int i = 0; i < peliculas.size(); i++) {
			Pelicula p = peliculas.get(i);
			System.out.println(p.toString());
		}
	}
	public static ArrayList<EspectadoresSesion> imprimirEspectadores(ControladorDB controlador, FechaSesion fecha,
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
	
	/*public static ArrayList<FechaSesion> mostarFecha(String titulo) {
		ArrayList<FechaSesion> fechas = modelo.gestorCine.controlador.obtenerfechasporperli(titulo);
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
	}*/
}
