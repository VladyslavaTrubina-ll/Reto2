package vista;

import java.util.Scanner;
import modelo.ClienteAcesso;
import modelo.EspectadoresSesion;
import modelo.FechaSesion;
import modelo.OrarioPrecioSalaSesion;
import controlador.ControladorEntradaYSalida;
import java.util.ArrayList;
import controlador.ControladorDB;
import modelo.Pelicula;
import modelo.Sala;

public class prueba {

	static Sala S1 = new Sala("Sala Principal", 70);
	static Sala S2 = new Sala("Sala Premium", 55);
	static Sala S3 = new Sala("Sala 3D", 56);
	static Sala S4 = new Sala("Sala VIP", 50);
	static Sala S5 = new Sala("Sala Familiar", 80);
	static ControladorEntradaYSalida controladorentrada = new ControladorEntradaYSalida();

	public static void main(String args[]) {
		ArrayList<FechaSesion> fecha;
		String respuesta;
		ArrayList<OrarioPrecioSalaSesion> orariopreciosala;
		ControladorDB controlador = new ControladorDB("cine_daw");
		boolean conexionConExito = controlador.iniciarConexion();
		if (conexionConExito) {
			System.out.println("Se realizó la conexion con exito");
			if (!login(controlador)) {
				return;
			}
		} else {
			System.out.println("No hubo suerte");
			return;
		}

		mostrarpeliculas(controlador);
		String peliculaElegida = elegirpelicula(controlador);
		fecha = mostarfecha(controlador, peliculaElegida);
		FechaSesion fechaelegida = elegirfecha(controlador, fecha);

		orariopreciosala = mostrarorariopreciosala(controlador, fechaelegida);

		OrarioPrecioSalaSesion orarioelegido = elegirorario(controlador, orariopreciosala);
		ArrayList<EspectadoresSesion> printespectadores = mostrarespectadores(controlador, fechaelegida, orarioelegido);
		espectadoresactuales(printespectadores);
		selecionarnumerositios(printespectadores, orarioelegido);

	}

	public static boolean login(ControladorDB controlador) {

		System.out.println("inserire email");
		String email = controladorentrada.leerCadena();
		System.out.println("digitare password");
		String contraseña = controladorentrada.leerCadena();
		ArrayList<ClienteAcesso> cliente = controlador.obtenercliente(email, contraseña);
		boolean encontrado = false;
		int contador = 0;
		while (contador < cliente.size() && !encontrado) {
			ClienteAcesso c = cliente.get(contador);
			if (email.equals(c.getEmail()) && contraseña.equals(c.getContraseña())) {
				encontrado = true;
				System.out.println("login effettuato");
				return true;
			}
			contador++;
		}
		if (!encontrado) {
			System.out.println("error");

		}
		return encontrado;
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
		System.out.println("selecionar la pelicula que se quiere ver");
		String pelicula = controladorentrada.leerCadena();
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
		if (fechas.isEmpty()) {
			System.out.println("error");
			return null;
		}

		int opcion = controladorentrada.esValorMenuValido(1, fechas.size());
		return fechas.get(opcion - 1);
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

		int opcion = controladorentrada.esValorMenuValido(1, orario.size());

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
		}
		return numespectadores;
	}

	public static int espectadoresactuales(ArrayList<EspectadoresSesion> espectadores) {
		int sitiosdisponibles = espectadores.get(0).getEspectadores();
		if (sitiosdisponibles == S1.getSitios() || sitiosdisponibles == S2.getSitios()
				|| sitiosdisponibles == S3.getSitios() || sitiosdisponibles == S4.getSitios()
				|| sitiosdisponibles == S5.getSitios()) {
			System.out.println("no hay sitios disponibles");
		}
		return sitiosdisponibles;
	}

	public static void selecionarnumerositios(ArrayList<EspectadoresSesion> espectadores,
			OrarioPrecioSalaSesion obtenersala) {
		int capacidad = 0;
		String salanombre = obtenersala.getSala();

		if (salanombre.contains("Principal"))
			capacidad = 70;
		else if (salanombre.contains("Premium"))
			capacidad = 55;
		else if (salanombre.contains("3D"))
			capacidad = 56;
		else if (salanombre.contains("VIP"))
			capacidad = 50;
		else if (salanombre.contains("Familiar"))
			capacidad = 80;
		int ocupados = espectadores.get(0).getEspectadores();
		int disponibles = capacidad - ocupados;
		System.out.print("selecionar numero de asientos");
		int participantes = controladorentrada.pedirParticipantes(disponibles);
		espectadores.get(0).anadirespectadores(participantes);

		System.out.println("Reservados " + participantes + " asientos");
		System.out.println("Total en sala: " + espectadores.get(0).getEspectadores());

	}

}
