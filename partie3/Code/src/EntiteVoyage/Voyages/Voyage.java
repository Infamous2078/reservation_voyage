package EntiteVoyage.Voyages;

import EntiteVoyage.Compagnies.Compagnie;
import EntiteVoyage.Terminaux.Terminale;
import EntiteVoyage.Voyages.Sections.Section;

import java.util.LinkedList;

public abstract class Voyage implements Ivisitable {

	private String ID;
	private Compagnie compagnie;
	private Terminale terminaleOrigine;
	private Terminale terminaleDestination;
	private String dateDepart;
	private String dateArrivee;
	private String heureDepart;
	private String heureArrivee;
	protected LinkedList<Section> listeSection;


	public Voyage(String ID, Compagnie compagnie, Terminale terminaleOrigine, Terminale terminaleDestination,
				  String dateDepart, String dateArrivee, String heureDepart, String heureArrivee,
				  LinkedList<Section> listeSection) {
		this.ID = ID;
		this.compagnie = compagnie;
		this.terminaleOrigine = terminaleOrigine;
		this.terminaleDestination = terminaleDestination;
		this.dateDepart = dateDepart;
		this.dateArrivee = dateArrivee;
		this.heureDepart = heureDepart;
		this.heureArrivee = heureArrivee;
		this.listeSection = listeSection;
	}

	public String getID() {
		return ID;
	}


	public Compagnie getCompagnie() {
		return compagnie;
	}

	public Terminale getTerminaleOrigine() {
		return terminaleOrigine;
	}

	public Terminale getTerminaleDestination() {
		return terminaleDestination;
	}

	public String getDateDepart() {
		return dateDepart;
	}

	public String getDateArrivee() {
		return dateArrivee;
	}

	public String getHeureDepart() {
		return heureDepart;
	}

	public String getHeureArrivee() {
		return heureArrivee;
	}

	public LinkedList<Section> getListeSection() {
		return listeSection;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public void setCompagnie(Compagnie compagnie) {
		this.compagnie = compagnie;
	}

	public void setTerminaleOrigine(Terminale terminaleOrigine) {
		this.terminaleOrigine = terminaleOrigine;
	}

	public void setTerminaleDestination(Terminale terminaleDestination) {
		this.terminaleDestination = terminaleDestination;
	}

	public void setDateDepart(String dateDepart) {
		this.dateDepart = dateDepart;
	}

	public void setDateArrivee(String dateArrivee) {
		this.dateArrivee = dateArrivee;
	}

	public void setHeureDepart(String heureDepart) {
		this.heureDepart = heureDepart;
	}

	public void setHeureArrivee(String heureArrivee) {
		this.heureArrivee = heureArrivee;
	}
}