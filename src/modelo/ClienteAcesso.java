package modelo;

public class ClienteAcesso {
	private String dni, email, contraseña;

	public ClienteAcesso() {

	}

	public ClienteAcesso(String dni,String email, String contraseña) {
		this.dni = dni;
		this.email = email;
		this.contraseña = contraseña;

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
