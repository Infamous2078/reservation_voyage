package Utilisateurs.VoletClient.MVCClient;

import EntiteVoyage.Voyages.Itineraire;
import EntiteVoyage.Voyages.Places.*;
import EntiteVoyage.Voyages.Sections.Section;
import EntiteVoyage.Voyages.Sections.SectionPaquebot;
import EntiteVoyage.Voyages.Sections.SectionSiege;
import EntiteVoyage.Voyages.Trajet;
import EntiteVoyage.Voyages.Vol;
import Utilisateurs.Visiteur;

import java.util.ArrayList;
import java.util.LinkedList;

public class Client implements Visiteur {

    private String nom;
    private String prenom;
    private String email;
    private String mdp;
    private ArrayList<Reservation> reservations;

    public Client(String nom, String prenom, String email, String mdp) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
        reservations = new ArrayList<>();
    }


    @Override
    public void consulterVol(Vol vol) {
        System.out.println(vol.getTerminaleOrigine().getID() + "-" + vol.getTerminaleDestination().getID() +
                ":" + "[" + vol.getCompagnie().getID() + "]" + vol.getID() + "(" + vol.getDateDepart() + ":" +
                vol.getHeureDepart() + "-" + vol.getDateArrivee() + ":" + vol.getHeureArrivee() + ")" +
                (vol.getListeSection() != null ? getSectionsSiegeInfos(vol.getListeSection()) : getSiegeInfos(vol.getSieges())));
    }

    @Override
    public void consulterTrajet(Trajet trajet) {
        System.out.println(trajet.getTerminaleOrigine().getID() + "-" + trajet.getTerminaleDestination().getID() +
                ":" + "[" + trajet.getCompagnie().getID() + "]" + trajet.getID() + "(" + trajet.getDateDepart() + ":" +
                trajet.getHeureDepart() + "-" + trajet.getDateArrivee() + ":" + trajet.getHeureArrivee() + ")" +
                getSectionsSiegeInfos(trajet.getListeSection()));
    }

    @Override
    public void consulterItineraire(Itineraire itineraire) {
        System.out.println(itineraire.getTerminaleOrigine().getID() + "-" + itineraire.getTerminaleDestination().getID() +
                ":" + "[" + itineraire.getCompagnie().getID() + "]" + itineraire.getID() + "(" + itineraire.getDateDepart() + ":" +
                itineraire.getHeureDepart() + "-" + itineraire.getDateArrivee() + ":" + itineraire.getHeureArrivee() + ")" +
                getSectionPaquebotInfos(itineraire.getListeSection()));
    }

    private String getSectionsSiegeInfos(LinkedList<Section> sections) {
        String texte = "";
        for (Section section : sections) {
            Siege[][] sieges = ((SectionSiege) section).getSieges();
            texte += "|" + section.getPrix() + "|";
            int counter = 0;
            for (Siege[] siege : sieges) {
                for (Siege value : siege) {
                    if (value.getEtatCourant() instanceof EtatDisponible) {
                        counter++;
                    }
                }
            }
            texte += section.getClasseSection() + counter;
        }
        return texte;
    }

    private String getSectionPaquebotInfos(LinkedList<Section> sections) {
        String texte = "";
        for (Section section : sections) {
            Cabine[] cabines = ((SectionPaquebot) section).getCabines();
            texte += "|" + section.getPrix() + "|";
            int counter = 0;
            for (Cabine cabine : cabines) {
                if (cabine.getEtatCourant() instanceof EtatDisponible) {
                    counter++;
                }
            }
            texte += section.getClasseSection() + counter;
        }
        return texte;
    }

    private String getSiegeInfos(Siege[][] sieges) {
        int counter = 0;
        for (Siege[] siege : sieges) {
            for (Siege value : siege) {
                if (value.getEtatCourant() instanceof EtatDisponible) {
                    counter++;
                }
            }
        }
        return "|" + sieges[0][0].getPrix() + "|" + counter;
    }

    public String getMdp() {
        return mdp;
    }

    public void creerReservation(Place place) {
        reservations.add(new Reservation(place));
    }

    public void payerPlaceReserver(int numReservation) {
        for (Reservation reservation : reservations) {
            if (numReservation == reservation.getNumReservation()) {
                reservation.confirmer();
            }
            break;
        }
    }
}