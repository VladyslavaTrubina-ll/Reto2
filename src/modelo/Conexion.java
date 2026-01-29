package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    // Datos de la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/cine";
    private static final String USUARIO = "root";
    private static final String PASSWORD = ""; // No tengo contraseña entonces vacío

    // Este método nos devuelve la conexión abierta
    public static Connection conectar() {
        Connection con = null;
        try {
            // 1. Aquí he cargado  (el archivo .jar que esta en librerias m(Driver )
            Class.forName("com.mysql.jdbc.Driver");
            
            // 2. Intentamos conectar
            con = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println(" Conexión establecida con éxito.");
            
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Error: No encuentro el archivo .jar (Driver).");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Error: No puedo conectarme a XAMPP (revisa si está encendido MySQL).");
            e.printStackTrace();
        }
        return con;
    }
}