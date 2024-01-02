package EntiteVoyage.Voyages;

import EntiteVoyage.Compagnies.Compagnie;
import EntiteVoyage.Terminaux.*;
import EntiteVoyage.Voyages.Sections.Section;
import EntiteVoyage.Voyages.Sections.SectionSiege;
import Utilisateurs.Visiteur;
import Utilisateurs.VoletClient.MVCClient.Client;

import java.util.LinkedList;

public class Trajet extends Voyage {

	private LinkedList<Terminale> listGareVisite;

	public Trajet(String ID, Compagnie compagnie, Terminale terminaleOrigine, Terminale terminaleDestination,
                  String dateDepart, String dateArrivee, String heureDepart, String heureArrivee,
                  LinkedList<Section> listeSection, LinkedList<Terminale> listGareVisite) {
		super(ID, compagnie, terminaleOrigine, terminaleDestination, dateDepart, dateArrivee,
				heureDepart, heureArrivee, listeSection);
		this.listGareVisite = listGareVisite;
	}

	@Override
	public void accepter(Visiteur visiteur) {
		visiteur.consulterTrajet(this);
	}

	public void choisirSectionSiege(Client client, String classeSection, boolean coteFenetre, boolean coteAile) {
		for (Section section : listeSection) {
			if (section.getClasseSection().equals(classeSection)) {
				((SectionSiege) section).choisirSiege(client, coteFenetre, coteAile);
				break;
			}
		}
	}
}