package view.render;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import model.Coordinate;
import model.GameModel;
import model.GameState;
import model.elements.PlayerIndex;
import utils.Mathx;

public class GameStateRenderer {
    
    private SpriteHandler spriteHandler;
    
    public GameStateRenderer() {
        this.spriteHandler = SpriteHandler.getInstance();
        
        this.initSprites();
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
    	for (int i = 0; i < gameState.getBullets().size(); i++) {
    		this.draw(gfx, "view/sprites/bullet.png", gameState.getBullets().get(i).getPosition());
		}
    	
    	//Draw invaders
    	for (int i = 0; i < gameState.getInvaders().size(); i++) {
    		this.draw(gfx, "view/sprites/invader2.png", gameState.getInvaders().get(i).getPosition());
		}
        
    	// Empty the graphics buffer
    	gfx.dispose();
    	canvas.getBufferStrategy().show();
    }
        
    public void draw(Graphics g, String ref, Coordinate pos) {
        g.drawImage(spriteHandler.get(ref).getImage(), (int) pos.x, (int) pos.y, null);
    }
    
    private void initSprites() {
        spriteHandler.add("view/sprites/player.png");
        spriteHandler.add("view/sprites/invader1.png");
        spriteHandler.add("view/sprites/invader2.png");
        spriteHandler.add("view/sprites/invader3.png");
        spriteHandler.add("view/sprites/bonus.png");
        spriteHandler.add("view/sprites/bullet.png");
        // TODO: Bunker
    }
    
    
}
