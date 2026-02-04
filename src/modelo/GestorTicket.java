package modelo;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
    

public class GestorTicket {
	private static final String ARCHIVO = "ficheros/compras.txt";
	
	public static void salvaCompra(ClienteAcesso cliente, Carrito carrito) {
		// Obtener fecha y hora actual
		LocalDateTime ahora = LocalDateTime.now();
		
		try (FileWriter fw = new FileWriter(ARCHIVO, false);
		PrintWriter pw = new PrintWriter(fw)) {
			// Encabezado
		    pw.println("\n╔══════════════════════════════════════════════════════════════════════════════╗");
		    pw.println("║                            TICKET DE COMPRA                                  ║");
		    pw.println("╚══════════════════════════════════════════════════════════════════════════════╝");
		    pw.println();
		            
		    // Cliente
		    pw.println("Cliente: " + cliente.getNombre() + " " + cliente.getApellidos());
		    pw.println("DNI:     " + cliente.getDni());
		    pw.println("Fecha:   " + ahora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
		    pw.println();
		    pw.println("════════════════════════════════════════════════════════════════════════════════");
		            
		    // Sesiones seleccionadas - Encabezado de tabla
		    pw.println("SESIONES SELECCIONADAS:");
		    pw.println("────────────────────────────────────────────────────────────────────────────────");
		    pw.printf("%-20s %-12s %-18s %-10s %8s %8s%n", 
		    		"PELÍCULA", "FECHA", "SALA", "HORA", "ENTRADAS", "PRECIO");
		    pw.println("────────────────────────────────────────────────────────────────────────────────");
		    
		    for (int i = 0; i < carrito.getSesiones().size(); i++) {
		    	Sesion e = carrito.getSesiones().get(i);
		    	int numEntradas = carrito.getCantidadesEntradas().get(i);
		    	double precioLinea = e.getPrecio() * numEntradas;
		    	
				pw.printf("%-20s %-12s %-18s %-10s %8d %7.2f€%n",
		                	e.getPelicula().getNombre(),
		                	e.getFecha(),
		                	e.getSala().getNombre(),
		                	e.getHoraInicio(),
		                    numEntradas,
		                    precioLinea);
			}
		    
			// Totales
			pw.println("════════════════════════════════════════════════════════════════════════════════");
		    pw.printf("%10s %7.2f€%n", "Subtotal:", carrito.getPrecioSubTotal());
		    pw.printf("%10s %7.2f€%n", "Descuento aplicado:", carrito.getDescuento());
		    pw.println("────────────────────────────────────────────────────────────────────────────────");
		    pw.printf("%10s %7.2f€%n", "TOTAL A PAGAR:", carrito.getPrecioTotal());
		    pw.println("════════════════════════════════════════════════════════════════════════════════");
		    pw.println();
		    pw.println("						¡Gracias por su compra!");
		    pw.println("						Disfrute de su película");
		    pw.println();
		    
		    System.out.println("Ticket guardado en: " + ARCHIVO);
		} catch (IOException e) {
			System.out.println("Error al guardar ticket: " + e.getMessage());
		}
	
	}
}
