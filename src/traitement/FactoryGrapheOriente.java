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
public class FactoryGrapheOriente implements FactoryGraphe {

    @Override
    public Graphe creerGraphe(String libelle) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Lien creerLien(Noeud source, Noeud cible) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Noeud creerNoeud(double coordX, double coordY, AnchorPane zoneDessin) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
