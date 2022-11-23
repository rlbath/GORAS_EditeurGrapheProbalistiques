package traitement;


public class NoeudSimple extends Noeud {
    
    public static int cpt = 0;
    
    public NoeudSimple(double coordX, double coordY) {
        super(Integer.toString(cpt+=1), coordX, coordY);
    }

}
