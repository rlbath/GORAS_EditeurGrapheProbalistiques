/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traitement;

import application.AccueilController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;

/**
 *
 * @author Gouzy
 */
class ArcProbabiliste extends Lien{
    

    private double ponderation = 0.0;
    
    public ArcProbabiliste() {
        
    }
    
    public ArcProbabiliste(Noeud source, Noeud cible) {
        super(source, cible);
    }

   
    public void setPonderation(double nouvellePonderation) {
       ponderation = nouvellePonderation;
    }
    
    public double getPonderation() {
        return ponderation;
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
        
        Label labelPonderation = new Label(Double.toString(ponderation));
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
                
        groupe = new Group();
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

    /**
     * Actualise les propriétés de l'arc en fonction des paramètres des combobox
     * @param noeudsSource ComboBox contenant tous les noeuds du graphe
     * @param noeudsCible ComboBox contenant tous les noeuds du graphe
     * @param graphe graphe en cours de traitement
     * @param zoneDessin zone de dessin du graphe
     */
    @Override
    public void setPropriete(ComboBox noeudsSource, ComboBox noeudsCible, Graphe graphe, AnchorPane zoneDessin, Group groupe, double nouvellePonderation) {
        
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
            
            ponderation = nouvellePonderation;
            
            supprimer(zoneDessin, groupe);
            //Dessin du nouveau lien
            dessinerLien(zoneDessin);
           
        }
    }
    
    /**
     * Affiche sur la zone de propriété les zones de saisie des propriétés d'un lien
     * @param zonePropriete zone ou les propriete s'afficher sur l'interface graphique
     * @param graphe graphe en cours de traitement
     * @param zoneDessin zone de dessin du graphe
     * @param groupe dessin du lien
     */
    @Override
    public void proprieteLien(AnchorPane zonePropriete, Graphe graphe, AnchorPane zoneDessin, Group groupe) {
        
        //Gestion propriete source
        Label labelSource = new Label("Source : ");
        labelSource.setLayoutX(10);
        labelSource.setLayoutY(53);
        
        ComboBox noeudsSource = new ComboBox();
        noeudsSource.setLayoutX(120);
        noeudsSource.setLayoutY(50);
        for (Noeud noeud : graphe.getNoeuds()) {
            
            if (noeud == this.source) { // Si le noeud actuel est la source du lien
                noeudsSource.getItems().add(noeud.getLibelle());
                noeudsSource.setValue(noeud.getLibelle()); //Selected ComboBox
            } else { // Ajout des autres noeuds
                noeudsSource.getItems().add(noeud.getLibelle());
            }  
        }
        zonePropriete.getChildren().addAll(labelSource, noeudsSource);

        //Gestion propriete cible
        Label labelCible = new Label("Cible : ");
        labelCible.setLayoutX(10);
        labelCible.setLayoutY(103);
        
        ComboBox noeudsCible = new ComboBox();
        noeudsCible.setLayoutX(120);
        noeudsCible.setLayoutY(100);
        for (Noeud noeud : graphe.getNoeuds()) {
            
            if (noeud == this.cible) { // Si le noeud actuel est la cible du lien
                noeudsCible.getItems().add(noeud.getLibelle());
                noeudsCible.setValue(noeud.getLibelle()); //Selected ComboBox
            } else { // Ajout des autres noeuds
                noeudsCible.getItems().add(noeud.getLibelle());
            }  
        }
        zonePropriete.getChildren().addAll(labelCible, noeudsCible);
       
        
        // Titre de TextField du changement de pondération de l'arc
        Label labelPonderation = new Label();
        labelPonderation.setText("Pondération : ");
        labelPonderation.setLayoutX(10);
        labelPonderation.setLayoutY(153);
        
        
        
        // récupération de la pondération de l'arc
        Label getterPonderation = (Label) groupe.getChildren().get(3);
        // Pour changer la pondération de l'arc
        TextField ponderationText = new TextField();
        ponderationText.setLayoutX(90);
        ponderationText.setLayoutY(150);
        ponderationText.setText(getterPonderation.getText());
        
        zonePropriete.getChildren().addAll(labelPonderation, ponderationText);
        
        
        // Bouton de validation
        Button validationModif = new Button("Valider");
        validationModif.setLayoutX(60);
        validationModif.setLayoutY(203);
        zonePropriete.getChildren().addAll(validationModif);
        
        // Si validation des changements
        validationModif.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent evt) {
                double anciennePonde = ponderation;
                Noeud ancienneSource = source;
                Noeud ancienneCible = cible;
                
                double nouvellePonderation = Double.parseDouble(ponderationText.getText());
                
                
                setPropriete(noeudsSource, noeudsCible, graphe, zoneDessin, groupe, nouvellePonderation);
                GrapheProbabiliste grapheProba = (GrapheProbabiliste) graphe;
                boolean ponderationOk = grapheProba.getPondeNoeud(getSource());
                
                if (!ponderationOk){
                    
                    supprimer(zoneDessin, getGroupe());
                    //Dessin du nouveau lien
                    dessinerLien(zoneDessin);
                    noeudsSource.setValue(ancienneSource.getLibelle());
                    noeudsCible.setValue(ancienneCible.getLibelle());

                    setPropriete(noeudsSource, noeudsCible, graphe, zoneDessin, getGroupe(), anciennePonde);
                    
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur Pondération");
                    alert.setHeaderText("Pondération totale supérieur à 1");
                    alert.showAndWait();
                    ponderationText.setText(Double.toString(ponderation));
                    
                }else{
                    //setPropriete(noeudsSource, noeudsCible, graphe, zoneDessin, groupe, nouvellePonderation);
                    zonePropriete.getChildren().clear();
                }
                
                
            }

            
        });
        
        // Bouton de suppression de l'arc
        Button supprimerLien = new Button("Supprimer");
        supprimerLien.setLayoutX(60);
        supprimerLien.setLayoutY(233);
        zonePropriete.getChildren().addAll(supprimerLien);
        
        
        // Si validation de la suppression de l'arc
        supprimerLien.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent evt) {
                supprimer(zoneDessin, groupe);
                graphe.supprimerLien(noeudsSource, noeudsCible);
                zonePropriete.getChildren().clear();
            }
        });
        
        
        
    }
    
}
