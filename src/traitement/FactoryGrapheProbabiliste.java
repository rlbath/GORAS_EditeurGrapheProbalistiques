/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traitement;

import java.io.Serializable;

/**
 *
 * @author Gouzy
 */
public class FactoryGrapheProbabiliste implements FactoryGraphe, Serializable{

    @Override
    public GrapheProbabiliste creerGraphe(String libelle) {
        return new GrapheProbabiliste(libelle);
    }

    @Override
    public Noeud creerNoeud(double coordX, double coordY) {
        return new NoeudSimple(coordX, coordY);
    }

    @Override
    public ArcProbabiliste creerLien(Noeud source, Noeud cible) {
        return new ArcProbabiliste(source, cible);
    }

    @Override
    public FactoryGraphe getInstance() {
        return this;
    }
    
}
