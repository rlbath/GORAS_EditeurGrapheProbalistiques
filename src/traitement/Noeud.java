package traitement;

//import java.util.ArrayList;
//import java.util.List;

public abstract class Noeud {
    private String libelle;
    private double coordX;
    private double coordY;

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
        this.coordX = value;;
    }

    public double getY() {
        return coordY;
    }
    
    public void setY(Double value) {
        this.coordY = value;
    }
    
    public String toString() {
        String noeud = libelle + " X: " + coordX + " Y :" + coordY;
        return noeud;
        
    }

}
