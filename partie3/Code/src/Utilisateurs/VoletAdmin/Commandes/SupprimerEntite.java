package Utilisateurs.VoletAdmin.Commandes;

import Utilisateurs.VoletAdmin.MVCAdmin.MenuAdmin;
import Gestionnaires.*;

public class SupprimerEntite implements CommandeAdmin {

	public void executer(Gestionnaire gestionnaire) {
		System.out.println("Veuillez entrez l'indentifiant d'entité que vous souhaitez supprimer");
		String ID = MenuAdmin.scan.nextLine();
		gestionnaire.supprimer(ID);
	}

}