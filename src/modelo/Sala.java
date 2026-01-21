package modelo;

public class Sala {
	private String nombre;

	public Sala() {

	}

	public Sala(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Sala [nombre=" + nombre + "]";
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
