package modelo;

public class Perro {

	int idCollar;
	String nombre, raza;
	boolean vacunado;

	public Perro() {

	}

	public Perro(int idCollar, String nombre, String raza, boolean vacunado) {

		this.idCollar = idCollar;
		this.nombre = nombre;
		this.raza = raza;
		this.vacunado = vacunado;
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

	public boolean isVacunado() {
		return vacunado;
	}

	public void setVacunado(boolean vacunado) {
		this.vacunado = vacunado;
	}

}
