package traitement;

import application.AccueilController;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.ComboBox;

public abstract class Graphe {
    
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

    /**
     * Ajoute un lien au graphe
     * @param lien , lien a ajouter au graphe
     */
    public void ajouterLien(Lien lien) {
        liens.add(lien);
    }
    
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
    
    /**
     * @return la liste des liens du graphe
     */
    public List<Lien> getLiens() {
        return liens;
    }
    
    /**
     * Determine si des coordonnées font partie d'un noeud du graphe
     * @param xATester
     * @param yATester
     * @return true si les coordonnées en paramètre corresponde à un noeud, false sinon
     */
    public Noeud estNoeudGraphe(double xATester ,double yATester) {
        
        for(Noeud noeud : noeuds) {
            
            double minX = noeud.getX() - AccueilController.getRadius();
            double maxX = noeud.getX() + AccueilController.getRadius();
            double minY = noeud.getY() - AccueilController.getRadius();
            double maxY = noeud.getY() + AccueilController.getRadius();
            
            if (minX < xATester && xATester < maxX && minY < yATester && yATester < maxY) {
                return noeud;   
            }
        }
        
        return null;
    }

    /**
     * Determine si deux noeuds forment une arete du graphe
     * @param noeudATester
     * @param noeudATester2
     * @return true si les deux noeuds forment une arete, false sinon
     */
    public boolean estAreteDuGraphe(Noeud noeudATester, Noeud noeudATester2) {
        
        for (Lien lien : liens) {
            if (lien.getSource() == noeudATester && lien.getCible() == noeudATester2 
                || lien.getSource() == noeudATester2 && lien.getCible() == noeudATester) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Determine si deux noeuds forment un arc du graphe
     * @param noeudATester
     * @param noeudATester2
     * @return true si les deux noeuds forment un arc, false sinon
     */
    public boolean estArcDuGraphe(Noeud noeudATester, Noeud noeudATester2) {
        for (Lien lien : liens) {
            if (lien.getSource() == noeudATester && lien.getCible() == noeudATester2) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Determine si une arete existe entre deux noeuds 
     * @param sourceATester source ou cible potentielle de l'arete (arete non oriente)
     * @param cibleATester  cible ou source potentielle de l'arete (arete non oriente)
     * @return une arete du graphe a partir de deux noeuds si elle existe, sinon null
     */
    public Lien getAreteDuGraphe(Noeud sourceATester, Noeud cibleATester) {
                
        for (Lien lien : liens) {
            if ((lien.getSource() == sourceATester && lien.getCible() == cibleATester )
                || (lien.getSource() == cibleATester && lien.getCible() == sourceATester)) {
                return lien;
            }
        }
        return null;
    }
    
    
    @Override
    public String toString() {
        
        String tout = "nom : " + libelle + "   noeuds : " + noeuds.toString() + "   liens : " + liens.toString();
        return tout;
    }

    /**
     * Supprime un lien du graphe en fonction de 2 libelles de noeuds
     * @param noeudsSource combobox contenant le libelle de la source du lien a supprimer
     * @param noeudsCible ombobox contenant le libelle de la cible du lien a supprimer
     */
    public void supprimerArete(ComboBox noeudsSource, ComboBox noeudsCible) {
    
        String libelleNoeudSource = (String) noeudsSource.getValue();
        String libelleNoeudCible = (String) noeudsCible.getValue();
        Noeud noeudSource = null; //Init pour le compilateur
        Noeud noeudCible = null;
        
        //Recuperation des noeuds source et cible en fonction des libelles
        for (Noeud noeud : getNoeuds()) {
            
            if (noeud.getLibelle().equals(libelleNoeudSource)) {
                noeudSource = noeud;                
            }
            if (noeud.getLibelle().equals(libelleNoeudCible)) {
                noeudCible = noeud;               
            }
        }
        Lien lienAsuppr = getAreteDuGraphe(noeudSource, noeudCible);
        liens.remove(lienAsuppr);

    }

}