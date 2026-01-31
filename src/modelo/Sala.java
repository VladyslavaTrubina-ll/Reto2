package modelo;

public class Sala {
	private String nombre;
	private int sitios;

	public Sala(String nombre, int sitios) {
		this.nombre = nombre;
		this.sitios = sitios;
	}

	@Override
	public String toString() {
		return "Sala [nombre=" + nombre + ", sitios=" + sitios + "]";
	}

	public String getNombre() {
		return nombre;
	}

//	public void setNombre(String nombre) {
//		this.nombre = nombre;
//	}

	public int getSitios() {
		return sitios;
	}

//	public void setSitios(int sitios) {
//		this.sitios = sitios;
//	}

	
}
