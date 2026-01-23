package vista;

import java.util.Scanner;
import modelo.ClienteAcesso;
import modelo.EspectadoresSesion;
import modelo.FechaSesion;
import modelo.OrarioPrecioSalaSesion;

import java.util.ArrayList;
import controlador.ControladorDB;
import modelo.Pelicula;

public class prueba {

	public static Scanner sc = new Scanner(System.in);

	public static void main(String args[]) {
		ArrayList<FechaSesion> fecha;
		String respuesta;
		ArrayList<OrarioPrecioSalaSesion> orariopreciosala;
		ControladorDB controlador = new ControladorDB("cine_daw");
		boolean conexionConExito = controlador.iniciarConexion();
		if (conexionConExito) {
			System.out.println("Se realizó la conexion con exito");
			login(controlador);
		} else {
			System.out.println("No hubo suerte");
		}
		
			mostrarpeliculas(controlador);
			String peliculaElegida = elegirpelicula(controlador);
			 fecha = mostarfecha(controlador, peliculaElegida);
			FechaSesion fechaelegida = elegirfecha(controlador, fecha);

			orariopreciosala = mostrarorariopreciosala(controlador, fechaelegida);
		
		OrarioPrecioSalaSesion orarioelegido = elegirorario(controlador, orariopreciosala);
		ArrayList<EspectadoresSesion> printespectadores = mostrarespectadores(controlador, fechaelegida, orarioelegido);
		
	}

	public static void login(ControladorDB controlador) {

		System.out.println("inserire email");
		String email = sc.nextLine();
		System.out.println("digitare password");
		String contraseña = sc.nextLine();
		ArrayList<ClienteAcesso> cliente = controlador.obtenercliente(email, contraseña);
		boolean encontrado = false;
		int contador = 0;
		while (contador < cliente.size() && !encontrado) {
			ClienteAcesso c = cliente.get(contador);
			if (email.equals(c.getEmail()) && contraseña.equals(c.getContraseña())) {
				encontrado = true;

			} else {
				contador++;
			}
			if (encontrado) {
				System.out.println("login effettuato");

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

	public static String elegirpelicula(ControladorDB controlador) {
		ArrayList<Pelicula> peliculas = controlador.obtenerpelis();
		System.out.println("selecionar la pelicula que se quiere vivir");
		String pelicula = sc.nextLine();
		for (Pelicula p : peliculas) {
			if (pelicula.equalsIgnoreCase(p.getTitulo())) {
				System.out.println("Pelicula selecionada con suceso");
				return p.getTitulo();
			}
		}
		System.out.println("no peli");
		return null;
	}

	public static ArrayList<FechaSesion> mostarfecha(ControladorDB controlador, String titulo) {
		ArrayList<FechaSesion> fechas = controlador.obtenerfechasporperli(titulo);
		for (int i = 0; i < fechas.size(); i++) {
			System.out.println((1 + i) + ". " + (fechas.get(i)));
		}
		return fechas;
	}

	public static FechaSesion elegirfecha(ControladorDB controlador, ArrayList<FechaSesion> fechas) {
		System.out.println("Elegir una fecha");
		int opcion = sc.nextInt();
		sc.nextLine();
		if (fechas.isEmpty()) {
			System.out.println("error");
			return null;
		}

		FechaSesion fechaelegida = fechas.get(opcion - 1);
		return fechaelegida;
	}

	public static ArrayList<OrarioPrecioSalaSesion> mostrarorariopreciosala(ControladorDB controlador,
			FechaSesion fecha) {
		ArrayList<FechaSesion> unafecha = new ArrayList<>();
		unafecha.add(fecha);
		ArrayList<OrarioPrecioSalaSesion> orariopreciosala = controlador.obtenerhorariopreciosala(unafecha);
		for (int i = 0; i < orariopreciosala.size(); i++) {
			System.out.println((1 + i) + ". " + (orariopreciosala.get(i)));
		}
		return orariopreciosala;
	}

	public static OrarioPrecioSalaSesion elegirorario(ControladorDB controlador,
			ArrayList<OrarioPrecioSalaSesion> orario) {
		
		int opcion = sc.nextInt();
		sc.nextLine();
		if (orario.isEmpty()) {
			System.out.println("error");
			return null;
		}
		OrarioPrecioSalaSesion orarioelegido = orario.get(opcion - 1);
		
		return orarioelegido; 
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
	    } else {
	        for (EspectadoresSesion e : numespectadores) {
	            System.out.println(e);
	        }
	    }
	    return numespectadores;
	}
}
