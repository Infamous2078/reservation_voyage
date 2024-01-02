package Utilisateurs.VoletAdmin.Commandes;

import Gestionnaires.*;

import java.util.Stack;

public class InvoquateurAdmin {

	private CommandeAdmin commandeAdmin;
	private final Stack<Memento> etats = new Stack<>();

	public void setCommande(CommandeAdmin commandeAdmin) {
		this.commandeAdmin = commandeAdmin;
	}

	public void gererEntite(Gestionnaire gestionnaire) {
		Memento m = gestionnaire.establish();
		addMemento(m);
		commandeAdmin.executer(gestionnaire);
	}

	private void addMemento(Memento m) {
		etats.push(m);
	}

	private Memento getMemento() {
		return etats.pop();
	}

	public void executerUndo() {
		Memento etatPrecedent = getMemento();
		Gestionnaire dataModifier = etatPrecedent.getDataModifier();
		dataModifier.restore(etatPrecedent);
	}
}