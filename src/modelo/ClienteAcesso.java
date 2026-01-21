package modelo;

public class ClienteAcesso {
	private String email, contraseña;

	public ClienteAcesso() {

	}

	public ClienteAcesso(String email, String contraseña) {
		this.email = email;
		this.contraseña = contraseña;

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
