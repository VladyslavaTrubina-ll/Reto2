package modelo;
/**
 * Esta clase gestiona el contador de espectadores (la ocupación) de una sesión.
 * Permite llevar el registro de cuántas butacas han sido ocupadas.
 */
public class EspectadoresSesion {
	private int espectadores;

	public EspectadoresSesion() {

	}

	public EspectadoresSesion(int espectadores) {
		this.espectadores = espectadores;
	}

	@Override
	public String toString() {
		return "EspectadoresSesion Sillas ocupadas [espectadores=" + espectadores + "]";
	}

	public int getEspectadores() {
		return espectadores;
	}

	public void setEspectadores(int espectadores) {
		this.espectadores = espectadores;
	}
/**
 * Incrementa el número actual de espectadores.Suma la
 * cantidad de nuevas entradas vendidas al total acumulado.
 * 
 * @param nuevoespectadores
 */
	public void anadirespectadores(int nuevoespectadores) {
		this.espectadores = espectadores + nuevoespectadores;
	}
}
