package traitement;

public class FactoryGrapheSimpleNonOriente implements FactoryGraphe{

    @Override
    public Graphe creerGraphe(String libelle) {
        return new GrapheSimple(libelle);
    }

    @Override
    public Noeud creerNoeud(double coordX, double coordY) {
        return new NoeudSimple(coordX, coordY);
    }

    @Override
    public Lien creerLien(Noeud source, Noeud cible) {
        return new Arete(source, cible);
    }
    
    public FactoryGrapheSimpleNonOriente getInstance() {
        return this;
    }
}
