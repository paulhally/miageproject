package Robot;

/**
 * Created by Master on 08/03/2015.
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.*;
import de.linguatools.disco.*;

public class CheckUrlByContains extends LinkSelector {

    private String[] keyWords;

    public CheckUrlByContains(String[] keyWords){
        super();
        /*String tab[] = new String[100];
        tab[0] = "common";
        this.keyWords = tab;*/
        //this.keyWords = super.keyWords;

        this.keyWords = keyWords;
        /*String[] toto = {"toto"};
        this.keyWords = toto;*/
        //System.out.println(this.keyWords[0]);

    }

    /*
    * lieu ipmplementation:
    * role:
    * */
    public Object[][] checkUrlContains(List <String> TableauLiens){

        int counter = 0;
        for(int i = 0; i < keyWords.length; i++){
            System.out.println(keyWords[i]);
        }
        Object[][] lienRanking = new Object[TableauLiens.size()][2];
        for(int j = 0; j< TableauLiens.size(); j++) { // parcours du tableau de lien
            counter = 0;
            for (int z = 0; z < keyWords.length; z++) { // parcours des mots clés
                if (TableauLiens.get(j).contains(keyWords[z])) {
                    counter++;
                }
            }
            lienRanking[j][0] = TableauLiens.get(j);
            lienRanking[j][1] = new Integer(counter);
        }
        return lienRanking;
    }
}
