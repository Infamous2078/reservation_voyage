package Utilisateurs.VoletAdmin.Commandes;

import Utilisateurs.VoletAdmin.MVCAdmin.MenuAdmin;
import Gestionnaires.*;

public class ModifierEntitee implements CommandeAdmin {

	public void executer(Gestionnaire gestionnaire) {
		System.out.println("Veuillez entrez l'identifiant de l'entité que vous souhaitez modifier");
		String ID = MenuAdmin.scan.nextLine();
		gestionnaire.modifier(ID);
	}

}