package view.render;

import java.awt.Canvas;
import java.awt.Graphics;
import model.GameState;

public class GameStateRenderer {
    
    private SpriteHandler spriteHandler;
    
    public GameStateRenderer() {
        spriteHandler = SpriteHandler.getInstance();
        initSprites();
    }
    
    public void render(Canvas canvas, GameState gameState) {
        // TODO
        canvas.getGraphics();
    }
        
    public void draw(Graphics g, String ref, int x,int y) {
        g.drawImage(spriteHandler.get(ref).getImage(),x,y,null);
    }
    
    private void initSprites() {
        spriteHandler.add("sprites/player.png");
        spriteHandler.add("sprites/invader1.png");
        spriteHandler.add("sprites/invader2.png");
        spriteHandler.add("sprites/invader3.png");
        spriteHandler.add("sprites/bonus.png");
        // TODO: Bullet and bunker
    }
    
    
}
