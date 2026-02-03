package modelo;

public class Pelicula {
	private String nombre, genero;
	private int duracion;
	private double precioBase;

	public Pelicula(String titulo, int duracion, String genero, double precioBase) {
		this.nombre = titulo;
		this.duracion = duracion;
		this.genero = genero;
		this.precioBase = precioBase;
		
	}

	@Override
	public String toString() {
		return "Pelicula [nombre=" + nombre + ", duracion=" + duracion + ", genero=" + genero + ", precioBase=" + precioBase + "]";
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
	
	public double getPrecioBase() {
		return precioBase;
	}

}
