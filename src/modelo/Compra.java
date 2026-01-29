package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Compra {

    private int idCompra;
    private Cliente cliente;
    private LocalDateTime fecha;
    private double subtotal;
    private double descuento;
    private double precioTotal;

    private List<Entrada> listaEntradas;

    // ---------------------------------------------------------
    // CONSTRUCTORES
    // ---------------------------------------------------------
    public Compra() {
        this.listaEntradas = new ArrayList<>();
        this.fecha = LocalDateTime.now();
    }

    public Compra(Cliente cliente) {
        this();
        this.cliente = cliente;
    }

    // ---------------------------------------------------------
    // AÑADIR ENTRADAS AL CARRITO
    // ---------------------------------------------------------
    public void agregarEntrada(Entrada e) {
        this.listaEntradas.add(e);
    }

    // ---------------------------------------------------------
    // CALCULAR SUBTOTAL, DESCUENTO Y TOTAL
    // ---------------------------------------------------------
    public void calcularTotal() {

        subtotal = 0;

        for (Entrada e : listaEntradas) {
            // Precio total por entrada = precio sesión × nº personas
            subtotal += e.getPrecioPorPersona() * e.getNumeroPersonas();
        }

        int cantidadPeliculas = listaEntradas.size();

        // Descuentos según nº de películas distintas
        if (cantidadPeliculas >= 3) {
            descuento = subtotal * 0.30;
        } else if (cantidadPeliculas == 2) {
            descuento = subtotal * 0.20;
        } else {
            descuento = 0;
        }

        precioTotal = subtotal - descuento;
    }

    // ---------------------------------------------------------
    // GUARDAR COMPRA EN BD
    // ---------------------------------------------------------
    public void guardarEnBD() {
        try {
            Connection con = Conexion.conectar();

            // 1. Insertar compra
            String sqlCompra = """
                INSERT INTO compra (dni_cliente, fecha, precio_total, descuento)
                VALUES (?, ?, ?, ?)
            """;

            PreparedStatement psCompra = con.prepareStatement(sqlCompra, PreparedStatement.RETURN_GENERATED_KEYS);

            psCompra.setString(1, cliente.getDni());
            psCompra.setTimestamp(2, java.sql.Timestamp.valueOf(fecha));
            psCompra.setDouble(3, precioTotal);
            psCompra.setDouble(4, descuento);

            psCompra.executeUpdate();

            // 2. Recuperar ID autogenerado
            ResultSet rs = psCompra.getGeneratedKeys();
            if (rs.next()) {
                this.idCompra = rs.getInt(1);
            }

            // 3. Insertar entradas asociadas
            String sqlEntrada = """
                INSERT INTO entrada (id_compra, id_sesion, numero_personas, precio, descuento)
                VALUES (?, ?, ?, ?, ?)
            """;

            PreparedStatement psEntrada = con.prepareStatement(sqlEntrada);

            for (Entrada e : listaEntradas) {
                psEntrada.setInt(1, this.idCompra);
                psEntrada.setString(2, e.getIdSesion());
                psEntrada.setInt(3, e.getNumeroPersonas());
                psEntrada.setDouble(4, e.getPrecioPorPersona() * e.getNumeroPersonas()); // precio total por entrada
                psEntrada.setDouble(5, e.getDescuento());
                psEntrada.executeUpdate();
            }

            con.close();

        } catch (Exception e) {
            System.out.println("❌ Error al guardar la compra: " + e.getMessage());
        }
    }

    // ---------------------------------------------------------
    // GENERAR TICKET DETALLADO
    // ---------------------------------------------------------
    public void generarTicket() {

        System.out.println("\n==================== TICKET DE COMPRA ====================");
        System.out.println("Cliente: " + cliente.getNombre() + " " + cliente.getApellidos());
        System.out.println("DNI: " + cliente.getDni());
        System.out.println("Fecha: " + fecha);
        System.out.println("----------------------------------------------------------");

        int contador = 1;

        for (Entrada e : listaEntradas) {
            System.out.println(contador + ". Sesión: " + e.getIdSesion());
            System.out.println("   Personas: " + e.getNumeroPersonas());
            System.out.println("   Precio por persona: " + e.getPrecioPorPersona() + "€");
            System.out.println("   Total línea: " + (e.getPrecioPorPersona() * e.getNumeroPersonas()) + "€");
            System.out.println("----------------------------------------------------------");
            contador++;
        }

        System.out.println("Subtotal: " + subtotal + "€");
        System.out.println("Descuento: -" + descuento + "€");
        System.out.println("TOTAL A PAGAR: " + precioTotal + "€");
        System.out.println("==========================================================");
    }
 // ---------------------------------------------------------
 // GUARDAR TICKET EN ARCHIVO .TXT
 // ---------------------------------------------------------
 public void guardarTicketEnArchivo() {

     String nombreArchivo = "ticket_compra_" + idCompra + ".txt";

     try (java.io.PrintWriter writer = new java.io.PrintWriter(nombreArchivo)) {

         writer.println("==================== TICKET DE COMPRA ====================");
         writer.println("Cliente: " + cliente.getNombre() + " " + cliente.getApellidos());
         writer.println("DNI: " + cliente.getDni());
         writer.println("Fecha: " + fecha);
         writer.println("----------------------------------------------------------");

         int contador = 1;

         for (Entrada e : listaEntradas) {
             writer.println(contador + ". Sesión: " + e.getIdSesion());
             writer.println("   Personas: " + e.getNumeroPersonas());
             writer.println("   Precio por persona: " + e.getPrecioPorPersona() + "€");
             writer.println("   Total línea: " + (e.getPrecioPorPersona() * e.getNumeroPersonas()) + "€");
             writer.println("----------------------------------------------------------");
             contador++;
         }

         writer.println("Subtotal: " + subtotal + "€");
         writer.println("Descuento: -" + descuento + "€");
         writer.println("TOTAL A PAGAR: " + precioTotal + "€");
         writer.println("==========================================================");

         System.out.println("📄 Ticket guardado correctamente en: " + nombreArchivo);

     } catch (Exception e) {
         System.out.println("❌ Error al guardar el ticket: " + e.getMessage());
     }
 }

    // ---------------------------------------------------------
    // GETTERS Y SETTERS
    // ---------------------------------------------------------
    public int getIdCompra() { return idCompra; }
    public void setIdCompra(int idCompra) { this.idCompra = idCompra; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public double getSubtotal() { return subtotal; }

    public double getPrecioTotal() { return precioTotal; }
    public void setPrecioTotal(double precioTotal) { this.precioTotal = precioTotal; }

    public double getDescuento() { return descuento; }
    public void setDescuento(double descuento) { this.descuento = descuento; }

    public List<Entrada> getListaEntradas() { return listaEntradas; }
    public void setListaEntradas(List<Entrada> listaEntradas) { this.listaEntradas = listaEntradas; }
}
