package traitement;

import application.AccueilController;
import static application.AccueilController.factory;
import static application.AccueilController.graphe;
import traitement.Graphe;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
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
     * @return Group le groupe crée
     */
    @Override
    public Group dessinerNoeud(AnchorPane zoneDessin) {
        
        /* Cercle extérieur */
        Circle cercleExterieur = new Circle(getX(), getY(), Noeud.getRadius() * 2.5);
        cercleExterieur.setFill(Color.TRANSPARENT);
        cercleExterieur.setStroke(Color.TRANSPARENT);
        
        /* cercle */
        Circle cercle = new Circle(getX(), getY(), Noeud.getRadius());
        cercle.setFill(Color.TRANSPARENT);  
        cercle.setStroke(Color.BLACK);


        /* label */
        Label libelle = new Label(this.getLibelle());
        libelle.setLayoutX(this.getX() - 3);
        libelle.setLayoutY(this.getY() - 8);

        /* Groupe cercle + label */
        Group groupe = new Group();
        groupe.getChildren().addAll(cercle, libelle, cercleExterieur);

        groupe.setOnMousePressed((new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent evt) {
                AccueilController.isDrawable = false;
                AccueilController.noeudEnCoursGroup = groupe;
            }
        }));
        zoneDessin.getChildren().addAll(groupe);
        return groupe;
    }

    public void selectionGroupe(AnchorPane main, Group groupe, Graphe graphe, AnchorPane zoneDessin) {
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
                String nomBase = getterLabelNom.getText();
                
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
                Double coordXBase = Double.parseDouble(coordX.getText());
                Double coordYBase = Double.parseDouble(coordY.getText());
                
                // Bouton de validation du nouveau nom
                Button validationModif = new Button();
                validationModif.setText("Valider");
                validationModif.setLayoutX(60);
                validationModif.setLayoutY(203);
                
                // Bouton de supression d'un noeud
                Button suppression = new Button();
                suppression.setText("Supprimer");
                suppression.setLayoutX(120);
                suppression.setLayoutY(203);
                
                validationModif.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent evt) {
                        
                        String nouveauNom = libelleModif.getText();
                        Double nouvelleCoordX = Double.parseDouble(coordX.getText());
                        Double nouvelleCoordY = Double.parseDouble(coordY.getText());
                        
                        // gestion d'erreur de collision après modification des coordonnées de X et Y
                        
                        //TODO ça marche pas donc il faut trouver d'autres conditions
                        int cptErreur = 0;
                        /*
                        for (int i = 0; i < Graphe.noeuds.size(); i++) {
                            if (nouvelleCoordX - Graphe.noeuds.get(i).getX() < 50 && nouvelleCoordY - Graphe.noeuds.get(i).getY() < 50) {
                                cptErreur = 1;
                            } 
                            if (coordXBase - nouvelleCoordX == 0 && coordYBase - nouvelleCoordY == 0) {
                                cptErreur = 0;
                            }
                        }
                        // Si erreur alors on remets la coordonnées avant le changement
                        if (cptErreur == 1) {
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Erreur Coord X");
                            alert.setHeaderText("Coordonnée de X trop proche d'un autre noeud");
                            alert.showAndWait();
                            nouvelleCoordX = coordXBase;
                            nouvelleCoordY = coordYBase;
                        } 
                        */

                        // Gestion des noms en double
                        cptErreur = 0;
                        for (int i = 0; i < graphe.noeuds.size(); i++) {
                            if (nouveauNom.equals(graphe.noeuds.get(i).getLibelle())) {
                                cptErreur = 1;
                            } 
                            if (nomBase.equals(nouveauNom)) {
                                cptErreur = 0;
                            }
                        }
                        // si le nom existe déjà alors on remets l'ancien nom d'avant la modification
                        if (cptErreur == 1) {
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Erreur Nom");
                            alert.setHeaderText("Nom déjà existant sur un autre noeud");
                            alert.showAndWait();
                            nouveauNom = nomBase;
                        }
                        
                        /* Cercle extérieur */
                        Circle nvCercleExterieur = new Circle(nouvelleCoordX, nouvelleCoordY, Noeud.getRadius() * 2.5);
                        nvCercleExterieur.setFill(Color.TRANSPARENT);
                        nvCercleExterieur.setStroke(Color.TRANSPARENT);

                        /* cercle */
                        Circle nvCercle = new Circle(nouvelleCoordX, nouvelleCoordY, Noeud.getRadius());
                        nvCercle.setFill(Color.TRANSPARENT);  
                        nvCercle.setStroke(Color.BLACK);

                        /* label */    
                        Label nvLibelle = new Label(nouveauNom);
                        nvLibelle.setLayoutX(nouvelleCoordX - 3);
                        nvLibelle.setLayoutY(nouvelleCoordY - 8); 
  
                        groupe.getChildren().clear();
                        groupe.getChildren().addAll(nvCercle, nvLibelle, nvCercleExterieur);
                        
                        AccueilController.noeudASelectionner.setLibelle(nouveauNom);
                        AccueilController.noeudASelectionner.setX(nouvelleCoordX);
                        AccueilController.noeudASelectionner.setY(nouvelleCoordY);
                        
                        graphe.modifLienNoeud(AccueilController.noeudASelectionner);

                        // On efface tout le dessin pour le redessiner avec les nouvelles modifications
                        zoneDessin.getChildren().clear();
                        
                        // On redessine les noeuds et les liens
                        for (Noeud noeud : graphe.getNoeuds()) {
                            noeud.dessinerNoeud(zoneDessin);
                        }
                        for (Lien lien : graphe.getLiens()) {
                            lien.dessinerLien(zoneDessin);
                        }
                        
                        /*
                        Group ligneEnCours;
                        for (int i = 0; i < graphe.getLiensNoeud(AccueilController.noeudASelectionner).size(); i = i + 2) {     
                            ligneEnCours = graphe.getLienDuGraphe(graphe.getLiensNoeud(AccueilController.noeudASelectionner).get(i),
                                    graphe.getLiensNoeud(AccueilController.noeudASelectionner).get(i + 1)).dessinerModifLien();
                            System.out.println(ligneEnCours);
                            groupe.getChildren().addAll(ligneEnCours);
                        } 
                        */
                    }
                });
                
                validationModif.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent evt) {
                        
                    }    
                });
                
                
                main.getChildren().addAll(libelleModif, labelLibelleModif, coordX, coordY, labelCoordX, labelCoordY, validationModif, suppression);
            }
        }));
    }

    /** @return l'id de ce noeud */
    @Override
    public int getId() {
        return id;
    }
    
    @Override
    public String toString() {
        String noeud = libelle + " X: " + coordX + " Y :" + coordY + " id : " + id;
        return noeud;
    }
}
