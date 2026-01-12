package modelo;

public class Tortuga {
	int idCollar;
	String especie;
	boolean aguaDulceONo;

	public Tortuga() {

	}

	public Tortuga(int idCollar, String especie, boolean aguaDulceONo) {
		this.idCollar = idCollar;
		this.especie = especie;
		this.aguaDulceONo = aguaDulceONo;
	}

	public int getIdCollar() {
		return idCollar;
	}

	public void setIdCollar(int idCollar) {
		this.idCollar = idCollar;
	}

	public String getEspecie() {
		return especie;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	public boolean isAguaDulceONo() {
		return aguaDulceONo;
	}

	public void setAguaDulceONo(boolean aguaDulceONo) {
		this.aguaDulceONo = aguaDulceONo;
	}

}
