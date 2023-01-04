/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traitement;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.ComboBox;

/**
 *
 * @author Gouzy
 */
public class GrapheProbabiliste extends Graphe{

     /** Libelle du graphe */
    public String libelle;

    /** Liste des noeuds du graphe */
    public List<NoeudSimple> noeuds;

    /** Liste des liens du graphe */
    public List<ArcProbabiliste> liens;
    
    /**
     * Creer une instance de graphe oriente
     * @param libelle libelle de ce graphe
     */
    public GrapheProbabiliste(String libelle) {
        super(libelle);
        this.libelle = libelle;
        noeuds = new ArrayList<> ();
        liens = new ArrayList<> ();
    }
    
    /**
     * Determine si deux noeuds forment un arc du graphe
     * @param noeudATester
     * @param noeudATester2
     * @return true si les deux noeuds forment un arc, false sinon
     */
    @Override
    public boolean estLienDuGraphe(Noeud noeudATester, Noeud noeudATester2) {
        for (Lien lien : liens) {
            if (lien.getSource() == noeudATester && lien.getCible() == noeudATester2) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Determine si un arc existe entre deux noeuds 
     * @param sourceATester source potentielle de l'arc
     * @param cibleATester  cible potentielle de l'arc
     * @return un arc du graphe a partir de deux noeuds si il existe, sinon null
     */
    @Override
    public ArcProbabiliste getLienDuGraphe(Noeud sourceATester, Noeud cibleATester) {
        
        for (ArcProbabiliste lien : liens) {
            if (lien.getSource() == sourceATester && lien.getCible() == cibleATester) {
                return lien;
            }
        }
        return null;
    }
    
    
    /**
     * Supprime un lien du graphe en fonction de 2 libelles de noeuds
     * @param noeudsSource combobox contenant le libelle de la source du lien a supprimer
     * @param noeudsCible ombobox contenant le libelle de la cible du lien a supprimer
     */
    @Override
    public void supprimerLien(ComboBox noeudsSource, ComboBox noeudsCible) {
    
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
        ArcProbabiliste lienAsuppr = getLienDuGraphe(noeudSource, noeudCible);
        liens.remove(lienAsuppr);

    }
    
    @Override
    public List<NoeudSimple> getLiensNoeud(Noeud noeudCourant) {
        List<NoeudSimple> noeudLien = new ArrayList<>();
        for (Lien lien : liens) {
            if (lien.getCible() == noeudCourant) {
                noeudLien.add((NoeudSimple) lien.getSource());
                noeudLien.add((NoeudSimple) noeudCourant);
            } else if (lien.getSource() == noeudCourant) {
                noeudLien.add((NoeudSimple) noeudCourant);
                noeudLien.add((NoeudSimple) lien.getCible());
            }
        }
        return noeudLien;
    }
    
    
    /**
     * Ajoute un lien au graphe
     * @param lien lien a ajouter au graphe
     */
    @Override
    public void ajouterLien(Lien lien) {  
        liens.add((ArcProbabiliste) lien);
    }
    
    /** @return la liste des liens de ce graphe */
    @Override
    public List<ArcProbabiliste> getLiens() {
        return liens;
    }
    
    @Override
    public String toString() {
        String tout = "nom : " + libelle + "   noeuds : " + noeuds.toString() + "   liens : " + liens.toString();
        return tout;
    }   

    
}
