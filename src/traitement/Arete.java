package traitement;

import application.AccueilController;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

public class Arete extends Lien {
    
    public Arete(Noeud source, Noeud cible) {
        super(source, cible);
    }
    
    
    public static Line dessinerLien(AnchorPane zoneDessin, Noeud source, Noeud cible) {
        
        double l = Math.sqrt( Math.pow(source.getX()- cible.getX(), 2) + Math.pow(source.getY()- cible.getY(), 2));

        double xSource = source.getX() + (cible.getX() - source.getX()) / l * AccueilController.getRadius();
        double ySource = source.getY() + (cible.getY() - source.getY()) / l * AccueilController.getRadius();

        double xCible = cible.getX() + (source.getX() - cible.getX()) / l * AccueilController.getRadius();
        double yCible  = cible.getY() + (source.getY() - cible.getY()) / l * AccueilController.getRadius();


        Line ligne = new Line(xCible, yCible, xSource, ySource);

        AccueilController.noeudCible = null;
        AccueilController.noeudSource = null;
        zoneDessin.getChildren().addAll(ligne);
        
        return ligne;
    }
}
