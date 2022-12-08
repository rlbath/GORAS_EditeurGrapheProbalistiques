package traitement;

import application.AccueilController;
import static application.AccueilController.noeudCible;
import static application.AccueilController.noeudSource;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class NoeudSimple extends Noeud {
    
    public static int cpt = 0;

    public NoeudSimple(double coordX, double coordY, AnchorPane zoneDessin) {
        super(Integer.toString(cpt+=1), coordX, coordY);
        dessinerNoeud(zoneDessin,this);
    }
    
    private void dessinerNoeud(AnchorPane zoneDessin, NoeudSimple noeud) {
        
        /* Cercle extérieur */
        Circle cercleExterieur = new Circle(noeud.getX(), noeud.getY(), AccueilController.getRadius() * 2.5);
        cercleExterieur.setFill(Color.TRANSPARENT);
        cercleExterieur.setStroke(Color.TRANSPARENT);
        
        /* cercle */
        Circle cercle = new Circle(noeud.getX(), noeud.getY(), AccueilController.getRadius());
        cercle.setFill(Color.TRANSPARENT);  
        cercle.setStroke(Color.BLACK);


        /* label */
        Label libelle = new Label(this.getLibelle());
        libelle.setLayoutX(noeud.getX());
        libelle.setLayoutY(noeud.getY());
                
        /* Groupe cercle + label */
        Group groupe = new Group();
        groupe.getChildren().addAll(cercle, libelle, cercleExterieur);
        groupe.setOnMouseClicked((new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent evt) {
                
                if (noeudSource == null) {
                    noeudSource = noeud;
                    System.out.println("source set");
                } else {
                    noeudCible = noeud;
                    System.out.println("cible set");
                }
            }
        }));
        
        
        groupe.setOnMousePressed((new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent evt) {
                //TODO trouver une méthode pour renvoyer un boolean
                System.out.println("Impossible de créer un noeud !");
                AccueilController.isDrawable = false;
            }
        }));
        
        
        //groupe.setOnMouseDragged();
        
        zoneDessin.getChildren().addAll(groupe);
    }
}
