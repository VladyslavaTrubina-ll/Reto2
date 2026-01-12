package modelo;

import java.util.ArrayList;

public class GestorMascotas {

	private ArrayList<Perro> perros;
	private ArrayList<Gato> gatos;
	private ArrayList<Tortuga> tortugas;

	public GestorMascotas() {
		perros = new ArrayList<Perro>();
		gatos = new ArrayList<Gato>();
		tortugas = new ArrayList<Tortuga>();
	}

	public GestorMascotas(ArrayList<Perro> p, ArrayList<Gato> g, ArrayList<Tortuga> t) {
		perros = p;
		gatos = g;
		tortugas = t;
	}

	public ArrayList<Perro> getPerros() {
		return perros;
	}

	public void setPerros(ArrayList<Perro> perros) {
		this.perros = perros;
	}

	public ArrayList<Gato> getGatos() {
		return gatos;
	}

	public void setGatos(ArrayList<Gato> gatos) {
		this.gatos = gatos;
	}

	public ArrayList<Tortuga> getTortugas() {
		return tortugas;
	}

	public void setTortugas(ArrayList<Tortuga> tortugas) {
		this.tortugas = tortugas;
	}

	public void nuevoPerro(Perro p) {
		perros.add(p);

	}

	public void nuevoGato(Gato g) {
		gatos.add(g);
	}

	public void nuevaTortuga(Tortuga t) {
		tortugas.add(t);
	}

	public void mostrarMascotas() {
		System.out.println("Perros");
		for (int i = 0; i < perros.size(); i++) {
			System.out.println(perros.get(i));
		}
		System.out.println("Gatos");
		for (int i = 0; i < gatos.size(); i++) {
			System.out.println(gatos.get(i));
		}
		System.out.println("Tortugas");
		for (int i = 0; i < tortugas.size(); i++) {
			System.out.println(tortugas.get(i));
		}
	}

	public void mostrarMascotasTipoIndicado(String tipo) {
		if (tipo.equalsIgnoreCase("tortuga")) {
			System.out.println("Tortugas");
			for (int i = 0; i < tortugas.size(); i++) {
				System.out.println(tortugas.get(i));
			}
		} else if (tipo.equalsIgnoreCase("perro")) {
			System.out.println("Perros");
			for (int i = 0; i < perros.size(); i++) {
				System.out.println(perros.get(i).getNombre());
			}
		} else {
			System.out.println("Gatos");
			for (int i = 0; i < gatos.size(); i++) {
				System.out.println(gatos.get(i));
			}
		}
	}
}
