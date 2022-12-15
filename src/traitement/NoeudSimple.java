package traitement;

import application.AccueilController;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
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
    
    public void selectionGroupe(AnchorPane main) {
        groupe.setOnMouseClicked((new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent evt) {
                // Text field pour changer libelle du noeud sélectionné
                TextField libelleModif = new TextField();
                libelleModif.setLayoutX(60);
                libelleModif.setLayoutY(50);
                
                // récupération du nom du noeud
                Label getterLabel = (Label) groupe.getChildren().get(1);
                libelleModif.setText(getterLabel.getText());
                
                // label pour la modification du nom du noeud
                Label labelLibelleModif = new Label();
                labelLibelleModif.setText("Nom :");
                labelLibelleModif.setLayoutX(26);
                labelLibelleModif.setLayoutY(53);
                
                // Pour changer les coord X du noeud
                TextField coordX = new TextField();
                coordX.setLayoutX(60);
                coordX.setLayoutY(100);
                
                // récupération coord X du noeud*
                // TODO Faire la récupération des coord X
                // Label getterLabel = (Label) groupe.getChildren().get(0);
                //libelleModif.setText(getterLabel.getText());
                
                // Label du changement coord X du noeud
                Label labelCoordX = new Label();
                labelCoordX.setText("coordX :");
                labelCoordX.setLayoutX(15);
                labelCoordX.setLayoutY(103);
                
                // pour changer les coord Y du noeud
                TextField coordY = new TextField();
                coordY.setLayoutX(60);
                coordY.setLayoutY(150);
                
                // label pour changemennt des coord Y du noeud
                Label labelCoordY = new Label();
                labelCoordY.setText("coordY :");
                labelCoordY.setLayoutX(15);
                labelCoordY.setLayoutY(153);
                
                // Bouton de validation du nouveau nom
                Button validationModifNom = new Button();
                validationModifNom.setText("Valider");
                validationModifNom.setLayoutX(60);
                validationModifNom.setLayoutY(203);
                
                
                
                
                main.getChildren().addAll(libelleModif, labelLibelleModif, coordX, coordY, labelCoordX, labelCoordY, validationModifNom);
            }
        }));
    }
}
