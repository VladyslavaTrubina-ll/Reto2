package modelo;

import modelo.Carrito;
import modelo.Entrada;
import modelo.Ticket;
import modelo.ClienteAcesso;
import java.util.ArrayList;
import java.util.Scanner;

public class GestorCarrito {
	private Carrito carrito;
	private ClienteAcesso cliente;

	public GestorCarrito() {
		this.carrito = new Carrito();
		this.cliente = new ClienteAcesso();
	}

	public GestorCarrito(Carrito carrito, ClienteAcesso cliente) {
		this.cliente = cliente;
		this.carrito = carrito;
	}

	public Carrito getCarrito() {
		return carrito;
	}

	public void setCarrito(Carrito carrito) {
		this.carrito = carrito;
	}

	public ClienteAcesso getCliente() {
		return cliente;
	}

	public void setCliente(ClienteAcesso cliente) {
		this.cliente = cliente;
	}

	public void anadirentrada(Entrada entrada) {
		carrito.getEntrada().add(entrada);

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
			double precioentrada = e.getPrecio() * e.getNumeropersonas();
			subtotal += precioentrada;

			double descuentoEntrada = precioentrada * porcentaje;
			descuentoTotal += descuentoEntrada;
			e.setSubtotal(precioentrada);

			e.setDescuento(descuentoEntrada);
		}

		// 4. Precio final
		double total = subtotal - descuentoTotal;

		carrito.setPreciototal(total);
		carrito.setDescuentoaplicato(descuentoTotal);
	}

	public void resumencarrito(Entrada entrada) {
		cliente.getNombre();
		ArrayList<Entrada> entradas = carrito.getEntrada();
		System.out.println("\n=== RESUMEN COMPRA ===");

		for (int i = 0; i < entradas.size(); i++) {
			Entrada e = entradas.get(i);
			double precioEntrada = e.getSubtotal();
			double descuento = e.getDescuento();

			System.out.println("\nEntrada " + (i + 1) + ": " + e.getPelicula());
			System.out.println("  Identificador" + ": " + cliente.getNombre() + cliente.getApellidos());
			System.out.println("  Fecha: " + e.getFecha());
			System.out.println("  Sala: " + e.getSala());
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

	public void vaciarCarrito() {
		this.carrito.vaciar();
	}
}
