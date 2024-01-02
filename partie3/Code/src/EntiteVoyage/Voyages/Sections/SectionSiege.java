package EntiteVoyage.Voyages.Sections;

import EntiteVoyage.Voyages.Places.EtatDisponible;
import EntiteVoyage.Voyages.Places.Siege;
import Utilisateurs.VoletClient.MVCClient.Client;

public class SectionSiege extends Section {

    private final String dispositionSieges;
    private final Siege[][] listSieges;

    public SectionSiege(String classeSection, double prix, String dispositionSieges, Siege[][] sieges) {
        super(classeSection, prix);
        this.dispositionSieges = dispositionSieges;
        this.listSieges = sieges;
    }

    public String getDispositionSieges() {
        return dispositionSieges;
    }

    public Siege[][] getSieges() {
        return listSieges;
    }

    public void choisirSiege(Client client, boolean coteFenetre, boolean coteAile) {
        for (Siege[] sieges : listSieges) {
            for (Siege siege : sieges) {
                if (siege.getEtatCourant() instanceof EtatDisponible &&
                        siege.isCoteAile() == coteAile && siege.isCoteFenetre() == coteFenetre) {
                    siege.changerEtat(true);
                    client.creerReservation(siege);
                    return;
                }
            }
        }
        System.out.println("La configuration de siége demander n'est pas disponible");
    }
}