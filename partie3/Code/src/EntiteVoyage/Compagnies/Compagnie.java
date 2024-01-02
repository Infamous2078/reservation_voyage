package EntiteVoyage.Compagnies;

public abstract class Compagnie {

	protected String ID;
	protected double prixPleinTarif;

	public Compagnie(String ID, double prixPleinTarif) {
		this.ID = ID;
		this.prixPleinTarif = prixPleinTarif;
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public void setPrixPleinTarif(double prixPleinTarif) {
		this.prixPleinTarif = prixPleinTarif;
	}

	public double getPrixPleinTarif() {
		return prixPleinTarif;
	}


}