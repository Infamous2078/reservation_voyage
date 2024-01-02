package EntiteVoyage.Voyages.Places;

public abstract class Place {

	private String section;
	private final double prix;
	private EtatPlace etatCourant;

	public Place(String section, double prix, EtatPlace etatCourant) {
		this.section = section;
		this.prix = prix;
		this.etatCourant = etatCourant;
	}

	public EtatPlace getEtatCourant() {
		return etatCourant;
	}

	public double getPrix() {
		return prix;
	}

	//Si changementEtat est true le changement se fait vers l'avant se fait vers l'avant sinon vers l'arrière
	//Les changement sont: disponible -> reservé -> payé
    public void changerEtat(boolean changementEtat) {
		etatCourant.handle(changementEtat, this);
    }

	public void setEtatCourant(EtatPlace etatCourant) {
		this.etatCourant = etatCourant;
	}


}