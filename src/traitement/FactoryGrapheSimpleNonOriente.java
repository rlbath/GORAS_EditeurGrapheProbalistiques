package traitement;

import java.io.Serializable;

public class FactoryGrapheSimpleNonOriente implements FactoryGraphe, Serializable{

    @Override
    public GrapheSimple creerGraphe(String libelle) {
        return new GrapheSimple(libelle);
    }

    @Override
    public Noeud creerNoeud(double coordX, double coordY) {
        return new NoeudSimple(coordX, coordY);
    }

    @Override
    public Arete creerLien(Noeud source, Noeud cible) {
        return new Arete(source, cible);
    }
    
    @Override
    public FactoryGrapheSimpleNonOriente getInstance() {
        return this;
    }
}
