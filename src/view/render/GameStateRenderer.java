package view.render;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import model.Coordinate;
import model.GameModel;
import model.GameState;
import model.elements.Player;
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
    	gfx.drawString(String.format("P1 Lives: %d\nTime:%s", gameState.getPlayer(PlayerIndex.One).getLives(), Mathx.prettyTime(gameState.getTotalGameTime())), 10, 10);
    	
    	// Draw player
    	Player player = gameState.getPlayer(PlayerIndex.One);
    	for (int i = 0; i < player.getLives(); i++) {
    		this.draw(gfx, "view/sprites/player_life.png", new Coordinate(4 + i * 30, GameModel.SCREEN_HEIGHT - 20));
		}
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
        g.drawImage(SpriteHandler.getInstance().get(ref).getImage(), (int) pos.x, (int) pos.y, null);
    }
}
