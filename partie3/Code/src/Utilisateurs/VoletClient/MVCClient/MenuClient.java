package Utilisateurs.VoletClient.MVCClient;

import Gestionnaires.GestionnaireVoyages.GestionnaireVoyage;
import Gestionnaires.GestionnaireVoyages.GestionnaireVoyageAerien;
import Gestionnaires.GestionnaireVoyages.GestionnaireVoyageFerroviere;
import Gestionnaires.GestionnaireVoyages.GestionnaireVoyageNaval;
import Gestionnaires.GestionnairesCompagnies.GestionnaireCompAerienne;
import Gestionnaires.GestionnairesCompagnies.GestionnaireCompCroisiere;
import Gestionnaires.GestionnairesCompagnies.GestionnaireLigneDeTrain;
import Gestionnaires.GestionnairesTerminaux.GestionnaireAeroport;
import Gestionnaires.GestionnairesTerminaux.GestionnaireGare;
import Gestionnaires.GestionnairesTerminaux.GestionnairePort;
import Gestionnaires.GestionnairesTerminaux.GestionnaireTerminale;
import Utilisateurs.Observateur;
import Utilisateurs.VoletClient.MVCClient.ControleurClient;

import java.util.Scanner;

public class MenuClient implements Observateur {

    public static Scanner scan;
    private boolean isConsulting;

    public MenuClient() {
        this.isConsulting = false;
        attachAllObervers();
    }

    @Override
    public void update() {
        if (isConsulting) {
            System.out.println("Une mise à jour dois être effectuer car une modification sur entités afficher à été détecter");
            ControleurClient.getInstance().executerConsultation();
        }
    }

    @Override
    public void attachAllObervers() {
        GestionnaireVoyageAerien.getInstance().attacher(this);
        GestionnaireVoyageNaval.getInstance().attacher(this);
        GestionnaireVoyageFerroviere.getInstance().attacher(this);
        GestionnaireAeroport.getInstance().attacher(this);
        GestionnaireGare.getInstance().attacher(this);
        GestionnairePort.getInstance().attacher(this);
        GestionnaireCompAerienne.getInstance().attacher(this);
        GestionnaireCompCroisiere.getInstance().attacher(this);
        GestionnaireLigneDeTrain.getInstance().attacher(this);
    }

    public void ouvrirMenu() {
        ControleurClient.setInstance(this);
        ControleurClient remote = ControleurClient.getInstance();
        System.out.println("""
                Que voulez vous faire, entrez une des options suivantes
                1. Se connecter
                2. Créer un compte""");
        String isNew;
        do {
            System.out.println("Veuillez entrer 1 ou 2");
            isNew = scan.nextLine();
        } while (!isNew.equals("1") && !isNew.equals("2"));

        if (isNew.equals("1")) {
            String mdp = scan.nextLine();
            boolean correcteMdp = remote.connecterClient(mdp);
            if (!correcteMdp) {
                System.out.println("Le mot de passe entrez n'est associé à aucun compte, vous êtes renvoyez au menu principale");
                return;
            }
        }
        else {
            System.out.println("Veuillez entrez les informations suivantes");
            System.out.println("Nom:");
            String nom = scan.nextLine();
            System.out.println("Prenom:");
            String prenom = scan.nextLine();
            System.out.println("Email:");
            String email = scan.nextLine();
            System.out.println("Mot de passe:");
            String mdp = scan.nextLine();
            ControleurClient.getInstance().creerCompteClient(nom, prenom, email, mdp);
        }
        boolean printChoix = true;
        while (true) {
            if (printChoix) {
                System.out.println(
                        """
                                Choisissez une opération à effectuer, pour cela entrez un chiffre entre 1 et 4 pour sélectionner une des options suivantes:
                                1. Consultation
                                2. Réservation
                                3. Paiement
                                4. Revenir au menu principale""");
            }
            String choix = scan.nextLine();
            switch (choix) {
                case "1" -> {
                    isConsulting = true;
                    remote.executerConsultation();
                    printChoix = true;
                    isConsulting = false;
                }
                case "2" -> {
                    remote.executerReservation();
                    printChoix = true;
                }
                case "3" -> {
                    remote.executerPaiement();
                    printChoix = true;
                }
                case "4" -> {
                    return;
                }
                default -> {
                    System.out.println("Veuillez entrer uniquement un chiffre entre 1 et 4 svp");
                    printChoix = false;
                }
            }
        }
    }

    public GestionnaireTerminale requestChoixVoyageConsultation() {
        System.out.println(
                """
                        Choisissez un type de voyage, pour cela entrez un chiffre entre 1 et 3 pour sélectionner une des options suivantes:
                        1. Voyage par avion
                        2. Voyage par bateau
                        3. Voyage par train""");
        while (true) {
            String choix = scan.nextLine();
            switch (choix) {
                case "1" -> {
                    return GestionnaireAeroport.getInstance();
                }
                case "2" -> {
                    return GestionnairePort.getInstance();
                }
                case "3" -> {
                    return GestionnaireGare.getInstance();
                }
                default -> System.out.println("Veuillez entrer uniquement un chiffre entre 1 et 3 svp");
            }
        }
    }

    public GestionnaireVoyage requestChoixVoyageReservation() {
        System.out.println(
                """
                        Choisissez un type de voyage, pour cela entrez un chiffre entre 1 et 3 pour sélectionner une des options suivantes:
                        1. Voyage par avion
                        2. Voyage par bateau
                        3. Voyage par train""");
        while (true) {
            String choix = scan.nextLine();
            switch (choix) {
                case "1" -> {
                    return GestionnaireVoyageAerien.getInstance();
                }
                case "2" -> {
                    return GestionnaireVoyageNaval.getInstance();
                }
                case "3" -> {
                    return GestionnaireVoyageFerroviere.getInstance();
                }
                default -> System.out.println("Veuillez entrer uniquement un chiffre entre 1 et 3 svp");
            }
        }
    }
}