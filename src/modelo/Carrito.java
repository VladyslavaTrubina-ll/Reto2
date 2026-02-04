package modelo;

//import modelo.Entrada;
import java.util.ArrayList;

/**
 * Esta clase gestiona el carrito de compra de las entradas de cine
 * Almacena las sesiones seleccionadas, calcula subtotales,
 * aplica descuentos según la cantidad de películas y genera el resumen de compra.
 */
public class Carrito {

	private ArrayList<Sesion> sesiones;
	private ArrayList<Integer> cantidadesEntradas;
	private double precioSubTotal, precioTotal, descuentoAplicado, descuento;

	/**
	 * Constructor: Inicializa las listas y pone los contadores a cero.
	 */
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
	/**
	 * Reinicia el estado del carrito
	 * Elimina todas las sesione y entradas guardadas y resetea los precios a 0 
	 */
	public void vaciar() {
		this.sesiones.clear(); 
		this.cantidadesEntradas.clear();
		this.precioSubTotal = 0.0;
		this.precioTotal = 0.0;
		this.descuentoAplicado = 0.0;
		this.descuento = 0.0;
	}
	
	public double calcularDescuentoAplicado(int numSesiones) {

		/**
		 * Determina el porcentaje de descuento aplicable según el número de sesiones distintas.
		 * reglas de negocio:
		 * 3 sesiones o mas 30% de descuento
		 * 2 sesiones: 20% de descuento
		 * 1 sesión 0% de descuento
		 */
		if (numSesiones >= 3) {
			return 0.30; 
		} else if (numSesiones == 2) {
			return 0.20; 
		} else if (numSesiones == 1) {
			return 0.0;
		} else {
			throw new IllegalArgumentException("No puede ser " + numSesiones + " numSesiones < 1");
		}
	}
	/**
	 * 1.Suma el costo de todas las entradas (precio sesión * cantidad).
	 * 2. Determina el descuentocorrespondiente.
	 * 3.Invoca a aplicarDescuento()para obtener los valores finales.
	 * Actualiza los atributos de la clase con los resultados.
	 
	 */
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
	/**
	 * Realiza la operación matemática para restar el descuento al subtotal.
	 * Actualiza los atributos internos y devuelve los valores calculados.
	 * @param precioSubTotal El precio acumulado antes de descuentos.
	 * @param descuentoAplicado El porcentaje de descuento ej 0,20
	 * @return un array de doubles donde 0 es el precio Total, y 1 es el dinero descontado.
	 */
	public double[] aplicarDescuento(double precioSubTotal, double descuentoAplicado) {
		// 4. Aplicar descuento
		descuento = precioSubTotal * descuentoAplicado;
		
		// 5. Calcular precio final
		precioTotal = precioSubTotal - descuento;
		
		return new double[]{precioTotal, descuento};
	}
	/**
	 * Recalcula automáticamente todos los precios y descuentos al añadir.
	 * @param sesion
	 * @param numEntradas
	 */
	public void anadirEntrada(Sesion sesion, int numEntradas) {
		this.sesiones.add(sesion);
		this.cantidadesEntradas.add(numEntradas);
		//recalcular estado de carrito automáticamente
		this.calcularPrecioYDescuento();
	}	
	
	public void resumen(String nombre, String apellidos) {
		System.out.println("\n=== RESUMEN COMPRA ===");
		
		System.out.println("\nIdentificador" + ": " + nombre + " " + apellidos);

		for (int i = 0; i < sesiones.size(); i++) {
			Sesion e = sesiones.get(i);
			int numEntradas = cantidadesEntradas.get(i);

			System.out.println("\nPelicula " + (i + 1) + ": " + e.getPelicula().getNombre());
			System.out.println("  Fecha: " + e.getFecha());
			System.out.println("  Sala: " + e.getSala().getNombre());
			System.out.println("  Horario: " + e.getHoraInicio());
			System.out.println("  Personas: " + numEntradas);
			System.out.println("  Precio de sesion: " + e.getPrecio() + "€");
			System.out.println("  Subtotal: " +  e.getPrecio() * numEntradas + "€");
		}

		System.out.println("\n--- TOTAL ---");
		System.out.println("Subtotal: " + precioSubTotal + "€");
		System.out.println("Descuento (" + descuentoAplicado * 100 + "%): "+ descuento +"€");
		System.out.println("TOTAL: " + precioTotal + "€");
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
