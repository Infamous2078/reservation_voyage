package Gestionnaires;

import Utilisateurs.Observateur;

public interface Sujet {

	void attacher(Observateur obs);

	void detacher(Observateur obs);

	void notifier();

}