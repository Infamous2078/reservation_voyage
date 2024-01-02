package Gestionnaires.GestionnairesTerminaux;

import EntiteVoyage.Terminaux.Terminale;
import Utilisateurs.VoletAdmin.MVCAdmin.MenuAdmin;
import Utilisateurs.Observateur;
import Gestionnaires.Gestionnaire;
import Utilisateurs.VoletAdmin.Commandes.Memento;
import Utilisateurs.VoletClient.MVCClient.Client;

import java.util.LinkedList;
import java.util.Scanner;

public abstract class GestionnaireTerminale implements Gestionnaire {

	protected LinkedList<Terminale> listTerminale = new LinkedList<>();
	protected LinkedList<Observateur> observateurs = new LinkedList<>();

	public void supprimer(String ID) {
		listTerminale.removeIf(terminale -> terminale.getID().equals(ID));
		System.out.println("Le terminale " + ID + " a été supprimer avec succés");
		notifier();

	}

	public LinkedList<Terminale> getList() {
		return listTerminale;
	}

	@Override
	public boolean checkIDExistance(String id) {
		for (Terminale aeroport : listTerminale) {
			if (aeroport.getID().equals(id))
				return true;
		}
		return false;
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
		memento.setEtat(this, listTerminale);
		return memento;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void restore(Memento m) {
		listTerminale = (LinkedList<Terminale>) m.getEtat();
	}

	public Terminale getIdMatch(String ID) {
		for (Terminale terminale : listTerminale) {
			if (terminale.getID().equals(ID))
				return terminale;
		}
		return null;
	}

	@Override
	public void modifier(String ID) {
		Terminale portModifier = null;
		for (Terminale terminale : listTerminale) {
			if (terminale.getID().equals(ID)) {
				portModifier = terminale;
				break;
			}
			System.out.println("Identifiant inexistant");
			return;
		}
		Scanner scan = MenuAdmin.scan;
		System.out.println("Veuillez entrez le nouvel identifiant du terminale");
		String newID;
		while (true) {
			newID = scan.nextLine();
			if (newID.length() <= 6)
				break;
			System.out.println("L'identifiant dois avoir au plus six caractères");
		}
		if (!newID.equals(""))
			portModifier.setID(newID.toUpperCase());
		notifier();
	}

	public void consulterVolsClient(String idTerminaleDepart, String idTerminaleArrivee, Client client) {
		for (Terminale terminale : listTerminale) {
			if (terminale.getID().equals(idTerminaleDepart)) {
				terminale.accepter(client, idTerminaleArrivee);
				break;
			}
		}

	}
}