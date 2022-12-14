package application;

import exceptions.TypeGrapheFactoryException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;


import traitement.FactoryGraphe;
import traitement.FactoryManager;
import traitement.Graphe;
import traitement.Noeud;
import traitement.NoeudSimple;
import traitement.Arc;
import traitement.Arete;

import static traitement.NoeudSimple.dessinerNoeud;

import traitement.FactoryGrapheSimpleNonOriente;
import traitement.FactoryGrapheSimpleOriente;

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
    private TextField nomGraphe;

    
    private Line ligneEnCours = null;
    private Group arcEnCours = null;
    
    
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
                    Noeud  noeud = factory.creerNoeud(evt.getX(), evt.getY());
                    graphe.ajouterNoeud(noeud);
                    dessinerNoeud(zoneDessin, noeud);
                }
                isDrawable = true;
                
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
        } catch (TypeGrapheFactoryException e) {
            
        }
        //TODO Empecher validation si nom vide ou type vide
    }
    
    
    public static double getRadius() {
        return RADIUS;
    }

    @FXML
    private void zoneDessinMouseDragged(MouseEvent evt) {
        
        if (lienBtn.isSelected()) {
            
            int compteurNoeud = NoeudSimple.cpt;

            double x = evt.getX();
            double y = evt.getY();            
            
            // Si le graphe est une instance de graphe simple non oriente
            if (factory.getClass() == new FactoryGrapheSimpleNonOriente().getClass()) { 
                
                if (graphe.estNoeudGraphe(x, y) != null && ligneEnCours == null) {
                    noeudSource = graphe.estNoeudGraphe(x, y);
                    ligneEnCours = Arete.dessinerLien(zoneDessin, noeudSource, noeudSource);
                
                } else if (noeudSource != null && ligneEnCours != null) {
                    zoneDessin.getChildren().remove(ligneEnCours);
                    Noeud noeudProvisoire = factory.creerNoeud(evt.getX(), evt.getY());
                    ligneEnCours = Arete.dessinerLien(zoneDessin, noeudSource, noeudProvisoire);
                    NoeudSimple.cpt = compteurNoeud;
                }
                
            } else if (factory.getClass() == new FactoryGrapheSimpleOriente().getClass()) {
                
                if (graphe.estNoeudGraphe(x, y) != null && arcEnCours == null) {
                    noeudSource = graphe.estNoeudGraphe(x, y);
                    arcEnCours = Arc.dessinerLien(zoneDessin, noeudSource, noeudSource);
                    
                    
                } else if (noeudSource != null && arcEnCours != null) {
                    // Supression de l'arc en cours
                    zoneDessin.getChildren().remove(arcEnCours);
                    Noeud noeudProvisoire = factory.creerNoeud(evt.getX(), evt.getY());
                    arcEnCours = Arc.dessinerLien(zoneDessin, noeudSource, noeudProvisoire);
                    NoeudSimple.cpt = compteurNoeud;
                }
            }
            
            
            
            
            
        }
    }

    @FXML
    private void zoneDessinMouseReleased(MouseEvent evt){
        
        if (lienBtn.isSelected() && ligneEnCours != null || lienBtn.isSelected() && arcEnCours != null) {
            Noeud noeudCible = graphe.estNoeudGraphe(evt.getX(), evt.getY());          
            
            // Si le graphe est une instance de graphe simple non oriente
            if (factory.getClass() == new FactoryGrapheSimpleNonOriente().getClass()) { 
                
                if (noeudCible != null && !graphe.estAreteDuGraphe(noeudSource, noeudCible)) {
                    try{
                        graphe.ajouterLien(factory.creerLien(noeudSource, noeudCible));
                        Arete.dessinerLien(zoneDessin,noeudSource,noeudCible);
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                }
                zoneDessin.getChildren().remove(ligneEnCours);
                ligneEnCours = null;
                noeudSource = null;
                
            } else if (factory.getClass() == new FactoryGrapheSimpleOriente().getClass()) {
             
                if (noeudCible != null) {
                    try{
                        graphe.ajouterLien(factory.creerLien(noeudSource, noeudCible));
                        Arc.dessinerLien(zoneDessin,noeudSource,noeudCible);
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                }
                zoneDessin.getChildren().remove(arcEnCours);
                arcEnCours = null;
                noeudSource = null;
                
            }
        }
    }
    
    @FXML
    private void unDo() {
        try {
            System.out.println(graphe.noeuds);
            //archiveNoeud.add(noeuds.get(noeuds.size() - 1));
            // graphe.archiveNoeud.add(graphe.noeuds.size() - 1));
            graphe.noeuds.remove(graphe.noeuds.get(graphe.noeuds.size() - 1));
            System.out.println(graphe.noeuds);

            zoneDessin.getChildren().remove(graphe.noeuds.size());
        } catch (Exception e) {
            System.err.println("UnDo sur un noeud impossible"); 
        }
        
    }
    
    @FXML
    private void reDo() {
        try {
            System.out.println(graphe.noeuds);
            //archiveNoeud.add(noeuds.get(noeuds.size() - 1));
            graphe.noeuds.remove(graphe.noeuds.get(graphe.noeuds.size() - 1));
            System.out.println(graphe.noeuds);
            zoneDessin.getChildren().remove(graphe.noeuds.size());
        } catch (Exception e) {
            System.err.println("UnDo sur un noeud impossible"); 
        }
        
    }

    // Supprime le dernier lien crée puis l'ajoute dans l'arrayList archiveLien
    public void undoLien() {
        try {
            System.out.println(graphe.liens);
            //archiveLien.add(liens.get(liens.size() - 1));
            graphe.liens.remove(graphe.liens.get(graphe.liens.size()));
            System.out.println(graphe.liens);
            zoneDessin.getChildren().remove(graphe.liens.size());
        } catch (Exception e) {
            System.err.println("UnDo sur un noeud impossible"); 
        }
    }
}    