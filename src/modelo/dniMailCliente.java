package modelo;

public class dniMailCliente {
	private String dni, email;

	public dniMailCliente(String dni, String email) {
		this.dni = dni;
		this.email = email;
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
}
