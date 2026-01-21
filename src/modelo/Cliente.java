package modelo;

public class Cliente {
	private String dni, nombre, apellido, mail, password;

	public Cliente() {

	}

	public Cliente(String dni, String nombre, String apellido, String mail, String password) {
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.mail = mail;
		this.password = password;
	}

	@Override
	public String toString() {
		return "Cliente [dni=" + dni + ", nombre=" + nombre + ", apellido=" + apellido + ", mail=" + mail
				+ ", password=" + password + "]";
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
