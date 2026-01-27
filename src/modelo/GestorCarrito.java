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

	public void calculoscarrito(Entrada entrada) {
		ArrayList<Entrada> entradas = carrito.getEntrada();

		if (entradas.isEmpty()) {
			carrito.setPreciototal(0.0);
			carrito.setDescuentoaplicato(0.0);
			return;
		}

		// 1. Contamos cuántas entradas hay
		int numentradas = entradas.size();

		// 2. Determinamos el porcentaje de descuento
		double porcentaje = 0.0;
		if (numentradas >= 3) {
			porcentaje = 0.30; // 30%
		} else if (numentradas == 2) {
			porcentaje = 0.20; // 20%
		}
		// Si hay 1 entrada: porcentaje = 0.0

		// 3. Calculamos subtotal y descuentos
		double subtotal = 0.0;
		double descuentoTotal = 0.0;

		for (Entrada e : entradas) {
			double precioEntrada = e.getPrecio() * e.getNumeropersonas();
			subtotal += precioEntrada;

			double descuentoEntrada = precioEntrada * porcentaje;
			descuentoTotal += descuentoEntrada;

			e.setDescuento(descuentoEntrada);
		}

		// 4. Precio final
		double total = subtotal - descuentoTotal;

		carrito.setPreciototal(total);
		carrito.setDescuentoaplicato(descuentoTotal);
	}

	public void resumencarrito(Entrada entrada) {

		System.out.println("*****RESUMEN COMPRA*****");
		ArrayList<Entrada> entradas = carrito.getEntrada();
		System.out.println("\n=== RESUMEN COMPRA ===");

		for (int i = 0; i < entradas.size(); i++) {
			Entrada e = entradas.get(i);
			double precioEntrada = e.getPrecio() * e.getNumeropersonas();
			double descuento = e.getDescuento();

			System.out.println("\nEntrada " + (i + 1) + ": " + e.getPelicula());
			System.out.println("  Horario: " + e.getOrario());
			System.out.println("  Personas: " + e.getNumeropersonas());
			System.out.println("  Precio/u: " + e.getPrecio() + "€");
			System.out.println("  Subtotal: " + precioEntrada + "€");
			System.out.println("  Descuento: " + descuento + "€");
			System.out.println("  Total entrada: " + (precioEntrada - descuento) + "€");
		}

		System.out.println("\n--- TOTAL ---");
		System.out.println("Subtotal: " + (carrito.getPreciototal() + carrito.getDescuentoaplicato()) + "€");
		System.out.println("Descuento: " + carrito.getDescuentoaplicato() + "€");
		System.out.println("TOTAL: " + carrito.getPreciototal() + "€");
	}

}
