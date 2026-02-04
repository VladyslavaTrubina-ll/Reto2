package modelo;

public class Sesion {
	
	Pelicula pelicula;
	private String fecha, horaInicio, horaFin;
	private Sala sala;
	private int numEspectadores;
	private double precio;

	public Sesion(Pelicula pelicula, String fecha, String horaInicio, String horaFin, Sala sala, int numEspectadores, double precio) {
		this.pelicula = pelicula;
		this.fecha = fecha;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.sala = sala;
		this.precio = precio;
		this.numEspectadores = numEspectadores;
	}
	
	public Sesion() {
		
	}

	public Pelicula getPelicula() {
		return pelicula;
	}
	
	public String getFecha() {
		return fecha;
	}
	
	public String getHoraInicio() {
		return horaInicio;
	}

	public String getHoraFin() {
		return horaFin;
	}

	public Sala getSala() {
		return sala;
	}
	
	public double getPrecio() {
		return precio;
	}
	
	public int getNumEspectadores() {
		return numEspectadores;
	}
	
	@Override
	public String toString() {
		return "Sesion [pelicula=" + pelicula.getNombre()
		+ ", fecha=" + fecha + ", horaInicio=" + horaInicio  + ", horaFIn=" + horaFin  + ", sala="+ sala 
		+ ", numEspectadores=" + numEspectadores + ", precio=" + precio + "]";
	}
	
	public void actualizarEspectadores(int numEntradas) { //TODO utilisar
		this.numEspectadores += numEntradas;
	}
	
}
