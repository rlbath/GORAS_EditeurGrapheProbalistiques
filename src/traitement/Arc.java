/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traitement;

import application.AccueilController;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;

/**
 *
 * @author antoine.gouzy
 */
public class Arc extends Lien {

    public Arc(Noeud source, Noeud cible) {
        super(source, cible);
    }
    
    
    public static Group dessinerLien(AnchorPane zoneDessin, Noeud source, Noeud cible) {
        
        double l = Math.sqrt( Math.pow(source.getX()- cible.getX(), 2) + Math.pow(source.getY()- cible.getY(), 2));

        double xSource = source.getX() + (cible.getX() - source.getX()) / l * AccueilController.getRadius();
        double ySource = source.getY() + (cible.getY() - source.getY()) / l * AccueilController.getRadius();

        double xCible = cible.getX() + (source.getX() - cible.getX()) / l * AccueilController.getRadius();
        double yCible  = cible.getY() + (source.getY() - cible.getY()) / l * AccueilController.getRadius();

        Line ligne = new Line(xCible, yCible, xSource, ySource);
        
        double angleRad = 0;
        
        if (xSource > xCible ) {
            angleRad = Math.PI / 4;
        } else {
            angleRad = 3 * Math.PI / 4;
        }
        double distance = 30;
        double xflecheH = xCible + distance * Math.cos(Math.atan((yCible - ySource) /(xCible - xSource)) - angleRad);
        double yflecheH = yCible + distance * Math.sin(Math.atan((yCible - ySource) /(xCible - xSource)) - angleRad);
        
        double xflecheB = xCible + distance * Math.cos(Math.atan((yCible - ySource) /(xCible - xSource)) + angleRad);
        double yflecheB = yCible + distance * Math.sin(Math.atan((yCible - ySource) /(xCible - xSource)) + angleRad);
        
        
        System.out.println("xflecheH : " + xflecheH);
        System.out.println("yflecheH : " + yflecheH);
        System.out.println("xflecheB : " + xflecheB);
        System.out.println("yflecheB : " + yflecheB);
        
        Line flecheHaut = new Line(xCible, yCible, xflecheH, yflecheH);
        Line flecheBas = new Line(xCible, yCible, xflecheB, yflecheB);
                
        Group arc = new Group();
        arc.getChildren().addAll(ligne, flecheBas, flecheHaut);
        
        zoneDessin.getChildren().addAll(arc);
        
        
        QuadCurve ligne1 = new QuadCurve();
        
        return arc;
    }
}
