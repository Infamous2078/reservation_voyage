package Gestionnaires.GestionnairesCompagnies;

import EntiteVoyage.Compagnies.Compagnie;
import Utilisateurs.VoletAdmin.MVCAdmin.MenuAdmin;
import Utilisateurs.Observateur;
import Gestionnaires.Gestionnaire;
import Utilisateurs.VoletAdmin.Commandes.Memento;

import java.util.LinkedList;
import java.util.Scanner;

public abstract class GestionnaireCompagnie implements Gestionnaire {

	protected LinkedList<Compagnie> listCompagnie = new LinkedList<>();
	protected LinkedList<Observateur> observateurs = new LinkedList<>();

	@Override
	public void supprimer(String ID) {
		listCompagnie.removeIf(compagnie -> compagnie.getID().equals(ID));
		System.out.println("La compagnie " + ID + " a été supprimer avec succés");
		notifier();
	}

	public LinkedList<Compagnie> getList() {
		return listCompagnie;
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
		memento.setEtat(this, listCompagnie);
		return memento;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void restore(Memento m) {
		listCompagnie = (LinkedList<Compagnie>) m.getEtat();
	}


	@Override
	public boolean checkIDExistance(String id) {
		for (Compagnie compagnie : listCompagnie) {
			if (compagnie.getID().equals(id))
				return true;
		}
		return false;
	}

	public Compagnie getIdMatch(String ID) {
		for (Compagnie compagnie : listCompagnie) {
			if (compagnie.getID().equals(ID))
				return compagnie;
		}
		return null;
	}


	@Override
	public void modifier(String ID) {
		Compagnie compagnieModifier = null;
		for (Compagnie compagnie: listCompagnie) {
			if (compagnie.getID().equals(ID)) {
				compagnieModifier = compagnie;
				break;
			}
			System.out.println("Identifiant inexistant");
			return;
		}
		Scanner scan = MenuAdmin.scan;
		System.out.println("Veuillez entrez le nouvel identifiant de la compagnie");
		String id = scan.nextLine();
		System.out.println("Veuillez entrez le nouveau prix plein tarif");
		String prix = scan.nextLine();

		if (!id.equals(""))
			compagnieModifier.setID(id);
		if (!prix.equals("")) {
			compagnieModifier.setPrixPleinTarif(Double.parseDouble(prix));
		}
		notifier();
	}
}