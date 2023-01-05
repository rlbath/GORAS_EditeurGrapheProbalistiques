package traitement;

//import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
//import java.util.List;

public abstract class Noeud {
    
    /* Libelle du noeud */
    String libelle;
    
    /* Coordonnée d'un noeud */
    double coordX;
    double coordY;
    
    /** id de ce noeud utiliser pour l'ouverture d'un graphe */
    int id;
    
    /** groupe du lien */
    Group groupe;
    
    /* Rayon des cercle représentant un noeud */
    private static double radius = 30.0;
    
    //public List<ElementGraphe> elementGraphe = new ArrayList<ElementGraphe> ();

    public Noeud() {
        
    }
    
    public Noeud(String libelle, double coordX, double coordY) {
       this.libelle = libelle;
       this.coordX = coordX;
       this.coordY = coordY;
    }

    public String getLibelle() {
        return libelle;
    }

    public double getCoordX() {
        return coordX;
    }

    public double getCoordY() {
        return coordY;
    }

    public int getId() {
        return id;
    }

    public Group getGroupe() {
        return groupe;
    }

    public static double getRadius() {
        return radius;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void setCoordX(double coordX) {
        this.coordX = coordX;
    }

    public void setCoordY(double coordY) {
        this.coordY = coordY;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGroupe(Group groupe) {
        this.groupe = groupe;
    }

    public static void setRadius(double radius) {
        Noeud.radius = radius;
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
