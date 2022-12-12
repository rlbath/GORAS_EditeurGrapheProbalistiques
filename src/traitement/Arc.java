/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traitement;

import application.AccueilController;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

/**
 *
 * @author antoine.gouzy
 */
public class Arc extends Lien {

    public Arc(Noeud source, Noeud cible) {
        super(source, cible);
    }
    
    public static Line dessinerLien(AnchorPane zondeDessin,Noeud source, Noeud cible) {
        Line ligne;
                
        double l = Math.sqrt( Math.pow(source.getX()- cible.getX(), 2) + Math.pow(source.getY()- cible.getY(), 2));

        double xSource = source.getX() + (cible.getX() - source.getX()) / l * AccueilController.getRadius();
        double ySource = source.getY() + (cible.getY() - source.getY()) / l * AccueilController.getRadius();

        double xCible = cible.getX() + (source.getX() - cible.getX()) / l * AccueilController.getRadius();
        double yCible  = cible.getY() + (source.getY() - cible.getY()) / l * AccueilController.getRadius();


        ligne = new Line( xCible, yCible, xSource, ySource);

        AccueilController.noeudCible = null;
        AccueilController.noeudSource = null;
        //zoneDessin.getChildren().addAll(ligne);
        return ligne;
    }

}
