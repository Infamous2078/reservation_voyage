package EntiteVoyage.Voyages.Places;

public class EtatReserve implements EtatPlace {
    @Override
    public void handle(boolean changementEtat, Place place) {
        if (changementEtat) {
            place.setEtatCourant(new EtatPaye());
        }
        else {
            place.setEtatCourant(new EtatDisponible());
        }
    }
}