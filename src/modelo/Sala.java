package modelo;

public class Sala {

    private String idSala;
    private String nombre;
    private int capacidad;

    public Sala(String idSala, String nombre, int capacidad) {
        this.idSala = idSala;
        this.nombre = nombre;
        this.capacidad = capacidad;
    }

    public String getIdSala() { return idSala; }
    public void setIdSala(String idSala) { this.idSala = idSala; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }

    /** Comprueba si hay aforo disponible */
    public boolean hayAforoDisponible(int ocupacionActual, int cantidadSolicitada) {
        return ocupacionActual + cantidadSolicitada <= capacidad;
    }

    @Override
    public String toString() {
        return nombre + " (capacidad: " + capacidad + ")";
    }
}
