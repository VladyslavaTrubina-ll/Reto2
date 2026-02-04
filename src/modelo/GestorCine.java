package modelo;

import controlador.ControladorEntradaYSalida;
import controlador.ControladorDB;
import controlador.Imprimir;
import java.util.ArrayList;

public class GestorCine {

	/* Controladores y gestión */
	public ControladorEntradaYSalida controladorEntrada;
	public ControladorDB controlador;
	public Imprimir imprimir = new Imprimir();
	// Salas disponibles
	public ClienteAcesso clienteLogueado;

	public GestorCine() {
		this.controlador = new ControladorDB("cine_daw");
		this.controladorEntrada = new ControladorEntradaYSalida(); // inicializo controladores
		this.clienteLogueado = null;
	}

	public boolean conexionRealizada() {
		boolean conexionConExito = controlador.iniciarConexion();
		if (conexionConExito) {
			System.out.println("Se realizó la conexion con exito");
			return true;
		} else {
			System.out.println("No se realizó la conexion");
			return false;
		}
	}

	public ClienteAcesso login() {

		boolean encontrado = false;

		while (!encontrado) {
			String email = controladorEntrada.leerCadena("Escribe su email: ");
			String contraseña = controladorEntrada.leerCadena("Escribe su contraseña: ");

			ArrayList<ClienteAcesso> clientes = controlador.obtenerCliente(email);
			System.out.println(clientes);
			int contador = 0;

			while (contador < clientes.size() && !encontrado) {
				ClienteAcesso c = clientes.get(contador);
				if (email.equals(c.getEmail()) && contraseña.equals(c.getContraseña())) {
					encontrado = true;
					System.out.println("login realizado con suceso");
					this.clienteLogueado = c;
				}
				contador++;
			}
			if (!encontrado) {
				System.out.println("Datos incorrectos, intentalo de nuevo");
			}
		}
		return this.clienteLogueado;
	}

	public Pelicula elegirPelicula(ArrayList<Pelicula> peliculas) {
		int peliculaIndex = controladorEntrada.esValorMenuValido(1, peliculas.size());

		Pelicula peliElegida = peliculas.get(peliculaIndex - 1);
		System.out.println("Película seleccionada: " + peliElegida.getNombre());
		return peliElegida;
	}

	public String elegirFecha(ArrayList<String> fechas) {
		System.out.println("Test Elegir una fecha");

		if (fechas.isEmpty()) {
			System.out.println("Error : No hay fechas disponibles para esta película");
			return null;
		}
		int opcion = controladorEntrada.esValorMenuValido(1, fechas.size());
		String fechaElegida = fechas.get(opcion - 1);
		System.out.println("Fecha seleccionada: " + fechaElegida);

		return fechaElegida;
	}

	public Sesion elegirSesion(ArrayList<Sesion> sesiones) {

		int opcion = controladorEntrada.esValorMenuValido(1, sesiones.size());

		Sesion sesionElegido = sesiones.get(opcion - 1);
		System.out.println("Fecha seleccionada: " + sesionElegido);
		return sesionElegido;
	}

	public int seleccionarNumEspectadores(Sesion sesion) {
		int capacidad = sesion.getSala().getSitios();
		int ocupados = sesion.getNumEspectadores();

		int disponibles = capacidad - ocupados;

		if (disponibles <= 0) {
			System.out.println("La sesion esta al completo");
			return 0;
		}
		System.out.print("selecionar numero de asientos");

		int participantes = controladorEntrada.numBilletesComprandos(disponibles);

		System.out.println("Reservados " + participantes + " asientos");
		System.out.println("Total en sala: " + (ocupados + participantes));
		return participantes;
	}

	public Sesion generarEntrada(Pelicula pelicula, String fecha, Sala sala, String horaInicio, String horaFin, int numPersonas,
			double precio) {
		Sesion nuevaentrada = new Sesion();
		nuevaentrada.setPelicula(pelicula);
		nuevaentrada.setFecha(fecha);
		nuevaentrada.setSala(sala);
		nuevaentrada.setHoraInicio(horaInicio);
		nuevaentrada.setHoraFin(horaFin);
		nuevaentrada.setNumEspectadores(numPersonas);
		nuevaentrada.setPrecio(precio);
		return nuevaentrada;

	}

	public boolean confirmarcompra(Carrito carrito) {
		String confirma = controladorEntrada.leerSiNo("Confirmar compra?");

		if (confirma.equalsIgnoreCase("si")) {
			System.out.println(" compra confirmada");
			return true;
		} else {
			return false;
		}
	}

	
	public ArrayList<String> obtenerIdSesion(Carrito carrito) {
		ArrayList<String> idSessiones = new ArrayList<>();

		for (Sesion sesion : carrito.getSesiones()) {
			String idSesion = controlador.obtenerIdSesion(sesion.getFecha(), sesion.getHoraInicio(),
					sesion.getSala().getNombre());
			idSessiones.add(idSesion);
		}
		return idSessiones;
	}

	public boolean dniOEmailYaRegistrados(String text) {
	
		int contador = 0;

		while (contador < controlador.dniEmailCliente().size()) {

			String dato = controlador.dniEmailCliente().get(contador).getDni();
			String dato2 = controlador.dniEmailCliente().get(contador).getEmail();
			// COMPARAMOS DIRECTAMENTE
			if (text.equals(dato) || text.equals(dato2)) {
				System.out.println("Error usuario ya registrado con este dato");
				return true;
			} else {
				contador++;
			}
		}
		return false;
	}
}
