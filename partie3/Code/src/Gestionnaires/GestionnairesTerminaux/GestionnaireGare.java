package Gestionnaires.GestionnairesTerminaux;

import EntiteVoyage.Terminaux.Gare;
import Utilisateurs.VoletAdmin.MVCAdmin.MenuAdmin;

import java.util.Scanner;

public class GestionnaireGare extends GestionnaireTerminale {

    private static final GestionnaireGare instance = new GestionnaireGare();

    public static GestionnaireGare getInstance() {
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
        listTerminale.add(new Gare(ID));
        System.out.println("La gare avec identifiant " + ID + " été crée avec succés");
        notifier();
    }

}