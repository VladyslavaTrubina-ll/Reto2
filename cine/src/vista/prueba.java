package vista;

import java.util.Scanner;
import modelo.ClienteAcesso;
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
			} else {
				contador++;
			}
		}

	}
}
