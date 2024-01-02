package Gestionnaires.GestionnaireVoyages;

import EntiteVoyage.Compagnies.Compagnie;
import EntiteVoyage.Terminaux.Terminale;
import EntiteVoyage.Voyages.Places.EtatDisponible;
import EntiteVoyage.Voyages.Sections.Section;
import EntiteVoyage.Voyages.Sections.SectionSiege;
import EntiteVoyage.Voyages.Places.Siege;
import EntiteVoyage.Voyages.Trajet;
import EntiteVoyage.Voyages.Voyage;
import Utilisateurs.VoletAdmin.MVCAdmin.MenuAdmin;
import Gestionnaires.GestionnairesCompagnies.GestionnaireLigneDeTrain;
import Gestionnaires.GestionnairesTerminaux.GestionnaireGare;
import Utilisateurs.VoletClient.MVCClient.Client;

import java.util.LinkedList;
import java.util.Scanner;

public class GestionnaireVoyageFerroviere extends GestionnaireVoyage {

    private static final GestionnaireVoyageFerroviere instance = new GestionnaireVoyageFerroviere();

    public static GestionnaireVoyageFerroviere getInstance() {
        return instance;
    }

    @Override
    public void creer() {
        Scanner scan = MenuAdmin.scan;
        Compagnie compagnie;
        System.out.println("Entrez l'identifiant de la ligne de train qui effectue le trajet");
        while (true) {
            String idCompagnie = scan.nextLine().toUpperCase();
            compagnie = GestionnaireLigneDeTrain.getInstance().getIdMatch(idCompagnie);
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
        System.out.println("Entrez l'identifiant de la gare de départ");
        Terminale terminaleOrigine;
        while (true) {
            String idTerminaleOrigine = scan.nextLine().toUpperCase();
            terminaleOrigine = GestionnaireGare.getInstance().getIdMatch(idTerminaleOrigine);
            if (terminaleOrigine != null)
                break;
            System.out.println("Identifiant inexistant réassayer de nouveau");
        }

        Terminale terminaleDestination;
        System.out.println("Entrez l'identifiant de la gare de destination");
        while (true) {
            String idTerminaleDestination = scan.nextLine().toUpperCase();
            terminaleDestination = GestionnaireGare.getInstance().getIdMatch(idTerminaleDestination);
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
        System.out.println("Entrez les gares que dois visiter le trajet");
        LinkedList<Terminale> listeGaresVisiter = new LinkedList<>();
        while (true) {
            System.out.println("Entrez l'identifiant de la gare");
            String id = scan.nextLine();
            for (Terminale terminale : GestionnaireGare.getInstance().getList()) {
                if (id.equals(terminale.getID()))
                    listeGaresVisiter.add(terminale);
            }
            System.out.println("Voulez vous ajouter une autre gare?");
            String reponse;
            do {
                System.out.println("Voulez vous ajoutez une autre section?, répondez par oui ou non");
                reponse = scan.nextLine();
            } while (!reponse.equalsIgnoreCase("oui") && !reponse.equalsIgnoreCase("non"));
            if (reponse.equals("non"))
                break;
        }
        LinkedList<Section> listeSection = creerListeSectionTrain(compagnie.getPrixPleinTarif());
        Trajet trajet = new Trajet(ID, compagnie, terminaleOrigine, terminaleDestination, dateDepart, dateArrivee,
                heureDepart, heureArrivee, listeSection, listeGaresVisiter);
        terminaleOrigine.addVoyagePartant(trajet);
        terminaleDestination.addVoyageArrivant(trajet);
        listVoyage.add(trajet);
        System.out.println("Le trajet avec identifiant " + ID + " à été créer avec succés");
        notifier();
    }


    private LinkedList<Section> creerListeSectionTrain(double prixPleinTarif) {
        String[] sections = {"P", "E"};
        Scanner scan = MenuAdmin.scan;
        LinkedList<Section> listeSection = new LinkedList<>();
        for (String classeSection : sections) {
            double prixSection = 0;
            switch (classeSection) {
                case "P" -> prixSection = prixPleinTarif * 0.60;
                case "E" -> prixSection = prixPleinTarif * 0.50;
            }
            int nbrRangee = 0;
            do {
                System.out.println("Veuillez entrez combien de rangée chaque section dispose-t-elle, un nombre entre 1 et 100 uniquement");
                try {
                    nbrRangee = Integer.parseInt(scan.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Veuillez entrer un nombre");
                }
            } while (nbrRangee <= 0 || nbrRangee > 100);
            Siege[][] sieges = creerListeSieges(nbrRangee, classeSection, prixSection);
            listeSection.add(new SectionSiege(classeSection, prixSection, "S", sieges));
        }
        return listeSection;
    }

    private Siege[][] creerListeSieges(int nbrRangee, String classeSection, double prix) {
        Siege[][] sieges = new Siege[nbrRangee][3];
        for (int numRangee = 1; numRangee <= sieges.length; numRangee++) {
            for (int numColonne = 1; numColonne <= sieges[0].length; numColonne++) {
                sieges[numRangee - 1][numColonne - 1] = new Siege(classeSection, prix, new EtatDisponible(), numRangee,
                        (char) (numColonne + 40), numColonne == 1 || numColonne == 3, numColonne == 1 || numColonne == 2);
            }
        }
        return sieges;
    }

    public void choisirSiegeClient(Client client, String classeSection, String idVoyage, boolean coteFenetre, boolean coteAile) {
        for (Voyage voyage : listVoyage) {
            if (voyage.getID().equals(idVoyage)) {
                ((Trajet) voyage).choisirSectionSiege(client, classeSection, coteFenetre, coteAile);
                break;
            }
        }
    }
}