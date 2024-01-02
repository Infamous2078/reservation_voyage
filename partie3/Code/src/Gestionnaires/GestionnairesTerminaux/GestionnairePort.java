package Gestionnaires.GestionnairesTerminaux;

import EntiteVoyage.Terminaux.Port;
import Utilisateurs.VoletAdmin.MVCAdmin.MenuAdmin;

import java.util.Scanner;

public class GestionnairePort extends GestionnaireTerminale {

    private static final GestionnairePort instance = new GestionnairePort();

    public static GestionnairePort getInstance() {
        return instance;
    }

    @Override
    public void creer() {
        Scanner scan = MenuAdmin.scan;
        String ID;
        System.out.println("Veuillez entrer l'identifiant de cette gare");
        while (true) {
            ID = scan.nextLine().toUpperCase();
            if (checkIDExistance(ID)) {
                System.out.println("ID déjà existant");
                continue;
            }
            if (ID.length() == 3)
                break;
            System.out.println("L'identifiant dois avoir exactement trois caractères");
        }
        listTerminale.add(new Port(ID));
        System.out.println("Le port avec identifiant " + ID + " été crée avec succés");
        notifier();
    }
}