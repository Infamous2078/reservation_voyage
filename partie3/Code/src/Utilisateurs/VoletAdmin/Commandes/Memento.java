package Utilisateurs.VoletAdmin.Commandes;

import Gestionnaires.Gestionnaire;
import java.util.LinkedList;

public class Memento {

	private LinkedList<?> etat;
	private Gestionnaire dataModifier;

	public LinkedList<?> getEtat() {
		return this.etat;
	}

	public Gestionnaire getDataModifier() {
		return this.dataModifier;
	}

	public void setEtat(Gestionnaire gestionnaire, LinkedList<?> list) {
		this.etat = (LinkedList<?>) list.clone();
		this.dataModifier = gestionnaire;
	}

}