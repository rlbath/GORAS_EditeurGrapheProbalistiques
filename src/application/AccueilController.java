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
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static traitement.Arete.dessinerLien;

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
    
    private static final double RADIUS = 30.0;
    
    /* pour le dessin d'un lien */
    public static Noeud noeudSource = null;
    public static boolean isDrawable = true;
    
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
    private ComboBox typesGraphe;
    @FXML
    private MenuItem AideManipGraphe;
    @FXML
    private TextField nomGraphe;
    
    private Line ligneEnCours = null;
    
    
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
            if (selectionBtn.isSelected()) { //Cas si on selectione l'option selection
            //TODO
            } else if(noeudBtn.isSelected()) { //Cas si on selectione l'option noeud
                
                if (isDrawable == true) {
                    Noeud  noeud = factory.creerNoeud(evt.getX(), evt.getY(), zoneDessin);
                    graphe.ajouterNoeud(noeud);
                }
                isDrawable = true;
                
            } else if(lienBtn.isSelected()){ //Cas si on selectione l'option lien

                /*
                if (!graphe.liens.isEmpty() ) {
                    for (Lien lienATester : graphe.liens) {
                        Noeud sourceATester = lienATester.getSource();
                        Noeud cibleATester = lienATester.getCible();

                        if (noeudSource != null && noeudCible != null 
                            && (noeudSource == sourceATester && noeudCible == cibleATester 
                            || noeudSource == cibleATester && noeudCible == sourceATester)) {
                            
                            System.out.println("Impossible de dessiner un lien deja existant");
                        } else {
                            Lien lien = factory.creerLien(noeudSource, noeudCible);
                            graphe.ajouterLien(lien);
                            dessinerLien(zoneDessin, noeudSource, noeudCible);
                        }
                 
                    }
                } else {
                    Lien lien = factory.creerLien(noeudSource, noeudCible);
                    graphe.ajouterLien(lien);
                    dessinerLien(zoneDessin, noeudSource, noeudCible);
                }
                */
            }
        } catch (Exception  e) {
            //TODO indique l'erreur
        }
        
    }
    
    /* 
     * Création d'un nouveau graphe 
     * Ouverture de la fenetre de selection de type
     */
    @FXML
    private void nouveauGraphe() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLNouveauGraphe.fxml"));
        Stage nouveauGrapheStage = new Stage();
        nouveauGrapheStage.initModality(Modality.APPLICATION_MODAL);
        nouveauGrapheStage.setTitle("Nouveau graphe");
        nouveauGrapheStage.setScene(new Scene(root));  
        nouveauGrapheStage.show();
    }
    
    @FXML
    private void afficheAideManipGraphe() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLAideManipGraphe.fxml"));
        Stage afficheAideManipGraphe = new Stage();
        afficheAideManipGraphe.initModality(Modality.APPLICATION_MODAL);
        afficheAideManipGraphe.setTitle("Aide Manipulation d'un Graphe");
        afficheAideManipGraphe.setScene(new Scene(root));  
        afficheAideManipGraphe.show();
    }
    
    @FXML
    private void aficheAideCreaGraphe() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLAideCreaGraphe.fxml"));
        Stage aficheAideCreaGraphe = new Stage();
        aficheAideCreaGraphe.initModality(Modality.APPLICATION_MODAL);
        aficheAideCreaGraphe.setTitle("Aide Création d'un Graphe");
        aficheAideCreaGraphe.setScene(new Scene(root));  
        aficheAideCreaGraphe.show();
    }
    
    @FXML
    private void aficheAideMenu() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLAideMenus.fxml"));
        Stage aficheAideCreaGraphe = new Stage();
        aficheAideCreaGraphe.initModality(Modality.APPLICATION_MODAL);
        aficheAideCreaGraphe.setTitle("Aide navigation dans les menus");
        aficheAideCreaGraphe.setScene(new Scene(root));  
        aficheAideCreaGraphe.show();
    }
    
    @FXML
    private void fermeFenetre() {
        Stage stage = (Stage) annulerBtn.getScene().getWindow();
        stage.close();
    }
    /* 
     * Verification des infos saisi
     * Creation d'un objet graphe correspondant au type selectionne   
     */
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

    @FXML
    private void zoneDessinMouseDragged(MouseEvent evt) {
        
        if (lienBtn.isSelected()) {

            double x = evt.getX();
            double y = evt.getY();            

            if (graphe.estNoeudGraphe(x, y) != null && ligneEnCours == null) {
                
                noeudSource = graphe.estNoeudGraphe(x, y);
                System.out.println("Drag noeudSource ok");
                ligneEnCours = dessinerLien(zoneDessin, noeudSource, noeudSource);
                System.out.println(graphe.estNoeudGraphe(x, y));
                System.out.print(ligneEnCours);
                System.out.println(noeudSource);
                
                
            } else if (noeudSource != null) {
                System.out.println("noeudSource set");
                Noeud noeudProvisoire = factory.creerNoeud(evt.getX(), evt.getY(), zoneDessin);
                ligneEnCours = dessinerLien(zoneDessin, noeudSource, noeudProvisoire);                
            }
            
        }
    }

    @FXML
    private void zoneDessinMouseReleased(MouseEvent event){
        
        if (lienBtn.isSelected()) {
            Noeud noeudCible = graphe.estNoeudGraphe(ligneEnCours.getEndX(), ligneEnCours.getEndY());
            System.out.println(noeudCible);
            if (noeudCible != null) {
                try{
                    graphe.ajouterLien(factory.creerLien(noeudSource, noeudCible));
                    dessinerLien(zoneDessin,noeudSource,noeudCible);
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
                
            }
            zoneDessin.getChildren().remove(ligneEnCours);
            ligneEnCours = null;
        }
    }
}    