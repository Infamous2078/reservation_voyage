package Utilisateurs.VoletAdmin.MVCAdmin;

import EntiteVoyage.Voyages.Itineraire;
import EntiteVoyage.Voyages.Places.*;
import EntiteVoyage.Voyages.Sections.Section;
import EntiteVoyage.Voyages.Sections.SectionPaquebot;
import EntiteVoyage.Voyages.Sections.SectionSiege;
import EntiteVoyage.Voyages.Trajet;
import EntiteVoyage.Voyages.Vol;
import Gestionnaires.GestionnaireVoyages.GestionnaireVoyage;
import Gestionnaires.GestionnairesCompagnies.GestionnaireCompagnie;
import Gestionnaires.GestionnairesTerminaux.GestionnaireTerminale;
import Utilisateurs.Visiteur;
import Utilisateurs.VoletAdmin.Commandes.CommandeAdmin;
import Utilisateurs.VoletAdmin.Commandes.InvoquateurAdmin;

import java.util.LinkedList;


public class Admin implements Visiteur {

    private final InvoquateurAdmin invoquateurAdmin;
    private final String motDePasse;

    public Admin(String motDePasse) {
        this.invoquateurAdmin = new InvoquateurAdmin();
        this.motDePasse = motDePasse;
    }

    public void gererCompagnies(GestionnaireCompagnie gesComp, CommandeAdmin commandeAdmin) {
        invoquateurAdmin.setCommande(commandeAdmin);
        invoquateurAdmin.gererEntite(gesComp);

    }

    public void gererVoyages(GestionnaireVoyage gesVoy, CommandeAdmin commandeAdmin) {
        invoquateurAdmin.setCommande(commandeAdmin);
        invoquateurAdmin.gererEntite(gesVoy);
    }

    public void gererTerminaux(GestionnaireTerminale gesTerm, CommandeAdmin commandeAdmin) {
        invoquateurAdmin.setCommande(commandeAdmin);
        invoquateurAdmin.gererEntite(gesTerm);
    }

    public void undoOperation() {
        invoquateurAdmin.executerUndo();
    }

    public String getMotDePasse() {
        return motDePasse;
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
            texte += "|" + section.getClasseSection() + ((SectionSiege) section).getDispositionSieges();
            int counter = 0;
            for (Siege[] siege : sieges) {
                for (Siege value : siege) {
                    if (value.getEtatCourant() instanceof EtatReserve || value.getEtatCourant() instanceof EtatPaye) {
                        counter++;
                    }
                }
            }
            texte += "(" + counter + "/" + (sieges.length * sieges[0].length) + ")" + section.getPrix();
        }
        return texte;
    }

    private String getSectionPaquebotInfos(LinkedList<Section> sections) {
        String texte = "";
        for (Section section : sections) {
            Cabine[] cabines = ((SectionPaquebot) section).getCabines();
            texte += "|" + section.getClasseSection();
            int counter = 0;
            for (Cabine cabine : cabines) {
                if (cabine.getEtatCourant() instanceof EtatReserve || cabine.getEtatCourant() instanceof EtatPaye) {
                    counter++;
                }
            }
            texte += "(" + counter + "/" + cabines.length + ")" + section.getPrix();
        }
        return texte;
    }

    private String getSiegeInfos(Siege[][] sieges) {
        int counter = 0;
        for (Siege[] siege : sieges) {
            for (Siege value : siege) {
                if (value.getEtatCourant() instanceof EtatReserve || value.getEtatCourant() instanceof EtatPaye) {
                    counter++;
                }
            }
        }
        return "|(" + counter + "/" + (sieges.length * sieges[0].length) + ")" + sieges[0][0].getPrix();
    }
}