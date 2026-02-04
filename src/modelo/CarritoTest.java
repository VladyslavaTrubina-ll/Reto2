package modelo;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;

public class CarritoTest {

	private static Carrito carrito = null;
	private static Pelicula pelicula = null;
	private static Sala sala = null;
	private static Sesion sesion10 = null;
	private static Sesion sesion5 = null;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		carrito = new Carrito();
		
		pelicula = new Pelicula("Test pelicula", 90, "test genero");
		sala = new Sala("Sala Test", 20);

		sesion10 = new Sesion(pelicula,"26-02-2026", "12:00", "16:00", sala, 5, 10.0);
		sesion5 = new Sesion(pelicula,"26-02-2026", "12:00", "16:00", sala, 5, 5.0);
	}

	@After
	public void limpiarAfter() {
		carrito.vaciar();
		System.out.print("limpiar");
	}
	
	@Test
	public void testVaciar() {
		
		carrito.anadirEntrada(sesion10, 2);
		carrito.vaciar();
		
		assertEquals("sesiones", 0, carrito.getSesiones().size());
		assertEquals("cantidadesEntradas", 0, carrito.getCantidadesEntradas().size());
		assertEquals("precioSubTotal ", 0.0, carrito.getPrecioSubTotal(), 0.01);
		assertEquals("precioTotal", 0.0, carrito.getPrecioTotal(), 0.01);
	    assertEquals("descuentoAplicado", 0.0, carrito.getDescuentoAplicado(), 0.01);
	    assertEquals("descuento", 0.0, carrito.getDescuento(), 0.01);
	}

	@Test
	public void testCalcularDescuentoAplicado() {
	    assertEquals("DescuentoAplicado 1", 0.0, carrito.calcularDescuentoAplicado(1), 0.01);
	    assertEquals("DescuentoAplicado 2", 0.20, carrito.calcularDescuentoAplicado(2), 0.01);
	    assertEquals("DescuentoAplicado 3", 0.30, carrito.calcularDescuentoAplicado(3), 0.01);
	    assertEquals("DescuentoAplicado 5", 0.30, carrito.calcularDescuentoAplicado(5), 0.01);
	    
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCalcularDescuentoAplicadoCero() {
	    carrito.calcularDescuentoAplicado(0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCalcularDescuentoAplicadoNegativo() {
	    carrito.calcularDescuentoAplicado(-1);
	}
	
	@Test
	public void testAplicarDescuento() {
		double[] resultado = carrito.aplicarDescuento(100.0, 0.20);
		assertEquals("precio total", 80.0, resultado[0], 0.01);
		assertEquals("descuento", 20.0, resultado[1], 0.01);
	}

	@Test
	public void testCalcularPrecioYDescuento1() {
		 carrito.anadirEntrada(sesion10, 2);
		 assertEquals("Precio subtotal sesion10 x 2", 20.0, carrito.getPrecioSubTotal(), 0.01);
		 assertEquals("Precio total sesion10 x 2", 20.0, carrito.getPrecioTotal(), 0.01);
		 assertEquals("Descuento sesion10 x 2", 0.0, carrito.getDescuento(), 0.01);
		 assertEquals("DescuentoAplicado sesion10 x 2", 0.0, carrito.getDescuentoAplicado(), 0.01); 
	}
	
	@Test
	public void testCalcularPrecioYDescuento2() {
		 carrito.anadirEntrada(sesion10, 2);
		 carrito.anadirEntrada(sesion5, 2);
		 assertEquals("Precio subtotal sesion10 x 2 + sesion5 x 2 ", 30.0, carrito.getPrecioSubTotal(), 0.01);
		 assertEquals("Precio total sesion10 x 2 + sesion5 x 2 ", 24.0, carrito.getPrecioTotal(), 0.01);
		 assertEquals("Descuento sesion10 x 2 + sesion5 x 2", 6.0, carrito.getDescuento(), 0.01);
		 assertEquals("DescuentoAplicado sesion10 x 2 + sesion5 x 2", 0.20, carrito.getDescuentoAplicado(), 0.01); 
	}
	@Test
	public void testCalcularPrecioYDescuento3() {
		 carrito.anadirEntrada(sesion10, 1);
		 carrito.anadirEntrada(sesion5, 1);
		 carrito.anadirEntrada(sesion5, 1);
		 assertEquals("Precio subtotal sesion10 x 1 + sesion5 x 1 +  sesion5 x 1", 20.0, carrito.getPrecioSubTotal(), 0.01);
		 assertEquals("Precio total sesion10 x 1 + sesion5 x 1 +  sesion5 x 1", 14.0, carrito.getPrecioTotal(), 0.01);
		 assertEquals("Descuento  sesion10 x 1 + sesion5 x 1 +  sesion5 x 1", 6.0, carrito.getDescuento(), 0.01);
		 assertEquals("DescuentoAplicado  sesion10 x 1 + sesion5 x 1 +  sesion5 x 1", 0.30, carrito.getDescuentoAplicado(), 0.01); 
	}


}
