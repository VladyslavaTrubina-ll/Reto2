package modelo;

public class FechaSesion {  //TODO quiza nececito eliminar
	private String fecha;

	public FechaSesion() {

	}

	public FechaSesion(String fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return fecha;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
}
