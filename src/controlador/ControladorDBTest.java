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
	    String timestamp = String.valueOf(System.currentTimeMillis());
		String dni =  timestamp.substring(timestamp.length()-8) + "A";
		String nombre = "Ernesto";
		String apellidos = "Sparalesto";
		String email =  timestamp + "@gmail.com";
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

	@Test
	public void testObtenerClienteConEmailNull() {
		ArrayList<ClienteAcesso> clientes = controlador.obtenerCliente(null);
		assertNotNull("La lista non dovrebbe essere null anche con email null", clientes);
		System.out.println("Risultato: OK - Lista non null");
	}

	@Test
	public void testObtenerClienteConEmailVuoto() {
		ArrayList<ClienteAcesso> clientes = controlador.obtenerCliente("");
		assertNotNull("La lista non dovrebbe essere null anche con email vuota", clientes);
		System.out.println("Risultato: OK - Lista non null");
	}

	@Test
	public void testObtenerClienteConEmailInesistente() {
		String emailInesistente = "email.che.non.esiste@gmail.com";
		System.out.println("Email usata: " + emailInesistente);
		ArrayList<ClienteAcesso> clientes = controlador.obtenerCliente(emailInesistente);
		assertNotNull("La lista non dovrebbe essere null", clientes);
		assertTrue("La lista dovrebbe essere vuota per email inesistente", clientes.isEmpty());
		System.out.println("Risultato: OK - Lista vuota");
	}

	@Test
	public void testObtenerFechasPorPerliConTitoloNull() {
		ArrayList<String> fechas = controlador.obtenerFechasPorPerli(null);
		assertNotNull("La lista non dovrebbe essere null anche con titolo null", fechas);
		System.out.println("Risultato: OK - Lista non null");
	}

	@Test
	public void testObtenerFechasPorPerliConTitoloVuoto() {
		System.out.println("=== TEST: Obtener date con titolo vuoto ===");
		ArrayList<String> fechas = controlador.obtenerFechasPorPerli("");
		assertNotNull("La lista non dovrebbe essere null anche con titolo vuoto", fechas);
		System.out.println("Risultato: OK - Lista non null");
	}

	@Test
	public void testObtenerFechasPorPerliConTitoloInesistente() {
		String titoloInesistente = "TitoloCheNonEsiste";
		System.out.println("Titolo usato: " + titoloInesistente);
		ArrayList<String> fechas = controlador.obtenerFechasPorPerli(titoloInesistente);
		assertNotNull("La lista non dovrebbe essere null", fechas);
		assertTrue("La lista dovrebbe essere vuota per titolo inesistente", fechas.isEmpty());
		System.out.println("Risultato: OK - Lista vuota");
	}

	@Test
	public void testObtenerSesionesPorPerliConFechaNull() {
		Pelicula peliculaTest = new Pelicula("Dune", 155, "Ciencia Ficción/Aventura");
		ArrayList<Sesion> sesiones = controlador.obtenerSesionesPorPerli(null, peliculaTest);
		assertNotNull("La lista non dovrebbe essere null anche con data null", sesiones);
		System.out.println("Risultato: OK - Lista non null");
	}

	@Test(expected = NullPointerException.class)
	public void testObtenerSesionesPorPerliConPeliculaNull() {
		String fechaTest = "2026-02-11";
		ArrayList<Sesion> sesiones = controlador.obtenerSesionesPorPerli(fechaTest, null);
		assertNotNull("La lista non dovrebbe essere null anche con pelicula null", sesiones);
		System.out.println("Risultato: OK - Lista non null");
	}

	@Test
	public void testInsertarUsuarioConDniDuplicato() {
		String dniEsistente = "0230000AJ";
		String nombre = "Ernesto";
		String apellidos = "Sparalesto";
		String email = "email@gmail.com";
		String contrasena = "password123";
		
		System.out.println("DNI duplicato: " + dniEsistente);
		System.out.println("Email unica: " + email);

		int resultado = controlador.insertarUsuario(dniEsistente, nombre, apellidos, email, contrasena);
		assertTrue("Inserimento con DNI duplicato dovrebbe fallire", resultado <= 0);
		System.out.println("Risultato: OK - Inserimento fallito (risultato: " + resultado + ")");
	}

	@Test
	public void testInsertarUsuarioConEmailDuplicata() {
		String dniNuovo = "43403291X";
		String nombre = "Test";
		String apellidos = "Duplicato";
		String emailEsistente = "LAmadonnalala@gmail.com";
		String contrasena = "password123";
		
		System.out.println("DNI nuovo: " + dniNuovo);
		System.out.println("Email duplicata: " + emailEsistente);

		int resultado = controlador.insertarUsuario(dniNuovo, nombre, apellidos, emailEsistente, contrasena);
		assertTrue("Inserimento con email duplicata dovrebbe fallire", resultado <= 0);
		System.out.println("Risultato: OK - Inserimento fallito (risultato: " + resultado + ")");
	}

	@Test
	public void testInsertarCompraConDniClienteInexistente() {
		String dniInexistente = "DNI_INESISTENTE_999";
		int numentradas = 2;
		double preciototal = 20.0;
		double descuentoaplicado = 2.0;
		
		System.out.println("DNI inesistente: " + dniInexistente);

		int idCompra = controlador.insertarCompra(dniInexistente, numentradas, preciototal, descuentoaplicado);
		assertTrue("Inserimento compra con cliente inesistente dovrebbe fallire", idCompra <= 0);
		System.out.println("Risultato: OK - Inserimento fallito (risultato: " + idCompra + ")");
	}

	@Test
	public void testInsertarEntradaConIdCompraInexistente() {
		int id_compra_inexistente = 999999;
		String id_sesion = "SES01";
		int numentradas = 2;
		double preciototal = 20.0;
		double descuentoaplicado = 2.0;
		
		System.out.println("ID compra inesistente: " + id_compra_inexistente);

		int resultado = controlador.insertarEntrada(id_compra_inexistente, id_sesion, numentradas, preciototal, descuentoaplicado);
		assertTrue("Inserimento entrata con compra inesistente dovrebbe fallire", resultado <= 0);
		System.out.println("Risultato: OK - Inserimento fallito (risultato: " + resultado + ")");
	}

	@Test
	public void testInsertarEspectadoresConSesionInexistente() {
		String idSesionInexistente = "SES_INEXISTENTE_999";
		int numespectadores = 10;
		
		System.out.println("ID sesion inexistente: " + idSesionInexistente);

		int resultado = controlador.insertarEspectadores(idSesionInexistente, numespectadores);
		assertTrue("Update spettatori con sessione inesistente dovrebbe fallire", resultado <= 0);
		System.out.println("Risultato: OK - Update fallito (risultato: " + resultado + ")");
	}

	@Test
	public void testObtenerIdSesionConParametriInexistentes() {
		String fechaInexistente = "2099-01-01";
		String horaInexistente = "99:99:99";
		String salaInexistente = "SalaCheNonEsiste";
		System.out.println("Data: " + fechaInexistente);
		System.out.println("Ora: " + horaInexistente);
		System.out.println("Sala: " + salaInexistente);

		String idSesion = controlador.obtenerIdSesion(fechaInexistente, horaInexistente, salaInexistente);
		assertTrue("Con parametri inesistenti dovrebbe restituire stringa vuota o null", idSesion == null || idSesion.isEmpty());
		System.out.println("Risultato: OK - Stringa vuota/null (risultato: '" + idSesion + "')");
	}

	@Test(expected = NullPointerException.class)
	public void testMetodiConConexionCerrada() {
		ControladorDB controladorSinConexion = new ControladorDB("cine_daw");
		System.out.println("Testando obtenerPelis() senza connessione...");
		ArrayList<Pelicula> pelis = controladorSinConexion.obtenerPelis();
		assertNotNull("D", pelis);
		System.out.println("obtenerPelis: OK");
		System.out.println("Testando dniEmailCliente() senza connessione...");
		ArrayList<dniMailCliente> clientes = controladorSinConexion.dniEmailCliente();
		assertNotNull("algo sin conexion", clientes);
		System.out.println("dniEmailCliente: OK");
	}

	@Test
	public void testIniciarConexionConDBInexistente() {
		ControladorDB controladorDBInesistente = new ControladorDB("cine");
		System.out.println("DB inesistente: " + controladorDBInesistente);
		boolean resultado = controladorDBInesistente.iniciarConexion();
		assertFalse("La connessione a DB inesistente dovrebbe fallire", resultado);
		System.out.println("Risultato: OK - Connessione fallita");
	}
}
