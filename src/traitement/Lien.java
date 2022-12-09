package traitement;

//import java.util.ArrayList;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

//import java.util.List;

public abstract class Lien {
    
    private final Noeud source;

    private final Noeud cible;

    //public List<ElementGraphe> elementGraphe = new ArrayList<ElementGraphe> ();

    public Lien(Noeud source, Noeud cible) {
        this.source = source;
        this.cible = cible;
    }
    
    public Noeud getSource() {
        return source;
    }
    
    public Noeud getCible() {
        return cible;
    }

    @Override
    public String toString() {
        String lien = "Source :" + source + " Cible: " + cible;
        return lien;
    }
    
    
    public static Line dessinerLien(AnchorPane zondeDessin,Noeud source, Noeud cible) {
        return null;
    }
}
