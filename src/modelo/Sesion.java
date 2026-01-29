package modelo;

import java.time.LocalDate;
import java.time.LocalTime;

public class Sesion {

    private String idSesion;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String idSala;
    private String idPelicula;
    private int espectadores;      // Personas que ya han comprado
    private double precioSesion;   // Precio por persona

    // ---------------------------------------------------------
    // CONSTRUCTORES
    // ---------------------------------------------------------
    public Sesion() {}

    public Sesion(String idSesion, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin,
                  String idSala, String idPelicula, int espectadores, double precioSesion) {

        this.idSesion = idSesion;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.idSala = idSala;
        this.idPelicula = idPelicula;
        this.espectadores = espectadores;
        this.precioSesion = precioSesion;
    }

    // ---------------------------------------------------------
    // MÉTODOS ÚTILES
    // ---------------------------------------------------------

    /** Devuelve la fecha y hora en formato bonito */
    public String getFechaHora() {
        return fecha.toString() + " " + horaInicio.toString();
    }

    /** Incrementa espectadores tras una compra */
    public void sumarEspectadores(int cantidad) {
        this.espectadores += cantidad;
    }

    // ---------------------------------------------------------
    // GETTERS Y SETTERS
    // ---------------------------------------------------------
    public String getIdSesion() { return idSesion; }
    public void setIdSesion(String idSesion) { this.idSesion = idSesion; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }

    public String getIdSala() { return idSala; }
    public void setIdSala(String idSala) { this.idSala = idSala; }

    public String getIdPelicula() { return idPelicula; }
    public void setIdPelicula(String idPelicula) { this.idPelicula = idPelicula; }

    public int getEspectadores() { return espectadores; }
    public void setEspectadores(int espectadores) { this.espectadores = espectadores; }

    public double getPrecioSesion() { return precioSesion; }
    public void setPrecioSesion(double precioSesion) { this.precioSesion = precioSesion; }

    // ---------------------------------------------------------
    // TO STRING (para mostrar sesiones en consola)
    // ---------------------------------------------------------
    @Override
    public String toString() {
        return "[" + idSesion + "] " +
               fecha + " " + horaInicio +
               " | Sala: " + idSala +
               " | Película: " + idPelicula +
               " | Precio: " + precioSesion + "€";
    }
}
