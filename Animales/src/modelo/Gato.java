package modelo;

public class Gato {
	int idCollar;
	String nombre, raza, color;

	public Gato() {

	}

	public Gato(int idCollar, String nombre, String raza, String color) {

		this.idCollar = idCollar;
		this.nombre = nombre;
		this.raza = raza;
		this.color = color;
	}

	public int getIdCollar() {
		return idCollar;
	}

	public void setIdCollar(int idCollar) {
		this.idCollar = idCollar;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRaza() {
		return raza;
	}

	public void setRaza(String raza) {
		this.raza = raza;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
