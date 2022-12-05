package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
    private ComboBox typesGraphe;
    
    private static final double RADIUS = 20.0;

    @FXML
    private TextField nomGraphe;
    
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
            typesGraphe.getItems().addAll(factoryManager.getFactories().keySet());
        } catch (Exception e) {
            //TODO
        }
        
    }  

    @FXML
    private void dessin(javafx.scene.input.MouseEvent evt) {        
        try {
            if (selectionBtn.isSelected()) {
            //TODO
            } else if(noeudBtn.isSelected()) {

                Noeud  noeud = factory.creerNoeud(evt.getX(), evt.getY(), zoneDessin);
                graphe.ajouterNoeud(noeud);
                
            } else if(lienBtn.isSelected()){
                
                if (graphe.liens.isEmpty() ) {
                    for (Lien lienATester : graphe.liens) {
                        Noeud sourceATester = lienATester.getSource();
                        Noeud cibleATester = lienATester.getCible();

                        if (source != null && cible != null) {

                            if (source != null && cible != null 
                                && (source == sourceATester && cible == cibleATester 
                                    || source == cibleATester && cible == sourceATester)) {
                                
                                System.out.println("Impossible de dessiner un lien deja existant");
                            } else {
                                Lien lien = factory.creerLien(source, cible, zoneDessin);
                                graphe.ajouterLien(lien);
                            }
                        }
                    }
                } else {
                    Lien lien = factory.creerLien(source, cible, zoneDessin);
                    graphe.ajouterLien(lien);
                }
            }
        } catch (Exception  e) {
            //TODO indique l'erreur
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
        //TODO Empecher validation si nom vide ou type vide
    }
    
    
    public static double getRadius() {
        return RADIUS;
    }
}    