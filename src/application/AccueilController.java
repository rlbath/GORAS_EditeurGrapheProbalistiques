package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import traitement.FactoryGraphe;
import traitement.FactoryManager;
import traitement.Graphe;
import traitement.Lien;
import traitement.Noeud;

/**
 *
 * @author GORAS
 */
public class AccueilController implements Initializable {
    
    public static FactoryManager factoryManager = new FactoryManager();
    
    public static FactoryGraphe factory;
    
    public static Graphe graphe;
    
    /* pour le dessin d'un lien */
    public static Noeud cible;
    public static Noeud source;
    
    @FXML
    private ToggleButton selectionBtn;
    @FXML
    private ToggleButton noeudBtn;
    @FXML
    private ToggleButton lienBtn;
    @FXML
    private AnchorPane zoneDessin;
    @FXML
    private Button annulerBtn;
    @FXML
    private MenuItem newGrapheBtn;
    @FXML
    private Label nomGraphe;
    @FXML
    private ComboBox typesGraphe;
    
    private final double RADIUS = 20.0;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            ToggleGroup group = new ToggleGroup();
            noeudBtn.setToggleGroup(group);
            selectionBtn.setToggleGroup(group);
            lienBtn.setToggleGroup(group); 
        } catch (NullPointerException e) {
            /* Si nouvelle fenetre diff de accueil*/
        }
        
        try{
            typesGraphe.getItems().addAll(factoryManager.factories.keySet());
        } catch (Exception e) {
            //TODO
        }
        
    }  
    
    //TODO delacer methode dessin dans les noeuds et lien
    @FXML
    private void dessin(javafx.scene.input.MouseEvent evt) {        
        try {
            if (selectionBtn.isSelected()) {
            //TODO
            } else if(noeudBtn.isSelected()) {

                Noeud  noeud = factory.creerNoeud(evt.getX(), evt.getY());
                graphe.ajouterNoeud(noeud);
                
                /* cercle */
                Circle cercle = new Circle(evt.getX(), evt.getY(), RADIUS);
                cercle.setFill(Color.TRANSPARENT);
                cercle.setStroke(Color.BLACK);
                cercle.setOnMouseClicked((new EventHandler<MouseEvent>() {
 
                    @Override
                    public void handle(MouseEvent evt) {
                        if (source == null) {
                            source = noeud;
                        } else {
                            cible = noeud;
                        }
                    }
                }));
                
                /* label */
                Label libelle = new Label(noeud.getLibelle());
                libelle.setLayoutX(evt.getX());
                libelle.setLayoutY(evt.getY());
                zoneDessin.getChildren().addAll(cercle, libelle);

            } else if(lienBtn.isSelected()){
                Lien lien = factory.creerLien(source, cible);
                graphe.ajouterLien(lien);
                Line ligne;
                
                double l = Math.sqrt( Math.pow(source.getX()- cible.getX(), 2) + Math.pow(source.getY()- cible.getY(), 2));
                
                double xSource = source.getX() + (cible.getX() - source.getX()) / l * RADIUS;
                double ySource = source.getY() + (cible.getY() - source.getY()) / l * RADIUS;
                
                double xCible = cible.getX() + (source.getX() - cible.getX()) / l * RADIUS;
                double yCible  = cible.getY() + (source.getY() - cible.getY()) / l * RADIUS;
                
                
                ligne = new Line( xCible, yCible, xSource, ySource);
                
                cible = null;
                source = null;
                zoneDessin.getChildren().addAll(ligne);
            }
        } catch (Exception  e) {
            //Si graphe inexistant
        }
        
    }
    
    /* Création d'un nouveau graphe */
    @FXML
    private void nouveauGraphe() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLNouveauGraphe.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Nouveau graphe");
        stage.setScene(new Scene(root));  
        stage.show();
    }
    
    @FXML
    private void fermeFenetre() {
        Stage stage = (Stage) annulerBtn.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void confirmeNouveauGraphe() {
        
        String type = (String) typesGraphe.getValue();
        String nom = nomGraphe.getText();
        fermeFenetre();
        try {
            factory = factoryManager.getInstance().getFactoryGraphe(type);
            graphe = factory.creerGraphe(nom);
            System.out.println("Creation du nouveau graphe : " + nom);
        } catch (Exception e) {
            
        }
    }
    
    
}    