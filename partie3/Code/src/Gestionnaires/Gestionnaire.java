package Gestionnaires;

import Utilisateurs.VoletAdmin.Commandes.Memento;

import java.util.LinkedList;

public interface Gestionnaire extends Sujet {

	void creer();

	void supprimer(String ID);

	void modifier(String ID);

	Memento establish();

	void restore(Memento m);

	boolean checkIDExistance(String id);

	LinkedList<?> getList();

}