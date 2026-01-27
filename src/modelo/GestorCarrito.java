package modelo;

import modelo.Carrito;
import modelo.Entrada;
import modelo.Ticket;

import java.util.ArrayList;
import java.util.Scanner;

public class GestorCarrito {
	private Carrito carrito;

	public GestorCarrito() {
		this.carrito = new Carrito();
	}

	public GestorCarrito(Carrito carrito) {
		this.carrito = carrito;
	}

	public void anadirentrada(Entrada entrada) {
		carrito.getEntrada().add(entrada);

	}

	public double preciototal(Entrada entrada) {
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

	public void resumencarrito(Entrada entrada) {
		
		System.out.println("*****RESUMEN COMPRA*****");
		ArrayList<Entrada> entradas = carrito.getEntrada();
		for (int i = 0; i < carrito.getEntrada().size(); i++) {
			 Entrada entradaActual = entradas.get(i);
			 System.out.println("\nEntrada " + (i + 1) + ":");
		        System.out.println("Película: " + entradaActual.getPelicula());
		        System.out.println("Horario: " + entradaActual.getOrario());
		        System.out.println("Número de personas: " + entradaActual.getNumeropersonas());
		        System.out.println("Precio unitario: " + entradaActual.getPrecio() + "€");
		        System.out.println("Descuento aplicado: " + entradaActual.getDescuento() + "€");
		        System.out.println("Subtotal: " + (entradaActual.getPrecio() * entradaActual.getNumeropersonas()) + "€");
		}
	}

}