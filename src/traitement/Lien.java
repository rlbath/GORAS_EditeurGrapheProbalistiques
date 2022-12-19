package traitement;

//import java.util.ArrayList;

import javafx.scene.Group;
//import java.util.List;

public abstract class Lien {
    
    public final Noeud source;

    public final Noeud cible;
    
    Group groupe;

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
}
