package traitement;

import application.AccueilController;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class NoeudSimple extends Noeud {
    
    public static int cpt = 0;
    
    public Group groupe;
    
    public NoeudSimple(double coordX, double coordY) {
        super(Integer.toString(cpt+=1), coordX, coordY);
    }
    
    public void dessinerNoeud(AnchorPane zoneDessin) {
        
        /* Cercle extérieur */
        Circle cercleExterieur = new Circle(this.getX(), this.getY(), AccueilController.getRadius() * 2.5);
        cercleExterieur.setFill(Color.TRANSPARENT);
        cercleExterieur.setStroke(Color.TRANSPARENT);
        
        /* cercle */
        Circle cercle = new Circle(this.getX(), this.getY(), AccueilController.getRadius());
        cercle.setFill(Color.TRANSPARENT);  
        cercle.setStroke(Color.BLACK);


        /* label */
        Label libelle = new Label(this.getLibelle());

        libelle.setLayoutX(this.getX() - 3);
        libelle.setLayoutY(this.getY() - 8);

        /* Groupe cercle + label */
        groupe = new Group();
        groupe.getChildren().addAll(cercle, libelle, cercleExterieur);

        groupe.setOnMousePressed((new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent evt) {
                AccueilController.isDrawable = false;
            }
        }));
        zoneDessin.getChildren().addAll(groupe);
        
    }
    
    public Group getGroupe(Group groupe) {
        return groupe;
    }
    
    public void selectionGroupe(AnchorPane main) {
        groupe.setOnMouseClicked((new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent evt) {
                // Text field pour changer libelle du noeud sélectionné
                TextField libelleModif = new TextField();
                libelleModif.setLayoutX(50);
                libelleModif.setLayoutY(50);
                
                main.getChildren().addAll(libelleModif);
                //System.out.println(this.getLibelle());
            }
        }));
    }
}
