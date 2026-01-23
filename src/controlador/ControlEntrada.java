package controlador;

import java.text.Normalizer;
import java.util.Scanner;
import vista.Prueba;

public class ControlEntrada {
	
	public static Scanner sc = new Scanner(System.in);
	
	//metodo coger si/no
	public String oncionSiNo() {
		String respuesta;
		do {
			respuesta = sc.nextLine();
			if (!respuesta.equalsIgnoreCase("si") && !respuesta.equalsIgnoreCase("no")) {
				System.out.print("Error, escribe 'si' o 'no': ");
			}
		} while (!respuesta.equalsIgnoreCase("si") && !respuesta.equalsIgnoreCase("no"));
		return respuesta;
	}

	//coger entrada sin acentos, trim, minusculas, colapsando espacios
	public static String textoIgnorCase(String entrada) {
		if (entrada == null) {
			return "";
		}
		String trimmed = entrada.trim();
		String decomposed = Normalizer.normalize(trimmed, Normalizer.Form.NFD);
		String sinAcentos = decomposed.replaceAll("\\p{M}", "");
		String sinEspaciosExtra = sinAcentos.replaceAll("\\s+", " ").trim();
		return sinEspaciosExtra.toLowerCase();
	}
	// controlar si la entrada es un numero integer 0 o positivo
		public static int controlnumeros(boolean siVolverPermitido) {
			boolean numeroValido = false;
			int numero = 0;
			do {
				try {
					String numeroString = sc.nextLine();

					// imprimirStack(numeroString); // debug

					if (siVolverPermitido &&numeroString.equalsIgnoreCase("volver")) {
						// System.out.println("Volviendo al menu anterior...");
						return -1;
					}

					
					numero = Integer.parseInt(numeroString);
				
					if (numero < 0) { 
						String suffix = "";
						if (siVolverPermitido) {
							suffix = " o \"volver\"";
						}
						System.out.print("Error, escribe un numero positivo" + suffix + ": ");
					} else {
						numeroValido = true;
					}
				} catch (NumberFormatException e) {
					String suffix = "";
					if (siVolverPermitido) {
						suffix = " o \"volver\"";
					}

					System.out.print("Error, escribe un numero entero" + suffix + ": ");
				}
			} while (!numeroValido);

			return numero;
		}

		// controlar si la entrada es un numero double
		public static double controlnumerosDouble() {
			boolean numeroValido = false;
			double numero = 0.00;
			do {

				String numeroString = sc.nextLine();
				// imprimirStack(numeroString); // debug

				// 1. para permitir numeros con coma y con punto, convertir coma a punto

				int comaIndex = numeroString.indexOf(',');

				if (comaIndex != -1) {
					numeroString = numeroString.substring(0, comaIndex) + "." + numeroString.substring(comaIndex + 1);
				}

				boolean formatoValido = false;

				// 2. verificar que solo hay dos symbolos despues del punto

				int puntoIndex = numeroString.indexOf('.');

				if (puntoIndex != -1) {
					if (numeroString.length()-1 - puntoIndex > 2) {
						System.out.print("Por favor, escribe un numero con maximo dos decimales: ");
						
					} else {
						formatoValido = true;
					}

				} else {
					formatoValido = true;
				}


				// 3. si el formato es valido, intentar convertir a double

				if (formatoValido) {

					try {

						numero = Double.parseDouble(numeroString);

						if (numero <= 0) { 
							System.out.print("Error, escribe un numero mas de 0.00: ");
						} else {
							numeroValido = true;
						}

					} catch (NumberFormatException e) {

						System.out.print("Error, escribe un numero: ");
					}
					
				}


				
			} while (!numeroValido);

			return numero;
		}

		// confirmar si la entrada es si o no
		public static String confirma() {
			String conf = "";
			do {

				conf = sc.nextLine();
				// imprimirStack(conf); // debug
				if (!conf.equalsIgnoreCase("si") && !conf.equalsIgnoreCase("no"))

					System.out.print("Error, escribe 'si' o 'no': ");
			} while (!conf.equalsIgnoreCase("si") && !conf.equalsIgnoreCase("no"));

			return conf;
		}

		/* verificar si el tipo es valido
		public static boolean tipoValido(String input) {
			// if input in tipos array
			for (String tipo : Inicio.tipos) { 
				if (tipo.equalsIgnoreCase(input)) {
					return true;
				}
			}
			return false;
		}

		// verificar si id de producto en array
		public static boolean productoExiste(int id) {
			for (String[] producto : principal.Inicio.catalogo) {
				if (Integer.parseInt(producto[0]) == id) {
					return true;
				}
			}
			return false;
		}*/

		public static void graciasBienvenida(int segundos) {
			System.out.println("Gracias por su compra!");
			try {
				Thread.sleep(segundos * 1000);
				// Inicio.mostrarBienvenida();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

}
