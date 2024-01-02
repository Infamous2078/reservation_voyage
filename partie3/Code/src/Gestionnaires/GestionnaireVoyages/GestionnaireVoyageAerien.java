package Gestionnaires.GestionnaireVoyages;

import EntiteVoyage.Compagnies.Compagnie;
import EntiteVoyage.Terminaux.Terminale;
import EntiteVoyage.Voyages.Places.*;
import EntiteVoyage.Voyages.Sections.Section;
import EntiteVoyage.Voyages.Sections.SectionSiege;
import EntiteVoyage.Voyages.Vol;
import EntiteVoyage.Voyages.Voyage;
import Utilisateurs.VoletAdmin.MVCAdmin.MenuAdmin;
import Gestionnaires.GestionnairesCompagnies.GestionnaireCompAerienne;
import Gestionnaires.GestionnairesTerminaux.GestionnaireAeroport;
import Utilisateurs.VoletClient.MVCClient.Client;

import java.util.LinkedList;
import java.util.Scanner;

public class GestionnaireVoyageAerien extends GestionnaireVoyage {

    private static final GestionnaireVoyageAerien instance = new GestionnaireVoyageAerien();

    public static GestionnaireVoyageAerien getInstance() {
        return instance;
    }

    @Override
    public void creer() {
        Scanner scan = MenuAdmin.scan;
        Compagnie compagnie;
        System.out.println("Entrez l'identifiant de la compagnie qui dois gérer le vol");
        while (true) {
            String idCompagnie = scan.nextLine().toUpperCase();
            compagnie = GestionnaireCompAerienne.getInstance().getIdMatch(idCompagnie);
            if (compagnie != null)
                break;
            System.out.println("Identifiant inexistant réassayer de nouveau");
        }
        String ID;
        System.out.println("Veuillez entrer l'identifiant de nouveau voyage");
        while (true) {
            ID = scan.nextLine().toUpperCase();
            if (checkIDExistance(ID)) {
                System.out.println("Identifiant déjà existant");
                continue;
            }
            if (!compagnie.getID().substring(0, 2).equals(ID.substring(0, 2))) {
                System.out.println("Les deux premieres lettre de l'identifiant doivent etre les deux premiére lettre de l'identifiant de la compagnie");
                continue;
            }
            if (ID.length() <= 6)
                break;
            System.out.println("L'identifiant dois avoir au plus six caractères");
        }
        System.out.println("Entrez l'identifiant de l'aéroport de départ");
        Terminale terminaleOrigine;
        while (true) {
            String idTerminaleOrigine = scan.nextLine().toUpperCase();
            terminaleOrigine = GestionnaireAeroport.getInstance().getIdMatch(idTerminaleOrigine);
            if (terminaleOrigine != null)
                break;
            System.out.println("Identifiant inexistant réassayer de nouveau");
        }

        Terminale terminaleDestination;
        System.out.println("Entrez l'identifiant de l'aéroport de destination");
        while (true) {
            String idTerminaleDestination = scan.nextLine().toUpperCase();
            terminaleDestination = GestionnaireAeroport.getInstance().getIdMatch(idTerminaleDestination);
            if (terminaleDestination != null)
                break;
            System.out.println("Identifiant inexistant réassayer de nouveau");
        }
        System.out.println("Entrez la date de départ suivant le format AAAA.MM.JJ");
        String dateDepart = scan.nextLine();
        System.out.println("Entrez la date d'arrivée suivant le format AAAA.MM.JJ");
        String dateArrivee = scan.nextLine();
        System.out.println("Entrez l'heure de départ suivant le format HH.MM");
        String heureDepart = scan.nextLine();
        System.out.println("Entrez l'heure d'arrivée suivant le format HH.MM");
        String heureArrivee = scan.nextLine();
        String choixCreationSection;
        do {
            System.out.println("Voulez vous crée des sections, répondez par oui ou non");
            choixCreationSection = scan.nextLine();
        } while (!choixCreationSection.equalsIgnoreCase("oui") && !choixCreationSection.equalsIgnoreCase("non"));
        LinkedList<Section> listeSection = null;
        Siege[][] listeSiege = null;
        if (choixCreationSection.equals("oui"))
            listeSection = creerListeSectionAvion(compagnie.getPrixPleinTarif());
        else {
            System.out.println("Aucune section ne sera crée, veuillez entrer les informations suivantes sur les sièges:");
            int nbrRangee = 0;
            do {
                System.out.println("Veuillez entrez combien de rangée de siége, un nombre entre 1 et 100 uniquement");
                try {
                    nbrRangee = Integer.parseInt(scan.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Veuillez entrer un nombre");
                }
            } while (nbrRangee <= 0 || nbrRangee > 100);
            System.out.println("""
                    Veuillez entrez la disposition des siéges selon quatre possibilités:
                    Étroit (S) : trois colonnes avec une aile entre les colonnes 1 et 2
                    Confort (C) : quatre colonnes avec une aile entre les colonnes 2 et 3
                    Moyen (M) : six colonnes avec une aile entre 3 et 4
                    Large (L) : dix colonnes avec une aile entre les colonnes 3 et 4, et entre 7 et 8""");
            String disposition;
            int nbrColonne;
            while (true) {
                disposition = scan.nextLine();
                switch (disposition) {
                    case "S" -> nbrColonne = 3;
                    case "C" -> nbrColonne = 4;
                    case "M" -> nbrColonne = 6;
                    case "L" -> nbrColonne = 10;
                    default -> {
                        System.out.println("Mauvaise disposition, réessayez");
                        continue;
                    }
                }
                break;
            }
            listeSiege = creerListeSieges(nbrRangee, nbrColonne, null, compagnie.getPrixPleinTarif());
        }
        Vol vol = new Vol(ID, compagnie, terminaleOrigine, terminaleDestination, dateDepart, dateArrivee,
                heureDepart, heureArrivee, listeSection, listeSiege);
        listVoyage.add(vol);
        terminaleOrigine.addVoyagePartant(vol);
        terminaleDestination.addVoyageArrivant(vol);
        System.out.println("Le vol avec identifiant " + ID + " à été créer avec succés");
        notifier();
    }

    private LinkedList<Section> creerListeSectionAvion(double prixPleinTarif) {
        Scanner scan = MenuAdmin.scan;
        LinkedList<Section> listeSection = new LinkedList<>();
        while (true) {
            System.out.println("""
                    Veuillez entrez la classe de la nouvelle section, selon quatre possibilité:
                    F: Première
                    A: Affaire
                    P: Économique premium
                    E: Économique""");
            String classeSection;
            double prixSection;
            while (true) {
                classeSection = scan.nextLine();
                switch (classeSection) {
                    case "F" -> prixSection = prixPleinTarif;
                    case "A" -> prixSection = prixPleinTarif * 0.75;
                    case "P" -> prixSection = prixPleinTarif * 0.60;
                    case "E" -> prixSection = prixPleinTarif * 0.50;
                    default -> {
                        System.out.println("Mauvaise classe de section, réessayez");
                        continue;
                    }
                }
                break;
            }
            int nbrRangee = 0;
            do {
                System.out.println("Veuillez entrez combien de rangée de siége la section dispose-t-elle, un nombre entre 1 et 100 uniquement");
                try {
                    nbrRangee = Integer.parseInt(scan.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Veuillez entrer un nombre");
                }
            } while (nbrRangee <= 0 || nbrRangee > 100);
            System.out.println("""
                    Veuillez entrez la disposition des siéges dans la section selon quatre possibilités:
                    Étroit (S) : trois colonnes avec une aile entre les colonnes 1 et 2
                    Confort (C) : quatre colonnes avec une aile entre les colonnes 2 et 3
                    Moyen (M) : six colonnes avec une aile entre 3 et 4
                    Large (L) : dix colonnes avec une aile entre les colonnes 3 et 4, et entre 7 et 8""");
            String disposition;
            int nbrColonne;
            while (true) {
                disposition = scan.nextLine();
                switch (disposition) {
                    case "S" -> nbrColonne = 3;
                    case "C" -> nbrColonne = 4;
                    case "M" -> nbrColonne = 6;
                    case "L" -> nbrColonne = 10;
                    default -> {
                        System.out.println("Mauvaise disposition, réessayez");
                        continue;
                    }
                }
                break;
            }
            Siege[][] sieges = creerListeSieges(nbrRangee, nbrColonne, classeSection, prixSection);
            listeSection.add(new SectionSiege(classeSection, prixSection, disposition, sieges));
            String reponse;
            do {
                System.out.println("Voulez vous ajoutez une autre section?, répondez par oui ou non");
                reponse = scan.nextLine();
            } while (!reponse.equalsIgnoreCase("oui") && !reponse.equalsIgnoreCase("non"));
            if (reponse.equals("non"))
                return listeSection;
        }
    }

    private Siege[][] creerListeSieges(int nbrRangee, int nbrColonne, String classeSection, double prix) {
        Siege[][] sieges = new Siege[nbrRangee][nbrColonne];
        switch (nbrColonne) {
            case 3 -> {
                for (int numRangee = 1; numRangee <= sieges.length; numRangee++) {
                    for (int numColonne = 1; numColonne <= sieges[0].length; numColonne++) {
                        sieges[numRangee - 1][numColonne - 1] = new Siege(classeSection, prix, new EtatDisponible(), numRangee,
                                (char) (numColonne + 40), numColonne == 1 || numColonne == 3, numColonne == 1 || numColonne == 2);
                    }
                }
            }
            case 4 -> {
                for (int numRangee = 1; numRangee <= sieges.length; numRangee++) {
                    for (int numColonne = 1; numColonne <= sieges[0].length; numColonne++) {
                        sieges[numRangee - 1][numColonne - 1] = new Siege(classeSection, prix, new EtatDisponible(), numRangee,
                                (char) (numColonne + 40), numColonne == 1 || numColonne == 4, numColonne == 2 || numColonne == 3);
                    }
                }
            }
            case 6 -> {
                for (int numRangee = 1; numRangee <= sieges.length; numRangee++) {
                    for (int numColonne = 1; numColonne <= sieges[0].length; numColonne++) {
                        sieges[numRangee - 1][numColonne - 1] = new Siege(classeSection, prix, new EtatDisponible(), numRangee,
                                (char) (numColonne + 40), numColonne == 1 || numColonne == 6, numColonne == 3 || numColonne == 4);
                    }
                }
            }
            case 10 -> {
                for (int numRangee = 1; numRangee <= sieges.length; numRangee++) {
                    for (int numColonne = 1; numColonne <= sieges[0].length; numColonne++) {
                        sieges[numRangee - 1][numColonne - 1] = new Siege(classeSection, prix, new EtatDisponible(), numRangee,
                                (char) (numColonne + 40), numColonne == 1 || numColonne == 10, numColonne == 3 || numColonne == 4 ||
                                numColonne == 7 || numColonne == 8);
                    }
                }
            }
        }
        return sieges;
    }

    public void choisirSiegeClient(Client client, String classeSection, String idVoyage, boolean coteFenetre, boolean coteAile) {
        for (Voyage voyage : listVoyage) {
            if (voyage.getID().equals(idVoyage)) {
                ((Vol) voyage).choisirSectionSiege(client, classeSection, coteFenetre, coteAile);
                break;
            }
        }
    }
}