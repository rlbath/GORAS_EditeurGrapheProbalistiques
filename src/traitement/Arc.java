package traitement;

import application.AccueilController;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;

/**
 *
 * @author antoine.gouzy
 */
public class Arc extends Lien {
    
    public Arc() {
        
    }

    public Arc(Noeud source, Noeud cible) {
        super(source, cible);
    }
    
    
    @Override
    public Group dessinerLien(AnchorPane zoneDessin) {
        
        /* TODO Si dessin d'une boucle verifier que l, xDirDroite et yDirDroite !=0
         * Sinon division par 0 donc coord égal NaN
         */
        
        double l = Math.sqrt( Math.pow(source.getCoordX()- cible.getCoordX(), 2) + Math.pow(source.getCoordY()- cible.getCoordY(), 2));
        
        double xSource = source.getCoordX() + (cible.getCoordX() - source.getCoordX()) / l * Noeud.getRadius();
        double ySource = source.getCoordY() + (cible.getCoordY() - source.getCoordY()) / l * Noeud.getRadius();

        double xCible = cible.getCoordX() + (source.getCoordX() - cible.getCoordX()) / l * Noeud.getRadius();
        double yCible  = cible.getCoordY() + (source.getCoordY() - cible.getCoordY()) / l * Noeud.getRadius();
        
        
        /* Creation de la ligne courbe  */
        double xDirDroite = xCible-xSource;
        double yDirDroite = yCible-ySource;

        double xNorDroite = -yDirDroite * (1 / Math.sqrt(Math.pow(yDirDroite, 2) + Math.pow(xDirDroite, 2)));
        double yNorDroite = xDirDroite * (1 / Math.sqrt(Math.pow(yDirDroite, 2) + Math.pow(xDirDroite, 2)));
        
        double xMilieuLien = (xCible + xSource)/2;
        double yMilieuLien = (yCible + ySource)/2;
        
        double xControle;
        double yControle;

        if (source.getCoordX()- Noeud.getRadius() <= cible.getCoordX() && cible.getCoordX() <= source.getCoordX() + Noeud.getRadius() 
            && source.getCoordY() - Noeud.getRadius() <= cible.getCoordY() && cible.getCoordY() <= source.getCoordY() + Noeud.getRadius() ) {
            xControle = xNorDroite * 200 + xMilieuLien;
            yControle = yNorDroite * 200 + yMilieuLien;            
            
        } else {
            xControle = xNorDroite * 60 + xMilieuLien;
            yControle = yNorDroite * 60 + yMilieuLien;
        }
        
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
                
        groupe = new Group();
        groupe.getChildren().addAll(ligne, flecheBas, flecheHaut);
        
        //Action s'il on clique sur l'arc
        groupe.setOnMousePressed((new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent evt) {
                AccueilController.estLien = true;
                AccueilController.lienEnCoursGroup = groupe;
                AccueilController.noeudSource = source;
                AccueilController.noeudCible = cible;
            }
        }));
        
        zoneDessin.getChildren().addAll(groupe);
        return groupe;
    }

    /**
     * Actualise les propriétés de l'arc en fonction des paramètres des combobox
     * @param noeudsSource ComboBox contenant tous les noeuds du graphe
     * @param noeudsCible ComboBox contenant tous les noeuds du graphe
     * @param graphe graphe en cours de traitement
     * @param zoneDessin zone de dessin du graphe
     */
    @Override
    public void setPropriete(ComboBox noeudsSource, ComboBox noeudsCible, Graphe graphe, AnchorPane zoneDessin, Group groupe) {
        
        String libelleNoeudSource = (String) noeudsSource.getValue();
        String libelleNoeudCible = (String) noeudsCible.getValue();
        Noeud noeudSource = null; //Init pour le compilateur
        Noeud noeudCible = null;
        
        //Recuperation des noeuds source et cible en fonction des libelles
        for (Noeud noeud : graphe.getNoeuds()) {
            
            if (noeud.getLibelle().equals(libelleNoeudSource)) {
                noeudSource = noeud;                
            }
            if (noeud.getLibelle().equals(libelleNoeudCible)) {
                noeudCible = noeud;               
            }
        }
        
        //Verification que l'arc n'existe pas deja
        if (!graphe.estLienDuGraphe(noeudSource, noeudCible)) {
            
            //Modification des sources et cibles de l'instance
            setSource(noeudSource);
            setCible(noeudCible);
            
            supprimer(zoneDessin, groupe);
            //Dessin du nouveau lien
            dessinerLien(zoneDessin);
            
        } else {            
            //ComboBox aux parametres par defauts
            noeudsSource.setValue(getSource().getLibelle());
            noeudsCible.setValue(getCible().getLibelle());
        }
    }
}
