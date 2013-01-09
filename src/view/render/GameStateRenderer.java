package view.render;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import model.Coordinate;
import model.GameModel;
import model.GameState;
import model.elements.Bullet;
import model.elements.Invader;
import model.elements.PlayerIndex;
import utils.Mathx;

public class GameStateRenderer {
    
    
    public GameStateRenderer() {
    }
    
    public void render(Canvas canvas, GameState gameState) {
    	Graphics2D gfx = (Graphics2D) canvas.getBufferStrategy().getDrawGraphics();
    	
    	// Clear the screen
    	gfx.setColor(Color.BLACK);
    	gfx.fillRect(0, 0, GameModel.SCREEN_WIDTH, GameModel.SCREEN_HEIGHT);
        
    	// Draw time
    	gfx.setColor(Color.WHITE);
    	gfx.drawString(Mathx.prettyTime(gameState.getTotalGameTime()), 10, 10);
    	
    	// Draw player
    	this.draw(gfx, "view/sprites/player.png", gameState.getPlayer(PlayerIndex.One).getPosition());
    	
    	//Draw shots
        for (Bullet bullet : gameState.getBullets()) {
            this.draw(gfx, "view/sprites/bullet.png", bullet.getPosition());
        }
    	
    	//Draw invaders
        for (Invader invader : gameState.getInvaders()) {
            String ref = "";
            switch (invader.getType()) {
                case A: 
                    ref = "view/sprites/invaderA.png";
                    break;
                case B:
                    ref = "view/sprites/invaderB.png";
                    break;
                case C:
                    ref = "view/sprites/invaderC.png";
                    break;
            }
            this.draw(gfx, ref, invader.getPosition());
        }
        
    	// Empty the graphics buffer
    	gfx.dispose();
    	canvas.getBufferStrategy().show();
    }
        
    public void draw(Graphics g, String ref, Coordinate pos) {
        g.drawImage(SpriteHandler.getInstance().get(ref).getImage(), (int) pos.x, (int) pos.y, null);
    }
    
    
    
    
}
