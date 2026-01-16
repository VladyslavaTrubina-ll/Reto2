package modelo;

import modelo.Pelicula;

public class Sesión {
	private String id_sesion, fecha, horarioinicio, horariofin;
	private double precio;
	Pelicula peli = new Pelicula();

	public Sesión() {

	}

	public Sesión(String id_sesion,String fecha, String horarioinicio, String horariofin, double precio, Pelicula peli) {
		this.id_sesion = id_sesion;
		this.fecha = fecha;
		this.horarioinicio = horarioinicio;
		this.horariofin = horariofin;
		this.precio = precio;
		this.peli = peli;
	}

	public String getId_sesion() {
		return id_sesion;
	}

	public void setId_sesion(String id_sesion) {
		this.id_sesion = id_sesion;
	}

	@Override
	public String toString() {
		return "Sesión [fecha=" + fecha + ", horarioinicio=" + horarioinicio + ", horariofin=" + horariofin
				+ ", precio=" + precio + ", peli=" + peli + "]";
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHorarioinicio() {
		return horarioinicio;
	}

	public void setHorarioinicio(String horarioinicio) {
		this.horarioinicio = horarioinicio;
	}

	public String getHorariofin() {
		return horariofin;
	}

	public void setHorariofin(String horariofin) {
		this.horariofin = horariofin;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public Pelicula getPeli() {
		return peli;
	}

	public void setPeli(Pelicula peli) {
		this.peli = peli;
	}
}
