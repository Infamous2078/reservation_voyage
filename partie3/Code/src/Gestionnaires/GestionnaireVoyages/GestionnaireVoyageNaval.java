package Gestionnaires.GestionnaireVoyages;

import EntiteVoyage.Compagnies.Compagnie;
import EntiteVoyage.Terminaux.Terminale;
import EntiteVoyage.Voyages.Itineraire;
import EntiteVoyage.Voyages.Places.Cabine;
import EntiteVoyage.Voyages.Places.EtatDisponible;
import EntiteVoyage.Voyages.Sections.Section;
import EntiteVoyage.Voyages.Sections.SectionPaquebot;
import EntiteVoyage.Voyages.Voyage;
import Utilisateurs.VoletAdmin.MVCAdmin.MenuAdmin;
import Gestionnaires.GestionnairesCompagnies.GestionnaireCompCroisiere;
import Gestionnaires.GestionnairesTerminaux.GestionnairePort;
import Utilisateurs.VoletClient.MVCClient.Client;

import java.util.LinkedList;
import java.util.Scanner;

public class GestionnaireVoyageNaval extends GestionnaireVoyage {

    private static final GestionnaireVoyageNaval instance = new GestionnaireVoyageNaval();

    public static GestionnaireVoyageNaval getInstance() {
        return instance;
    }

    @Override
    public void creer() {
        Scanner scan = MenuAdmin.scan;
        Compagnie compagnie;
        System.out.println("Entrez l'identifiant de la compagnie effectue l'itinéraire");
        while (true) {
            String idCompagnie = scan.nextLine().toUpperCase();
            compagnie = GestionnaireCompCroisiere.getInstance().getIdMatch(idCompagnie);
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
        System.out.println("Entrez l'identifiant du port de départ");
        Terminale terminaleOrigine;
        while (true) {
            String idTerminaleOrigine = scan.nextLine().toUpperCase();
            terminaleOrigine = GestionnairePort.getInstance().getIdMatch(idTerminaleOrigine);
            if (terminaleOrigine != null)
                break;
            System.out.println("Identifiant inexistant réassayer de nouveau");
        }
        Terminale terminaleDestination;
        System.out.println("Entrez l'identifiant du port de destination");
        while (true) {
            String idTerminaleDestination = scan.nextLine().toUpperCase();
            terminaleDestination = GestionnairePort.getInstance().getIdMatch(idTerminaleDestination);
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
        LinkedList<Section> listeSection = creerListeSectionPaquebot(compagnie.getPrixPleinTarif());
        Itineraire itineraire = new Itineraire(ID, compagnie, terminaleOrigine, terminaleDestination, dateDepart,
                dateArrivee, heureDepart, heureArrivee, listeSection);
        terminaleOrigine.addVoyagePartant(itineraire);
        terminaleDestination.addVoyageArrivant(itineraire);
        listVoyage.add(itineraire);
        System.out.println("L'itinéraire avec identifiant " + ID + " à été créer avec succés");
        notifier();
    }

    private LinkedList<Section> creerListeSectionPaquebot(double prixPleinTarif) {
        String[] sections = {"I", "O", "S", "F", "D"};
        Scanner scan = MenuAdmin.scan;
        LinkedList<Section> listeSection = new LinkedList<>();
        for (String classeSection : sections) {
            double prixSection = 0;
            switch (classeSection) {
                case "I" -> prixSection = prixPleinTarif * 0.50;
                case "O" -> prixSection = prixPleinTarif * 0.75;
                case "S", "F" -> prixSection = prixPleinTarif * 0.90;
                case "D" -> prixSection = prixPleinTarif;
            }
            int nbrCabine;
            while (true) {
                System.out.println("Veuillez entrez combien de cabine" + classeSection + "la section dispose-t-elle");
                try {
                    nbrCabine = Integer.parseInt(scan.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Veuillez entrer un nombre");
                }
            }
            Cabine[] cabines = creerListeCabine(nbrCabine, classeSection, prixSection);
            listeSection.add(new SectionPaquebot(classeSection, prixSection, cabines));
        }
        return listeSection;
    }

    private Cabine[] creerListeCabine(int nbrCabine, String classeSection, double prix) {
        Cabine[] cabines = new Cabine[nbrCabine];
        for (int i = 0; i < nbrCabine; i++) {
            cabines[i] = new Cabine(classeSection, prix, new EtatDisponible());
        }
        return cabines;
    }

    public void choisirCabineClient(Client client, String classeSection, String idVoyage) {
        for (Voyage voyage : listVoyage) {
            if (voyage.getID().equals(idVoyage)) {
                ((Itineraire) voyage).choisirSectionCabine(client, classeSection);
                break;
            }
        }
    }
}