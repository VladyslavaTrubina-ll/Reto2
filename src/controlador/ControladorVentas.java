package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import modelo.Cliente;
import modelo.Compra;
import modelo.Conexion;
import modelo.Entrada;
import modelo.Pelicula;
import modelo.Sesion;
import vista.VistaConsola;

public class ControladorVentas {

    private VistaConsola vista;

    public ControladorVentas(VistaConsola vista) {
        this.vista = vista;
    }

    // ---------------------------------------------------------
    // INICIO DEL PROCESO DE COMPRA
    // ---------------------------------------------------------
    public void iniciarVenta(Cliente cliente) {

        vista.mostrarMensaje("\n--- 🎟️ COMPRA DE ENTRADAS ---");

        Compra compraActual = new Compra();
        compraActual.setCliente(cliente);

        boolean seguir = true;

        while (seguir) {

            // 1. Elegir película
            Pelicula pelicula = elegirPelicula();
            if (pelicula == null) {
                vista.mostrarMensaje("❌ No hay películas disponibles.");
                return;
            }

            // 2. Elegir fecha
            String fecha = elegirFecha(pelicula.getIdPelicula());
            if (fecha == null) {
                vista.mostrarMensaje("❌ No hay fechas disponibles.");
                continue;
            }

            // 3. Elegir sesión
            Sesion sesion = elegirSesion(pelicula.getIdPelicula(), fecha);
            if (sesion == null) {
                vista.mostrarMensaje("❌ No hay sesiones disponibles.");
                continue;
            }

            // 4. Pedir número de espectadores
            int cantidad = vista.pedirInt("¿Cuántas entradas deseas?");
            if (cantidad <= 0) {
                vista.mostrarMensaje("⚠️ Cantidad no válida.");
                continue;
            }

            // 5. CONTROL DE AFORO
            int capacidad = obtenerCapacidadSala(sesion.getIdSala());
            int ocupacionActual = sesion.getEspectadores();

            if (ocupacionActual + cantidad > capacidad) {
                vista.mostrarMensaje("❌ No hay suficientes asientos disponibles.");
                vista.mostrarMensaje("Capacidad: " + capacidad + " | Ocupados: " + ocupacionActual);
                continue;
            }

            // 6. Crear entrada
            Entrada entrada = new Entrada(
                0,
                0,
                sesion.getIdSesion(),
                cantidad,
                sesion.getPrecioSesion(),
                0
            );

            compraActual.agregarEntrada(entrada);
            vista.mostrarMensaje("✅ Entrada añadida al carrito.");

            // 7. Preguntar si quiere seguir
            String resp = vista.pedirDato("¿Quieres añadir otra película? (s/n)");
            if (!resp.equalsIgnoreCase("s")) {
                seguir = false;
            }
        }

        finalizarCompra(compraActual);
    }

    // ---------------------------------------------------------
    // OBTENER CAPACIDAD DE LA SALA
    // ---------------------------------------------------------
    private int obtenerCapacidadSala(String idSala) {
        int capacidad = 0;

        Connection con = Conexion.conectar();
        String sql = "SELECT capacidad FROM sala WHERE id_sala = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, idSala);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                capacidad = rs.getInt("capacidad");
            }

