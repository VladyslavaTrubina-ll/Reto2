package modelo;

import modelo.Carrito;
import modelo.Entrada;
import modelo.Ticket;
import java.util.Scanner;

public class GestorCarrito {
	private Carrito carrito;

	public GestorCarrito() {

	}

	public GestorCarrito(Carrito carrito) {
		this.carrito = carrito;
	}

	public void anadirentrada(Entrada entrada) {
		carrito.getEntrada().add(entrada);

	}

	public double preciototal() {
		double sumatotal = 0.0;
		for (int i = 0; i < carrito.getEntrada().size(); i++) {
			Entrada entradaActual = carrito.getEntrada().get(i);
			double precioentrada = entradaActual.getPrecio() * entradaActual.getNumeropersonas();
			sumatotal = sumatotal + precioentrada;
		}
		return sumatotal;
	}

	public void calculardescuento(Entrada entrada) {
		if (carrito.getEntrada().size() >= 3) {
			double descuento = (entrada.getPrecio() * 0.30);
			entrada.setDescuento(descuento);
			double preciocondescuento = (entrada.getPrecio() - descuento);
			entrada.setPrecio(preciocondescuento);
		} else if (carrito.getEntrada().size() == 2) {
			double descuento = (entrada.getPrecio() * 0.20);
			entrada.setDescuento(descuento);
			double preciocondescuento = (entrada.getPrecio() - descuento);
			entrada.setPrecio(preciocondescuento);

		}
	}

	public void resumencarrito() {

	}
}