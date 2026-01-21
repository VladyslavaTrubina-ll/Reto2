package modelo;

public class FechaSesion {
	private String fecha;

	public FechaSesion() {

	}

	public FechaSesion(String fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "FechaSesion [fecha=" + fecha + "]";
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
}
