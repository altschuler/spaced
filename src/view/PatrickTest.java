package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;


public class PatrickTest {

	static JFrame patrick_frame01;
	static int enemies[][];
	static GameViewPanel theGameViewPanel;
	
	public static void main(String[]args){
		patrick_frame01 = new JFrame();
		patrick_frame01.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		patrick_frame01.setSize(new Dimension(600,700));
		patrick_frame01.setLocation(100, 10);
		patrick_frame01.setTitle("This ain't my dad, this is a patrick_frame01!");
		patrick_frame01.setResizable(false);
		
		patrick_frame01.setLayout(new BorderLayout());
		
		
//populates the enemies-Array
//[r�kker][kolonner] med fjender. 1 = fjende, 0 = d�d
		enemies = new int[10][20];
		for (int i = 0; i < enemies.length; i++) {
			for (int j = 0; j < enemies[0].length; j++) {
				enemies[i][j] = 1;
				if(i * j == 3 || i + j == 2){
					enemies[i][j] = 0;
				}
			}
		}
		
//Laver fjerne for f�rste gang
		theGameViewPanel = new GameViewPanel(enemies, 0);
		
//viser fjerne til at starte med
//Vi kan evt. adde en "tryk p� en knap for at starte", og s� g�r spillet igang
		patrick_frame01.add(theGameViewPanel);
		patrick_frame01.setVisible(true);	
		
		
		while(true){
			moveEnemiesRight();
			moveEnemiesLeft();
		}
		
	}
	
	/**
	 * Moves the array with aliens to the right
	 */
	public static void moveEnemiesRight(){
		//fjerne k�rer hen over sk�rmen
		for (int i = 0; i < 70; i++) {
			try { Thread.currentThread().sleep(10); }
	        catch (InterruptedException e) { System.out.println("Error sleeping"); }
		patrick_frame01.remove(theGameViewPanel); //fatter ikke hvorfor, men remove skal komme f�r den bliver definieret igen.
		theGameViewPanel = new GameViewPanel(enemies, i);
		patrick_frame01.add(theGameViewPanel);
		
		patrick_frame01.setVisible(true);
		patrick_frame01.repaint(); //begge disse skal v�re her
		}
	}
		
		public static void moveEnemiesLeft(){
			//fjerne k�rer hen over sk�rmen
			for (int i = 70; i > 0; i--) {
				try { Thread.currentThread().sleep(10); }
		        catch (InterruptedException e) { System.out.println("Error sleeping"); }
			patrick_frame01.remove(theGameViewPanel); //fatter ikke hvorfor, men remove skal komme f�r den bliver definieret igen.
			theGameViewPanel = new GameViewPanel(enemies, i);
			patrick_frame01.add(theGameViewPanel);
			
			patrick_frame01.setVisible(true);
			patrick_frame01.repaint(); //begge disse skal v�re her
			}
	}
	
		public static void updateEnemiesArray(){
			
		}
}
