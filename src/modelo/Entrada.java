package modelo;

public class Entrada {

    private int idEntrada;
    private int idCompra;      
    private String idSesion;
    private int numeroPersonas;
    private double precioPorPersona;   // precio sesión
    private double descuento;          // descuento por entrada (si lo hubiera)

    // ---------------------------------------------------------
    // CONSTRUCTOR
    // ---------------------------------------------------------
    public Entrada(int idEntrada, int idCompra, String idSesion,
                   int numeroPersonas, double precioPorPersona, double descuento) {

        this.idEntrada = idEntrada;
        this.idCompra = idCompra;
        this.idSesion = idSesion;
        this.numeroPersonas = numeroPersonas;
        this.precioPorPersona = precioPorPersona;
        this.descuento = descuento;
    }

    // ---------------------------------------------------------
    // MÉTODOS ÚTILES
    // ---------------------------------------------------------

    /** Precio total de esta entrada (personas × precio sesión) */
    public double getPrecioTotal() {
        return (precioPorPersona * numeroPersonas) - descuento;
    }

    /** Texto bonito para mostrar en ticket o resumen */
    @Override
    public String toString() {
        return "Entrada para sesión " + idSesion +
               " | Personas: " + numeroPersonas +
               " | Precio/persona: " + precioPorPersona + "€" +
               " | Total: " + getPrecioTotal() + "€";
    }

    // ---------------------------------------------------------
    // GETTERS Y SETTERS
    // ---------------------------------------------------------
    public int getIdEntrada() { return idEntrada; }
    public void setIdEntrada(int idEntrada) { this.idEntrada = idEntrada; }

    public int getIdCompra() { return idCompra; }
    public void setIdCompra(int idCompra) { this.idCompra = idCompra; }

    public String getIdSesion() { return idSesion; }
    public void setIdSesion(String idSesion) { this.idSesion = idSesion; }

    public int getNumeroPersonas() { return numeroPersonas; }
    public void setNumeroPersonas(int numeroPersonas) { this.numeroPersonas = numeroPersonas; }

    public double getPrecioPorPersona() { return precioPorPersona; }
    public void setPrecioPorPersona(double precioPorPersona) { this.precioPorPersona = precioPorPersona; }

    public double getDescuento() { return descuento; }
    public void setDescuento(double descuento) { this.descuento = descuento; }
}
