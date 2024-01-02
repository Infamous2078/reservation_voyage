package Utilisateurs.VoletClient.Commandes;

import Utilisateurs.VoletClient.MVCClient.MenuClient;
import Gestionnaires.GestionnairesTerminaux.GestionnaireTerminale;
import Utilisateurs.VoletClient.MVCClient.Client;
import Utilisateurs.VoletClient.MVCClient.ControleurClient;

public class Consulter implements CommandeClient{
    @Override
    public void executer(Client client) {
        GestionnaireTerminale gestionnaireTerminale = ControleurClient.getInstance().getChoixVoyageConsultation();
        System.out.println("Veuillez entrez le nom du terminale de départ");
        String idTerminaleDepart = MenuClient.scan.nextLine().toUpperCase();
        System.out.println("Veuillez entrez le nom du terminale de destination");
        String idTerminaleArrivee = MenuClient.scan.nextLine().toUpperCase();
        gestionnaireTerminale.consulterVolsClient(idTerminaleDepart, idTerminaleArrivee, client);
    }
}
