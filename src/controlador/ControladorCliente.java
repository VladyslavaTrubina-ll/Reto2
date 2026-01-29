package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import modelo.Cliente;
import modelo.Conexion;
import vista.VistaConsola;

public class ControladorCliente {

    private VistaConsola vista;
    private Cliente clienteLogueado = null;

    public ControladorCliente(VistaConsola vista) {
        this.vista = vista;
    }
    
    public Cliente getClienteLogueado() {
        return clienteLogueado;
    }

    // ---------------------------------------------------------
    // REGISTRO
    // ---------------------------------------------------------
    public void registrarNuevoCliente() {
        vista.mostrarMensaje("\n--- REGISTRO DE NUEVO USUARIO ---");
        
        String dni = vista.pedirDato("DNI");

        if (existeCliente(dni)) {
            vista.mostrarMensaje("❌ Este DNI ya está registrado. Intenta hacer Login.");
            return;
        }

        String nombre = vista.pedirDato("Nombre");
        String apellidos = vista.pedirDato("Apellidos");
        String email = vista.pedirDato("Email");
        String passRaw = vista.pedirContrasena("Contraseña");

        Cliente nuevoCliente = new Cliente(dni, nombre, apellidos, email, passRaw);
        guardarEnBaseDeDatos(nuevoCliente);
    }

    // ---------------------------------------------------------
    // LOGIN
    // ---------------------------------------------------------
    public boolean iniciarSesion() {
        vista.mostrarMensaje("\n--- INICIAR SESIÓN ---");

        String dni = vista.pedirDato("DNI");
        String passRaw = vista.pedirContrasena("Contraseña");

        try (Connection con = Conexion.conectar()) {

            String sql = """
                SELECT dni, nombre, apellidos, email
                FROM cliente
                WHERE dni = ?
                AND contrasena = AES_ENCRYPT(?, 'clave_secreta_cine')
            """;

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, dni);
            ps.setString(2, passRaw);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                clienteLogueado = new Cliente(
                    rs.getString("dni"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getString("email"),
                    "*****"
                );

                vista.mostrarMensaje("✅ ¡Bienvenido/a, " + rs.getString("nombre") + "!");
                return true;
            }

            vista.mostrarMensaje("❌ Usuario o contraseña incorrectos.");
            return false;

        } catch (Exception e) {
            vista.mostrarMensaje("❌ Error al iniciar sesión: " + e.getMessage());
            return false;
        }
    }

    // ---------------------------------------------------------
    // AUXILIARES
    // ---------------------------------------------------------
    private void guardarEnBaseDeDatos(Cliente c) {
        try (Connection con = Conexion.conectar()) {

            String sql = """
                INSERT INTO cliente (dni, nombre, apellidos, email, contrasena)
                VALUES (?, ?, ?, ?, AES_ENCRYPT(?, 'clave_secreta_cine'))
            """;

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, c.getDni());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getApellidos());
            ps.setString(4, c.getEmail());
            ps.setString(5, c.getContrasena());

            ps.executeUpdate();
            vista.mostrarMensaje("✅ Registro completado.");

        } catch (Exception e) {
            vista.mostrarMensaje("❌ Error al guardar en BD: " + e.getMessage());
        }
    }

    private boolean existeCliente(String dni) {
        try (Connection con = Conexion.conectar()) {

            PreparedStatement ps = con.prepareStatement(
                "SELECT dni FROM cliente WHERE dni = ?"
            );
            ps.setString(1, dni);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            return false;
        }
    }

    public void cerrarSesion() {
        clienteLogueado = null;
        vista.mostrarMensaje("🔒 Sesión cerrada.");
    }
}
