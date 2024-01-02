package Utilisateurs.VoletClient.Commandes;

import Utilisateurs.VoletClient.MVCClient.MenuClient;
import Utilisateurs.VoletClient.MVCClient.Client;

public class Payer implements CommandeClient{

    @Override
    public void executer(Client client) {
        System.out.println("Veuillez entrez votre numéro de réservation");
        int numReservation;
        while (true) {
            try {
                numReservation = Integer.parseInt(MenuClient.scan.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrez un nombre uniquement");
            }
        }
        client.payerPlaceReserver(numReservation);
    }
}
