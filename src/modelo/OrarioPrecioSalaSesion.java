package modelo;

public class OrarioPrecioSalaSesion {
	private String orario, sala;
	private double precio;

	public OrarioPrecioSalaSesion() {

	}

	public OrarioPrecioSalaSesion(String orario, Double precio, String sala) {
		this.orario = orario;
		this.precio = precio;
		this.sala = sala;
	}

	@Override
	public String toString() {
		return "OrarioPrecioSalaSesion [orario=" + orario + ", sala=" + sala + ", precio=" + precio + "]";
	}

	public String getOrario() {
		return orario;
	}

	public void setOrario(String orario) {
		this.orario = orario;
	}

	public String getSala() {
		return sala;
	}

	public void setSala(String sala) {
		this.sala = sala;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
}