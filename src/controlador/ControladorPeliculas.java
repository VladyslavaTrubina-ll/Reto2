package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import modelo.Conexion;
import modelo.Pelicula;
import vista.VistaConsola;

public class ControladorPeliculas {
    
    private VistaConsola vista;

    public ControladorPeliculas(VistaConsola vista) {
        this.vista = vista;
    }

    // --- MENÚ DE GESTIÓN (ADMINISTRADOR) ---
    public void menuPeliculas() {
        int opcion = 0;
        while (opcion != 5) {
            vista.mostrarMensaje("\n--- 🛠️ MODO ADMINISTRADOR: GESTIÓN DE PELÍCULAS ---");
            vista.mostrarMensaje("1. Ver todas las películas");
            vista.mostrarMensaje("2. Registrar nueva película");
            vista.mostrarMensaje("3. Cambiar precio de una película");
            vista.mostrarMensaje("4. Borrar película");
            vista.mostrarMensaje("5. Volver al Menú Principal");
            
            opcion = vista.pedirInt("Elige una opción");

            switch (opcion) {
                case 1: 
                    mostrarPeliculas(); 
                    break;
                case 2: 
                    registrarNuevaPelicula();
                    break;
                case 3: 
                    modificarPrecio();
                    break;
                case 4: 
                    eliminarPelicula();
                    break;
                case 5: 
                    vista.mostrarMensaje("🔙 Volviendo al menú principal..."); 
                    break;
                default: 
                    vista.mostrarMensaje("⚠️ Opción incorrecta.");
            }
        }
    }

    // --- MÉTODOS PRIVADOS (CRUD) ---

    private void mostrarPeliculas() {
        Connection con = Conexion.conectar();
        String sql = "SELECT * FROM pelicula";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            vista.mostrarMensaje("\n--- 🎬 LISTADO DE PELÍCULAS ---");
            boolean hayPeliculas = false;
            
            while (rs.next()) {
                hayPeliculas = true;
                String id = rs.getString("id_pelicula");
                String titulo = rs.getString("titulo");
                int duracion = rs.getInt("duracion");
                double precio = rs.getDouble("precio_base");
                
                // Aquí se cortaba tu código, lo he completado:
                System.out.println("[" + id + "] " + titulo + " | " + duracion + " min | " + precio + "€");
            }

            if (!hayPeliculas) {
                vista.mostrarMensaje("No hay películas registradas.");
            }
            
            con.close();

        } catch (Exception e) {
            vista.mostrarMensaje("❌ Error al mostrar películas: " + e.getMessage());
        }
    }

    private void registrarNuevaPelicula() {
        vista.mostrarMensaje("\n--- ➕ NUEVA PELÍCULA ---");
        
        // Pedimos datos al admin
        String id = vista.pedirDato("ID (ej: P01)");
        String titulo = vista.pedirDato("Título");
        int duracion = vista.pedirInt("Duración (min)");
        String genero = vista.pedirDato("Género");
        double precio = vista.pedirDouble("Precio Base");

        // Creamos el objeto (opcional, pero buena práctica)
        Pelicula p = new Pelicula(id, titulo, duracion, genero, precio);

        Connection con = Conexion.conectar();
        try {
            String sql = "INSERT INTO pelicula (id_pelicula, titulo, duracion, genero, precio_base) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setString(1, p.getIdPelicula());
            ps.setString(2, p.getTitulo());
            ps.setInt(3, p.getDuracion());
            ps.setString(4, p.getGenero());
            ps.setDouble(5, p.getPrecioBase());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                vista.mostrarMensaje("✅ Película guardada correctamente.");
            }
            con.close();
        } catch (Exception e) {
            vista.mostrarMensaje("❌ Error al guardar (¿ID duplicado?): " + e.getMessage());
        }
    }

    private void modificarPrecio() {
        vista.mostrarMensaje("\n--- 💲 ACTUALIZAR PRECIO ---");
        // Reutilizamos mostrarPeliculas para que el admin vea los IDs
        mostrarPeliculas(); 
        
        String id = vista.pedirDato("Escribe el ID de la película a modificar");
        double nuevoPrecio = vista.pedirDouble("Nuevo precio");

        Connection con = Conexion.conectar();
        try {
            String sql = "UPDATE pelicula SET precio_base = ? WHERE id_pelicula = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1, nuevoPrecio);
            ps.setString(2, id);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                vista.mostrarMensaje("✅ Precio actualizado.");
            } else {
                vista.mostrarMensaje("⚠️ No se encontró ese ID.");
            }
            con.close();
        } catch (Exception e) {
            vista.mostrarMensaje("❌ Error al actualizar: " + e.getMessage());
        }
    }

    private void eliminarPelicula() {
        vista.mostrarMensaje("\n--- 🗑️ ELIMINAR PELÍCULA ---");
        mostrarPeliculas();
        
        String id = vista.pedirDato("Escribe el ID de la película a eliminar");

        Connection con = Conexion.conectar();
        try {
            String sql = "DELETE FROM pelicula WHERE id_pelicula = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, id);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                vista.mostrarMensaje("✅ Película eliminada.");
            } else {
                vista.mostrarMensaje("⚠️ No se encontró ese ID.");
            }
            con.close();
        } catch (Exception e) {
            // Este error suele saltar si intentas borrar una peli que ya tiene sesiones o entradas vendidas (Integridad referencial)
            vista.mostrarMensaje("❌ No se puede borrar la película. Es posible que tenga sesiones o entradas vendidas asociadas.");
        }
    }
}