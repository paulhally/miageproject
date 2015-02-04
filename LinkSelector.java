package Robot;

import de.linguatools.disco.DISCO;

import java.io.IOException;
import java.util.ArrayList;
import java.io.IOException;
import java.util.*;

import de.linguatools.disco.DISCO;
import de.linguatools.disco.DISCO.ReturnDataCommonContext;

public class LinkSelector {

    //private ArrayList<String> linkList;
    //private ArrayList<String> keyWords;
    String linkList = "garage";
    String keyWords = "voiture moto";
    private String bestLink;
    private float similarity;

    public LinkSelector(/*String linkList, String keyWords*/) {
        this.keyWords = keyWords;
        this.linkList = linkList;

        //création objet DISCO
        try {
            System.out.println("linkSelector ok");
            DISCO dis = new DISCO("F:\\boulot\\projetM1\\Jar\\fr-wikipedia-20110201-lemma", false);
            similarity = dis.firstOrderSimilarity(linkList, keyWords);
            System.out.println("les mots " + linkList +" et le mot: " + keyWords + " ont un indice de premier" +
                    "ordre de " + similarity);
        } catch (IOException e){
            System.out.println("erreur nom: " + e.getMessage());
        }
    }
    public int compareLinks(ArrayList<String> linkList, ArrayList<String> keyWords){

        return 1;
    }
}


