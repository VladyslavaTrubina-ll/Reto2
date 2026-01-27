package modelo;

import modelo.Entrada;
import java.util.ArrayList;

public class Carrito {
	private ArrayList<Entrada> entrada;
	private double preciototal, descuentoaplicato;

	public Carrito() {

		 this.entrada = new ArrayList<>();
	        this.preciototal = 0.0;
	        this.descuentoaplicato = 0.0;
	}

	public Carrito(ArrayList<Entrada> entrada, double preciototal, double descuentoaplicado) {

		this.entrada = entrada;
		this.preciototal = preciototal;
		this.descuentoaplicato = descuentoaplicado;
	}

	@Override
	public String toString() {
		return "Carrito [entrada=" + entrada + ", preciototal=" + preciototal + ", descuentoaplicato="
				+ descuentoaplicato + "]";
	}

	public ArrayList<Entrada> getEntrada() {
		return entrada;
	}

	public void setEntrada(ArrayList<Entrada> entrada) {
		this.entrada = entrada;
	}

	public double getPreciototal() {
		return preciototal;
	}

	public void setPreciototal(double preciototal) {
		this.preciototal = preciototal;
	}

	public double getDescuentoaplicato() {
		return descuentoaplicato;
	}

	public void setDescuentoaplicato(double descuentoaplicato) {
		this.descuentoaplicato = descuentoaplicato;
	}
	public void vaciar() {
	    this.entrada.clear();  
	    this.preciototal = 0.0;
	    
	}
}
