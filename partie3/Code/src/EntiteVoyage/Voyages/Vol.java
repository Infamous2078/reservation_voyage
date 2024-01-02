package EntiteVoyage.Voyages;

import EntiteVoyage.Compagnies.Compagnie;
import EntiteVoyage.Terminaux.Terminale;
import EntiteVoyage.Voyages.Sections.Section;
import EntiteVoyage.Voyages.Sections.SectionSiege;
import EntiteVoyage.Voyages.Places.Siege;
import Utilisateurs.Visiteur;
import Utilisateurs.VoletClient.MVCClient.Client;

import java.util.LinkedList;

public class Vol extends Voyage {

	private final Siege[][] sieges;

	public Vol(String ID, Compagnie compagnie, Terminale terminaleOrigine, Terminale terminaleDestination,
			   String dateDepart, String dateArrivee, String heureDepart, String heureArrivee,
			   LinkedList<Section> listeSection, Siege[][] sieges) {
		super(ID, compagnie, terminaleOrigine, terminaleDestination, dateDepart, dateArrivee, heureDepart,
				heureArrivee, listeSection);
		this.sieges = sieges;
	}

	@Override
	public void accepter(Visiteur visiteur) {
		visiteur.consulterVol(this);
	}

	public Siege[][] getSieges() {
		return sieges;
	}

	public void choisirSectionSiege(Client client, String classeSection, boolean coteFenetre, boolean coteAile) {
		for (Section section : listeSection) {
			if (section.getClasseSection().equals(classeSection)) {
				((SectionSiege) section).choisirSiege(client, coteFenetre, coteAile);
				return;
			}
		}
		System.out.println("La section demander n'est pas disponible");
	}
}