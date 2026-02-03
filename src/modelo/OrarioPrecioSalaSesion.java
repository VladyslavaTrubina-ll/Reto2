package modelo;

public class OrarioPrecioSalaSesion {

	private String horario, sala;
	private double precio;

	public OrarioPrecioSalaSesion() {

	}

	public OrarioPrecioSalaSesion(String horario,String sala ,Double precio) {
		this.horario = horario;
		this.precio = precio;
		this.sala = sala;
	}

	

	@Override
	public String toString() {
		return "Informe sobre sesion elegido\n  " + sala + " hora de inicio: " + horario + ", precio: " + precio + "]";
	}

	public String getOrario() {
		return horario;
	}

	public void setOrario(String horario) {
		this.horario = horario;
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
