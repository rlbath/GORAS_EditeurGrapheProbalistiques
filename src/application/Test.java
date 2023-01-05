/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import traitement.NoeudSimple;

/**
 *
 * @author antoine.gouzy
 */
public class Test {
    
    public static void main(String[] args) {
        try {
            // Sérialisation XML d'un noeud dans fichier essai.xml
            XMLEncoder encoder = new XMLEncoder(new FileOutputStream("essai.xml"));
            NoeudSimple n1 = new NoeudSimple(10, 20);
            encoder.writeObject(n1);
            encoder.close();
           
            // Déserialisation et affichage du noeud pour véfification
            XMLDecoder decoder = new XMLDecoder(new FileInputStream("essai.xml"));
            NoeudSimple n2 = (NoeudSimple)decoder.readObject();
            System.out.println("n2 : x ="+n2.getCoordX()+ " y="+n2.getCoordY());
        }
        catch(FileNotFoundException e) {
            
        }
    }
    
}
