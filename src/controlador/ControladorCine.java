package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import modelo.Conexion;
import modelo.Pelicula;
import vista.VistaConsola;

public class ControladorCine {
    
    private VistaConsola vista;

    public ControladorCine(VistaConsola vista) {
        this.vista = vista;
    }

    // --- MENÚ PRINCIPAL DE GESTIÓN (ADMIN) ---
    public void iniciar() {
        int opcion = 0;
        
        while (opcion != 5) {
            vista.mostrarMensaje("\n===============================");
            vista.mostrarMensaje("   🛠️ GESTIÓN DE CINE (ADMIN)   ");
            vista.mostrarMensaje("===============================");
            vista.mostrarMensaje("1. Ver cartelera");
            vista.mostrarMensaje("2. Insertar nueva película");
            vista.mostrarMensaje("3. Actualizar precio");
            vista.mostrarMensaje("4. Borrar película");
            vista.mostrarMensaje("5. Volver / Salir");
            
            opcion = vista.pedirInt("Elige una opción");

            switch (opcion) {
                case 1: 
                    mostrarPeliculas();
                    break;
                case 2: 
                    registrarPelicula();
                    break;
                case 3: 
                    actualizarPrecio();
                    break;
                case 4: 
                    borrarPelicula();
                    break;
                case 5:
                    vista.mostrarMensaje("Saliendo del modo gestión...");
                    break;
                default:
                    vista.mostrarMensaje("⚠️ Opción no válida.");
            }
        }
    }

    // --- 1. VER PELÍCULAS (SELECT) ---
    private void mostrarPeliculas() {
        Connection con = Conexion.conectar();
        
        if (con == null) {
            vista.mostrarMensaje("❌ Error: No hay conexión con la base de datos.");
            return;
        }

        String sql = "SELECT * FROM pelicula";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            vista.mostrarMensaje("\n--- 🎬 CARTELERA ACTUAL ---");
            boolean hayDatos = false;

            while (rs.next()) {
                hayDatos = true;
                String id = rs.getString("id_pelicula");
                String titulo = rs.getString("titulo");
                int duracion = rs.getInt("duracion");
                String genero = rs.getString("genero");
                double precio = rs.getDouble("precio_base");

                // Formato bonito: [ID] Titulo (Duracion) - Genero : Precio
                System.out.println(String.format("[%s] %-20s (%d min) | %s | %.2f€", 
                                    id, titulo, duracion, genero, precio));
            }

            if (!hayDatos) {
                vista.mostrarMensaje("📭 No hay películas registradas.");
            }
            
            con.close();

        } catch (Exception ex) {
            vista.mostrarMensaje("❌ Error al listar: " + ex.getMessage());
        }
    }

    // --- 2. INSERTAR PELÍCULA (INSERT) ---
    private void registrarPelicula() {
        vista.mostrarMensaje("\n--- ➕ NUEVA PELÍCULA ---");
        
        // Pedimos los datos
        String id = vista.pedirDato("ID (ej: P01)");
        String titulo = vista.pedirDato("Título");
        int duracion = vista.pedirInt("Duración (minutos)");
        String genero = vista.pedirDato("Género");
        double precio = vista.pedirDouble("Precio Base");
        
        // Creamos el objeto Pelicula
        Pelicula p = new Pelicula(id, titulo, duracion, genero, precio);

        Connection con = Conexion.conectar();
        String sql = "INSERT INTO pelicula (id_pelicula, titulo, duracion, genero, precio_base) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setString(1, p.getIdPelicula());
            ps.setString(2, p.getTitulo());
            ps.setInt(3, p.getDuracion());
            ps.setString(4, p.getGenero());
            ps.setDouble(5, p.getPrecioBase());

            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas > 0) {
                vista.mostrarMensaje("✅ ¡Éxito! Película guardada: " + p.getTitulo());
            }

            con.close();

        } catch (Exception ex) {
            vista.mostrarMensaje("❌ Error al guardar. ¿Quizás el ID '" + id + "' ya existe?");
        }
    }

    // --- 3. ACTUALIZAR PRECIO (UPDATE) ---
    private void actualizarPrecio() {
        vista.mostrarMensaje("\n--- 💲 CAMBIAR PRECIO ---");
        // Mostramos la lista primero para ayudar al usuario a ver los IDs
        mostrarPeliculas(); 

        String id = vista.pedirDato("Escribe el ID de la película a cambiar");
        double nuevoPrecio = vista.pedirDouble("Nuevo precio");
        
        Connection con = Conexion.conectar();
        String sql = "UPDATE pelicula SET precio_base = ? WHERE id_pelicula = ?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1, nuevoPrecio);
            ps.setString(2, id);
            
            int filas = ps.executeUpdate();
            if (filas > 0) {
                vista.mostrarMensaje("✅ Precio actualizado correctamente.");
            } else {
                vista.mostrarMensaje("⚠️ No encontré ninguna película con el ID: " + id);
            }
            con.close();
        } catch (Exception e) {
            vista.mostrarMensaje("❌ Error al actualizar: " + e.getMessage());
        }
    }

    // --- 4. BORRAR PELÍCULA (DELETE) ---
    private void borrarPelicula() {
        vista.mostrarMensaje("\n--- 🗑️ BORRAR PELÍCULA ---");
        mostrarPeliculas();

        String id = vista.pedirDato("Escribe el ID de la película a eliminar");
        
        // Confirmación de seguridad
        String confirmacion = vista.pedirDato("¿Estás seguro? (s/n)");
        if (!confirmacion.equalsIgnoreCase("s")) {
            vista.mostrarMensaje("Operación cancelada.");
            return;
        }

        Connection con = Conexion.conectar();
        String sql = "DELETE FROM pelicula WHERE id_pelicula = ?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, id);
            
            int filas = ps.executeUpdate();
            if (filas > 0) {
                vista.mostrarMensaje("🗑️ Película eliminada correctamente.");
            } else {
                vista.mostrarMensaje("⚠️ No existe ese ID.");
            }
            con.close();
        } catch (Exception e) {
            // Este error suele saltar por integridad referencial (Foreign Keys)
            vista.mostrarMensaje("❌ Error crítico: No se puede borrar esta película.");
            vista.mostrarMensaje("Posible causa: Ya tiene sesiones programadas o entradas vendidas.");
        }
    } 
}