            con.close();

        } catch (Exception e) {
            vista.mostrarMensaje("❌ Error al obtener capacidad de la sala: " + e.getMessage());
        }

        return capacidad;
    }

    // ---------------------------------------------------------
    // MOSTRAR PELÍCULAS
    // ---------------------------------------------------------
    private Pelicula elegirPelicula() {

        List<Pelicula> lista = new ArrayList<>();

        Connection con = Conexion.conectar();
        String sql = """
            SELECT DISTINCT p.id_pelicula, p.titulo, p.duracion, p.genero, p.precio_base
            FROM pelicula p
            JOIN sesion s ON p.id_pelicula = s.id_pelicula
            ORDER BY s.fecha, s.hora_inicio
        """;

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            vista.mostrarMensaje("\n--- 🎬 PELÍCULAS DISPONIBLES ---");

            int i = 1;
            while (rs.next()) {
                Pelicula p = new Pelicula(
                    rs.getString("id_pelicula"),
                    rs.getString("titulo"),
                    rs.getInt("duracion"),
                    rs.getString("genero"),
                    rs.getDouble("precio_base")
                );
                lista.add(p);
                System.out.println(i + ". " + p.getTitulo());
                i++;
            }

            con.close();

        } catch (Exception e) {
            vista.mostrarMensaje("❌ Error al obtener películas: " + e.getMessage());
        }

        if (lista.isEmpty()) return null;

        int opcion = vista.pedirInt("Elige una película");
        return lista.get(opcion - 1);
    }

    // ---------------------------------------------------------
    // MOSTRAR FECHAS
    // ---------------------------------------------------------
    private String elegirFecha(String idPelicula) {

        List<String> fechas = new ArrayList<>();

        Connection con = Conexion.conectar();
        String sql = """
            SELECT DISTINCT fecha
            FROM sesion
            WHERE id_pelicula = ?
            ORDER BY fecha
        """;

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, idPelicula);
            ResultSet rs = ps.executeQuery();

            vista.mostrarMensaje("\n--- 📅 FECHAS DISPONIBLES ---");

            int i = 1;
            while (rs.next()) {
                String fecha = rs.getDate("fecha").toString();
                fechas.add(fecha);
                System.out.println(i + ". " + fecha);
                i++;
            }

            con.close();

        } catch (Exception e) {
            vista.mostrarMensaje("❌ Error al obtener fechas: " + e.getMessage());
        }

        if (fechas.isEmpty()) return null;

        int opcion = vista.pedirInt("Elige una fecha");
        return fechas.get(opcion - 1);
    }

    // ---------------------------------------------------------
    // MOSTRAR SESIONES
    // ---------------------------------------------------------
    private Sesion elegirSesion(String idPelicula, String fecha) {

        List<Sesion> sesiones = new ArrayList<>();

        Connection con = Conexion.conectar();
        String sql = """
            SELECT *
            FROM sesion
            WHERE id_pelicula = ? AND fecha = ?
            ORDER BY hora_inicio
        """;

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, idPelicula);
            ps.setString(2, fecha);
            ResultSet rs = ps.executeQuery();

            vista.mostrarMensaje("\n--- 🕒 SESIONES DISPONIBLES ---");

            int i = 1;
            while (rs.next()) {

                Sesion s = new Sesion();
                s.setIdSesion(rs.getString("id_sesion"));
                s.setFecha(rs.getDate("fecha").toLocalDate());
                s.setHoraInicio(rs.getTime("hora_inicio").toLocalTime());
                s.setHoraFin(rs.getTime("hora_fin").toLocalTime());
                s.setIdSala(rs.getString("id_sala"));
                s.setIdPelicula(rs.getString("id_pelicula"));
                s.setEspectadores(rs.getInt("espectadores"));
                s.setPrecioSesion(rs.getDouble("precio_sesion"));

                sesiones.add(s);

                System.out.println(i + ". " + s.getHoraInicio() + " - Sala " + s.getIdSala() + " - " + s.getPrecioSesion() + "€");
                i++;
            }

            con.close();

        } catch (Exception e) {
            vista.mostrarMensaje("❌ Error al obtener sesiones: " + e.getMessage());
        }

        if (sesiones.isEmpty()) return null;

        int opcion = vista.pedirInt("Elige una sesión");
        return sesiones.get(opcion - 1);
    }

    // ---------------------------------------------------------
    // MOSTRAR RESUMEN
    // ---------------------------------------------------------
    private void mostrarResumen(Compra compra) {

        vista.mostrarMensaje("\n--- 🧾 RESUMEN DE COMPRA ---");

        int contador = 1;

        for (Entrada e : compra.getListaEntradas()) {
            vista.mostrarMensaje(
                contador + ". Sesión: " + e.getIdSesion() +
                " | Personas: " + e.getNumeroPersonas() +
                " | Precio: " + e.getPrecioPorPersona() + "€"
            );
            contador++;
        }

        vista.mostrarMensaje("Subtotal: " + compra.getPrecioTotal() + "€");
        vista.mostrarMensaje("Descuento aplicado: " + compra.getDescuento() + "€");
        vista.mostrarMensaje("TOTAL FINAL: " + compra.getPrecioTotal() + "€");
    }

    // ---------------------------------------------------------
    // FINALIZAR COMPRA
    // ---------------------------------------------------------
    private void finalizarCompra(Compra compra) {

        vista.mostrarMensaje("\n--- 💳 FINALIZAR COMPRA ---");

        compra.calcularTotal();

        // Mostrar resumen antes de confirmar
        mostrarResumen(compra);

        String confirmar = vista.pedirDato("¿Confirmas la compra? (s/n)");

        if (!confirmar.equalsIgnoreCase("s")) {
            vista.mostrarMensaje("❌ Compra cancelada.");
            return;
        }

        compra.guardarEnBD();
        vista.mostrarMensaje("🎉 ¡Compra realizada con éxito!");

        compra.generarTicket();
        
        //Esta parte es para que guarde el tiket en txt
        String guardar = vista.pedirDato("¿Deseas guardar el ticket en un archivo? (s/n)");

        if (guardar.equalsIgnoreCase("s")) {
            compra.guardarTicketEnArchivo();
        }

    }
}
