package modelo;
/**
 * Esta clase es la encargada de generar y guardar el ticket.
 * Se encarga de escribir en un archivo txt el resumen de la compra 
 * con un formato visual de tabla.
 * 
 */
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
    

public class GestorTicket {
	/**
	 * Ruta donde se guardará el historial de ventas
	 */
	private static final String ARCHIVO = "ficheros/compras.txt";
	/**
	 * El modo 'append':No borra el historial previo, añade a la nueva venta final.
	 * Autocierre: Usa try with resources para cerrar el archivo automáticamente
	 * @param cliente
	 * @param carrito
	 */
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
		    /**
		     * se repite el proceso sobre el crrito para imprimir cada línea
		     */
		    for (int i = 0; i < carrito.getSesiones().size(); i++) {
		    	Sesion e = carrito.getSesiones().get(i);
		    	int numEntradas = carrito.getCantidadesEntradas().get(i);
		    	double precioLinea = e.getPrecio() * numEntradas;
		    	/**
		    	 * Imprimimos la fila con el mismo formato que la cabecera
		    	 * 
		    	 */
		    	
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
		    
		    System.out.println("\nTicket guardado en " + ARCHIVO);
		} catch (IOException e) {
			System.out.println("Error al guardar ticket: " + e.getMessage());
		}
	
	}
}
