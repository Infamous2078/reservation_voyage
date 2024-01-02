package Utilisateurs.VoletClient.MVCClient;

import Gestionnaires.GestionnaireVoyages.GestionnaireVoyage;
import Gestionnaires.GestionnairesTerminaux.GestionnaireTerminale;
import Utilisateurs.VoletClient.Commandes.Consulter;
import Utilisateurs.VoletClient.Commandes.InvoquateurClient;
import Utilisateurs.VoletClient.Commandes.Payer;
import Utilisateurs.VoletClient.Commandes.Reserver;

import java.util.ArrayList;

public class ControleurClient {

	private static ControleurClient instance;
	private final MenuClient vue;
	private Client client;
	private final ArrayList<Client> clients;
	private final InvoquateurClient invoquateurClient;

	public ControleurClient(MenuClient vue) {
		this.vue = vue;
		invoquateurClient = new InvoquateurClient();
		clients = new ArrayList<>();
	}

	public static ControleurClient getInstance() {
		return instance;
	}

	public static void setInstance(MenuClient vue) {
		if (instance == null) {
			instance = new ControleurClient(vue);
		}
	}

	public boolean connecterClient(String mdp) {
		for (Client client : clients) {
			if (client.getMdp().equals(mdp)) {
				this.client = client;
				return true;
			}
		}
		return false;
	}

	public void creerCompteClient(String nom, String prenom, String email, String mdp) {
		Client client = new Client(nom, prenom, email, mdp);
		this.client = client;
		clients.add(client);
	}

	public void executerConsultation() {
		invoquateurClient.setCommandeClient(new Consulter());
		invoquateurClient.effectuerAction(client);
	}

	public void executerReservation() {
		invoquateurClient.setCommandeClient(new Reserver());
		invoquateurClient.effectuerAction(client);
	}

	public void executerPaiement() {
		invoquateurClient.setCommandeClient(new Payer());
		invoquateurClient.effectuerAction(client);

	}

	public GestionnaireTerminale getChoixVoyageConsultation() {
		return vue.requestChoixVoyageConsultation();
	}

	public GestionnaireVoyage getChoixVoyageReservation() {
		return vue.requestChoixVoyageReservation();
	}
}