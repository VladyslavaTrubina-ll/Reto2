package vista;

import java.util.Scanner;
import modelo.ClienteAcesso;
import java.util.ArrayList;
import controlador.ControladorDB;

public class prueba {

		private static Scanner sc = new Scanner(System.in);

		public static void main(String args[]) {
			ControladorDB controlador = new ControladorDB("cine_daw");
			boolean conexionConExito = controlador.iniciarConexion();
			if (conexionConExito) {
				System.out.println("Se realizó la conexion con exito");
			
			} else {
				System.out.println("No hubo suerte");
			}

			login(controlador);

		}

		public static void login(ControladorDB controlador) {

			System.out.println("inserire email");
			String email = sc.nextLine();
			System.out.println("digitare password");
			String contraseña = sc.nextLine();
			ArrayList<ClienteAcesso> cliente = controlador.obtenercliente(email, contraseña);
			boolean encontrado = false;
			int contador = 0;
			while (contador < cliente.size() && !encontrado) {
				ClienteAcesso c = cliente.get(contador);
				if (email.equals(c.getEmail()) && contraseña.equals(c.getContraseña())) {
					encontrado = true;

				} else {
					contador++;
				}
				if (encontrado) {
					System.out.println("login effettuato");
				} else {
					System.out.println("error");
				}
			}

		}

	}

