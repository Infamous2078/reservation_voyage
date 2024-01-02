package Utilisateurs.VoletClient.Commandes;

import Utilisateurs.VoletClient.MVCClient.Client;

public class InvoquateurClient {
    private CommandeClient commandeClient;

    public void setCommandeClient(CommandeClient commandeClient) {
        this.commandeClient = commandeClient;
    }

    public void effectuerAction(Client client) {
        commandeClient.executer(client);
    }
}
