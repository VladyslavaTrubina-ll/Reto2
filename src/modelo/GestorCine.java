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

	/**
	 * @param ClienteAcesso para hacer login, pide mail y contrasena, recoge los datos del cliente corrispodiente al mail
	 * con el metodo aposito creato en el controladorDB 
	 * 
	 * @return si el login es correcto devueve un objeto ClienteAcesso con los datos del cliente sino sale error 
	 * y pide los datos otra vez
	 */
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

	/**
	 * @param peliculas metodo para elegir una pelicula en el arraylist peliculas que nos devuelve el metodo imprimir peliculas
	 * @return retorna un objeto Pelicula con los atributos de la pelicula elegida.
	 */
	
	public Pelicula elegirPelicula(ArrayList<Pelicula> peliculas) {
		int peliculaIndex = controladorEntrada.esValorMenuValido(1, peliculas.size());

		Pelicula peliElegida = peliculas.get(peliculaIndex - 1);
		System.out.println("Película seleccionada: " + peliElegida.getNombre());
		return peliElegida;
	}

	/**
	 * 
	 * @param fechas igual a pelicula este metodo sirve para elegir una fecha entre las disponibles en el array generado
	 * por el metodo imprimir.fechas que utiliza la query del controladorDB
	 * @return devuelve uno String que corresponde a la fecha elegida
	 */
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
/**
 * 
 * @param sesiones igual a fecha, metodo para elegir las diferentes sesiones del arraylist sesion generado con la combinacion
 * de los metodos imprimir y la query en el controladorDB
 * @return objeto Sesion con atributos de sesion elegida
 */
	
	public Sesion elegirSesion(ArrayList<Sesion> sesiones) {

		int opcion = controladorEntrada.esValorMenuValido(1, sesiones.size());

		Sesion sesionElegido = sesiones.get(opcion - 1);
		System.out.println("Fecha seleccionada: " + sesionElegido);
		return sesionElegido;
	}

/**
 * 
 * @param sesion metodo para selecionar numero de partecipanetes a la sesion, recoge un objeto sesion
 * compara los sitios ya ocupados con la capacidad de la sala, si hay sitios enseña los sitios disponible y deja elegir
 * atarves del controlador de entrada no permite selecionar mas sitios de lo disponibles
 * @return un int con los sitios selecionados
 */
	
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
	
	/**
	 * 
	 * @param carrito metodo que recoge el carrito para comparar la fecha, horainicio, e sala de cada entrada comprada
	 * estos dato seran luego utilizados por el metodo en  el controladorDB que obterra el IDSESION que corresponde a cada entrada
	 * comprada por el cliente
	 * @return devuelve un arraylist de string con los id de las diferentes sesioens
	 */
	
	public ArrayList<String> obtenerIdSesion(Carrito carrito) {
		ArrayList<String> idSessiones = new ArrayList<>();

		for (Sesion sesion : carrito.getSesiones()) {
			String idSesion = controlador.obtenerIdSesion(sesion.getFecha(), sesion.getHoraInicio(),
					sesion.getSala().getNombre());
			idSessiones.add(idSesion);
		}
		return idSessiones;
	}

/**
 * 
 * @param text meotodo que recoge un String (dni o mail) y lo compara con los presentes en la base de datos
 * si el usuario en fase de registracion ententa poner un dni o un mail que ya estan registrado no podra hacerlo
 * @return retorna un false si no encuentra corispondencia y un true si la encuentra
 */
	public boolean dniOEmailYaRegistrados(String text) {
	
		int contador = 0;
		ArrayList<dniMailCliente> dniEmailClientes = controlador.dniEmailCliente();
		while (contador < dniEmailClientes.size()) {

			String dato = dniEmailClientes.get(contador).getDni();
			String dato2 = dniEmailClientes.get(contador).getEmail();
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
