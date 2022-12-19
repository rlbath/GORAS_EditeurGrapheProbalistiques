package traitement;

import application.AccueilController;
import java.util.ArrayList;
import java.util.List;
import traitement.NoeudSimple;

public abstract class Graphe {
    public String libelle;

    public List<Noeud> noeuds;

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
   
    public void ajouterNoeud(Noeud noeud) {
        noeuds.add(noeud);
    }

    public void ajouterLien(Lien lien) {
        liens.add(lien);
    }
    /* TODO a mettre dans graphe proba, orienté
    public void ajouterTraitement(Traitement traitement) {
        traitements.add(traitement);
    }*/
    
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
     * 
     * @param noeudATester
     * @param noeudATester2
     * @return 
     */
    public boolean estArcDuGraphe(Noeud noeudATester, Noeud noeudATester2) {
        for (Lien lien : liens) {
            if (lien.getSource() == noeudATester && lien.getCible() == noeudATester2) {
                return true;
            }
        }
        return false;
    }
    
    
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

}