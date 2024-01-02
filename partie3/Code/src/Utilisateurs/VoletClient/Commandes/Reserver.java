package Utilisateurs.VoletClient.Commandes;

import Utilisateurs.VoletClient.MVCClient.MenuClient;
import Gestionnaires.GestionnaireVoyages.GestionnaireVoyage;
import Gestionnaires.GestionnaireVoyages.GestionnaireVoyageAerien;
import Gestionnaires.GestionnaireVoyages.GestionnaireVoyageFerroviere;
import Gestionnaires.GestionnaireVoyages.GestionnaireVoyageNaval;
import Utilisateurs.VoletClient.MVCClient.Client;
import Utilisateurs.VoletClient.MVCClient.ControleurClient;

public class Reserver implements CommandeClient {
    @Override
    public void executer(Client client) {
        GestionnaireVoyage gestionnaireVoyage = ControleurClient.getInstance().getChoixVoyageReservation();
        System.out.println("Veuillez entrer le numéro du voyage que vous voulez réserver");
        String idVoyage = MenuClient.scan.nextLine().toUpperCase();
        System.out.println("Veuillez entrez la classe de la section désirée si il y a lieu sinon laisser vide");
        String classeSection = MenuClient.scan.nextLine().toUpperCase();
        if (!(gestionnaireVoyage instanceof GestionnaireVoyageNaval)) {
            System.out.println("Voulez vous un siége coté aile? Répondez par oui ou non");
            String choixFenetre;
            do {
                choixFenetre = MenuClient.scan.nextLine();
            } while (!choixFenetre.equals("non") && !choixFenetre.equals("oui"));
            System.out.println("Voulez vous un siége coté fenêtre? Répondez par oui ou non");
            String choixAile;
            do {
                choixAile = MenuClient.scan.nextLine();
            } while (!choixAile.equals("non") && !choixAile.equals("oui"));

            boolean coteFenetre = choixFenetre.equals("oui");
            boolean coteAile = choixAile.equals("oui");

            if (gestionnaireVoyage instanceof GestionnaireVoyageAerien)
                ((GestionnaireVoyageAerien) gestionnaireVoyage).choisirSiegeClient(client, classeSection, idVoyage, coteFenetre, coteAile);
            else
                ((GestionnaireVoyageFerroviere) gestionnaireVoyage).choisirSiegeClient(client, classeSection, idVoyage, coteFenetre, coteAile);

        }
        else {
            ((GestionnaireVoyageNaval) gestionnaireVoyage).choisirCabineClient(client, classeSection, idVoyage);
        }
    }
}
