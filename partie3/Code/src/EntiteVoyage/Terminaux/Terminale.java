package EntiteVoyage.Terminaux;

import EntiteVoyage.Voyages.Ivisitable;
import EntiteVoyage.Voyages.Voyage;
import Utilisateurs.Visiteur;

import java.util.LinkedList;

public abstract class Terminale {

	private String ID;
	private final LinkedList<Voyage> listVoyageArrivant = new LinkedList<>();
	private final LinkedList<Voyage> listVoyagePartant = new LinkedList<>();

	public Terminale(String ID) {
		this.ID = ID;
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public void addVoyageArrivant(Voyage voyage) {
		listVoyageArrivant.add(voyage);
	}

	public void addVoyagePartant(Voyage voyage) {
		listVoyagePartant.add(voyage);
	}

	public void accepter(Visiteur visiteur, String idTerminaleDestination) {
		for (Voyage voyage : listVoyagePartant) {
			if(voyage.getTerminaleDestination().getID().equals(idTerminaleDestination))
				voyage.accepter(visiteur);
		}
	}
}