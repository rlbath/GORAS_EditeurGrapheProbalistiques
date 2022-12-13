/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traitement;

import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Gouzy
 */
public class FactoryGrapheSimpleOriente implements FactoryGraphe {

    @Override
    public Graphe creerGraphe(String libelle) {
        return new GrapheOriente(libelle);
    }

    @Override
    public Lien creerLien(Noeud source, Noeud cible) {
        return new Arc(source, cible);
    }

    @Override
    public Noeud creerNoeud(double coordX, double coordY) {
        return new NoeudSimple(coordX, coordY);
    }
    
    public FactoryGrapheSimpleOriente getInstance() {
        return this;
    }
    
}
