package modelo;

public class Pelicula {
	private String nombre;
	private int duracion;

	public Pelicula(String titulo, int duracion) {
		this.nombre = titulo;
		this.duracion = duracion;
	}

	@Override
	public String toString() {
		return "Pelicula\nnombre: " + nombre + "\nduracion: " + duracion + "min"; // TODO? reformat
	}

	public String getNombre() {
		return nombre;
	}

	public int getDuracion() {
		return duracion;
	}
}
