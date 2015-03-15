package ihm;

import javax.swing.*;
import java.awt.*;
import Robot.RobotFunctionnality;

/**
 * Created by Master on 13/03/2015.
 */
public class DisplayEndWindow extends javax.swing.JFrame {

    public DisplayEndWindow(String urlInitiale, String urlFinale, long executionTime){
        this.setTitle("DisplayEndWindow");
        this.setSize(1000, 400);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        /*Création des composants */
        Insets insets = new Insets(0,30,80,0);
        Dimension cell1Dim = new Dimension(100,400);
        Dimension cell2Dim = new Dimension(100,50);
        Dimension cell3Dim = new Dimension(100,400);

        JLabel UrlDépart = new JLabel("Page de départ: " + urlInitiale);
        JLabel pageTrouvée = new JLabel("Page trouvée: " + urlFinale);
        JLabel dureeRecherche = new JLabel("La recherche a duré: " + executionTime + " secondes" );
        JPanel cell1 = new JPanel();
        cell1.add(UrlDépart);
        cell1.setPreferredSize(cell1Dim);
        JPanel cell2 = new JPanel();
        cell2.add(pageTrouvée);
        cell2.setPreferredSize(cell2Dim);
        JPanel cell3 = new JPanel();
        cell3.add(dureeRecherche);
        cell3.setPreferredSize(cell3Dim);

        //Conteneur principal
        JPanel content = new JPanel();
        content.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        //positionnement des composant 1
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = insets;

        content.add(cell1, gbc);

        //positionnement des composant 2
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = insets;
        content.add(cell2, gbc);

        //positionnement des composant 3
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = insets;
        content.add(cell3, gbc);


//On ajoute le conteneur
        this.setContentPane(content);
        this.setVisible(true);
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


