
package project;

import java.util.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import javax.swing.*;

import java.awt.BasicStroke;
import java.awt.Color;

import java.awt.Graphics2D;

import java.awt.Graphics;

import javax.swing.JFrame;


public class Brickbreaker extends JPanel implements ActionListener,KeyListener
{
	private boolean play = false;
	private int score = 0;
	
	private int totalBricks = 50;

	private int base = 310;
	
	private int ballposX = 420;
	private int ballposY = 350;
	private double ballXdir = -1;
	private double ballYdir = -2;
        private int delay = 3;
        private Timer timer;
	private Grid obj;
        
	
      /*  private Image img;

      

          public Brickbreaker(Image img) {
    this.img = img;
    Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
    setPreferredSize(size);
    setMinimumSize(size);
    setMaximumSize(size);
    setSize(size);
    setLayout(null);
  }

         public void paintComponent(Graphics g) {
             g.drawImage(img, 0, 0, null);
        }*/
	public Brickbreaker()
	{	//this(new ImageIcon(img).getImage());
		obj = new Grid(5,10);
		addKeyListener(this);
		setFocusable(true);
	        setFocusTraversalKeysEnabled(false);
                timer=new Timer(delay,this);
		timer.start();
	}
	
	public void paint(Graphics g)
	{    		
             //   lImage.setIcon(new ImageIcon("pic.png"));
               // add(lImage);
		// background
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		
		
		obj.draw((Graphics2D) g);                       // drawing grid
		
                g.setColor(Color.yellow);                                   // borders
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
				
		g.setColor(Color.white);                                 // the scores 
		g.setFont(new Font("serif",Font.BOLD, 25));
		g.drawString("Score:"+score, 10,30);
		
		// the paddle
                Color  brown= new Color(139,69,19);
		g.setColor(brown);
		g.fillRect(base, 550, 100, 8);
		
		// the ball
		g.setColor(Color.yellow);
		g.fillOval(ballposX, ballposY, 20, 20);
	
		// when you won the game
		if(totalBricks <= 0)
		{
	             play = false;
                  
                     g.setColor(Color.RED);
                     g.setFont(new Font("serif",Font.BOLD, 30));
                     g.drawString("You Won", 260,300);
             
                     g.setColor(Color.RED);
                     g.setFont(new Font("serif",Font.BOLD, 20));           
                     g.drawString("Press (Enter) to Restart", 230,350);  
		}
		
		// when you lose the game
		if(ballposY > 570)
               {
                     play = false;
                     g.setColor(Color.RED);
                     g.setFont(new Font("serif",Font.BOLD, 30));
                     g.drawString("Game Over, Scores: "+score, 190,300);
             
                     g.setColor(Color.RED);
                     g.setFont(new Font("serif",Font.BOLD, 20));           
                     g.drawString("Press (Enter) to Restart", 230,350);        
                }
		
	
	}	

	public void keyPressed(KeyEvent e) 
	{
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{        
			if(base >= 592)
			{   base = 592;
			}
			else
			{
	                       play = true;
	                       base+=20;
			}
                 }
		
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{          
			if( base < 12)
			{   base = 12;  }
			else
			{       play = true;
		                base-=20;
			}
             }		
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{          
			if(!play)
			{
				play = true;
				ballposX = 420;
				ballposY = 350;
				ballXdir = -1;
				ballYdir = -2;
				base = 310;
				score = 0;
				totalBricks = 50;
                                timer.setDelay(10);
			        obj = new Grid(5, 10);
				
				repaint();
			}
        }		
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	public void actionPerformed(ActionEvent e) 
	{
		//timer.start();
		if(play)
		{			
			if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(base, 550, 100, 8)))
			{
                                
				ballYdir = -ballYdir;
                                
			}
			
			//  grid collision with the ball		
			A: for(int i = 0; i<obj.grid.length; i++)
			{
				for(int j =0; j<obj.grid[0].length; j++)
				{				
					if(obj.grid[i][j] > 0)
					{
						//scores++;
						int brickX = j * obj.bWidth + 80;
						int brickY = i * obj.bHeight + 50;
											
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
						Rectangle brickRect = new Rectangle(brickX, brickY,obj.bWidth,obj.bHeight);
						
						if(ballRect.intersects(brickRect))
						{					
						        obj.grid[i][j]=0;
							score+=5;
                                                      
							totalBricks--;
							
							// when ball hit right or left of brick
							if(ballposX + 19 <= brickRect.x || ballposX >= brickRect.x + brickRect.width)	
                                                        {  ballXdir = -ballXdir;  } 
							// when ball hits top or bottom of brick
							else
							{
								ballYdir = -ballYdir;				
							}
							
							break A;
						}
					}
				}
			}  /// end of for
			
			ballposX += ballXdir;
			ballposY += ballYdir;
			if(ballposX < 0)
			   { 	ballXdir = -ballXdir;  }
			if(ballposY < 0)
			   {    ballYdir = -ballYdir;  }
			if(ballposX > 670)
			   {    ballXdir = -ballXdir;  }		
			
			repaint();		
		}
	}
}
class Grid 
{
	public int grid[][];
        public static final Color VERY_LIGHT_BLUE = new Color(255,51,51);	

        Color colors[]={Color.red,Color.pink,Color.yellow,Color.cyan,Color.green,VERY_LIGHT_BLUE};
     
	public int bWidth;
	public int bHeight;
       // public String var=yelow;
	
	public Grid (int r, int c)
	{
		grid = new int[r][c];		
		for(int i = 0; i<grid.length; i++)
		{
			for(int j =0; j<grid[0].length; j++)
			{  grid[i][j] = 1 ; }			
		}
		
		bWidth = 540/c;
		bHeight = 150/r;
                  
               // clor[]=;
	}	
	
	public void draw(Graphics2D g)
	{
		for(int i = 0; i<grid.length; i++)
		{ for(int j =0; j<grid[0].length; j++)
		    {
				if(grid[i][j] > 0)
				{
					g.setColor(colors[i]);
					g.fillRect(j * bWidth + 80, i * bHeight + 50, bWidth, bHeight);
			                g.setStroke(new BasicStroke(3));
					g.setColor(Color.black);
					g.drawRect(j * bWidth + 80, i * bHeight + 50, bWidth, bHeight);				
				}
			}
		}
	}
}