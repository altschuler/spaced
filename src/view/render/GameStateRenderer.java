package view.render;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import model.GameModel;
import model.GameState;

public class GameStateRenderer {
    
    private SpriteHandler spriteHandler;
    
    public GameStateRenderer() {
        this.spriteHandler = SpriteHandler.getInstance();
        
        this.initSprites();
    }
    
    public void render(Canvas canvas, GameState gameState) {
        // TODO
    	// Clear screen
    	Graphics2D gfx = (Graphics2D) canvas.getBufferStrategy().getDrawGraphics();
    	gfx.setColor(Color.BLACK);
    	gfx.fillRect(0, 0, GameModel.SCREEN_WIDTH, GameModel.SCREEN_HEIGHT);
        
    	// Draw player
    	this.draw(gfx, "view/sprites/player.png", gameState.getPlayer().getPosition());
    	
    	//Draw shots
    	for (int i = 0; i < gameState.getShots().size(); i++) {
    		this.draw(gfx, "view/sprites/bullet.png", gameState.getShots().get(i).getPosition());
		}
        
    	// Draw it all
    	gfx.dispose();
    	canvas.getBufferStrategy().show();
    }
        
    public void draw(Graphics g, String ref, Point pos) {
        g.drawImage(spriteHandler.get(ref).getImage(), pos.x, pos.y,null);
    }
    
    private void initSprites() {
        spriteHandler.add("view/sprites/player.png");
        spriteHandler.add("view/sprites/invader1.png");
        spriteHandler.add("view/sprites/invader2.png");
        spriteHandler.add("view/sprites/invader3.png");
        spriteHandler.add("view/sprites/bonus.png");
        spriteHandler.add("view/sprites/bullet.png");
        // TODO: Bullet and bunker
    }
    
    
}
