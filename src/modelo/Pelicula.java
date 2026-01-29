package modelo;

public class Pelicula {
	private String titulo;
	private int duracion;

	public Pelicula(String titulo, int duracion) {
		this.titulo = titulo;
		this.duracion = duracion;
	}

	@Override
	public String toString() {
		return "Pelicula\ntitulo: " + titulo + "\nduracion: " + duracion + "min"; // TODO? reformat
	}

	public String getTitulo() {
		return titulo;
	}

	public int getDuracion() {
		return duracion;
	}
}
