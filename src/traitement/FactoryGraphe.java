package traitement;

public abstract interface FactoryGraphe {
    
    public Graphe creerGraphe(String libelle);

    public Noeud creerNoeud(double coordX, double coordY);

    public Lien creerLien(Noeud source, Noeud cible);
    
    public FactoryGraphe getInstance();
}
