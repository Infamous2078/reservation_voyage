package EntiteVoyage.Voyages;

import EntiteVoyage.Compagnies.Compagnie;
import EntiteVoyage.Terminaux.Terminale;
import EntiteVoyage.Voyages.Sections.Section;
import EntiteVoyage.Voyages.Sections.SectionPaquebot;
import Utilisateurs.Visiteur;
import Utilisateurs.VoletClient.MVCClient.Client;

import java.util.LinkedList;

public class Itineraire extends Voyage {

	public Itineraire(String ID, Compagnie compagnie, Terminale terminaleOrigine,
					  Terminale terminaleDestination, String dateDepart, String dateArrivee, String heureDepart,
					  String heureArrivee, LinkedList<Section> listeSection) {
		super(ID, compagnie, terminaleOrigine, terminaleDestination, dateDepart, dateArrivee, heureDepart,
				heureArrivee, listeSection);
	}

	@Override
	public void accepter(Visiteur visiteur) {
		visiteur.consulterItineraire(this);
	}

	public void choisirSectionCabine(Client client, String classeSection) {
		for (Section section : listeSection) {
			if (section.getClasseSection().equals(classeSection)) {
				((SectionPaquebot) section).choisirCabine(client);
				break;
			}
		}
	}
}