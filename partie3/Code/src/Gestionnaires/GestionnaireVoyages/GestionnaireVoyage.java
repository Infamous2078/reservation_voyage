package Gestionnaires.GestionnaireVoyages;

import EntiteVoyage.Compagnies.Compagnie;
import EntiteVoyage.Terminaux.Terminale;
import EntiteVoyage.Voyages.Ivisitable;
import EntiteVoyage.Voyages.Voyage;
import Utilisateurs.VoletAdmin.MVCAdmin.MenuAdmin;
import Utilisateurs.Observateur;
import Gestionnaires.Gestionnaire;
import Gestionnaires.GestionnairesCompagnies.GestionnaireCompCroisiere;
import Gestionnaires.GestionnairesTerminaux.GestionnaireGare;
import Utilisateurs.Visiteur;
import Utilisateurs.VoletAdmin.Commandes.Memento;

import java.util.LinkedList;
import java.util.Scanner;

public abstract class GestionnaireVoyage implements Gestionnaire, Ivisitable {

	protected LinkedList<Voyage> listVoyage = new LinkedList<>();
	protected final LinkedList<Observateur> observateurs = new LinkedList<>();

	public void supprimer(String ID) {
		listVoyage.removeIf(voyage -> voyage.getID().equals(ID));
		System.out.println("Le voyage " + ID + " a été supprimer avec succés");
		notifier();
	}

	public LinkedList<Voyage> getList() {
		return listVoyage;
	}

	@Override
	public void attacher(Observateur obs) {
		observateurs.add(obs);
	}

	@Override
	public void detacher(Observateur obs) {
		observateurs.remove(obs);
	}

	@Override
	public void notifier() {
		for (Observateur obs : observateurs)
			obs.update();
	}

	@Override
	public Memento establish() {
		Memento memento = new Memento();
		memento.setEtat(this, listVoyage);
		return memento;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void restore(Memento m) {
		listVoyage = (LinkedList<Voyage>) m.getEtat();
	}

	@Override
	public void accepter(Visiteur visiteur) {
		for (Voyage voyage : listVoyage) {
			voyage.accepter(visiteur);
		}
	}

	@Override
	public boolean checkIDExistance(String id) {
		for (Voyage voyage : listVoyage) {
			if (voyage.getID().equals(id))
				return true;
		}
		return false;
	}

	@Override
	public void modifier(String ID) {
		Voyage voyageModifier = null;
		for (Voyage voyage : listVoyage) {
			if (voyage.getID().equals(ID)) {
				voyageModifier = voyage;
				break;
			}
			System.out.println("Identifiant inexistant");
			return;
		}
		Scanner scan = MenuAdmin.scan;
		System.out.println("Veuillez entrez la nouvelle valeur à tour de rôle pour chacun des attribut, laissez vide si vous ne souhaitez pas le modifier");
		System.out.println("Entrez l'identifiant de la nouvelle compagnie qui dois gérer le voyage");
		Compagnie compagnie = null;
		while (true) {
			String idCompagnie = scan.nextLine();
			if (idCompagnie.equals("")) break;
			compagnie = GestionnaireCompCroisiere.getInstance().getIdMatch(idCompagnie);
			if (compagnie != null)
				break;
			System.out.println("Identifiant inexistant réassayer de nouveau");
		}
		System.out.println("Entrez l'identifiant du nouvel terminale de départ");
		Terminale terminaleOrigine = null;
		while (true) {
			String idTerminaleOrigine = scan.nextLine();
			if (idTerminaleOrigine.equals("")) break;
			terminaleOrigine = GestionnaireGare.getInstance().getIdMatch(idTerminaleOrigine);
			if (terminaleOrigine != null)
				break;
			System.out.println("Identifiant inexistant réassayer de nouveau");
		}
		System.out.println("Entrez l'identifiant du nouvel terminale de destination");
		Terminale terminaleDestination = null;
		while (true) {
			String idTerminaleDestination = scan.nextLine();
			if (idTerminaleDestination.equals("")) break;
			terminaleDestination = GestionnaireGare.getInstance().getIdMatch(idTerminaleDestination);
			if (terminaleDestination != null)
				break;
			System.out.println("Identifiant inexistant réassayer de nouveau");
		}
		System.out.println("Entrez la date de départ suivant le format AAAA.MM.JJ");
		String dateDepart = scan.nextLine();
		System.out.println("Entrez la date d'arrivée suivant le format AAAA.MM.JJ");
		String dateArrivee = scan.nextLine();
		System.out.println("Entrez l'heure de départ suivant le format HH.MM");
		String heureDepart = scan.nextLine();
		System.out.println("Entrez l'heure d'arrivée suivant le format HH.MM");
		String heureArrivee = scan.nextLine();


		if (compagnie != null)
			voyageModifier.setCompagnie(compagnie);
		if (terminaleOrigine != null)
			voyageModifier.setTerminaleOrigine(terminaleOrigine);
		if (terminaleDestination != null)
			voyageModifier.setTerminaleDestination(terminaleDestination);
		if (dateDepart.equals(""))
			voyageModifier.setDateDepart(dateDepart);
		if (!dateArrivee.equals(""))
			voyageModifier.setDateArrivee(dateArrivee);
		if (!heureDepart.equals(""))
			voyageModifier.setHeureDepart(heureDepart);
		if (!heureArrivee.equals(""))
			voyageModifier.setHeureArrivee(heureArrivee);
		notifier();
	}
}