package vista;

import java.util.ArrayList;

import modelo.GestorMascotas;
import modelo.Perro;

public class Vista {

	public static void nuevoPerro(ArrayList<Perro> p) {
		Perro perro = new Perro(1, "rugus","pomeranian",false);
		p.add(perro);
	}

	public static void main(String[] args) {
		GestorMascotas gestor = new GestorMascotas();
		gestor.mostrarMascotasTipoIndicado("perro");
		nuevoPerro(gestor.getPerros());
		gestor.mostrarMascotasTipoIndicado("perro");
	}
}
