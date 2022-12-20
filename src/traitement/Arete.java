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
    @Override
    public void dessinerLien(AnchorPane zoneDessin) {
        
        double l = Math.sqrt( Math.pow(source.getX()- cible.getX(), 2) + Math.pow(source.getY()- cible.getY(), 2));

        double xSource = source.getX() + (cible.getX() - source.getX()) / l * Noeud.getRadius();
        double ySource = source.getY() + (cible.getY() - source.getY()) / l * Noeud.getRadius();

        double xCible = cible.getX() + (source.getX() - cible.getX()) / l * Noeud.getRadius();
        double yCible  = cible.getY() + (source.getY() - cible.getY()) / l * Noeud.getRadius();


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
     * Actualise les propriétés de l'arete en fonction des paramètres des combobox
     * @param noeudsSource ComboBox contenant tous les noeuds du graphe
     * @param noeudsCible ComboBox contenant tous les noeuds du graphe
     * @param graphe graphe en cours de traitement
     * @param zoneDessin zone de dessin du graphe
     */
    @Override
    public void setPropriete(ComboBox noeudsSource, ComboBox noeudsCible, Graphe graphe, AnchorPane zoneDessin) {
        
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
        if (!graphe.estLienDuGraphe(noeudSource, noeudCible) && noeudSource != noeudCible) {
            
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
