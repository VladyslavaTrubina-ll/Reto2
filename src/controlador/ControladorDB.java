package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import modelo.Pelicula;
import modelo.ClienteAcesso;
import modelo.EspectadoresSesion;
import modelo.FechaSesion;
import modelo.OrarioPrecioSalaSesion;

public class ControladorDB {
	private Connection conexion;
	private String nombreBD;

	// Constructores
	public ControladorDB(String nombreBD) {
		this.nombreBD = nombreBD;
	}

	public ControladorDB() {
		// TODO Auto-generated constructor stub
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
		String query = "SELECT dni, nombre, apellidos, email, AES_DECRYPT(contraseña,'clave_secreta_cine') FROM Cliente " // TODO
																															// error
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return clientes;
	}

	public ArrayList<Pelicula> obtenerPelis() {
		ArrayList<Pelicula> pelis = new ArrayList<Pelicula>();

		String query = "SELECT DISTINCT Titulo, duracion FROM Pelicula P "
				+ "JOIN Sesion S ON P.id_Pelicula = S.id_Pelicula " + "ORDER BY Titulo";

		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);

			while (resultado.next()) {
				Pelicula nuevaPeli = new Pelicula(resultado.getString(1), resultado.getInt(2));
				pelis.add(nuevaPeli);
			}
			consulta.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return pelis;
	}

	public ArrayList<FechaSesion> obtenerfechasporperli(String titulo) {
		ArrayList<FechaSesion> fechapeli = new ArrayList<FechaSesion>();
		String query = "SELECT  fecha FROM Sesion S JOIN Pelicula P on S.id_pelicula = P.id_pelicula WHERE P.titulo = '"
				+ titulo + "' && fecha >= CURDATE()" + "ORDER BY fecha";
		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);

			while (resultado.next()) {
				FechaSesion nuevafechasesion = new FechaSesion(resultado.getString(1));
				fechapeli.add(nuevafechasesion);

			}
			consulta.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fechapeli;
	}

	public ArrayList<OrarioPrecioSalaSesion> obtenerhorariopreciosala(FechaSesion fecha, String titulo) {

		String fechaString = fecha.getFecha();
		ArrayList<OrarioPrecioSalaSesion> orariopreciosala = new ArrayList<OrarioPrecioSalaSesion>();
		String query = "SELECT  SA.nombre, hora_inicio, precio_sesion FROM Sesion SE JOIN Sala SA ON SA.id_sala = SE.id_sala WHERE fecha ='"
				+ fechaString + "'AND id_pelicula =(Select id_pelicula FROM Pelicula WHERE Titulo = '" + titulo + "')";
		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);

			while (resultado.next()) {
				OrarioPrecioSalaSesion neworariopreciosesion = new OrarioPrecioSalaSesion(resultado.getString(1),
						resultado.getString(2), resultado.getDouble(3));
				orariopreciosala.add(neworariopreciosesion);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orariopreciosala;

	}

	public ArrayList<EspectadoresSesion> obtenerespectadoresporsesion(FechaSesion fecha,
			ArrayList<OrarioPrecioSalaSesion> orarioelegido) {
		String fechaElegida;
		ArrayList<EspectadoresSesion> numespectadores = new ArrayList<EspectadoresSesion>();
		if (orarioelegido.isEmpty()) {
			return numespectadores;
		}

		String hora = orarioelegido.get(0).getOrario();
		String Sala = orarioelegido.get(0).getSala();
		fechaElegida = fecha.getFecha();

		String query = "SELECT espectadores FROM Sesion SE JOIN Sala SA ON SA.id_sala = SE.id_sala WHERE SE.hora_inicio = '"
				+ hora + "' AND SA.nombre = '" + Sala + "' AND SE.fecha = '" + fechaElegida + "'";

		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);

			while (resultado.next()) {
				EspectadoresSesion newnumespectadores = new EspectadoresSesion(resultado.getInt("espectadores"));
				numespectadores.add(newnumespectadores);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return numespectadores;
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

	public void insertarCompra(String dni, int numentradas, double preciototal, double descuentoaplicado) {

		String sql = "INSERT INTO Compra (dni_cliente, fecha_hora, num_entradas, precio_total, descuento_aplicado)VALUES(?, ?, ?, ?,?)";

		try {
			PreparedStatement ps = conexion.prepareStatement(sql);
			java.sql.Timestamp fechaHora = new java.sql.Timestamp(System.currentTimeMillis());
			// Asignamos el parámetro String
			ps.setString(1, dni);
			ps.setTimestamp(2, fechaHora);
			ps.setInt(3, numentradas);
			ps.setDouble(4, preciototal);
			ps.setDouble(5, descuentoaplicado);

			// Ejecutamos el INSERT
			ps.executeUpdate();
			System.out.println("Compra insertada correctamente");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String obtenerSesion(String fecha, String hora, String sala) {
		String sesion = "dioporco";
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

	public void insertarEntrada(int numentradas, double preciototal, double descuentoaplicado) {
		String url = "jdbc:mysql://localhost:3306/cine_daw"; // cambia según tu BD
		String user = "root";
		String password = "";

		String sql = "INSERT INTO Entrada ( fecha_hora, num_entradas, precio_total, descuento_aplicado)VALUES(?, ?, ?, ?,)";

		try (Connection conn = DriverManager.getConnection(url, user, password);
				PreparedStatement ps = conn.prepareStatement(sql)) {
			java.sql.Timestamp fechaHora = new java.sql.Timestamp(System.currentTimeMillis());
			// Asignamos el parámetro String
			ps.setTimestamp(2, fechaHora);
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

}