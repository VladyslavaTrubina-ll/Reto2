package modelo;

public class Entrada {
	private String pelicula, orario;
	private int numeropersonas;
	private double precio;
	private double descuento;

	public Entrada() {

	}

	public Entrada(String pelicula, String orario, int numeropersonas, double precio, double descuento) {
		this.pelicula = pelicula;
		this.numeropersonas = numeropersonas;
		this.precio = precio;
		this.descuento = descuento;
		this.orario = orario;

	}



	@Override
	public String toString() {
		return "Entrada [pelicula=" + pelicula + ", orario=" + orario + ", numeropersonas=" + numeropersonas
				+ ", precio=" + precio + ", descuento=" + descuento + "]";
	}

	public String getOrario() {
		return orario;
	}

	public void setOrario(String orario) {
		this.orario = orario;
	}

	public void setPelicula(String pelicula) {
		this.pelicula = pelicula;
	}

	public String getPelicula() {
		return pelicula;
	}

	public void setId_sesion(String pelicula) {
		this.pelicula = pelicula;
	}

	public int getNumeropersonas() {
		return numeropersonas;
	}

	public void setNumeropersonas(int numeropersonas) {
		this.numeropersonas = numeropersonas;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public double getDescuento() {
		return descuento;
	}

	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}

	public double calcolarpreciototal(double precio, int numeropersonas) {
		double preciototal = precio * numeropersonas;
		return preciototal;
	}
}
