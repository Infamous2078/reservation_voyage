package Utilisateurs.VoletClient.MVCClient;

import EntiteVoyage.Voyages.Places.EtatReserve;
import EntiteVoyage.Voyages.Places.Place;

public class Reservation {

	private final int numReservation;
	private final Place place;

	public Reservation(Place place) {
		this.place = place;
		numReservation = genererNumReservation();
		System.out.println("Votre numéro de réservation est le " + numReservation);
	}

	public int genererNumReservation() {
		return (int) (Math.random() * 1000000);
	}

	public void confirmer() {
		if (place.getEtatCourant() instanceof EtatReserve) {
			place.changerEtat(true);
			System.out.println("Entrez le numéro de votre carte de crédit");
			MenuClient.scan.nextLine();
			System.out.println("Le paiement de la place a bien été effectuer");
		}
	}

	public int getNumReservation() {
		return numReservation;
	}
}