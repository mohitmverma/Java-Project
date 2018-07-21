
package project;

import javax.swing.JFrame;

public class Project {

    public static void main(String[] args) {
       
                JFrame ob=new JFrame();
               
		//Gameplay gamePlay = new Gameplay();
		Brickbreaker bb = new Brickbreaker();
              
		ob.setBounds(10, 10, 700, 600);
		ob.setTitle("Breakout Ball");		
		ob.setResizable(false);
		ob.setVisible(true);
		ob.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ob.add(bb);
             
    }
    
}
