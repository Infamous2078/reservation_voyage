package EntiteVoyage.Voyages.Places;

public interface EtatPlace {

	void handle(boolean changementEtat, Place place);

}