package controlador;

import java.util.ArrayList;
import modelo.Sesion;
import modelo.Pelicula;


public class Imprimir {

	public static void bienvenida(){
		
		System.out.println("=================================");
		System.out.println("	  Bienvenido a cine ");
		System.out.println("   Haz login con tu cuenta para comprar entradas");
		System.out.println("=================================");
	}

	public void imprimirPeliculas(ArrayList<Pelicula> peliculas) {
		System.out.println("\n	Peliculas disponibles");
		System.out.println("-------------------------------------------");

		if (peliculas.isEmpty()) {
			System.out.println("	No hay películas disponibles.");
			return;
    	}
		for (int i = 0; i < peliculas.size(); i++) {
			Pelicula p = peliculas.get(i);
			System.out.printf("[%d] %-20s - %3d min (%s)%n",
					(i + 1), p.getNombre(), p.getDuracion(), p.getGenero());
		}
	}
	public ArrayList<String> imprimirFechas(ArrayList<String> fechas, String titulo) {
	    
	    System.out.println("\nFechas disponibles para: " + titulo);
	    ArrayList<String> fechasUnicos = new ArrayList<String>();
	    ArrayList<Integer> cantidades = new ArrayList<Integer>();
	    
	    for(String f : fechas) {
	        if(fechasUnicos.contains(f)) {
	        	int i = fechasUnicos.indexOf(f);
	        	int n = cantidades.get(i);
	        	cantidades.add(i, n + 1);
	        } else { 
	        	fechasUnicos.add(f);
	        	cantidades.add(1);
	        }
	    }

	    for (int i = 0; i < fechasUnicos.size(); i++) {
	        System.out.printf("[%d] %s - %d sesion(es)%n" , (i + 1), fechasUnicos.get(i), cantidades.get(i));
	    }
	    return fechasUnicos;
	}
	
	public void imprimirSesiones(ArrayList<Sesion> sesiones, String fechaElegida) {
		System.out.println("\nSesiones disponibles para " + fechaElegida);
		System.out.println("-------------------------------------------");
		for (int i = 0; i < sesiones.size(); i++) {
			Sesion s = sesiones.get(i);
			System.out.printf("[%d] %s - %s | Sala: %s | Precio: %.2f€ | Disponibles: %d%n", 
				(i + 1), s.getHoraInicio(), s.getHoraFin(), 
				s.getSala().getNombre(), s.getPrecio(), 
				(s.getSala().getSitios() - s.getNumEspectadores()));
		}
		System.out.println("-------------------------------------------");
	}
}
