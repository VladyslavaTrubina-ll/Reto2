package controlador;

import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.ArrayList;
import modelo.Pelicula;
import modelo.Sala;
import modelo.ClienteAcesso;
import modelo.Sesion;
import modelo.dniMailCliente;

public class ControladorDBTest {

	private static ControladorDB controlador;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		controlador = new ControladorDB("cine_daw");
		boolean conectado = controlador.iniciarConexion();
		System.out.println("Connessione stabilita nel BeforeClass: " + conectado);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

		if (controlador != null) {
			boolean cerrado = controlador.cerrarConexion();
			System.out.println("Connessione chiusa nell'AfterClass: " + cerrado);
		}
	}

	@Test
	public void testIniciarConexion() {
		assertNotNull("Controlador non dovrebbe essere null", controlador);
	}

	@Test
	public void testCerrarConexion() {

		try {

			System.out.println("Test chiusura connessione (non chiusa realmente)");
		} catch (Exception e) {
			fail("Non dovrebbe lanciare eccezioni");
		}
	}

	@Test
	public void acesoCliente() {
		String email = "jorge.ibanez@email.com";
		ArrayList<ClienteAcesso> clientes = controlador.obtenerCliente(email);
		assertNotNull("La lista de clientes no debe ser nula", clientes);
		if (clientes != null) {
			assertFalse("La lista no debería estar vacía", clientes.isEmpty());
		}
		ClienteAcesso cliente = clientes.get(0);
		assertNotNull("el cliene no debe ser nulo", cliente);
		// el mail tiene que ser el mismo//
		assertEquals(email, cliente.getEmail());
		assertNotNull("el nombre no tiene que ser nulo", cliente.getNombre());
		assertNotNull("los apellidos no tienen que ser nulos", cliente.getApellidos());
		assertNotNull("el dni no tiene que ser nulo", cliente.getDni());
		assertNotNull("la contrasena no tiene que ser nula ", cliente.getContraseña());
	}

	@Test
	public void testObtenerPelis() {
		ArrayList<Pelicula> pelis = controlador.obtenerPelis();
		assertNotNull("La lista non dovrebbe essere null", pelis);
		for (Pelicula peli : pelis) {
			assertNotNull("Il titolo non dovrebbe essere null", peli.getNombre());
			assertTrue("La durata dovrebbe essere positiva", peli.getDuracion() > 0);
		}
	}

	@Test
	public void testObtenerFechasPorPerli() {

		String tituloTest = "Dune";
		ArrayList<String> fechas = controlador.obtenerFechasPorPerli(tituloTest);
		assertNotNull("La lista non dovrebbe essere null", fechas);
		for (String fecha : fechas) {
			assertNotNull("La data non dovrebbe essere null", fecha);
		}
	}

	@Test
	public void testObtenerSesionesPorPerli() {

		Pelicula peliculaTest = new Pelicula("Avatar", 155, "Ciencia Ficción/Aventura");
		String fechaTest = "2026-02-11";
		ArrayList<Sesion> sesiones = controlador.obtenerSesionesPorPerli(fechaTest, peliculaTest);
		assertNotNull("La lista non dovrebbe essere null", sesiones);
		for (Sesion sesion : sesiones) {
			assertNotNull("La sesione non dovrebbe essere null", sesion);
			assertEquals("La data dovrebbe corrispondere", fechaTest, sesion.getFecha());
			assertEquals("Il titolo del film dovrebbe corrispondere", peliculaTest.getNombre(),
					sesion.getPelicula().getNombre());
		}
	}

	@Test
	public void testInsertarUsuario() {
		String dni = "0230000AJ";
		String nombre = "Ernesto";
		String apellidos = "Sparalesto";
		String email = "LAmadonnalala@gmail.com";
		String contrasena = "password123";

		int resultado = controlador.insertarUsuario(dni, nombre, apellidos, email, contrasena);

		assertTrue("L'inserimento dovrebbe riuscire, risultato: " + resultado, resultado > 0);
	}

	@Test
	public void testInsertarCompra() {
		String dniCliente = "00113344F";

		int numentradas = 2;
		double preciototal = 20.0;
		double descuentoaplicado = 2.0;

		int idCompra = controlador.insertarCompra(dniCliente, numentradas, preciototal, descuentoaplicado);

		assertTrue("L'ID della compra dovrebbe essere positivo: " + idCompra, idCompra > 0);
	}

	@Test
	public void testInsertarEntrada() {

		int id_compra = 1;
		String id_sesion = "SES01";
		int numentradas = 2;
		double preciototal = 20.0;
		double descuentoaplicado = 2.0;

		int resultado = controlador.insertarEntrada(id_compra, id_sesion, numentradas, preciototal, descuentoaplicado);

		assertTrue("L'inserimento dovrebbe riuscire: " + resultado, resultado > 0);
	}

	@Test
	public void testObtenerIdSesion() {
		String fecha = "2026-02-11";
		String hora = "22:00:00";
		String sala = "Sal01";

		String idSesion = controlador.obtenerIdSesion(fecha, hora, sala);

		assertNotNull("L'ID della sessione non dovrebbe essere null", idSesion);
	}

	@Test
	public void testInsertarEspectadores() {

		String idSesion = "SES01";
		int numespectadores = 10;

		int resultado = controlador.insertarEspectadores(idSesion, numespectadores);

		assertTrue("L'aggiornamento dovrebbe riuscire: " + resultado, resultado >= 0);
	}

	@Test
	public void testDniEmailCliente() {
		ArrayList<dniMailCliente> resultados = controlador.dniEmailCliente();
		assertNotNull("La lista non dovrebbe essere null", resultados);
		for (dniMailCliente cliente : resultados) {
			assertNotNull("Il DNI non dovrebbe essere null", cliente.getDni());
			assertNotNull("L'email non dovrebbe essere null", cliente.getEmail());
		}
	}
}