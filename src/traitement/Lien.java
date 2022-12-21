package traitement;

//import java.util.ArrayList;

import application.AccueilController;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

//import java.util.List;

public abstract class Lien {
    
    private Noeud source;

    private Noeud cible;

    //public List<ElementGraphe> elementGraphe = new ArrayList<ElementGraphe> ();

    public Lien(Noeud source, Noeud cible) {
        this.source = source;
        this.cible = cible;
    }
    
    public Noeud getSource() {
        return source;
    }
    
    public void  setSource(Noeud value) {
        this.source = value;
    }
    
    public Noeud getCible() {
        return cible;
    }
    
    public void  setCible (Noeud value) {
        this.cible = value;
    }

    @Override
    public String toString() {
        String lien = "Source :" + source + " Cible: " + cible;
        return lien;
    }
}
