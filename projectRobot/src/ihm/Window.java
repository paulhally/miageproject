package ihm;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.*;

public class Window extends JFrame{
	
	
	JLabel nameUrl=new JLabel("URL");
	JLabel nameKW=new JLabel("Key words");
	JLabel nameTime=new JLabel("Time spent");
	JTextField url;
	JTextField keyWords;
	JTextField time;
	
	JButton go=new JButton("GO !");
	
	public Window(){
		this.setTitle("Robot Surfer");
		this.setResizable(false);
	    this.setSize(600,400);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);    

	    
	    url=new JTextField();
	    keyWords=new JTextField();
	    time=new JTextField();
	   
	    this.setLayout(new GridLayout(6, 4));

	    //On ajoute le bouton au content pane de la JFrame

	    //this.getContentPane().add(new JPanel());
	    
	    this.getContentPane().add(new JPanel());
	    this.getContentPane().add(nameUrl);
	    this.getContentPane().add(url);
	    this.getContentPane().add(new JPanel());
	    this.getContentPane().add(new JPanel());
	    this.getContentPane().add(new JPanel());
	    this.getContentPane().add(new JPanel());
	    this.getContentPane().add(new JPanel());
	    this.getContentPane().add(new JPanel());
	    this.getContentPane().add(new JPanel());
	    this.getContentPane().add(new JPanel());
	    this.getContentPane().add(nameKW);
	    this.getContentPane().add(keyWords);
	    this.getContentPane().add(new JPanel());
	    this.getContentPane().add(go);
	    this.getContentPane().add(new JPanel());
	    this.getContentPane().add(new JPanel());
	    this.getContentPane().add(new JPanel());
	    this.getContentPane().add(new JPanel());
	    this.getContentPane().add(new JPanel());
	    this.getContentPane().add(new JPanel());
	    this.getContentPane().add(nameTime);
	    this.getContentPane().add(time);
	    this.getContentPane().add(new JPanel());
	    this.getContentPane().add(new JPanel());
	    this.getContentPane().add(new JPanel());
	    this.getContentPane().add(new JPanel());
	    this.getContentPane().add(new JPanel());
	    this.getContentPane().add(new JPanel());
	  

	    this.setVisible(true);
    
	    
	}

}
