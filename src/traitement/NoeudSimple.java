package traitement;

import application.AccueilController;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class NoeudSimple extends Noeud {
    
    /** Compteur du nombre de noeud que contient un graphe */
    public static int cpt = 0;
    
    /** id de ce noeud utiliser pour l'ouverture d'un graphe */
    private int id;
    
    /**
     * Creer un noeud simple
     * @param coordX coordonnee en abscisse de ce noeud
     * @param coordY coordonnee en ordonnee de ce noeud
     */
    public NoeudSimple(double coordX, double coordY) {
        super(Integer.toString(cpt+=1), coordX, coordY);
        id = cpt;
    }
    
    /**
     * Dessine un noeudSimple sur la zone de dessin
     * @param zoneDessin zone de dessin de l'application
     * @param noeud noeud a dessiner
     */
    public static void dessinerNoeud(AnchorPane zoneDessin, Noeud noeud) {
        
        /* Cercle extérieur */
        Circle cercleExterieur = new Circle(noeud.getX(), noeud.getY(), Noeud.getRadius() * 2.5);
        cercleExterieur.setFill(Color.TRANSPARENT);
        cercleExterieur.setStroke(Color.TRANSPARENT);
        
        /* cercle */
        Circle cercle = new Circle(noeud.getX(), noeud.getY(), Noeud.getRadius());
        cercle.setFill(Color.TRANSPARENT);  
        cercle.setStroke(Color.BLACK);


        /* label */
        Label libelle = new Label(noeud.getLibelle());

        libelle.setLayoutX(noeud.getX() - 3);
        libelle.setLayoutY(noeud.getY() - 8);

        /* Groupe cercle + label */
        Group groupe = new Group();
        groupe.getChildren().addAll(cercle, libelle, cercleExterieur);

        groupe.setOnMousePressed((new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent evt) {
                AccueilController.isDrawable = false;
            }
        }));
        zoneDessin.getChildren().addAll(groupe);
        
    }
    
    /** @return l'id de ce noeud */
    public int getId() {
        return id;
    }
    
    @Override
    public String toString() {
        String noeud = libelle + " X: " + coordX + " Y :" + coordY + " id : " + id;
        return noeud;
    }
}
