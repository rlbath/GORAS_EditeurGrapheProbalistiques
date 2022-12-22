package traitement;

//import java.util.ArrayList;

import java.io.Serializable;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;

//import java.util.List;
public abstract class Noeud implements Serializable{
    
    /* Libelle du noeud */
    String libelle;
    
    /* Coordonnée d'un noeud */
    double coordX;
    double coordY;
    
    /* Rayon des cercle représentant un noeud */
    private static final double RADIUS = 30.0;
    
    //public List<ElementGraphe> elementGraphe = new ArrayList<ElementGraphe> ();

    public Noeud(String libelle, double coordX, double coordY) {
       this.libelle = libelle;
       this.coordX = coordX;
       this.coordY = coordY;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public void setLibelle(String value) {
        this.libelle = value;
    }
    
    public double getX() {
        return coordX;
    }
    
    public void setX(Double value) {
        coordX = value;
    }

    public double getY() {
        return coordY;
    }

    public void setY(Double value) {
        coordY = value;
    }
    
     public static double getRadius() {
        return RADIUS;
    }
    
    public int getId() {
        return 0;
    }
    
    public Group dessinerNoeud(AnchorPane zoneDessin) {
       return null;
   }
    
    @Override
    public String toString() {
        String noeud = libelle + " X: " + coordX + " Y :" + coordY;
        return noeud;
        
    }

}
