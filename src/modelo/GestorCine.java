package modelo;

import modelo.Carrito;
import modelo.Entrada;
import modelo.OrarioPrecioSalaSesion;
import modelo.ClienteAcesso;
import modelo.EspectadoresSesion;
import modelo.FechaSesion;
import modelo.GestorCarrito;
import modelo.Pelicula;
import modelo.Sesión;
import modelo.Sala;
import modelo.Ticket;
import controlador.ControladorEntradaYSalida;
import controlador.ControladorDB;
import java.util.ArrayList;

public class GestorCine {
	public ControladorEntradaYSalida controladorentrada;
	public ControladorDB controlador;
	public Sala S1 = new Sala("Sala Principal", 70);
	public Sala S2 = new Sala("Sala Premium", 55);
	public Sala S3 = new Sala("Sala 3D", 56);
	public Sala S4 = new Sala("Sala VIP", 50);
	public Sala S5 = new Sala("Sala Familiar", 80);
	public ClienteAcesso clienteLogueado;

	public GestorCine() {
		this.controlador = new ControladorDB("cine_daw");
		this.controladorentrada = new ControladorEntradaYSalida(); // inicializo controladores
		this.clienteLogueado = null;
	}

	public boolean conexionrealizada() {
		boolean conexionConExito = controlador.iniciarConexion();
		if (conexionConExito) {
			System.out.println("Se realizó la conexion con exito");
			return true;
		} else {
			System.out.println("No hubo suerte");
			return false;
		}

	}

	public ClienteAcesso login() {
		ArrayList<Pelicula> peliculas = this.controlador.obtenerpelis();
		boolean encontrado = false;
		ClienteAcesso clienteLogueado = null;

		while (!encontrado) {
			System.out.println("inserire email");
			String email = controladorentrada.leerCadena();
			System.out.println("digitare password");
			String contraseña = controladorentrada.leerCadena();
			ArrayList<ClienteAcesso> cliente = controlador.obtenercliente(email, contraseña);
			int contador = 0;
			while (contador < cliente.size() && !encontrado) {
				ClienteAcesso c = cliente.get(contador);
				if (email.equals(c.getEmail()) && contraseña.equals(c.getContraseña())) {
					encontrado = true;
					System.out.println("login effettuato");
					clienteLogueado = c;
				}
				contador++;
			}
			System.out.println("error");

		}
		return clienteLogueado;
	}

	public String elegirpelicula(ControladorDB controlador) { // elegir pelicula, con ciclo hasta que no se escriba la
																// pelicula, implementado
																// en el if manera de no contar acentos//

		boolean peliencontrada = false;

		while (!peliencontrada) {
			ArrayList<Pelicula> peliculas = controlador.obtenerpelis();
			System.out.println("selecionar la pelicula que se quiere ver");
			String pelicula = controladorentrada.leerCadena();
			for (Pelicula p : peliculas) {
				if (pelicula.equalsIgnoreCase(p.getTitulo())
						|| pelicula.replace('á', 'a').replace('é', 'e').replace('í', 'i').replace('ó', 'o')
								.replace('ú', 'u').equalsIgnoreCase(p.getTitulo().replace('á', 'a').replace('é', 'e')
										.replace('í', 'i').replace('ó', 'o').replace('ú', 'u'))) {
					System.out.println("Pelicula selecionada con suceso");
					peliencontrada = true;
					return p.getTitulo();
				}
			}
			System.out.println("no peli");
			peliencontrada = false;
		}
		return null;
	}

	public FechaSesion elegirfecha(ControladorDB controlador, ArrayList<FechaSesion> fechas) { // elegir fecha de
																								// pelicula
																								// selecionada//
		System.out.println("Elegir una fecha");
		if (fechas.isEmpty()) {
			System.out.println("error");
			return null;
		}

		int opcion = controladorentrada.esValorMenuValido(1, fechas.size());
		return fechas.get(opcion - 1);
	}

	public OrarioPrecioSalaSesion elegirorario(ControladorDB controlador, ArrayList<OrarioPrecioSalaSesion> orario) {

		int opcion = controladorentrada.esValorMenuValido(1, orario.size());

		if (orario.isEmpty()) {
			System.out.println("error");
			return null;
		}
		OrarioPrecioSalaSesion orarioelegido = orario.get(opcion - 1);

		return orarioelegido;
	}

	public int selecionarnumerositios(ArrayList<EspectadoresSesion> espectadores, OrarioPrecioSalaSesion obtenersala) { // selecionar
																														// asientos
																														// con

		if (espectadores == null || espectadores.isEmpty()) {
			System.out.println("Error: No hay espectadores para esta sesión");
			return 0; // AGGIUNGI QUESTO RETURN!
		}

		int capacidad = 0;

		String salanombre = obtenersala.getSala();

		if (salanombre.contains("Principal"))
			capacidad = S1.getSitios();
		else if (salanombre.contains("Premium"))
			capacidad = S2.getSitios();
		else if (salanombre.contains("3D"))
			capacidad = S3.getSitios();
		else if (salanombre.contains("VIP"))
			capacidad = S4.getSitios();
		else if (salanombre.contains("Familiar"))
			capacidad = S5.getSitios();
		int ocupados = espectadores.get(0).getEspectadores();
		int disponibles = capacidad - ocupados;
		System.out.print("selecionar numero de asientos");
		int participantes = controladorentrada.pedirParticipantes(disponibles);
		espectadores.get(0).anadirespectadores(participantes);

		System.out.println("Reservados " + participantes + " asientos");
		System.out.println("Total en sala: " + espectadores.get(0).getEspectadores());
		return participantes;
	}

	public Entrada generarEntrada(String titulo, String orario, int numeropersonas, double precio, double descuento) {
		Entrada nuevaentrada = new Entrada();
		nuevaentrada.setPelicula(titulo);
		nuevaentrada.setOrario(orario);
		nuevaentrada.setNumeropersonas(numeropersonas);
		nuevaentrada.setPrecio(precio);
		nuevaentrada.setDescuento(descuento);
		return nuevaentrada;

	}
}
