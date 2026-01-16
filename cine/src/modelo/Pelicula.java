package modelo;

public class Pelicula {
	private String titulo;
	private int duracion;

	public Pelicula() {

	}

	public Pelicula(String titulo, int duracion) {
		this.titulo = titulo;
		this.duracion = duracion;
	}

	@Override
	public String toString() {
		return "Pelicula [titulo=" + titulo + ", duracion=" + duracion + "]";
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
}
