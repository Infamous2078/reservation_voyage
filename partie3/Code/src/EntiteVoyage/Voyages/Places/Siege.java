package EntiteVoyage.Voyages.Places;

public class Siege extends Place {

	private final int ranger;
	private final char colonne;
	private final boolean coteFenetre;
	private final boolean coteAile;

	public Siege(String section, double prix, EtatPlace etatCourant, int ranger, char colonne, boolean coteFenetre, boolean coteAile) {
		super(section, prix, etatCourant);
		this.ranger = ranger;
		this.colonne = colonne;
		this.coteFenetre = coteFenetre;
		this.coteAile = coteAile;
	}

	public boolean isCoteFenetre() {
		return coteFenetre;
	}

	public boolean isCoteAile() {
		return coteAile;
	}
}