package traitement;

/**
 *
 * @author Gouzy
 */
public class FactoryGrapheSimpleOriente implements FactoryGraphe {

    @Override
    public GrapheOriente creerGraphe(String libelle) {
        return new GrapheOriente(libelle);
    }

    @Override
    public Arc creerLien(Noeud source, Noeud cible) {
        return new Arc(source, cible);
    }

    @Override
    public Noeud creerNoeud(double coordX, double coordY) {
        return new NoeudSimple(coordX, coordY);
    }
    
    @Override
    public FactoryGrapheSimpleOriente getInstance() {
        return this;
    }
    
}
