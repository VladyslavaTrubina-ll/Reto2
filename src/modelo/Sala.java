package modelo;

public class Sala {
	private String nombre;
	private int Sitios;

	public Sala() {

	}

	public Sala(String nombre, int Sitios) {
		this.nombre = nombre;
		this.Sitios = Sitios;
	}

	@Override
	public String toString() {
		return "Sala [nombre=" + nombre + ", Sitios=" + Sitios + "]";
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getSitios() {
		return Sitios;
	}

	public void setSitios(int sitios) {
		Sitios = sitios;
	}

	
}
