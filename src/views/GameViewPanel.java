package views;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


public class GameViewPanel extends JPanel {

	
	int enemyColumns;
	int enemyRows;
	int enemiesArray[][];
	int enemyDistanceToTop = 30, enemyDistanceToLeft = 20, enemySpace = 10;
	
	int width, height;
	int horizontalPosition;
	
	Graphics stupidGraphics;
	
	//konstruktør som tager en array med info om hvor der er levende fjender
	  public GameViewPanel(int[][] enemiesArray, int horizontalPosition) {
		  this.horizontalPosition = horizontalPosition;
		  this.enemyRows = enemiesArray.length;
		  this.enemyColumns = enemiesArray[0].length;
		  this.enemiesArray = enemiesArray;
	  }

	public void paintComponent(Graphics g) {
		stupidGraphics = g;
		width = getWidth()-100;
		height = getHeight()-300;
		drawEnemyRects();
	}
	  
	  public void drawEnemyRects(){
		  for (int i = 0; i < enemyColumns; i++) {
			  for (int j = 0; j < enemyRows; j++) {
				  //tegner kun hvis der er fjender
				  if(enemiesArray[j][i] == 1){
					  stupidGraphics.setColor(new Color(j*((int)255/enemyRows),0,0));
					  stupidGraphics.fillRect(i*width/enemyColumns+enemyDistanceToLeft+horizontalPosition,
							  				  j*height/enemyRows+enemyDistanceToTop, width/enemyColumns-enemySpace, height/enemyRows-enemySpace);
				  }
			  }
		  }
	  }
	}
