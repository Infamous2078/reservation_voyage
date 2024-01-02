package EntiteVoyage.Voyages.Sections;

import EntiteVoyage.Voyages.Places.Cabine;
import EntiteVoyage.Voyages.Places.EtatDisponible;
import Utilisateurs.VoletClient.MVCClient.Client;

public class SectionPaquebot extends Section{

    private Cabine[] cabines;

    public SectionPaquebot(String classeSection, double prix, Cabine[] cabines) {
        super(classeSection, prix);
        this.cabines = cabines;
    }

    public Cabine[] getCabines() {
        return cabines;
    }

    public void choisirCabine(Client client) {
        for (Cabine cabine : cabines) {
            if (cabine.getEtatCourant() instanceof EtatDisponible) {
                cabine.changerEtat(true);
                client.creerReservation(cabine);
                break;
            }
        }
    }
}
