package traitement;

import application.AccueilController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Arete extends Lien {
    
    /**
     * Creer une instance arete en apellant le constructeur de la superClass Lien
     * @param source noeud source de l'arete
     * @param cible noeud cible de l'arete
     */
    public Arete(Noeud source, Noeud cible) {
        super(source, cible);
    }
    
    /**
     * Dessine sur la zone de dessin une arete entre la source et la cible du lien
     * Le dessin d'un arete comporte une ligne visible par l'utilisateur
     * Et une enveloppe pour permettre les actions sur une arete plus confortable
     * @param zoneDessin zone de dessin du graphe
     */
    public void dessinerLien(AnchorPane zoneDessin) {
        
        double l = Math.sqrt( Math.pow(source.getX()- cible.getX(), 2) + Math.pow(source.getY()- cible.getY(), 2));

        double xSource = source.getX() + (cible.getX() - source.getX()) / l * AccueilController.getRadius();
        double ySource = source.getY() + (cible.getY() - source.getY()) / l * AccueilController.getRadius();

        double xCible = cible.getX() + (source.getX() - cible.getX()) / l * AccueilController.getRadius();
        double yCible  = cible.getY() + (source.getY() - cible.getY()) / l * AccueilController.getRadius();


        Line enveloppe = new Line(xCible, yCible, xSource, ySource);
        Line ligne = new Line(xCible, yCible, xSource, ySource);
        
        // Defini la zone cliquable autour de la ligne representant le lien
        enveloppe.setStrokeWidth(5);
        
        //Parametre seulement lors du dev Color.RED, sinon Color.TRANSPARENT
        enveloppe.setStroke(Color.RED);
        
        groupe.getChildren().addAll(enveloppe, ligne);
        
        //Action s'il on clique sur l'arete
        groupe.setOnMousePressed((new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent evt) {
                AccueilController.estLien = true;
                AccueilController.noeudSource = source;
                AccueilController.noeudCible = cible;
            }
        }));
        zoneDessin.getChildren().addAll(groupe);
    }
    
    /**
     * Affiche sur la zone de propriété les zones de saisie des propriétés d'une arete
     * @param zonePropriete zone ou les propriete s'afficher sur l'interface graphique
     * @param graphe graphe en cours de traitement
     * @param zoneDessin zone de dessin du graphe
     */
    public void proprieteLien(AnchorPane zonePropriete, Graphe graphe, AnchorPane zoneDessin) {
        System.out.println("propri " + getGroup());
        //Gestion propriete source
        Label labelSource = new Label("Source : ");
        labelSource.setLayoutX(26);
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
        labelCible.setLayoutX(26);
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
        
        // Bouton de validation du nouveau nom
        Button validationModif = new Button("Valider");
        validationModif.setLayoutX(60);
        validationModif.setLayoutY(203);
        zonePropriete.getChildren().addAll(validationModif);
        
        
        // Si validation des changements
        validationModif.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent evt) {
                setPropriete(noeudsSource, noeudsCible, graphe, zoneDessin);
            }
        });
        
        // Bouton de validation du nouveau nom
        Button supprimerLien = new Button("Supprimer");
        supprimerLien.setLayoutX(60);
        supprimerLien.setLayoutY(223);
        zonePropriete.getChildren().addAll(supprimerLien);
        
        
        // Si validation des changements
        supprimerLien.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent evt) {
                System.out.println("Avant suppr" + graphe.liens.toString());
                supprimer(zoneDessin);
                graphe.supprimerArete(noeudsSource, noeudsCible);
                System.out.println("Apres suppr" + graphe.liens.toString());
            }
        });
        
    }
    
    /**
     * Actualise les propriétés de l'arete en fonction des paramètres des combobox
     * @param noeudsSource ComboBox contenant tous les noeuds du graphe
     * @param noeudsCible ComboBox contenant tous les noeuds du graphe
     * @param graphe graphe en cours de traitement
     * @param zoneDessin zone de dessin du graphe
     */
    private void setPropriete(ComboBox noeudsSource, ComboBox noeudsCible, Graphe graphe, AnchorPane zoneDessin) {
        
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
        
        //Verification que l'arete n'existe pas deja et que les nouvelles valeurs ne forment pas de boucle
        if (!graphe.estAreteDuGraphe(noeudSource, noeudCible) && noeudSource != noeudCible) {
            
            //Modification des sources et cibles de l'instance
            setSource(noeudSource);
            setCible(noeudCible);
            
            supprimer(zoneDessin);
            //Dessin du nouveau lien
            dessinerLien(zoneDessin);
            
        } else {            
            //ComboBox aux parametres par defauts
            noeudsSource.setValue(getSource().getLibelle());
            noeudsCible.setValue(getCible().getLibelle());
        }
        
    }
    
}
