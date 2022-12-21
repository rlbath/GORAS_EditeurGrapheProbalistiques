package application;

import exceptions.TypeGrapheFactoryException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
import javafx.stage.Modality;
import javafx.stage.Stage;


import traitement.FactoryGraphe;
import traitement.FactoryManager;
import traitement.Graphe;
import traitement.Noeud;
import traitement.NoeudSimple;

import static traitement.NoeudSimple.dessinerNoeud;
import traitement.Lien;

/**
 *
 * @author GORAS
 */
public class AccueilController implements Initializable {
    
    public static FactoryManager factoryManager = new FactoryManager();
    
    public static FactoryGraphe factory;
    
    /* Instance du graphe en cours de traitement */
    public static Graphe graphe;
    
    /* Pour le dessin d'un lien */
    public static Noeud noeudCible;
    public static Noeud noeudSource;
    
    /* Boolean permettant la transmission d'information entre AccueilController
     *  et les actions des elements de l'interface graphique
     */
    public static boolean isDrawable = true;
    public static boolean estLien = false;
    
    /* Pour le dessin d'un lien ainsi que pour sa modification */
    private Lien lienEnCours;
    public static Group lienEnCoursGroup;
    
    //Element de l'interface graphique
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
    @FXML
    private AnchorPane modificationContainer;

    
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
                
                /* Recuperation du lien selectionner */
                lienEnCours = graphe.getLienDuGraphe(noeudSource, noeudCible);
                lienEnCours.proprieteLien(modificationContainer, graphe, zoneDessin, lienEnCoursGroup);
                /* Reinitialisation des valeurs */
                lienEnCours = null;
                lienEnCoursGroup = null;
                noeudSource = null;
                noeudCible = null;
                
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

    @FXML
    private void zoneDessinMouseDragged(MouseEvent evt) {
        
        if (lienBtn.isSelected()) {
            
            int compteurNoeud = NoeudSimple.cpt;

            double x = evt.getX();
            double y = evt.getY();            


            if (graphe.estNoeudGraphe(x, y) != null && lienEnCours == null) {
                noeudSource = graphe.estNoeudGraphe(x, y);
                lienEnCours = factory.creerLien(noeudSource, noeudSource);
                lienEnCoursGroup = lienEnCours.dessinerLien(zoneDessin);
                //zoneDessin.getChildren().add(lienEnCoursGroup);

            } else if (noeudSource != null && lienEnCours != null) {
                zoneDessin.getChildren().remove(lienEnCoursGroup);
                Noeud noeudProvisoire = factory.creerNoeud(evt.getX(), evt.getY());
                lienEnCours = factory.creerLien(noeudSource, noeudProvisoire);
                lienEnCoursGroup = lienEnCours.dessinerLien(zoneDessin);
                NoeudSimple.cpt = compteurNoeud;
            }

        }
    }

    @FXML
    private void zoneDessinMouseReleased(MouseEvent evt){
        
        if (lienBtn.isSelected() && lienEnCours != null || lienBtn.isSelected() && lienEnCours != null) {
            Noeud noeudCible = graphe.estNoeudGraphe(evt.getX(), evt.getY());          

            if (noeudCible != null && !graphe.estLienDuGraphe(noeudSource, noeudCible)) {
                try{
                    lienEnCours = factory.creerLien(noeudSource, noeudCible);                    
                    graphe.ajouterLien(lienEnCours);                    
                    lienEnCours.dessinerLien(zoneDessin);
                    System.out.println(graphe.getLiens().toString());
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }

            zoneDessin.getChildren().remove(lienEnCoursGroup);
            lienEnCours = null;
            lienEnCoursGroup = null;
            noeudSource = null;

        }
    }
    
    @FXML
    private void unDo() {
        try {
            System.out.println("Noeuds : " + graphe.noeuds);
            graphe.archiveNoeud.add(graphe.noeuds.get(graphe.noeuds.size() - 1));
            graphe.noeuds.remove(graphe.noeuds.get(graphe.noeuds.size() - 1));
            System.out.println("Noeuds après remove : " + graphe.noeuds);
            zoneDessin.getChildren().remove(graphe.noeuds.size());
            System.out.println("archive noeud à la fin du undo" + graphe.archiveNoeud);

            /* 
            System.out.println("Liens : " + graphe.liens);
            graphe.archiveReDo.add(graphe.liens.get(graphe.liens.size() - 1));
            graphe.liens.remove(graphe.liens.get(graphe.liens.size() - 1));
            zoneDessin.getChildren().remove(graphe.liens.size());
            System.out.println("Liens après remove : " + graphe.liens);
            */

        } catch (Exception e) {
            System.err.println("UnDo impossible"); 
        }
        
    }
    
    @FXML
    private void reDo() {
        try {
            graphe.noeuds.add(graphe.archiveNoeud.get(graphe.archiveNoeud.size() - 1));
            System.out.println("Noeud après ajout de l'archive" + graphe.noeuds);
            dessinerNoeud(zoneDessin, graphe.archiveNoeud.get(graphe.archiveNoeud.size() - 1));
            System.out.println("Archive noeud avant remove" + graphe.archiveNoeud);
            graphe.archiveNoeud.remove(graphe.archiveNoeud.get(graphe.archiveNoeud.size() - 1));
            System.out.println("Archive noeud après remove" + graphe.archiveNoeud);
        } catch (Exception e) {
            System.err.println("ReDo impossible"); 
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
    
    
    @FXML
    private void enregistrerSous() {
        System.out.println("enregistrer sous");
        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("graphe.dat"));
            output.writeObject(graphe);
            output.writeObject(factory);
            output.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        
    }
    
    @FXML
    private void ouvrir() {
        System.out.println("ouvrir");
        try {
            //Recuperation des objets dans le fichier
            ObjectInputStream input = new ObjectInputStream(new FileInputStream("graphe.dat"));
            graphe = (Graphe) input.readObject();
            factory = (FactoryGraphe) input.readObject();
            
            //Dessin du graphe choisi
            for (Noeud noeud : graphe.getNoeuds()) {
                dessinerNoeud(zoneDessin, noeud);
            }
            for (Lien lien : graphe.getLiens()) {
                lien.dessinerLien(zoneDessin);
            }
            
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
}    