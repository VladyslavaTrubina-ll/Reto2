package modelo;

//import modelo.Entrada;
import java.util.ArrayList;

public class Carrito {

	private ArrayList<Sesion> sesiones;
	private ArrayList<Integer> cantidadesEntradas;
	private double precioSubTotal, precioTotal, descuentoAplicado, descuento;

	public Carrito() {
		this.sesiones = new ArrayList<Sesion>();
		this.cantidadesEntradas = new ArrayList<Integer>();
		this.precioSubTotal = 0.0;
		this.precioTotal = 0.0;
		this.descuentoAplicado = 0.0;
		this.descuento = 0.0;
	}

	@Override
	public String toString() {
		return "Carrito [nSesiones=" + sesiones.size() 
				+ ", cantidadesEntradas=" + cantidadesEntradas 
				+  ", precioSubTotal=" + precioSubTotal 
				+  ", precioTotal=" + precioTotal 
				+ ", descuentoAplicado=" + descuentoAplicado 
				+ ", descuento=" + descuento 
				+ "]";
	}

	public ArrayList<Sesion> getSesiones() {
		return sesiones;
	}
	
	public ArrayList<Integer> getCantidadesEntradas() {
		return cantidadesEntradas;
	}
	
	public double getPrecioSubTotal() {
		return precioSubTotal;
	}

	public double getPrecioTotal() {
		return precioTotal;
	}

	public double getDescuentoAplicado() {
		return descuentoAplicado;
	}
	
	public double getDescuento() {
		return descuento;
	}
	
	public void vaciar() {
		this.sesiones.clear(); 
		this.cantidadesEntradas.clear();
		this.precioSubTotal = 0.0;
		this.precioTotal = 0.0;
		this.descuentoAplicado = 0.0;
		this.descuento = 0.0;
	}
	
	public double calcularDescuentoAplicado(int numSesiones) {

		// 2. Determinamos el porcentaje de descuento
		if (numSesiones >= 3) {
			return 0.30; // 30%
		} else if (numSesiones == 2) {
			return 0.20; // 20%
		} else if (numSesiones == 1) {
			return 0.0;
		} else {
			throw new IllegalArgumentException("No puede ser " + numSesiones + " numSesiones < 1");
		}
	}
	
	public void calcularPrecioYDescuento() {
		if (sesiones.isEmpty()) {
			this.precioSubTotal = 0.0;
			this.precioTotal = 0.0;
			this.descuentoAplicado = 0.0;
			this.descuento = 0.0;
			return;
		}

		// 1. Contar cuantos sesiones selecionados
		int numSesiones = sesiones.size();
		this.descuentoAplicado = calcularDescuentoAplicado(numSesiones);
		
		this.precioSubTotal = 0.0;
		
		// 3. Calcular subtotal
		for (int i = 0; i < numSesiones; i++) {
			Sesion sesion = sesiones.get(i);
			int numEntradas = cantidadesEntradas.get(i);
			this.precioSubTotal += sesion.getPrecio() * numEntradas;
		}
		double[] x = aplicarDescuento(precioSubTotal, descuentoAplicado);
		this.precioTotal = x[0];
		this.descuento = x[1];
	}
	
	public double[] aplicarDescuento(double precioSubTotal, double descuentoAplicado) {
		// 4. Aplicar descuento
		descuento = precioSubTotal * descuentoAplicado;
		
		// 5. Calcular precio final
		precioTotal = precioSubTotal - descuento;
		
		return new double[]{precioTotal, descuento};
	}
	
	public void anadirEntrada(Sesion sesion, int numEntradas) {
		this.sesiones.add(sesion);
		this.cantidadesEntradas.add(numEntradas);
		this.calcularPrecioYDescuento();
	}	
	
	public void resumen(String nombre, String apellidos) {
		System.out.println("\n================= TICKET DE COMPRA =================");
		System.out.println("Identificador: " + nombre + " " + apellidos);
		System.out.println("---------------------------------------------------");

		for (int i = 0; i < sesiones.size(); i++) {
			Sesion e = sesiones.get(i);
			int numEntradas = cantidadesEntradas.get(i);

			System.out.println("\nPelícula " + (i + 1) + ": " + e.getPelicula().getNombre());
			System.out.println("\n  Fecha: " + e.getFecha());
			System.out.println("  Sala: " + e.getSala().getNombre());
			System.out.println("  Horario: " + e.getHoraInicio());
			System.out.println("  Personas: " + numEntradas);
			System.out.printf("  Precio por sesión: %.2f€%n", e.getPrecio());
			System.out.printf("  Subtotal: %.2f€%n", e.getPrecio() * numEntradas);
		}

		System.out.println("\n------------------ TOTAL -----------------------");
		System.out.printf("Subtotal: %.2f€%n", precioSubTotal);
		System.out.printf("Descuento (%.0f%%): %.2f€%n", descuentoAplicado * 100, descuento);
		System.out.printf("TOTAL A PAGAR: %.2f€%n", precioTotal);
		System.out.println("===================================================");
	}
}
