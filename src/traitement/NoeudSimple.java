package traitement;

import application.AccueilController;
import javafx.event.ActionEvent;
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
                Label getterLabelNom = (Label) groupe.getChildren().get(1);
                libelleModif.setText(getterLabelNom.getText());
                
                // label pour la modification du nom du noeud
                Label labelLibelleModif = new Label();
                labelLibelleModif.setText("Nom :");
                labelLibelleModif.setLayoutX(26);
                labelLibelleModif.setLayoutY(53);
                
                // Pour changer les coord X du noeud
                TextField coordX = new TextField();
                coordX.setLayoutX(60);
                coordX.setLayoutY(100);
                
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
                
                // récupération coord X et Y du noeud
                Circle getterCoordonnees = (Circle) groupe.getChildren().get(0);
                coordX.setText(Double.toString(getterCoordonnees.getCenterX()));
                coordY.setText(Double.toString(getterCoordonnees.getCenterY()));
                
                // Bouton de validation du nouveau nom
                Button validationModifNom = new Button();
                validationModifNom.setText("Valider");
                validationModifNom.setLayoutX(60);
                validationModifNom.setLayoutY(203);
                
                // Changement du nom
                validationModifNom.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent evt) {
                        String nouveauNom = libelleModif.getText();
                        Double nouvelleCoordX = Double.parseDouble(coordX.getText());
                        Double nouvelleCoordY = Double.parseDouble(coordY.getText());
                        
                        /* Cercle extérieur */
                        Circle nvCercleExterieur = new Circle(nouvelleCoordX, nouvelleCoordY, AccueilController.getRadius() * 2.5);
                        nvCercleExterieur.setFill(Color.TRANSPARENT);
                        nvCercleExterieur.setStroke(Color.TRANSPARENT);

                        /* cercle */
                        Circle nvCercle = new Circle(nouvelleCoordX, nouvelleCoordY, AccueilController.getRadius());
                        nvCercle.setFill(Color.TRANSPARENT);  
                        nvCercle.setStroke(Color.BLACK);


                        /* label */
                        Label nvLibelle = new Label(nouveauNom);
                        nvLibelle.setLayoutX(nouvelleCoordX - 3);
                        nvLibelle.setLayoutY(nouvelleCoordY - 8);
                        
                        groupe.getChildren().clear();
                        groupe.getChildren().addAll(nvCercle, nvLibelle, nvCercleExterieur);
                        
                        // TODO Récupérer l'objet noeud pour le modifier (j'y arrive pas)
                        /* 
                        AccueilController.noeudASelectionner.libelle = nouveauNom;
                        AccueilController.noeudASelectionner.coordX = nouvelleCoordX;
                        AccueilController.noeudASelectionner.coordY = nouvelleCoordY;
                        */
                    }
                });
                
                main.getChildren().addAll(libelleModif, labelLibelleModif, coordX, coordY, labelCoordX, labelCoordY, validationModifNom);
            }
        }));
    }
}
