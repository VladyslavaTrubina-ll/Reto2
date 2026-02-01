package modelo;

import modelo.Carrito;
import modelo.Sesion;
import modelo.OrarioPrecioSalaSesion;
import modelo.ClienteAcesso;
import modelo.EspectadoresSesion;
import modelo.FechaSesion;
import modelo.Sala;
import modelo.Pelicula;

import modelo.Ticket;
import controlador.ControladorEntradaYSalida;
import controlador.ControladorDB;
import controlador.Imprimir;
import java.util.ArrayList;


public class GestorCine {

	/*Controladores y gestión*/
	public ControladorEntradaYSalida controladorEntrada;
	public ControladorDB controlador;

	// Salas disponibles
	public Sala S1 = new Sala("Sala Principal", 100);
	public Sala S2 = new Sala("Sala Premium", 55);
	public Sala S3 = new Sala("Sala 3D", 56);
	public Sala S4 = new Sala("Sala VIP", 50);
	public Sala S5 = new Sala("Sala Familiar", 80);
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
		ArrayList<Pelicula> peliculas = this.controlador.obtenerPelis();
		
		boolean encontrado = false;
		//ClienteAcesso clienteLogueado = null;

		while (!encontrado) {
			System.out.print("Escribe su email: ");
			String email = controladorEntrada.leerCadena();
			System.out.print("Escribe su contraseña: ");
			String contraseña = controladorEntrada.leerCadena();
			
			ArrayList<ClienteAcesso> cliente = this.controlador.obtenerCliente(email, contraseña);
			int contador = 0;
			
			while (contador < cliente.size() && !encontrado) {
				ClienteAcesso c = cliente.get(contador);
				if (email.equals(c.getEmail()) && contraseña.equals(c.getContraseña())) {
					encontrado = true;
					System.out.println("login realizado con suceso");
					this.clienteLogueado = c;
				}
				contador++;
			}
			if (!encontrado) {
				System.out.println("datos incorrectos, intentalo de nuevo");
			}
		}
		return this.clienteLogueado;
	}

	public Pelicula elegirPelicula(ControladorDB controlador) { /*elegir pelicula, con ciclo hasta que no se escriba la
																pelicula, implementado en el if manera de no contar acentos/ */

		boolean peliencontrada = false;

		while (!peliencontrada) {
			ArrayList<Pelicula> peliculas = controlador.obtenerPelis();
			System.out.println("selecionar la pelicula que se quiere ver");
			String pelicula = this.controladorEntrada.leerCadena();
			for (Pelicula p : peliculas) {
				if (pelicula.equalsIgnoreCase(p.getNombre())

						|| pelicula.replace('á', 'a').replace('é', 'e').replace('í', 'i').replace('ó', 'o')
							.replace('ú', 'u').equalsIgnoreCase(p.getNombre().replace('á', 'a').replace('é', 'e')
							.replace('í', 'i').replace('ó', 'o').replace('ú', 'u'))) {

					System.out.println("Pelicula selecionada con suceso");
					peliencontrada = true;
					return p;
				}
			}
			System.out.println("Pelicula no encontrada, intentalo de nuevo");
			peliencontrada = false;
		}
		return null; //TODO sin sentido
	}

	public FechaSesion elegirFecha(ControladorDB controlador /*TODO para que controlador */, ArrayList<FechaSesion> fechas) { // elegir fecha de
																								// pelicula
																								// seleccionada//
		System.out.println("Elegir una fecha");
		if (fechas.isEmpty()) {
			System.out.println("Error : No hay fechas disponibles para esta película");
			return null;
		}
		int opcion = controladorEntrada.esValorMenuValido(1, fechas.size());
		return fechas.get(opcion - 1);
	}

	public Sesion elegirSesion() {
		// TODO hacer
		
	}
	public OrarioPrecioSalaSesion elegirHorario(ControladorDB controlador, ArrayList<OrarioPrecioSalaSesion> horario) {

		int opcion = this.controladorEntrada.esValorMenuValido(1, horario.size());

		if (horario.isEmpty()) {
			System.out.println("Error : No hay horarios disponibles para esta fecha");
			return null;
		}
		OrarioPrecioSalaSesion horarioElegido = horario.get(opcion - 1);

		return horarioElegido;
	}

	public verificarSiHaySillas() {
		// TODO hacer

		//no 

		//si 
	}
	public int seleccionarNumEspectadores(ArrayList<EspectadoresSesion> espectadores, OrarioPrecioSalaSesion obtenerSala) { // seleccionar
																														// asientos
																														// con

		if (espectadores == null || espectadores.isEmpty()) {
			System.out.println("Error: No hay espectadores para esta sesión");
			return 0; // AGGIUNGI QUESTO RETURN!
		}

		int capacidad = 0;

		String salaNombre = obtenerSala.getSala();

		if (salaNombre.contains("Principal"))
			capacidad = S1.getSitios();
		else if (salaNombre.contains("Premium"))
			capacidad = S2.getSitios();
		else if (salaNombre.contains("3D"))
			capacidad = S3.getSitios();
		else if (salaNombre.contains("VIP"))
			capacidad = S4.getSitios();
		else if (salaNombre.contains("Familiar"))
			capacidad = S5.getSitios();

		int ocupados = espectadores.get(0).getEspectadores();
		int disponibles = capacidad - ocupados;
		System.out.print("selecionar numero de asientos");
		int participantes = this.controladorEntrada.pedirParticipantes(disponibles);
		espectadores.get(0).anadirespectadores(participantes);

		System.out.println("Reservados " + participantes + " asientos");
		System.out.println("Total en sala: " + espectadores.get(0).getEspectadores());
		return participantes;
	}

	public Sesion generarEntrada(Pelicula pelicula, String fecha, String sala, String horario, int numPersonas, double precio) {
		Sesion nuevaentrada = new Sesion();
		nuevaentrada.setPelicula(pelicula);
		nuevaentrada.setFecha(fecha);
		nuevaentrada.setSala(sala);
		nuevaentrada.setHorario(horario);
		nuevaentrada.setNumEspectadores(numPersonas);
		nuevaentrada.setPrecio(precio);
		return nuevaentrada;

	}

	public boolean confirmarcompra(Carrito carrito) {
		System.out.print("Confirmar compra? (si/no): ");
		String confirma = this.controladorEntrada.leerCadena();
		if (confirma.equalsIgnoreCase("si")) {
			System.out.println(" compra confirmada");
			return true;
		} else {
			return false;
		}
	}

	
	/*public static void main(String[] args) {
		GestorCine gestor = new GestorCine();
		System.out.println("GestorCine inicializado: " + gestor);

		if (!gestor.conexionRealizada()) {
			System.out.println("No se pudo conectar a la base de datos");
			return;
		}

		// 1) Login (requiere DB y entrada por consola)
		ClienteAcesso cliente = gestor.login();
		if (cliente == null) {
			System.out.println("No se pudo iniciar sesión");
			return;
		}

		// 2) Elegir horario (ejemplo con lista manual)
		ArrayList<OrarioPrecioSalaSesion> horarios = new ArrayList<>();
		horarios.add(new OrarioPrecioSalaSesion("18:00", 7.5, "Sala Principal"));
		horarios.add(new OrarioPrecioSalaSesion("20:30", 8.0, "Sala Premium"));
		OrarioPrecioSalaSesion horarioElegido = gestor.elegirHorario(gestor.controlador, horarios);
		if (horarioElegido == null) {
			System.out.println("No se pudo elegir horario");
			return;
		}

		// 3) Seleccionar número de espectadores
		ArrayList<EspectadoresSesion> espectadores = new ArrayList<>();
		espectadores.add(new EspectadoresSesion(0));
		int numPersonas = gestor.seleccionarNumEspectadores(espectadores, horarioElegido);
		if (numPersonas <= 0) {
			System.out.println("No se seleccionaron entradas");
			return;
		}

		// 4) Generar entrada (sesión de ejemplo)
		Pelicula pelicula = new Pelicula("Pelicula Test", 90);
		Sesion sesion = gestor.generarEntrada(
				pelicula,
				"2026-01-29",
				horarioElegido.getSala(),
				horarioElegido.getOrario(),
				numPersonas,
				horarioElegido.getPrecio()
		);

		// 5) Confirmar compra
		Carrito carrito = new Carrito();
		carrito.anadirEntrada(sesion, numPersonas);
		boolean compraOk = gestor.confirmarcompra(carrito);
		System.out.println("Compra confirmada: " + compraOk);
	}*/

}
