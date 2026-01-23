package modelo;

public class OrarioPrecioSalaSesion {
	private String orario, sala, precio;

	public OrarioPrecioSalaSesion() {

	}

	public OrarioPrecioSalaSesion(String orario, String precio, String sala) {
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

	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}

	public OrarioPrecioSalaSesion(String orario, Double precio, String sala) {
		this.orario = orario;
		this.precio = String.valueOf(precio);
		this.sala = sala;
	}
	 public void setPrecio(double precio) {
	        this.precio = String.valueOf(precio);
	    }
}