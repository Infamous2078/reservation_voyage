package Gestionnaires.GestionnairesCompagnies;

import EntiteVoyage.Compagnies.CompagnieAerienne;
import Utilisateurs.VoletAdmin.MVCAdmin.MenuAdmin;

import java.util.Scanner;

public class GestionnaireCompAerienne extends GestionnaireCompagnie {

    private static final GestionnaireCompAerienne instance = new GestionnaireCompAerienne();

    public static GestionnaireCompAerienne getInstance() {
        return instance;
    }

    @Override
    public void creer() {
        Scanner scan = MenuAdmin.scan;
        String ID;
        double prix;
        System.out.println("Veuillez entrer l'identifiant de cette nouvelle compagnie");
        while (true) {
            ID = scan.nextLine().toUpperCase();
            if (checkIDExistance(ID)) {
                System.out.println("ID déjà existant");
                continue;
            }
            if (ID.length() <= 6)
                break;
            System.out.println("L'identifiant dois avoir au plus six caractères");
        }
        System.out.println("Veuillez entrez le prix plein tarif des vols de cette nouvelle compagnie");
        while (true) {
            try {
                prix = Double.parseDouble(scan.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Le prix entrer n'est pas un nombre");
            }
        }
        listCompagnie.add(new CompagnieAerienne(ID, prix));
        System.out.println("La compagnie avec identifiant " + ID + " à été créer avec succés");
        notifier();
    }
}