package Utilisateurs.VoletAdmin.MVCAdmin;

import Utilisateurs.Observateur;
import Gestionnaires.GestionnaireVoyages.GestionnaireVoyage;
import Gestionnaires.GestionnaireVoyages.GestionnaireVoyageAerien;
import Gestionnaires.GestionnaireVoyages.GestionnaireVoyageFerroviere;
import Gestionnaires.GestionnaireVoyages.GestionnaireVoyageNaval;
import Gestionnaires.GestionnairesCompagnies.GestionnaireCompAerienne;
import Gestionnaires.GestionnairesCompagnies.GestionnaireCompCroisiere;
import Gestionnaires.GestionnairesCompagnies.GestionnaireCompagnie;
import Gestionnaires.GestionnairesCompagnies.GestionnaireLigneDeTrain;
import Gestionnaires.GestionnairesTerminaux.GestionnaireAeroport;
import Gestionnaires.GestionnairesTerminaux.GestionnaireGare;
import Gestionnaires.GestionnairesTerminaux.GestionnairePort;
import Gestionnaires.GestionnairesTerminaux.GestionnaireTerminale;
import Utilisateurs.VoletAdmin.Commandes.CommandeAdmin;
import Utilisateurs.VoletAdmin.Commandes.CreerEntite;
import Utilisateurs.VoletAdmin.Commandes.ModifierEntitee;
import Utilisateurs.VoletAdmin.Commandes.SupprimerEntite;
import Utilisateurs.VoletClient.MVCClient.ControleurClient;

import java.util.Scanner;

public class MenuAdmin implements Observateur {

    public static Scanner scan;
    private boolean isConsulting;

    public MenuAdmin() {
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
        System.out.println("Veuillez entrez votre mot de passe svp, ou crée en un si c'est votre premiére connexion:");
        String mdp = scan.nextLine();
        ControleurAdmin.setInstance(this, mdp);
        ControleurAdmin remote = ControleurAdmin.getInstance();
        boolean isCorrect = remote.checkPasswordCorrectness(mdp);
        if (!isCorrect) {
            System.out.println("Mot de passe incorrect vous êtes renvoyer au menu principale");
            return;
        }
        boolean printChoix = true;
        while (true) {
            if (printChoix) {
                System.out.println(
                        """
                                Choisissez une entité à gérer, pour cela entrez un chiffre entre 1 et 6 pour sélectionner une des options suivantes:
                                1. Gestion de terminaux
                                2. Gestion de compagnies
                                3. Gestion de voyages
                                4. Consultation de voyages
                                5. Annuler l'opération précédement effectuer
                                6. Revenir au menu pricipale""");
            }
            String choix = scan.nextLine();
            switch (choix) {
                case "1" -> {
                    remote.executerGesTerm();
                    printChoix = true;
                }
                case "2" -> {
                    remote.executerGesComp();
                    printChoix = true;
                }
                case "3" -> {
                    remote.executerGesVoy();
                    printChoix = true;
                }
                case "4" -> {
                    isConsulting = true;
                    requestChoixConsultation();
                    printChoix = true;
                    isConsulting = false;
                }
                case "5" -> {
                    remote.executerUndo();
                    printChoix = true;
                }
                case "6" -> {
                    return;
                }
                default -> {
                    System.out.println("Veuillez entrer uniquement un chiffre entre 1 et 3 svp");
                    printChoix = false;
                }
            }
        }
    }


    public GestionnaireTerminale requestChoixTerminale() {
        System.out.println(
                """
                        Choisissez quel type de terminaux vous voulez gérer, pour cela entrez un chiffre entre 1 et 3 pour sélectionner une des options suivantes:
                        1. Gestion d'aéroports
                        2. Gestion de ports
                        3. Gestion de gares""");
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

    public GestionnaireVoyage requestChoixVoyage() {
        System.out.println(
                """
                        Choisissez quel type de voyage vous voulez gérer ou consulter, pour cela entrez un chiffre entre 1 et 3 pour sélectionner une des options suivantes:
                        1. Gestion de voyage par avion
                        2. Gestion de voyage par bateau
                        3. Gestion de voyage par train""");
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

    public GestionnaireCompagnie requestChoixCompagnie() {
        System.out.println(
                """
                        Choisissez quel type de compagnie vous voulez gérer, pour cela entrez un chiffre entre 1 et 3 pour sélectionner une des options suivantes:
                        1. Gestion de compagnie aérienne
                        2. Gestion de compagnie de croisère
                        3. Gestion de ligne de train""");
        while (true) {
            String choix = scan.nextLine();
            switch (choix) {
                case "1" -> {
                    return GestionnaireCompAerienne.getInstance();
                }
                case "2" -> {
                    return GestionnaireCompCroisiere.getInstance();
                }
                case "3" -> {
                    return GestionnaireLigneDeTrain.getInstance();
                }
                default -> System.out.println("Veuillez entrer uniquement un chiffre entre 1 et 3 svp");
            }
        }
    }

    public CommandeAdmin requestChoixCommande() {
        System.out.println(
                """
                        Choisissez une opération à effectuer, pour cela entrez un chiffre entre 1 et 3 pour sélectionner une des options suivantes:
                        1. Création
                        2. Supression
                        3. Modification""");
        while (true) {
            String choix = scan.nextLine();
            switch (choix) {
                case "1" -> {
                    return new CreerEntite();
                }
                case "2" -> {
                    return new SupprimerEntite();
                }
                case "3" -> {
                    return new ModifierEntitee();
                }
                default -> System.out.println("Veuillez entrer uniquement un chiffre entre 1 et 3 svp");
            }
        }
    }

    private void requestChoixConsultation() {
        ControleurAdmin remote = ControleurAdmin.getInstance();
        System.out.println(
                """
                        Choisissez quel type de voyage vous voulez consulter, pour cela entrez un chiffre entre 1 et 3 pour sélectionner une des options suivantes:
                        1. Consultation des vols par avion
                        2. Consultation des itinéraire par bateau
                        3. Consultation des trajets par train""");
        loop:
        while (true) {
            String choix = scan.nextLine();
            switch (choix) {
                case "1" -> {
                    remote.executerConsultation(GestionnaireVoyageAerien.getInstance());
                    break loop;
                }
                case "2" -> {
                    remote.executerConsultation(GestionnaireVoyageNaval.getInstance());
                    break loop;
                }
                case "3" -> {
                    remote.executerConsultation(GestionnaireVoyageFerroviere.getInstance());
                    break loop;
                }
                default -> System.out.println("Veuillez entrer uniquement un chiffre entre 1 et 3 svp");
            }
        }
    }
}