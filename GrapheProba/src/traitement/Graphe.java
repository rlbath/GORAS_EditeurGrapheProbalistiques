package traitement;

import java.util.ArrayList;
import java.util.List;

public abstract class Graphe {
    public String libelle;

    public List<Noeud> noeuds;

    public List<Lien> liens;

    public List<Traitement> traitements;

    public Graphe(String libelle) {
        //TODO tester le libellé
        this.libelle = libelle;
        noeuds = new ArrayList<Noeud> ();
        liens = new ArrayList<Lien> ();
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
    
    
    public String toString() {
        
        String tout = "nom : " + libelle + "   noeuds : " + noeuds.toString() + "   liens : " + liens.toString();
        return tout;
    }
    
    
}
