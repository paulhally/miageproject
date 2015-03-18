package ihm;

import javax.swing.*;

import java.awt.*;
import java.util.*;

import Robot.RobotFunctionnality;

/**
 * Created by Master on 13/03/2015.
 */
public class DisplayEndWindow extends javax.swing.JFrame {

    @SuppressWarnings("null")
	public DisplayEndWindow(ArrayList <String>url, long executionTime){
        this.setTitle("DisplayEndWindow");
        this.setSize(1000, 400);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        /*Création des composants */
        Insets insets = new Insets(0,30,20,0);
        Dimension cell1Dim = new Dimension(100,400);
        Dimension cell2Dim = new Dimension(100,50);
        Dimension cell3Dim = new Dimension(100,400);
        JLabel [] urlParcourus = new JLabel[url.size()];
        
        
        
        for(int i=0;i<url.size();i++){
        	System.out.println("url sauvegardés : "+ url.get(i));
        	urlParcourus[i]=createJLabel();
        	if(i==0){
        		urlParcourus[i].setText("Page de départ : "+url.get(i));
        	}
        	else if(i==url.size()-1){
        		urlParcourus[i].setText("Page trouvée : "+url.get(i));
        	}
        	else{
        		urlParcourus[i].setText(url.get(i));
        	}
        }
    
      
   
        JLabel dureeRecherche = new JLabel("La recherche a duré: " + executionTime + " secondes" );
        
      //  JPanel cell1 = new JPanel();
       
      //  cell1.setPreferredSize(cell1Dim);
      //  if(urlParcourus.length>2){
        	JPanel[]cellP=new JPanel[urlParcourus.length];
	        for(int i=0;i<urlParcourus.length;i++){
	        	cellP[i]=new JPanel();
	        	cellP[i].add(urlParcourus[i]);
	        	cellP[i].setPreferredSize(cell2Dim);
	        }
     //   }
        
       /* JPanel cell3 = new JPanel();
        cell3.add(pageTrouvée);
        cell3.setPreferredSize(cell2Dim);*/
        JPanel cell4 = new JPanel();
        cell4.add(dureeRecherche);
        cell4.setPreferredSize(cell3Dim);

        //Conteneur principal
        JPanel content = new JPanel();
        content.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        //positionnement des composant 1
       /* gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = insets;

        content.add(cell1, gbc);*/
        
        int positionnementy=0;
        //positionnement detous les composants
        for(int i=0;i<urlParcourus.length;i++){
        	gbc.gridwidth = 1;
            gbc.gridheight = 1;
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.insets = insets;
        	content.add(cellP[i],gbc);
        	positionnementy++;
        }
        //positionnement des composant 3
       /* gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 0;
        gbc.gridy = positionnementy+1;
        gbc.insets = insets;
        content.add(cell3, gbc);*/

        //positionnement des composant 4
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 0;
        gbc.gridy = positionnementy+1;
        gbc.insets = insets;
        content.add(cell4, gbc);


//On ajoute le conteneur
        this.setContentPane(content);
        this.setVisible(true);
    }

    public JLabel createJLabel(){
        JLabel jl = new JLabel();
 
        return jl;
    }
    public static void main(String[] args){
            //DisplayEndWindow dew = new DisplayEndWindow();
            long tps1 = 0;
            long tps2 = 0;
            long execTime = 0;
            tps1 = System.currentTimeMillis();
            System.out.println(tps1);
            for(int i = 0; i < 100000; i++){
                    System.out.println("lol" + i);
                    for (int j = 0; j <20; j++){
                           // System.out.println("lol");
                    }
            }
            tps2 = System.currentTimeMillis();
            System.out.println(tps2);
            execTime = tps2- tps1;
            System.out.println("Temps en sec= " + (execTime / 1000));


    }
}


