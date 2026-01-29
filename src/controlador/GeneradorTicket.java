package controlador;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import modelo.Pelicula;

public class GeneradorTicket {

    public static void guardarFactura(Pelicula p, int cantidad) {
        
        String nombreCarpeta = "tickets";
        String nombreArchivo = "ticket_" + p.getTitulo().replace(" ", "_") + ".txt";

        File carpeta = new File(nombreCarpeta);

        if (!carpeta.exists()) {
            carpeta.mkdir();
        }

        File archivoFinal = new File(carpeta, nombreArchivo);

        try {
            FileWriter fw = new FileWriter(archivoFinal);
            PrintWriter pw = new PrintWriter(fw);

            double total = p.getPrecioBase() * cantidad;

            pw.println("=================================");
            pw.println("       CINE CIUDAD - TICKET      ");
            pw.println("=================================");
            pw.println("Fecha: " + LocalDateTime.now());
            pw.println("---------------------------------");
            pw.println("Película: " + p.getTitulo());
            pw.println("Duración: " + p.getDuracion() + " min");
            pw.println("Precio unidad: " + p.getPrecioBase() + "€");
            pw.println("---------------------------------");
            pw.println("Entradas: " + cantidad);
            pw.println("TOTAL A PAGAR: " + total + "€");
            pw.println("=================================");
            pw.println("    ¡Gracias por su visita!      ");

            pw.close(); 
            
            System.out.println("📄 Ticket guardado en la carpeta '" + nombreCarpeta + "': " + nombreArchivo);

        } catch (Exception e) {
            System.out.println("❌ Error al crear el ticket: " + e.getMessage());
        }
    }
}
