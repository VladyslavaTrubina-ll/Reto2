package modelo;

public class Entrada {
	private String id_sesion;
	private int numeropersonas;
	private double precio;
	private double descuento;

	public Entrada() {

	}

	public Entrada(String id_sesion, int numeropersonas, double precio, double descuento) {
		this.id_sesion = id_sesion;
		this.numeropersonas = numeropersonas;
		this.precio = precio;
		this.descuento = descuento;

	}

	@Override
	public String toString() {
		return "Entrada [id_sesion=" + id_sesion + ", numeropersonas=" + numeropersonas + ", precio=" + precio
				+ ", descuento=" + descuento + "]";
	}

	public String getId_sesion() {
		return id_sesion;
	}

	public void setId_sesion(String id_sesion) {
		this.id_sesion = id_sesion;
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
}
