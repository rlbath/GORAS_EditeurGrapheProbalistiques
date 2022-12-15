package traitement;

import application.AccueilController;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

public class Arete extends Lien {
    
    public Arete(Noeud source, Noeud cible) {
        super(source, cible);
    }
    
    public Line dessinerLien(AnchorPane zoneDessin) {
        
        double l = Math.sqrt( Math.pow(source.getX()- cible.getX(), 2) + Math.pow(source.getY()- cible.getY(), 2));

        double xSource = source.getX() + (cible.getX() - source.getX()) / l * AccueilController.getRadius();
        double ySource = source.getY() + (cible.getY() - source.getY()) / l * AccueilController.getRadius();

        double xCible = cible.getX() + (source.getX() - cible.getX()) / l * AccueilController.getRadius();
        double yCible  = cible.getY() + (source.getY() - cible.getY()) / l * AccueilController.getRadius();

        Line ligne = new Line(xCible, yCible, xSource, ySource);
        zoneDessin.getChildren().addAll(ligne);
        
        ligne.setOnMousePressed((new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent evt) {
                System.out.println(".handle()");
                AccueilController.estLien = true;
                AccueilController.noeudSource = source;
                AccueilController.noeudCible = cible;
            }
        }));
        
        return ligne;
    }
    
    public void proprieteLien(AnchorPane zonePropriete) {
        
    }
    
}
