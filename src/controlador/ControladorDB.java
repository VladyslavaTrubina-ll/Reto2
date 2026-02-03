package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import modelo.Pelicula;
import modelo.Sala;
import modelo.ClienteAcesso;
import modelo.EspectadoresSesion;
import modelo.Sesion;
import modelo.dniMailCliente;


public class ControladorDB {
	private Connection conexion;
	private String nombreBD;

	// Constructores
	public ControladorDB(String nombreBD) {
		this.nombreBD = nombreBD;
	}

	public ControladorDB() {

	}

	// Iniciar conexion
	public boolean iniciarConexion() {
		boolean conexionRealizada = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Parametros para la conexion --> URL, user, pass puede hacer falta el puerto
			// localhost:puerto/
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/" + this.nombreBD, "root", "");
			conexionRealizada = true;
		} catch (ClassNotFoundException e) {
			System.out.println("No se encontró la librería de sqlconnection.jar");
		} catch (SQLException e) {
			System.out.println("no se encontró la BD " + this.nombreBD);
		}

		return conexionRealizada;
	}

	// Cerrar conexion
	public boolean cerrarConexion() {
		boolean Conexioncerrada = false;

		try {
			if (conexion != null && !conexion.isClosed()) {
				conexion.close();
				Conexioncerrada = true;
			}
		} catch (SQLException e) {
			System.out.println("No hay conexion con la BD");
		}

		return Conexioncerrada;
	}

	public ArrayList<ClienteAcesso> obtenerCliente(String email) {
		ArrayList<ClienteAcesso> clientes = new ArrayList<ClienteAcesso>();
		String query = "SELECT dni, nombre, apellidos, email, AES_DECRYPT(contraseña,'clave_secreta_cine') FROM Cliente "
				+ "WHERE email = '" + email + "'";
		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);

			while (resultado.next()) {
				ClienteAcesso nuevoCliente = new ClienteAcesso(resultado.getString(1), resultado.getString(2),
						resultado.getString(3), resultado.getString(4), resultado.getString(5));
				;
				clientes.add(nuevoCliente);
			}
			consulta.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return clientes;
	}

	public ArrayList<Pelicula> obtenerPelis() {
		ArrayList<Pelicula> pelis = new ArrayList<Pelicula>();

		String query = "SELECT DISTINCT Titulo, duracion, genero FROM Pelicula P "
				+ "JOIN Sesion S ON P.id_Pelicula = S.id_Pelicula "
				+ "WHERE fecha >= CURDATE() AND hora_inicio > current_time "
				+ "GROUP BY titulo, duracion, genero "
				+ "HAVING min(fecha) > CURDATE() OR (min(fecha) = CURDATE() AND min(hora_inicio) > current_time) "
				+ "ORDER BY Titulo";
		
		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);

			while (resultado.next()) {
				Pelicula nuevaPeli = new Pelicula(resultado.getString(1), resultado.getInt(2), resultado.getString(3));
				pelis.add(nuevaPeli);
			}
			consulta.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return pelis;
	}

	public ArrayList<String> obtenerFechasPorPerli(String titulo) {
		ArrayList<String> fechas = new ArrayList<String>();

		String query = "SELECT fecha FROM Sesion S JOIN Pelicula P on S.id_pelicula = P.id_pelicula WHERE P.titulo = '"
				+ titulo + "' AND fecha >= CURDATE() "
				+ "ORDER BY fecha";

		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);

			while (resultado.next()) {
				String fecha = resultado.getString(1);
				fechas.add(fecha);

			}
			consulta.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fechas;
	}

	public ArrayList<Sesion> obtenerSesionesPorPerli(String fecha, Pelicula pelicula) {

		ArrayList<Sesion> sesiones = new ArrayList<Sesion>();
		String query = "SELECT SE.hora_inicio, SE.hora_fin, SE.espectadores, SE.precio_sesion, SA.nombre FROM Sesion SE JOIN Sala SA ON SA.id_sala = SE.id_sala WHERE fecha ='"
				+ fecha + "'AND id_pelicula =(Select id_pelicula FROM Pelicula WHERE Titulo = '" + pelicula.getNombre()
				+ "')";

		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);

			while (resultado.next()) {
				Sala sala = new Sala(resultado.getString(5), 100); // TODO Anadir a bd sillas en salas !!!!!
				Sesion sesion = new Sesion(pelicula, fecha, resultado.getString(1), resultado.getString(2), sala,
						resultado.getInt(3), resultado.getDouble(4));
				sesiones.add(sesion);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sesiones;

	}

	public void insertarUsuario(String dni, String nombre, String apellidos, String email, String contrasena) {

		String sql = "INSERT INTO Cliente (dni, nombre, apellidos, email, contraseña) VALUES(?,?,?,?,AES_ENCRYPT(?, 'clave_secreta_cine'))";

		try {
			PreparedStatement ps = conexion.prepareStatement(sql);

			// Asignamos el parámetro String
			ps.setString(1, dni);
			ps.setString(2, nombre);
			ps.setString(3, apellidos);
			ps.setString(4, email);
			ps.setString(5, contrasena);

			// Ejecutamos el INSERT
			ps.executeUpdate();

			System.out.println("Usuario insertado correctamente");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int insertarCompra(String dni, int numentradas, double preciototal, double descuentoaplicado) {

		String sql = "INSERT INTO Compra (dni_cliente, fecha_hora, num_entradas, precio_total, descuento_aplicado)VALUES(?, NOW(), ?, ?,?)";

		try {
			PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			// Asignamos el parámetro String
			ps.setString(1, dni);
			ps.setInt(2, numentradas);
			ps.setDouble(3, preciototal);
			ps.setDouble(4, descuentoaplicado);
			ps.executeUpdate();
			ResultSet generatedKeys = ps.getGeneratedKeys();
			if (generatedKeys.next()) {
				int idCompraGenerado = generatedKeys.getInt(1);
				System.out.println("Compra insertada correctamente con ID: " + idCompraGenerado);
				return idCompraGenerado;
			}
			// Ejecutamos el INSERT

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/*
	 * public int obtenerEspectadoresSesion(String sesion) { String query =
	 * "SELECT espectadores,  FROM Sesion WHERE id_sesion = '" + sesion + "' "; int
	 * obtenerEspectadores = 0; try { Statement consulta =
	 * conexion.createStatement(); ResultSet resultado =
	 * consulta.executeQuery(query);
	 * 
	 * while (resultado.next()) { obtenerEspectadores =
	 * resultado.getInt("espectadores");
	 * 
	 * } } catch (SQLException e) { e.printStackTrace(); } return
	 * obtenerEspectadores; }
	 */

	public void insertarEntrada(int id_compra, String id_sesion, int numentradas, double preciototal,
			double descuentoaplicado) {
		String url = "jdbc:mysql://localhost:3306/cine_daw"; // cambia según tu BD
		String user = "root";
		String password = "";

		String sql = "INSERT INTO Entrada (id_compra, id_sesion, numero_personas, precio, descuento)VALUES(?,?, ?, ?, ?)";

		try (Connection conn = DriverManager.getConnection(url, user, password);
				PreparedStatement ps = conn.prepareStatement(sql)) {

			// Asignamos el parámetro String
			ps.setInt(1, id_compra);
			ps.setString(2, id_sesion);
			ps.setInt(3, numentradas);
			ps.setDouble(4, preciototal);
			ps.setDouble(5, descuentoaplicado);

			// Ejecutamos el INSERT
			ps.executeUpdate();

			System.out.println("Entrada insertada correctamente");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String obtenerIdSesion(String fecha, String hora, String sala) {
		String sesion = "";
		String query = "SELECT id_sesion  FROM Sesion SE JOIN Sala SA on SA.id_sala = SE.id_sala WHERE hora_inicio = '"
				+ hora + "' AND SA.id_sala = (SELECT id_sala FROM Sala  WHERE nombre = '" + sala + "') "
				+ "AND fecha = '" + fecha + "'";

		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);

			while (resultado.next()) {
				sesion = resultado.getString("id_sesion");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sesion;
	}

	public void insertarEspectadores(String idSesion, int numespectadores) {
		String query = "UPDATE Sesion SET espectadores = espectadores + '" + numespectadores + "' WHERE  id_sesion = '"
				+ idSesion + "'";
		try {
			Statement stmt = conexion.createStatement();
			int filasActualizadas = stmt.executeUpdate(query);
			stmt.close();
		} catch (SQLException e) {
			System.out.println("Error en update: " + e.getMessage());
		}
	}

	public ArrayList<dniMailCliente> dniEmailCliente() {
		ArrayList<dniMailCliente> dniEmailCliente = new ArrayList<dniMailCliente>();
		String query = "SELECT dni, email FROM Cliente";
		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);

			while (resultado.next()) {
				dniMailCliente newdniEmailCliente = new dniMailCliente(resultado.getString(1), resultado.getString(2));
				dniEmailCliente.add(newdniEmailCliente);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dniEmailCliente;
	}
}