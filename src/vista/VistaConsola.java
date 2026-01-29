package vista;

import java.util.Scanner;

public class VistaConsola {

    private Scanner scanner;

    public VistaConsola() {
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public String pedirDato(String mensaje) {
        System.out.print(mensaje + ": ");
        return scanner.nextLine();
    }

    public int pedirInt(String mensaje) {
        int valor = 0;
        boolean valido = false;
        while (!valido) {
            System.out.print(mensaje + ": ");
            try {
                String input = scanner.nextLine();
                valor = Integer.parseInt(input);
                valido = true;
            } catch (NumberFormatException e) {
                System.out.println("❌ Error: Debes introducir un número válido.");
            }
        }
        return valor;
    }

    public double pedirDouble(String mensaje) {
        double valor = 0;
        boolean valido = false;
        while (!valido) {
            System.out.print(mensaje + ": ");
            try {
                String input = scanner.nextLine();
                // Reemplazamos coma por punto por si el usuario se equivoca
                valor = Double.parseDouble(input.replace(",", "."));
                valido = true;
            } catch (NumberFormatException e) {
                System.out.println("❌ Error: Debes introducir un precio válido (ej: 7.50).");
            }
        }
        return valor;
    }

    // --- ESTA ES LA ÚLTIMA MODIFICACIÓN ---
    public String pedirContrasena(String mensaje) {
        System.out.print(mensaje + ": ");
        
        // Intentamos obtener la consola del sistema (funciona en terminal real/CMD)
        java.io.Console consola = System.console();
        
        if (consola != null) {
            // Si hay consola real, esto oculta lo que escribes y devuelve char[]
            char[] passArray = consola.readPassword();
            return new String(passArray);
        } else {
            // Si estamos en Eclipse/NetBeans, no hay consola real, usamos Scanner
            // (Lamentablemente aquí se verán las letras, es limitación del IDE)
            return scanner.nextLine();
        }
    }
}