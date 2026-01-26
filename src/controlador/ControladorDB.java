package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
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

	public ArrayList<ClienteAcesso> obtenercliente(String email, String contraseña) {
		ArrayList<ClienteAcesso> cliente = new ArrayList<ClienteAcesso>();
		String query = "SELECT email,AES_DECRYPT(contraseña,'clave_secreta_cine') FROM Cliente";
		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);

			while (resultado.next()) {
				ClienteAcesso nuevoCliente = new ClienteAcesso(resultado.getString(1), resultado.getString(2));
				cliente.add(nuevoCliente);
			}
			consulta.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return cliente;
	}

	public ArrayList<Pelicula> obtenerpelis() {
		ArrayList<Pelicula> pelis = new ArrayList<Pelicula>();
		String query = "Select  Titulo, duracion,min(fecha), min(hora_inicio) FROM Pelicula P JOIN Sesion S ON P.id_Pelicula = S.id_Pelicula          \r\n"
				+ " WHERE fecha >= CURDATE() && hora_inicio >= now()\r\n"
				+ " group by titulo,duracion\r\n"
				+ "having  min(fecha) > CURDATE() or (min(fecha) = CURDATE() and min(hora_inicio) > current_time)\r\n"
				+ "order by min(fecha), min(hora_inicio)";
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
				+ titulo + "' && fecha >= CURDATE()"
						+ "ORDER BY fecha";
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

	public ArrayList<OrarioPrecioSalaSesion> obtenerhorariopreciosala(ArrayList<FechaSesion> fechas) {
		if (fechas.isEmpty()) {
			return new ArrayList<>();
		}
		String fechaString = fechas.get(0).getFecha();
		ArrayList<OrarioPrecioSalaSesion> orariopreciosala = new ArrayList<OrarioPrecioSalaSesion>();
		String query = "SELECT hora_inicio, precio_sesion, SA.nombre FROM Sesion SE JOIN Sala SA ON SA.id_sala = SE.id_sala WHERE fecha ='"
				+ fechaString + "'";
		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);

			while (resultado.next()) {
				OrarioPrecioSalaSesion neworariopreciosesion = new OrarioPrecioSalaSesion(resultado.getString(1),
						resultado.getDouble(2), resultado.getString(3));
				orariopreciosala.add(neworariopreciosesion);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orariopreciosala;

	}

	public ArrayList<EspectadoresSesion> obtenerespectadoresporsesion(ArrayList<FechaSesion> fecha, ArrayList<OrarioPrecioSalaSesion> orarioelegido) {
		String fechaElegida;
		ArrayList<EspectadoresSesion> numespectadores = new ArrayList<EspectadoresSesion>();
		if (fecha.isEmpty() || orarioelegido.isEmpty()) {
			return numespectadores;
		}

		

	String	hora = orarioelegido.get(0).getOrario();
	String	Sala = orarioelegido.get(0).getSala();
    fechaElegida = fecha.get(0).getFecha();
		
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
}