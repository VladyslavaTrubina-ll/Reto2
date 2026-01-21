package vista;

import java.util.Scanner;
import modelo.ClienteAcesso;
import modelo.FechaSesion;

import java.util.ArrayList;
import controlador.ControladorDB;
import modelo.Pelicula;

public class prueba {

	private static Scanner sc = new Scanner(System.in);

	public static void main(String args[]) {
		ControladorDB controlador = new ControladorDB("cine_daw");
		boolean conexionConExito = controlador.iniciarConexion();
		if (conexionConExito) {
			System.out.println("Se realizó la conexion con exito");
			login(controlador);
		} else {
			System.out.println("No hubo suerte");
		}

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
				mostrarpeliculas(controlador);
				elegirpelicula(controlador);
			}
		}
	}

	public static void mostrarpeliculas(ControladorDB controlador) {
		System.out.println("pelicuals disponibles");
		System.out.println("---------------------");
		ArrayList<Pelicula> peliculas = controlador.obtenerpelis();
		for (int i = 0; i < peliculas.size(); i++) {
			Pelicula p = peliculas.get(i);
			System.out.println(peliculas.toString());
		}
	}

	public static void elegirpelicula(ControladorDB controlador) {
		ArrayList<Pelicula> peliculas = controlador.obtenerpelis();
		System.out.println("selecionar la pelicula que se quiere vivir");
		String pelicula = sc.nextLine();
		boolean encontrado = false;
		int contador = 0;
		while (contador < peliculas.size() && !encontrado) {
			Pelicula p = peliculas.get(contador);
			if (pelicula.equalsIgnoreCase(p.getTitulo())) {
				encontrado = true;
				System.out.println("Pelicula selecionada con suceso");
				mostarfecha(controlador, pelicula);
			} else {
				contador++;
			}
		}

	}

	public static void mostarfecha(ControladorDB controlador, String titulo) {
		ArrayList<FechaSesion> fechas = controlador.obtenerfechasporperli(titulo);
		for (int i = 0; i < fechas.size(); i++) {
			System.out.println((1 + i) + ". " + (fechas.get(i)));
		}
		elegirfecha(fechas);
	}

	public static FechaSesion elegirfecha(ArrayList<FechaSesion> fechas2) {
		System.out.println("Elegir una fecha");
		int opcion = sc.nextInt();
		if (fechas2.isEmpty()) {
			System.out.println("error");

		} else {
			System.out.println("bene");
		}

		return fechas2.get(opcion - 1);
	}
}