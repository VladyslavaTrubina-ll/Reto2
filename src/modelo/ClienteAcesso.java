package modelo;

public class ClienteAcesso {
	private String dni, nombre, apellidos, email, contraseña;

	public ClienteAcesso() {

	}

	public ClienteAcesso(String dni, String nombre, String apellidos, String email, String contraseña) {
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.contraseña = contraseña;

	}
	
	@Override
	public String toString() {
		return "ClienteAcesso [dni=" + dni 
				+ ", nombre=" + nombre 
				+ ", apellidos=" + apellidos 
				+ ", email=" + email 
				+ ", contraseña=" + contraseña 
				+ "]";
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

}
