package modelo;

public class EspectadoresSesion {
	private int espectadores;

	public EspectadoresSesion() {

	}

	public EspectadoresSesion(int espectadores) {
		this.espectadores = espectadores;
	}

	@Override
	public String toString() {
		return "EspectadoresSesion [espectadores=" + espectadores + "]";
	}

	public int getEspectadores() {
		return espectadores;
	}

	public void setEspectadores(int espectadores) {
		this.espectadores = espectadores;
	}
}
