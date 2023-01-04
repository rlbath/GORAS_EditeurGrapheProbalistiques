/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traitement;

import application.AccueilController;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;

/**
 *
 * @author Gouzy
 */
class ArcProbabiliste extends Arete{
    

    private double nouvellePonderation = 1.0;
    
    public ArcProbabiliste(Noeud source, Noeud cible) {
        super(source, cible);
    }

   
    public void setPonderation(double ponderation) {
        nouvellePonderation = ponderation;
    }
    
    public double getPonderation() {
        return nouvellePonderation;
    }
    
    
    @Override
    public Group dessinerLien(AnchorPane zoneDessin) {
        
        /* TODO Si dessin d'une boucle verifier que l, xDirDroite et yDirDroite !=0
         * Sinon division par 0 donc coord égal NaN
         */
        
        double l = Math.sqrt( Math.pow(source.getX()- cible.getX(), 2) + Math.pow(source.getY()- cible.getY(), 2));
        
        double xSource = source.getX() + (cible.getX() - source.getX()) / l * Noeud.getRadius();
        double ySource = source.getY() + (cible.getY() - source.getY()) / l * Noeud.getRadius();

        double xCible = cible.getX() + (source.getX() - cible.getX()) / l * Noeud.getRadius();
        double yCible  = cible.getY() + (source.getY() - cible.getY()) / l * Noeud.getRadius();
        
        
        /* Creation de la ligne courbe  */
        double xDirDroite = xCible-xSource;
        double yDirDroite = yCible-ySource;

        double xNorDroite = -yDirDroite * (1 / Math.sqrt(Math.pow(yDirDroite, 2) + Math.pow(xDirDroite, 2)));
        double yNorDroite = xDirDroite * (1 / Math.sqrt(Math.pow(yDirDroite, 2) + Math.pow(xDirDroite, 2)));
        
        double xMilieuLien = (xCible + xSource)/2;
        double yMilieuLien = (yCible + ySource)/2;
        
        double xControle;
        double yControle;

        if (source.getX()- Noeud.getRadius() <= cible.getX() && cible.getX() <= source.getX() + Noeud.getRadius() 
            && source.getY() - Noeud.getRadius() <= cible.getY() && cible.getY() <= source.getY() + Noeud.getRadius() ) {
            xControle = xNorDroite * 200 + xMilieuLien;
            yControle = yNorDroite * 200 + yMilieuLien;            
            
        } else {
            xControle = xNorDroite * 60 + xMilieuLien;
            yControle = yNorDroite * 60 + yMilieuLien;
        }
        
        QuadCurve ligne = new QuadCurve(xSource, ySource, xControle, yControle, xCible, yCible);
        ligne.setFill(Color.TRANSPARENT);
        ligne.setStroke(Color.BLACK);
        
        Label labelPonderation = new Label(Double.toString(nouvellePonderation));
        labelPonderation.setLayoutX(xControle);
        labelPonderation.setLayoutY(yControle);


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
                
        Group groupe = new Group();
        groupe.getChildren().addAll(ligne, flecheBas, flecheHaut, labelPonderation);
        
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
    
    @Override
    public Group dessinerModifLien() {
        
        /* TODO Si dessin d'une boucle verifier que l, xDirDroite et yDirDroite !=0
         * Sinon division par 0 donc coord égal NaN
         */
        
        double l = Math.sqrt( Math.pow(source.getX()- cible.getX(), 2) + Math.pow(source.getY()- cible.getY(), 2));
        
        double xSource = source.getX() + (cible.getX() - source.getX()) / l * Noeud.getRadius();
        double ySource = source.getY() + (cible.getY() - source.getY()) / l * Noeud.getRadius();

        double xCible = cible.getX() + (source.getX() - cible.getX()) / l * Noeud.getRadius();
        double yCible  = cible.getY() + (source.getY() - cible.getY()) / l * Noeud.getRadius();
        
        
        /* Creation de la ligne courbe  */
        double xDirDroite = xCible-xSource;
        double yDirDroite = yCible-ySource;

        double xNorDroite = -yDirDroite * (1 / Math.sqrt(Math.pow(yDirDroite, 2) + Math.pow(xDirDroite, 2)));
        double yNorDroite = xDirDroite * (1 / Math.sqrt(Math.pow(yDirDroite, 2) + Math.pow(xDirDroite, 2)));
        
        double xMilieuLien = (xCible + xSource)/2;
        double yMilieuLien = (yCible + ySource)/2;
        
        double xControle;
        double yControle;

        if (source.getX()- Noeud.getRadius() <= cible.getX() && cible.getX() <= source.getX() + Noeud.getRadius() 
            && source.getY() - Noeud.getRadius() <= cible.getY() && cible.getY() <= source.getY() + Noeud.getRadius() ) {
            xControle = xNorDroite * 200 + xMilieuLien;
            yControle = yNorDroite * 200 + yMilieuLien;            
            
        } else {
            xControle = xNorDroite * 60 + xMilieuLien;
            yControle = yNorDroite * 60 + yMilieuLien;
        }
        
        QuadCurve ligne = new QuadCurve(xSource, ySource, xControle, yControle, xCible, yCible);
        ligne.setFill(Color.TRANSPARENT);
        ligne.setStroke(Color.BLACK);
        
        Label labelPonderation = new Label(Double.toString(nouvellePonderation));
        labelPonderation.setLayoutX(xControle);
        labelPonderation.setLayoutY(yControle);

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
                
        Group groupe = new Group();
        groupe.getChildren().addAll(ligne, flecheBas, flecheHaut, labelPonderation);
        
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
    public void setProprieteArcProba(ComboBox noeudsSource, ComboBox noeudsCible, Graphe graphe, AnchorPane zoneDessin, Group groupe, double ponderation) {
        
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
            
            System.err.println("Tu passe par là if");
            
            supprimer(zoneDessin, groupe);
            //Dessin du nouveau lien
            dessinerLien(zoneDessin);
            
        } else {            
            //ComboBox aux parametres par defauts
            noeudsSource.setValue(getSource().getLibelle());
            noeudsCible.setValue(getCible().getLibelle());
            //groupe.getChildren().remove(3);
            nouvellePonderation = ponderation;
            
            //TODO faire le changement du label (j'y arrive pas)
           
        }
    }
    
}
