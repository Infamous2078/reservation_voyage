package Gestionnaires.GestionnairesCompagnies;

import EntiteVoyage.Compagnies.CompagnieCroisiere;
import Utilisateurs.VoletAdmin.MVCAdmin.MenuAdmin;

import java.util.Scanner;

public class GestionnaireCompCroisiere extends GestionnaireCompagnie {

    private static final GestionnaireCompCroisiere instance = new GestionnaireCompCroisiere();

    public static GestionnaireCompCroisiere getInstance() {
        return instance;
    }

    @Override
    public void creer() {
        Scanner scan = MenuAdmin.scan;
        String ID;
        String prixS;
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
            prixS = scan.nextLine();
            try {
                prix = Double.parseDouble(prixS);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Le prix entrer n'est pas un nombre");
            }
        }
        listCompagnie.add(new CompagnieCroisiere(ID, prix));
        System.out.println("La compagnie avec identifiant " + ID + " a été créer avec succés");
        notifier();
    }
}