/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traitement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author remi.jauzion
 */
public class TraitementProbabiliste extends Traitement {
    
    public GrapheProbabiliste graphe;
    
    public TraitementProbabiliste(Graphe graphe) {
        super(graphe);
        this.graphe = (GrapheProbabiliste) graphe;
    }
    
    public void matriceTransition(){
        
        //Création de la matrice
        double[][] mat = new double [graphe.getNoeuds().size()][graphe.getNoeuds().size()];
        for(int i = 0; i < mat.length; i++){
            mat[i] = new double[graphe.getNoeuds().size()];
        }
        
        for (int i = 0; i<graphe.getNoeuds().size(); i++){      
            for (int j = 0; j<graphe.getNoeuds().size(); j++){
                if(graphe.getLienDuGraphe(graphe.getNoeuds().get(i), graphe.getNoeuds().get(j)) != null){
                    mat[i][j] = graphe.getLienDuGraphe(graphe.getNoeuds().get(i), graphe.getNoeuds().get(j)).getPonderation();
                }else {
                    mat[i][j] = 0.0;
                    
                }
                System.out.print(mat[i][j] + "  ");
            }
            System.out.println();
            System.out.println();
        }
    }
    
/*    
    public boolean testCheminValide(Noeud noeud1, Noeud noeud2, List chemin) {
        boolean cheminValide = false;
        chemin.add(noeud1.libelle);
        for (int i = 0 ; i < graphe.liens.size() ; i++) {
            if (graphe.liens.get(i).source == noeud1 && graphe.liens.get(i).cible == noeud2) {
                chemin.add(graphe.liens.get(i).cible.libelle);
                cheminValide = true;
            }else if (graphe.liens.get(i).source == noeud1) {
                testCheminValide(graphe.liens.get(i).cible, noeud2, chemin);
            }
        }
        return cheminValide;
    }
    
    public void testExistenceChemin(Noeud noeud1, Noeud noeud2) {
        for (int i = 0 ; i < graphe.liens.size() ; i++) {
            if (graphe.liens.get(i).source == noeud1 && graphe.liens.get(i).cible == noeud2) {
                System.out.println(graphe.liens.get(i).source.libelle + "," + graphe.liens.get(i).cible.libelle + " ; ");
            } else if (graphe.liens.get(i).source == noeud1) {
                List<Lien> chemin = new ArrayList<>();
                System.out.print(graphe.liens.get(i).source.libelle + "," + graphe.liens.get(i).cible.libelle + " ; ");
                if (testCheminValide(graphe.liens.get(i).cible , noeud2, chemin) == true) {
                    System.out.println(chemin);
                }
                
            }
        }
    }
    */
    
    public void loiDeProbabiliteEnNTransitions(int n) {
        //Création de la matrice
        double[][] mat = new double [graphe.getNoeuds().size()][graphe.getNoeuds().size()];
  /* ---- A quoi sert la boucle sui suit puisque les lignes de la matrice sont déja crées juste avant ? */
        for(int i = 0; i < mat.length; i++){
            mat[i] = new double[graphe.getNoeuds().size()];
        }
  /* ---- Dans les 2 boucles qui suivent on remplit la matrice avec les valeurs des probabilités en mettant 0.0 si pas d'arc ?  */
        for (int i = 0; i<graphe.getNoeuds().size(); i++){      
            for (int j = 0; j<graphe.getNoeuds().size(); j++){
                if(graphe.getLienDuGraphe(graphe.getNoeuds().get(i), graphe.getNoeuds().get(j)) != null){
                    mat[i][j] = graphe.getLienDuGraphe(graphe.getNoeuds().get(i), graphe.getNoeuds().get(j)).getPonderation();
                }else {
                    mat[i][j] = 0.0;
                    
                }
  /* ---- Est-ce les valeurs affichées par l'instruction qui suit sont correctes ? */
                System.out.print(mat[i][j] + "  ");
            }
            System.out.println();
            System.out.println();
        }
  
        //multiplication de la matrice
        double[][] nouvelleMat = new double [graphe.getNoeuds().size()][graphe.getNoeuds().size()];
 /* ---- Pour comprendre ce que vous chercher à calculer il faut me donner la formule mathématique du calcul matriciel ! */
        for (int t = 1 ; t < n-1 ; t++) {
            for (int i = 0; i < mat.length; i++) {
                for (int j = 0 ; j < mat.length ; j++) {
                    for (int x = 0 ; x < mat.length ; x++) {
                        nouvelleMat[i][j] += mat[i][x]*mat[x][j];
                    }
                }
            }
        }
    }
}
