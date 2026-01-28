package modelo;

public class Entrada {
	private String pelicula, orario,fecha,sala;
	private int numeropersonas;
	private double precio;
	private double descuento,subtotal;

	public Entrada() {
		double precio = 0.0;
		double descuento = 0.0;

	}

	public Entrada(String pelicula,String fecha,String sala, String orario, int numeropersonas, double precio, double descuento,double subtotal) {
		this.pelicula = pelicula;
		this.numeropersonas = numeropersonas;
		this.precio = precio;
		this.descuento = descuento;
		this.orario = orario;
		this.fecha = fecha;
		this.subtotal = subtotal;
		this.sala = sala;

	}

	public String getSala() {
		return sala;
	}

	public void setSala(String sala) {
		this.sala = sala;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	
	@Override
	public String toString() {
		return "Entrada [pelicula=" + pelicula + ", orario=" + orario + ", fecha=" + fecha + ", numeropersonas="
				+ numeropersonas + ", precio=" + precio + ", descuento=" + descuento + "]";
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
