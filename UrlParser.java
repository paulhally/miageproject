package javaRobotSelenium;
import java.io.*;

/**
 * Created by Master on 25/01/2015.
 */
public class UrlParser {
    String URL;
    public UrlParser(){
    }
    public static void main (String[] args){
        String url = "https://www.google.fr/search?q=parser+avec+string+decode&ie=utf-&oe=utf-&gws_rd=cr &ei=j_FVLeIIiUf  PYgAF#q=parse r+u ne+URL+en+java";
        // supprimer
        // Permet de savoir si la chaine contient un nombre Convertir le String en tableau de char avec String.toCharArray()
        char[] stringToCharArray = url.toCharArray();

        // On split l'ULR par rapport à "&" "/" "+" et "=" et " "
        String[] split_and = url.split("&");

        // instanciation du fileWriter fW et du File split_andTXT
        File split_andTXT = new File("F:\\boulot\\workspace\\javaRobotSelenium\\Docs", "split_and.txt");
        FileWriter fW = null;

        if(split_andTXT.exists() && split_andTXT.isFile()){
            System.out.println("fichier de sortie split_andTXT ok");
        }
        String[] splitSlash = null;
        //on split par rapport à "/" chaque case du tableau split_and pour chaque case
        // de split_and, un tableau splitSlash est créé
        for(int i = 0; i < split_and.length; i++ ){
            splitSlash = split_and[i].split("/");
            for(int j = 0; j < splitSlash.length; j++){
                try{
                    System.out.println("on est ds la case i= " + i + " et ds la case j " + j);
                    fW = new FileWriter(split_andTXT, true);
                    fW.write(splitSlash[j]);
                    //fW.write(splitSlash[i]);
                }catch (IOException e){
                    System.out.println("erreur " + e.getMessage());
                }
            }
        }
    }

    public static boolean contientChiffre(char[] stringToCharArray){
        boolean presenceNombre = false;
        for (int i = 0; i < stringToCharArray.length; i++){
            System.out.println("indice du char: " + (int)stringToCharArray[i] + " valeur du char  " + stringToCharArray[i]);
            if ((int)stringToCharArray[i] == 48 |  (int)stringToCharArray[i] == 49 | (int)stringToCharArray[i] == 50 | (int)stringToCharArray[i] == 51 | (int)stringToCharArray[i] == 52
                    | (int)stringToCharArray[i] == 53 | (int)stringToCharArray[i] == 54 | (int)stringToCharArray[i] == 55 | (int)stringToCharArray[i] == 56
                    | (int)stringToCharArray[i] == 57){
                presenceNombre = true;
            }

        }
        return presenceNombre;
    }

}

