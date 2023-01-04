/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traitement;

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
    
}
