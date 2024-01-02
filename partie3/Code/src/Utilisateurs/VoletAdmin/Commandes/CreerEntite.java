package Utilisateurs.VoletAdmin.Commandes;

import Gestionnaires.Gestionnaire;

public class CreerEntite implements CommandeAdmin {


	public void executer(Gestionnaire gestionnaire) {
		gestionnaire.creer();
	}
}