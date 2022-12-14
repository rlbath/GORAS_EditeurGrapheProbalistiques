package traitement;

import application.AccueilController;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;

/**
 *
 * @author antoine.gouzy
 */
public class Arc extends Lien {
    
    private Group arc;

    public Arc(Noeud source, Noeud cible) {
        super(source, cible);
    }
    
    
    public Group dessinerLien(AnchorPane zoneDessin) {
        
        double l = Math.sqrt( Math.pow(source.getX()- cible.getX(), 2) + Math.pow(source.getY()- cible.getY(), 2));

        double xSource = source.getX() + (cible.getX() - source.getX()) / l * AccueilController.getRadius();
        double ySource = source.getY() + (cible.getY() - source.getY()) / l * AccueilController.getRadius();

        double xCible = cible.getX() + (source.getX() - cible.getX()) / l * AccueilController.getRadius();
        double yCible  = cible.getY() + (source.getY() - cible.getY()) / l * AccueilController.getRadius();
        
        
        /* Creation de la ligne courbe  */
        double xDirDroite = xCible-xSource;
        double yDirDroite = yCible-ySource;

        double xNorDroite = -yDirDroite * (1 / Math.sqrt(Math.pow(yDirDroite, 2) + Math.pow(xDirDroite, 2)));
        double yNorDroite = xDirDroite * (1 / Math.sqrt(Math.pow(yDirDroite, 2) + Math.pow(xDirDroite, 2)));
        
        double xMilieuLien = (xCible + xSource)/2;
        double yMilieuLien = (yCible + ySource)/2;
        
        double xControle = xNorDroite * 40 + xMilieuLien;
        double yControle = yNorDroite * 40 + yMilieuLien;
        
        QuadCurve ligne = new QuadCurve(xSource, ySource, xControle, yControle, xCible, yCible);
        ligne.setFill(Color.TRANSPARENT);
        ligne.setStroke(Color.BLACK);


        /* Creation de la fleche */
        double angleRad;
        
        if (xSource > xCible ) {
            angleRad = Math.PI / 4;
        } else {
            angleRad = 3 * Math.PI / 4;
        }
        
        double distance = 15;
        double xflecheH = xCible + distance * Math.cos(Math.atan((yCible - yControle) /(xCible - xControle)) - angleRad);
        double yflecheH = yCible + distance * Math.sin(Math.atan((yCible - yControle) /(xCible - xControle)) - angleRad);
        double xflecheB = xCible + distance * Math.cos(Math.atan((yCible - yControle) /(xCible - xControle)) + angleRad);
        double yflecheB = yCible + distance * Math.sin(Math.atan((yCible - yControle) /(xCible - xControle)) + angleRad);
        
        Line flecheHaut = new Line(xCible, yCible, xflecheH, yflecheH);
        Line flecheBas = new Line(xCible, yCible, xflecheB, yflecheB);
                
        arc = new Group();
        arc.getChildren().addAll(ligne, flecheBas, flecheHaut);
        
        zoneDessin.getChildren().addAll(arc);

        return arc;
    }
}
