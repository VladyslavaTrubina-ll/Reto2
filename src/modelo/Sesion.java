package modelo;

public class Sesion {
	
	//TODO VERIFICSR set EN CLASSES
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

	public void setPelicula(Pelicula pelicula) {
		this.pelicula = pelicula;
	}

	public Pelicula getPelicula() {
		return pelicula;
	}
	
	public String getFecha() {
		return fecha;
	}
	
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}
	
	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	public int getNumEspectadores() {
		return numEspectadores;
	}

	public void setNumEspectadores(int numEspectadores) {
		this.numEspectadores = numEspectadores;
	}
	
	@Override
	public String toString() {
		return "Sesion [pelicula=" + pelicula.getNombre()
		+ ", fecha=" + fecha + ", horaInicio=" + horaInicio  + ", horaFIn=" + horaFin  + ", sala="+ sala 
		+ ", numEspectadores=" + numEspectadores + ", precio=" + precio + "]";
	}
	
	public void actualizarEspectadores(int numEntradas) {
		this.numEspectadores += numEntradas;
	}
	
	/*public static Sesion sample(String peliculaNombre, String fecha) {
		
		Pelicula pTest = new Pelicula(peliculaNombre, 90);
		Sala salaTest = new Sala("Sala Test", 20);
		
		Sesion sTest = new Sesion(pTest, fecha, "12:00", "16:00", salaTest, 5, 10.0);
		
		return sTest;
	}
	
	public static void test(int n) {
		System.out.println("test initializacion de Sesion y +" + n + " espectadores");
		
		Sesion sTest = sample("Avatar", "01-01-1970");
		
		System.out.println(sTest);
		
		sTest.actualizarEspectadores(n);
		
		System.out.println(sTest);
		
	}*/
	
//	public static void main(String args[]) {
//		test(1);
//	}

}
