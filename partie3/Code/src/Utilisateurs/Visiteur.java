package Utilisateurs;

import EntiteVoyage.Voyages.*;

public interface Visiteur {

	void consulterVol(Vol vol);

	void consulterTrajet(Trajet trajet);

	void consulterItineraire(Itineraire itineraire);

}