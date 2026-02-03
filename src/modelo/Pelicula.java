package modelo;

public class Pelicula {
	private String nombre, genero;
	private int duracion;

	public Pelicula(String titulo, int duracion, String genero) {
		this.nombre = titulo;
		this.duracion = duracion;
		this.genero = genero;
	}

	@Override
	public String toString() {
		return "Pelicula [nombre=" + nombre + ", duracion=" + duracion + ", genero=" + genero + "]";
	}

	public String getNombre() {
		return nombre;
	}
	
	public int getDuracion() {
		return duracion;
	}

	public String getGenero() {
		return genero;
	}

}
