package Utilisateurs.VoletAdmin.MVCAdmin;

import Gestionnaires.GestionnaireVoyages.GestionnaireVoyage;
import Gestionnaires.GestionnairesCompagnies.GestionnaireCompagnie;
import Gestionnaires.GestionnairesTerminaux.GestionnaireTerminale;
import Utilisateurs.VoletAdmin.Commandes.CommandeAdmin;

public class ControleurAdmin {

	private static ControleurAdmin instance;
	private Admin admin;
	private final MenuAdmin vue;

	private ControleurAdmin(MenuAdmin vue) {
		this.vue = vue;
	}

	public static void setInstance(MenuAdmin vue, String mdp) {
		if (instance == null) {
			instance = new ControleurAdmin(vue);
			ControleurAdmin.getInstance().admin = new Admin(mdp);
		}
	}

	public static ControleurAdmin getInstance() {
		return instance;
	}

	public void executerGesVoy() {
		GestionnaireVoyage gestionnaire = vue.requestChoixVoyage();
		CommandeAdmin commandeAdmin = vue.requestChoixCommande();
		admin.gererVoyages(gestionnaire, commandeAdmin);
	}

	public void executerGesTerm() {
		GestionnaireTerminale gestionnaire = vue.requestChoixTerminale();
		CommandeAdmin commandeAdmin = vue.requestChoixCommande();
		admin.gererTerminaux(gestionnaire, commandeAdmin);
	}

	public void executerGesComp() {
		GestionnaireCompagnie gestionnaire = vue.requestChoixCompagnie();
		CommandeAdmin commandeAdmin = vue.requestChoixCommande();
		admin.gererCompagnies(gestionnaire, commandeAdmin);
	}

	public void executerConsultation(GestionnaireVoyage gestionnaireVoyage) {
		gestionnaireVoyage.accepter(admin);
	}

	public boolean checkPasswordCorrectness(String mdp) {
		return mdp.equals(admin.getMotDePasse());
	}

	public void executerUndo() {
		admin.undoOperation();
	}
}