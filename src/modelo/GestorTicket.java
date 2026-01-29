package modelo;
    
	import java.io.FileWriter;
	import java.io.PrintWriter;
	import java.io.IOException;

	public class GestorTicket {
	    
		    
		    private static final String ARCHIVO = "compras.txt";
		    
		    public static void salvaCompra(ClienteAcesso cliente, Carrito carrito) {
		        try (FileWriter fw = new FileWriter(ARCHIVO, true);
		             PrintWriter pw = new PrintWriter(fw)) {
		            
		            // Separatore
		            pw.println("\n=== COMPRA ===");
		            
		            // Cliente (usa i tuoi getter)
		            pw.println("Cliente: " + cliente.getNombre() + " " + cliente.getApellidos());
		            pw.println("DNI: " + cliente.getDni());
		            
		            // Entradas (usa i tuoi getter)
		            pw.println("Entradas:");
		            for (Sesion e : carrito.getSesiones()) {
		                pw.println("  - " + e.getPelicula() + 
		                		" | " + e.getFecha() +
		                		" | " + e.getSala() + 
		                		" | " + e.getHorario() + 
		                          " | " + e.getNumEspectadores() + "p | " + 
		                          e.getPrecio() + "€/p | " +
		                          "Desc: " + carrito.getDescuentoAplicado() + "€");
		            }
		            
		            // Totali (usa i tuoi getter del Carrito)
		            pw.println("Total: " + carrito.getPrecioTotal() + "€");
		            pw.println("Descuento: " + carrito.getDescuento() + "€");
		            
		        } catch (IOException e) {
		            System.out.println("Error: " + e.getMessage());
		        }
		    }
		}
