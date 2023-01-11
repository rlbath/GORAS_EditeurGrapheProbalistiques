/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traitement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.control.Alert;

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
        
        String matrice = "";
        
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
                matrice += mat[i][j] + "  ";
            }
            matrice += "\n\n";
            
            
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Matrice de Transtion");
        alert.setHeaderText("Matrice de Transtion : ");
        alert.setContentText(matrice);
        alert.showAndWait();
    }
    
    public void testExistenceChemin(Noeud x, Noeud y) {
        for (int i = 0 ; i < graphe.liens.size() ; i++) {
            if (graphe.liens.get(i).getSource() == x && graphe.liens.get(i).getCible() == y) {
                System.out.println(graphe.liens.get(i));
            } else if (graphe.liens.get(i).getSource() == x) {
                System.out.println(graphe.liens.get(i));
                testExistenceChemin(graphe.liens.get(i).getCible() , y);
            }
        }
    }
    
    
    public void loiDeProbabiliteEnNTransitions(int n) {
        String matrice = "";
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
                matrice += mat[i][j] + "  ";
            }
            matrice += "\n\n";
        }
        
        //multiplication de la matrice
        String matriceApresTransition  = "";
        double[][] nouvelleMat = new double [graphe.getNoeuds().size()][graphe.getNoeuds().size()];
        for (int t = 1 ; t < n ; t++) {
            for (int i = 0; i < mat.length; i++) {
                for (int j = 0 ; j < mat.length ; j++) {
                    nouvelleMat[i][j] = 0.0;
                    for (int x = 0 ; x < mat.length ; x++) {
                        nouvelleMat[i][j] += mat[i][x]*mat[x][j];
                    }
                    if (t == n-1) {
                            matriceApresTransition += nouvelleMat[i][j] + "  ";
                    }
                }
                matriceApresTransition += "\n";
            }
            if (t ==1) {
                    mat = nouvelleMat;
            }
        }
        
        double[] loiEssai = new double [7];
        loiEssai[0] = 1.0;
        loiEssai[1] = 1.0;
        loiEssai[2] = 1.0;
        loiEssai[3] = 1.0;
        loiEssai[4] = 1.0;
        loiEssai[5] = 1.0;
        loiEssai[6] = 1.0;
        
        //définition de la loi de probabilité initiale
        double[] loiDeProba = new double[graphe.getNoeuds().size()];
        for (int index = 0 ; index < graphe.getNoeuds().size() ; index++) {
            loiDeProba[index] = loiEssai[index];
        }
        
        //multiplication de la matrice avec loi de proba
        double[] loiDeProbaFinale = new double[graphe.getNoeuds().size()];
        double valeur;
        if (n == 1) {
            for (int i = 0 ; i < loiDeProba.length; i++) {
                valeur = 0;
                for (int j = 0 ; j < loiDeProba.length ; j++) {
                    valeur += loiDeProba[i] * nouvelleMat[j][i];
                }
                loiDeProbaFinale[i] = valeur;
            }
        } else {
            for (int i = 0 ; i < loiDeProba.length; i++) {
                valeur = 0;
                for (int j = 0 ; j < loiDeProba.length ; j++) {
                    valeur += loiDeProba[i] * mat[j][i];
                }
                loiDeProbaFinale[i] = valeur;
            }
        }
        
        //passage en string pour fenetre
        String affichageLoiProba = " ";
        for (int i = 0 ; i < loiDeProbaFinale.length ; i++) {
            affichageLoiProba += loiDeProbaFinale[i] + "  ";
        }
        
        //affichage resultat
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("loi de probabilité en n transitions");
        alert.setHeaderText("loi de probabilité : ");
        alert.setContentText(affichageLoiProba);
        alert.showAndWait();
        
    }
}
