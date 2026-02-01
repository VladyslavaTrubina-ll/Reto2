package controlador;

import java.util.*;

public class ControladorEntradaYSalida {
	private Scanner sc;

	public ControladorEntradaYSalida() {
		this.sc = new Scanner(System.in);
	}

	public int esValorMenuValido(int minimo, int maximo) {

		while (true) {
			System.out.print("Seleccione una opción (" + minimo + "-" + maximo + "): ");

			if (!sc.hasNextLine()) {
				return -1;
			}

			String entrada = sc.nextLine().trim();

			// Salir con 0 si el usuario quiere cancelar (opcional)

			try {
				int opcion = Integer.parseInt(entrada);

				if (opcion >= minimo && opcion <= maximo) {
					return opcion;
				} else if (opcion == 0) {
					System.out.println(
							"Error: 0 non è un'opzione valida. Inserisci un numero tra " + minimo + " e " + maximo);
				} else if (opcion < 0) {
					System.out.println("Error: numero negativo. Inserisci un numero tra " + minimo + " e " + maximo);
				} else {
					System.out.println("Error: La opción debe estar entre " + minimo + " y " + maximo);
				}
			} catch (NumberFormatException e) {
				System.out.println("Error: Debe ingresar un número válido");
			}
		}
	}

	public String leerCadena() {
		String entrada = sc.nextLine().trim();

		if(entrada.isEmpty()) {
			return ("Error: no puedes dejar el campo vacío.");
		 // Llamada recursiva para volver a pedir la entrada
		}
		return entrada;
	}


	public int leerEntero() {
		return sc.nextInt();
	}
	
	public static String letraMalluscula(String texto) {
	    if (texto == null || texto.isEmpty()) {
	        return texto;
	    }

		String[] palabras = texto.trim().toLowerCase().split("\\s+");
	    StringBuilder resultado = new StringBuilder();
	    
		for (String palabra : palabras) {
        resultado.append(Character.toUpperCase(palabra.charAt(0)))
                 .append(palabra.substring(1))
                 .append(" ");
    }
    
    return resultado.toString().trim();
	}

	public int pedirParticipantes(int asientosDisponibles) {
		while (true) {
			System.out.print("Numero de participantes (max " + asientosDisponibles + "): ");

			try {
				String entrada = sc.nextLine().trim();
				int participantes = Integer.parseInt(entrada);

				if (participantes <= 0) {
					System.out.println("Error: Debe ser un número positivo");
				} else if (participantes > asientosDisponibles) {
					System.out.println("Error: Solo hay " + asientosDisponibles + " asientos");
				} else {
					return participantes; // numero valido //
				}
			} catch (NumberFormatException e) {
				System.out.println("Error: Ingrese un número válido");
			}
		}
	}
}