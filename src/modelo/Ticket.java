package modelo;

public class Ticket {
	private String dni, nombrecli, apellido, nombrepelicuals, nombresala, fechadecompra;
	private double preciocondescuentoaplicado;

	private Ticket() {

	}

	private Ticket(String dni, String nombrecli, String apellido, String nombrepeliculas, String nombresala,
			String fechadecompra, double preciocondescuentoaplicado) {
		this.dni = dni;
		this.nombrecli = nombrecli;
		this.apellido = apellido;
		this.nombrepelicuals = nombrepeliculas;
		this.nombresala = nombresala;
		this.fechadecompra = fechadecompra;
		this.preciocondescuentoaplicado = preciocondescuentoaplicado;
	}

	@Override
	public String toString() {
		return "Ticket [dni=" + dni + ", nombrecli=" + nombrecli + ", apellido=" + apellido + ", nombrepelicuals="
				+ nombrepelicuals + ", nombresala=" + nombresala + ", fechadecompra=" + fechadecompra
				+ ", preciocondescuentoaplicado=" + preciocondescuentoaplicado + "]";
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombrecli() {
		return nombrecli;
	}

	public void setNombrecli(String nombrecli) {
		this.nombrecli = nombrecli;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNombrepelicuals() {
		return nombrepelicuals;
	}

	public void setNombrepelicuals(String nombrepelicuals) {
		this.nombrepelicuals = nombrepelicuals;
	}

	public String getNombresala() {
		return nombresala;
	}

	public void setNombresala(String nombresala) {
		this.nombresala = nombresala;
	}

	public String getFechadecompra() {
		return fechadecompra;
	}

	public void setFechadecompra(String fechadecompra) {
		this.fechadecompra = fechadecompra;
	}

	public double getPreciocondescuentoaplicado() {
		return preciocondescuentoaplicado;
	}

	public void setPreciocondescuentoaplicado(double preciocondescuentoaplicado) {
		this.preciocondescuentoaplicado = preciocondescuentoaplicado;
	}
}
