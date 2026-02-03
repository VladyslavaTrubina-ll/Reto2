package modelo;

public class Sesion {
	Pelicula pelicula;
	private String fecha, horario;
	private String sala;
	private int numEspectadores;
	private double precio,descuento;

	public Sesion(Pelicula pelicula, String fecha, String horario, String sala, int numEspectadores, double precio,double descuento) {
		this.pelicula = pelicula;
		this.fecha = fecha;
		this.horario = horario;
		// a nadie le sale el horario de fin en una app de cine.
		this.sala = sala;
		this.descuento = descuento;
		this.precio = precio;
		this.numEspectadores = numEspectadores;
	}
	
	public double getDescuento() {
		return descuento;
	}

	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}

	public Sesion() {
		// TODO Auto-generated constructor stub
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
	
	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getSala() {
		return sala;
	}

	public void setSala(String sala) {
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
		+ ", fecha=" + fecha + ", horario=" + horario  + ", sala="+ sala 
		+ ", numEspectadores=" + numEspectadores + ", precio=" + precio + "]";
	}
	
	public void actualizarEspectadores(int numEntradas) {
		this.numEspectadores += numEntradas;
	}
	
	/*public static Sesion sample(String peliculaNombre, String fecha) {
		
		Pelicula pTest = new Pelicula(peliculaNombre, 90);
		Sala salaTest = new Sala("Sala Test", 20);
		
		Sesion sTest = new Sesion(pTest, fecha, "12:00", "", 5, 10.0);
		
		return sTest;
	}*/
	
	/*public static void test(int n) {
		System.out.println("test initializacion de Sesion y +" + n + " espectadores");
		
		Sesion sTest = sample("Avatar", "01-01-1970");
		
		System.out.println(sTest);
		
		sTest.actualizarEspectadores(n);
		
		System.out.println(sTest);*/
		
	}
	
//	public static void main(String args[]) {
//		test(1);
//	}


