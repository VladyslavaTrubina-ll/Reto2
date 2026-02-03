package modelo;

//import modelo.Entrada;
import java.util.ArrayList;

public class Carrito {

	private ArrayList<Sesion> sesiones;
	private ArrayList<Integer> cantidadesEntradas;
	private double precioSubTotal, precioTotal, descuentoAplicado, descuento, cambio;

	public Carrito() {
		this.sesiones = new ArrayList<Sesion>();
		this.cantidadesEntradas = new ArrayList<Integer>();
		this.precioSubTotal = 0.0;
		this.precioTotal = 0.0;
		this.descuentoAplicado = 0.0;
		this.descuento = 0.0;
		this.cambio = 0.0;
	}

	@Override
	public String toString() {
		return "Carrito [nSesiones=" + sesiones.size() 
				+ ", cantidadesEntradas=" + cantidadesEntradas 
				+  ", precioSubTotal=" + precioSubTotal 
				+  ", precioTotal=" + precioTotal 
				+ ", descuentoAplicado=" + descuentoAplicado 
				+ ", descuento=" + descuento 
				+ ", cambio=" + cambio
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
	
	public double getCambio() {
		return cambio;
	}
	
	public void setCambio(double cambio) {
		this.cambio = cambio;
	}
	
	public void vaciar() {
		this.sesiones.clear(); 
		this.cantidadesEntradas.clear();
		this.precioSubTotal = 0.0;
		this.precioTotal = 0.0;
		this.descuentoAplicado = 0.0;
		this.descuento = 0.0;
		this.cambio = 0.0;
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

		// 2. Determinamos el porcentaje de descuento
		
		if (numSesiones >= 3) {
			this.descuentoAplicado = 0.30; // 30%
			
		} else if (numSesiones == 2) {
			this.descuentoAplicado = 0.20; // 20%
		}
		// Si 1 sesion: porcentaje = 0.0
		
		this.precioSubTotal = 0.0;
		
		// 3. Calcular subtotal
		for (int i = 0; i < numSesiones; i++) {
			Sesion sesion = sesiones.get(i);
			int numEntradas = cantidadesEntradas.get(i);
			
			this.precioSubTotal += sesion.getPrecio() * numEntradas; // TODO verificar double*int
		}
		
		// 4. Aplicar descuento
		this.descuento = precioSubTotal * descuentoAplicado;
		
		// 5. Calcular precio final
		this.precioTotal = precioSubTotal - descuento;

	}
	
	public void anadirEntrada(Sesion sesion, int numEntradas) {
		this.sesiones.add(sesion);
		this.cantidadesEntradas.add(numEntradas);
		this.calcularPrecioYDescuento();
	}	
	
	public static void resumen(String nombre, String apellidos, Carrito carrito) {
		ArrayList<Sesion> sesiones = carrito.getSesiones();
		ArrayList<Integer> cantidadesEntradas = carrito.getCantidadesEntradas();
		System.out.println("\n=== RESUMEN COMPRA ===");
		
		System.out.println("\nIdentificador" + ": " + nombre + " " + apellidos);

		for (int i = 0; i < sesiones.size(); i++) {
			Sesion e = sesiones.get(i);
			int numEntradas = cantidadesEntradas.get(i);

			System.out.println("\nPelicula " + (i + 1) + ": " + e.getPelicula().getNombre());
			System.out.println("  Fecha: " + e.getFecha());
			//System.out.println("  Sala: " + e.getSala().getNombre());
			System.out.println("  Horario: " + e.getHoraInicio());
			System.out.println("  Personas: " + numEntradas);
			System.out.println("  Precio de sesion: " + e.getPrecio() + "€");
			System.out.println("  Subtotal: " +  e.getPrecio() * numEntradas + "€");
		}

		System.out.println("\n--- TOTAL ---");
		System.out.println("Subtotal: " + carrito.getPrecioSubTotal() + "€");
		System.out.println("Descuento (" + carrito.getDescuentoAplicado() * 100 + "%): "+ carrito.getDescuento() +"€");
		System.out.println("TOTAL: " + carrito.getPrecioTotal() + "€");
		if (carrito.getCambio() > 0) {
			System.out.println("Cambio: " + String.format("%.2f", carrito.getCambio()) + "€");
		}
	}
	
	/*public static void test() {
		System.out.println("test initializacion de Carrito");
		
		Carrito cTest = new Carrito();
		System.out.println(cTest);
		
		
		Sesion sTest1 = Sesion.sample("Peli 1", "01-01-2000");
		Sesion sTest2 = Sesion.sample("Peli 2", "02-01-2000");
		
		System.out.println("test anadir entradas a Carrito #1");
		cTest.anadirEntrada(sTest1, 1);
		System.out.println(cTest);
		
		System.out.println("test anadir entradas a Carrito #2");
		cTest.anadirEntrada(sTest2, 2);
		System.out.println(cTest);
	
		
		// si compra hacer esto!
//		sTest1.actualizarEspectadores(1);
//		sTest2.actualizarEspectadores(2);
	
//		System.out.println(sTest1);
//		System.out.println(sTest2);
		
		System.out.println("test resumen Carrito");
		resumen("Juan", "Garcia", cTest);
		
		System.out.println("test vaciar Carrito");
		cTest.vaciar();
		System.out.println(cTest);
	}
	
/*	public static void main(String args[]) {
		test();
	}*/

// Avatar 10.0 x 3 = 30.0
// Joker 5.0 x 2 = 10.0
// Subtotal 30+10 = 40
// Descuento 20% = 40 * 0.2 = 8
// Total 40-8 = 32

}
