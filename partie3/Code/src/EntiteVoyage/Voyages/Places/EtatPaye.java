package EntiteVoyage.Voyages.Places;

public class EtatPaye implements EtatPlace {
    @Override
    public void handle(boolean changementEtat, Place place) {
        if (changementEtat) {
            place.setEtatCourant(new EtatDisponible());
        }
    }
}