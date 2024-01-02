package EntiteVoyage.Voyages.Sections;

public abstract class Section {

    protected String classeSection;
    protected double prix;

    public Section(String classeSection, double prix) {
        this.classeSection = classeSection;
        this.prix = prix;
    }

    public String getClasseSection() {
        return classeSection;
    }

    public double getPrix() {
        return prix;
    }

}
