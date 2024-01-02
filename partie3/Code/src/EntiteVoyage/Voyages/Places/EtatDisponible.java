package EntiteVoyage.Voyages.Places;

public class EtatDisponible implements EtatPlace {
    @Override
    public void handle(boolean changementEtat, Place place) {
        if (changementEtat) {
            place.setEtatCourant(new EtatReserve());
        }
    }
}