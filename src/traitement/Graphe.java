package traitement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.ComboBox;

public abstract class Graphe implements Serializable {
    
    /** Libelle du graphe */
    public String libelle;

    /** Liste des noeuds du graphe */
    public List<Noeud> noeuds;

    /** Liste des liens du graphe */
    public List<Lien> liens;

    public List<Traitement> traitements;
    // Sert pour le REDO pour récupérer le dernier noeud / lien supprimé
    public List<Noeud> archiveNoeud = new ArrayList<>();
    public List<Lien> archiveLien = new ArrayList<>();
    
    public Graphe(String libelle) {
        //TODO tester le libellé
        this.libelle = libelle;
        noeuds = new ArrayList<> ();
        liens = new ArrayList<> ();
    }
   
    /**
     * Ajoute un noeud au graphe
     * @param noeud noeud a ajouter au graphe
     */
    public void ajouterNoeud(Noeud noeud) {
        noeuds.add(noeud);
    }

    public void ajouterLien(Lien lien) { }
    
    /* TODO a mettre dans graphe proba, orienté
    public void ajouterTraitement(Traitement traitement) {
        traitements.add(traitement);
    }*/
    
    /**
     * @return la liste des noeuds du graphe
     */
    public List<Noeud> getNoeuds() {
        return noeuds;
    }
    
    public List<? extends Lien> getLiens() {
        return null;
    }
    
    public String getLibelle() {
        return libelle;
    }
    
    /**
     * Determine si des coordonnées font partie d'un noeud du graphe
     * @param xATester
     * @param yATester
     * @return true si les coordonnées en paramètre corresponde à un noeud, false sinon
     */
    public Noeud estNoeudGraphe(double xATester ,double yATester) {
        
        for(Noeud noeud : noeuds) {
            
            double minX = noeud.getX() - Noeud.getRadius();
            double maxX = noeud.getX() + Noeud.getRadius();
            double minY = noeud.getY() - Noeud.getRadius();
            double maxY = noeud.getY() + Noeud.getRadius();
            
            if (minX < xATester && xATester < maxX && minY < yATester && yATester < maxY) {
                return noeud;   
            }
        }
        
        return null;
    }

    public boolean estLienDuGraphe(Noeud noeudATester, Noeud noeudATester2) {        
        return false; //Bouchon pour compilateur
    }
    
    public Lien getLienDuGraphe(Noeud sourceATester, Noeud cibleATester) {
        return null; //Bouchon pour compilateur
    }
    
    public void supprimerLien(ComboBox noeudsSource, ComboBox noeudsCible) { }
    
    @Override
    public String toString() {
        String tout = "nom : " + libelle + "   noeuds : " + noeuds.toString() + "   liens : " + liens.toString();
        return tout;
    }   

}