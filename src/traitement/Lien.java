package traitement;

//import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
//import java.util.List;

public abstract class Lien {
    
    /** Source du lien*/ 
    Noeud source;

    /** Cible du lien*/
    Noeud cible;
    
    /** Groupe (dessin) du lien*/
    Group groupe;

    //public List<ElementGraphe> elementGraphe = new ArrayList<ElementGraphe> ();
    
    /**
     * Creer une instance de Lien
     * @param source noeud source de l'arete
     * @param cible noeud cible de l'arete
     */
    public Lien(Noeud source, Noeud cible) {
        this.source = source;
        this.cible = cible;
        groupe = new Group();
    }
    
    /** @return la source du lien */
    public Noeud getSource() {
        return source;
    }
    
    /** @return la cible du lien */
    public Noeud getCible() {
        return cible;
    }
    
    /** @return le group du lien */
    public Group getGroup() {
        return groupe;
    }
    
    /**
     * Modifie la source du lien
     * @param nouvelleSource le noeud qui est la nouvelle source du lien
     */
    public void setSource(Noeud nouvelleSource) {
        source = nouvelleSource;
    }
    
    /**
     * Modifie la cible du lien
     * @param nouvelleCible le noeud qui est la nouvelle cible du lien
     */
    public void setCible(Noeud nouvelleCible) {
        cible = nouvelleCible;
    }
    
    /**
     * Supprime le lien de la zone de dessin
     * @param zoneDessin la zone de dessin du graphe
     */
    public void supprimer(AnchorPane zoneDessin) {
        //Suppression de ce que contient le groupe 
        getGroup().getChildren().clear();
        //Suppression du groupe sur la zone de dessin
        zoneDessin.getChildren().remove(getGroup());
        
    }
    
    
    @Override
    public String toString() {
        String lien = "Source :" + source + " Cible: " + cible + " Group: " + groupe;
        return lien;
    }
}
