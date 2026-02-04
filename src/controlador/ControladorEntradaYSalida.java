package controlador;

import java.util.*;

public class ControladorEntradaYSalida {
	private Scanner sc;

	public ControladorEntradaYSalida() {
		this.sc = new Scanner(System.in);
	}

	public int esValorMenuValido(int minimo, int maximo) {

		while (true) {
			System.out.print("\nElige una opción (" + minimo + " - " + maximo + "): ");

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

	public String leerCadena(String text) {

		boolean valido = false;
		String entradaValida = "";
		while (!valido) {
			System.out.println("\n------------------------------------------");
			System.out.print(text);
			String entrada = sc.nextLine().trim();
			System.out.println("------------------------------------------");

			if (entrada.isEmpty()) {
				System.out.println("Error: no puedes dejar el campo vacío.");

			} else {
				valido = true;
				entradaValida = entrada;
			}
		}
		return entradaValida;
	}

	public String leerSiNo(String text) {

		boolean valido = false;
		String entradaValida = "";
		System.out.println("");
		while (!valido) {
			System.out.print(text + " (si/no): ");
			String entrada = sc.nextLine().trim();

			if (!entrada.equalsIgnoreCase("no") && !entrada.equalsIgnoreCase("si")) {
				System.out.println("Error: escribe si o no"); // Respuesta no válida. Por favor, responde 'si' o 'no'.

			} else {
				valido = true;
				entradaValida = entrada;
			}
		}
		return entradaValida;
	}

	public int leerEntero() {
		return sc.nextInt();
	}

	public int leerOpciones(String text, ArrayList<Integer> opciones) {
		boolean valido = false;
		int entradaValida = 0;
		while (!valido) {
			System.out.print(text);
			int entrada = sc.nextInt();

			if (!opciones.contains(entrada)) {
				System.out.println("Error: opción no existe");

			} else {
				valido = true;
				entradaValida = entrada;
			}
		}
		return entradaValida;
	}

	public static String letraMalluscula(String texto) {
		if (texto == null || texto.isEmpty()) {
			return texto;
		}

		String[] palabras = texto.trim().toLowerCase().split("\\s+");
		StringBuilder resultado = new StringBuilder();

		for (String palabra : palabras) {
			resultado.append(Character.toUpperCase(palabra.charAt(0))).append(palabra.substring(1)).append(" ");
		}

		return resultado.toString().trim();
	}

	public int numBilletesComprandos(int asientosDisponibles) {
		while (true) {
			System.out.println("\nHay " + asientosDisponibles + " billetes disponibles");
			System.out.print("Cuántas billetes quieres comprar: ");
			try {
				String entrada = sc.nextLine().trim();
				int billetes = Integer.parseInt(entrada);

				if (billetes <= 0) {
					System.out.println("Error: Debe ser un número positivo");
				} else if (billetes > asientosDisponibles) {
					System.out.println("Error: Solo hay " + asientosDisponibles + " asientos");
				} else {
					return billetes; // numero valido //
				}
			} catch (NumberFormatException e) {
				System.out.println("Error: Ingrese un número válido");
			}
		}
	}

	public double leerNumeroDouble() {
		while (true) {
			try {
				String entrada = sc.nextLine().trim();
				double numero = Double.parseDouble(entrada);

				if (numero < 0) {
					System.out.println("Error: Debe ser un número positivo");
				} else {
					return numero;
				}
			} catch (NumberFormatException e) {
				System.out.println("Error: Ingrese un número válido");
			}
		}
	}

	public String leerDNI(String text) {
		String nie = "";
		boolean valido = false;

		System.out.println(text); // Mostra il messaggio all'utente

		while (!valido) {
			System.out.print("Escribe tu  DNI: ");
			nie = sc.nextLine();

			if (nie.length() == 9) {
				valido = true;
			
			} else {
				System.out.println("ERROR: EL DNI tiene que ser de 9 caracteres!");
				System.out.println("Has escrito " + nie.length() + " caracteres");
			}
		}

		return nie;
	}
}