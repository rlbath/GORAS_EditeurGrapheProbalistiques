package traitement;

import javafx.scene.layout.AnchorPane;


public abstract interface FactoryGraphe {
    
    public Graphe creerGraphe(String libelle);
    
    public Noeud creerNoeud(double coordX, double coordY, AnchorPane zoneDessin);
    
    public Lien creerLien(Noeud source, Noeud cible);
}
