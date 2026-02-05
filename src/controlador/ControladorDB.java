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

	/*
	 * @param Metodo para obtener los datos del cliente registrado en la base de datos con el String email selecionado
	 * 
	 * @return retorna los datos dni,nombre,apellido,email,contraseña y los guarda en un array
	 */
	public ArrayList<ClienteAcesso> obtenerCliente(String email) {
		ArrayList<ClienteAcesso> clientes = new ArrayList<ClienteAcesso>();
		String query = "SELECT dni, nombre, apellidos, email, AES_DECRYPT(contraseña,'clave_secreta_cine') FROM Cliente "
				+ "WHERE email = '" + email + "'";
		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);

			while (resultado.next()) {
				ClienteAcesso nuevoCliente = new ClienteAcesso(resultado.getString(1), resultado.getString(2),
						resultado.getString(3), resultado.getString(4), resultado.getString(5)); ;
				clientes.add(nuevoCliente);
			}
			consulta.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return clientes;
	}

	/*
	 * @param Metodo para obtener las peli disponilbes en la fecha y hora mas cercana a la actual
	 * 
	 * @return devuelve un array con objeto peli (nombre,genero,duracion)
	 */
	public ArrayList<Pelicula> obtenerPelis() {
		ArrayList<Pelicula> pelis = new ArrayList<Pelicula>();

		String query = "SELECT DISTINCT Titulo, duracion, genero FROM Pelicula P "
				+ "JOIN Sesion S ON P.id_Pelicula = S.id_Pelicula " 
				+ "WHERE fecha >= CURDATE() AND hora_inicio > current_time "
				+ "GROUP BY titulo, duracion, genero " 
				+ "HAVING min(fecha) > CURDATE() OR (min(fecha) = CURDATE() AND min(hora_inicio) > current_time) " +
				 "order by min(fecha), min(hora_inicio)";
		
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

	/*
	 * @param Metodo que recoge el String con el titulo elegido y lo usa para que la query nos retorne las fechas donde se puede ver la pelicula
	 * 
	 * @return devuelve un array con objeto fechas, las varias fecahs disponibles.
	 */
	
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

	
	/*
	 * @param Metodo que recge String de fceha eleida anteriometne y String de pelicula elegida anteriormente para usarla en la query y obtener las sesiones
	 * disponibles en esta fecha para esta pelicula
	 * 
	 * @return devuelve un array con objeto sesion que tiene datos de la sala,hora inicio, precio de la sesion 
	 *
	 */
	
	public ArrayList<Sesion> obtenerSesionesPorPerli(String fecha, Pelicula pelicula) {

		ArrayList<Sesion> sesiones = new ArrayList<Sesion>();
		String query = "SELECT SE.hora_inicio, SE.hora_fin, SE.espectadores, SE.precio_sesion, SA.nombre,SA.capacidad FROM Sesion SE JOIN Sala SA ON SA.id_sala = SE.id_sala WHERE fecha ='"
				+ fecha + "'AND id_pelicula =(Select id_pelicula FROM Pelicula WHERE Titulo = '" + pelicula.getNombre()
				+ "')";

		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);

			while (resultado.next()) {
				Sala sala = new Sala(resultado.getString(5), resultado.getInt(6));
				Sesion sesion = new Sesion(pelicula, fecha, resultado.getString(1), resultado.getString(2), sala,
						resultado.getInt(3), resultado.getDouble(4));
				sesiones.add(sesion);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sesiones;
	}

	/*
	 * @param Metodo para insertar nuevo cliente al registarse, tiene que recoger los valores que se ponen al registrase y usarlos para generar el insert
	 * 
	 * @return devuelve -1 solo en caso de error 
	 *
	 */
	public int insertarUsuario(String dni, String nombre, String apellidos, String email, String contrasena) {

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
			int nRows = ps.executeUpdate();

			
			return nRows;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/*
	 * @param Metodo para insertar nuevo compra, como para cliente tiene que recoger los datos generados de la compra confirmada
	 * y usarlos para generar el isnert en la DB
	 * @return devuelve -1 solo en caso de error 
	 *
	 */
	public int insertarCompra(String dni, int numentradas, double preciototal, double descuentoaplicado) {

		String sql = "INSERT INTO Compra (dni_cliente, fecha_hora, num_entradas, precio_total, descuento_aplicado)VALUES(?, NOW(), ?, ?,?)";

		try {
			PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, dni);
			ps.setInt(2, numentradas);
			ps.setDouble(3, preciototal);
			ps.setDouble(4, descuentoaplicado);
			ps.executeUpdate();
			ResultSet generatedKeys = ps.getGeneratedKeys();
			
			if (generatedKeys.next()) {
				int idCompraGenerado = generatedKeys.getInt(1);
				
				return idCompraGenerado;
			}
		

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/*
	 * @param Metodo para insertar nueva entrada, como para compra tiene que recoger los datos generados de la compra confirmada
	 * y usarlos para generar el isnert en la DB
	 * 
	 * @return devuelve -1 solo en caso de error 
	 *
	 */

	public int insertarEntrada(int id_compra, String id_sesion, int numentradas, double preciototal,
			double descuentoaplicado) {

		String sql = "INSERT INTO Entrada (id_compra, id_sesion, numero_personas, precio, descuento)VALUES(?,?, ?, ?, ?)";

		try {
			PreparedStatement ps = conexion.prepareStatement(sql);
			
			ps.setInt(1, id_compra);
			ps.setString(2, id_sesion);
			ps.setInt(3, numentradas);
			ps.setDouble(4, preciototal);
			ps.setDouble(5, descuentoaplicado);

			
			int nRows = ps.executeUpdate();
			
			
			return nRows;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/*
	 * @param Metodo para obtener el idsesion, utiliza fecha, hora y sala porque son claves alternativas de sesion
	 *  se necesita idsesion para actualizar los sitios disponibles despues de cada compra confirmada
	 * 
	 * @return devuelve un String con el idSesion
	 *
	 */
	
	public String obtenerIdSesion(String fecha, String hora, String sala) {
		String sesionId = "";
		String query = "SELECT id_sesion  FROM Sesion SE JOIN Sala SA on SA.id_sala = SE.id_sala WHERE hora_inicio = '"
				+ hora + "' AND SA.id_sala = (SELECT id_sala FROM Sala  WHERE nombre = '" + sala + "') "
				+ "AND fecha = '" + fecha + "'";

		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);

			while (resultado.next()) {
				sesionId = resultado.getString("id_sesion");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sesionId;
	}

	/*
	 * @param Metodo para modificar espectadores en la sesion elegida, necesito recoger el idSesion y los sitios selecionados
	 * por el cliente en la respectiva sesion
	 *  
	 * 
	 * @return -1 en caso de error
	 *
	 */
	
	public int insertarEspectadores(String idSesion, int numespectadores) {
		String query = "UPDATE Sesion SET espectadores = espectadores + '" + numespectadores + "' WHERE  id_sesion = '"
				+ idSesion + "'";
		try {
			Statement stmt = conexion.createStatement();
			int filasActualizadas = stmt.executeUpdate(query);
			stmt.close();
			return filasActualizadas;
		} catch (SQLException e) {
			System.out.println("Error en update: " + e.getMessage());
		}
		return -1;
	}

	/*@param metodo para obtener las mail y los dni de todos los clientes registrado, necesario para comparacioin en fase de registro
	 * 
	 * @return array con objeto dniEmailCliente( 2 String)
	 */
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