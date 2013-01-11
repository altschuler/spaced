package view.render;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import java.util.Iterator;

import model.MainModel;
import model.GameState;
import model.GameStateState;
import model.core.Coordinate;
import model.core.PlayerIndex;
import model.elements.Animation;
import model.elements.Bullet;
import model.elements.Bunker;
import model.elements.GameElement;
import model.elements.Invader;
import model.elements.Player;
import utils.Mathx;

public class GameStateRenderer {
	private int topBarHeight = 30;

	public GameStateRenderer() {
	}

	public void render(Canvas canvas, GameState gameState, MainModel gameModel) {
		// Setup graphics
		Graphics2D gfx = (Graphics2D) canvas.getBufferStrategy().getDrawGraphics();
		gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Clear the screen
		gfx.setColor(Color.BLACK);
//		gfx.fillRect(0, 0, MainModel.SCREEN_WIDTH, MainModel.SCREEN_HEIGHT);
		this.draw(gfx, "view/sprites/IC1396_Hubble.jpg", new Coordinate(0, 0));

		// Draw player
		Player player = gameState.getPlayer(PlayerIndex.One);
		for (int i = 0; i < player.getLives(); i++) {
			this.draw(gfx, "view/sprites/player_life.png", new Coordinate(4 + i * 30, MainModel.SCREEN_HEIGHT - 20));
		}

		// Draws everything else
		this.drAwesome(gfx, gameState.getPlayer(PlayerIndex.One));
		for (Bullet bullet : gameState.getBullets()) {		this.drAwesome(gfx, bullet);		}
		for (Invader invader : gameState.getInvaders()) {	this.drAwesome(gfx, invader);		}
		for (Bunker bunker : gameState.getBunkers()){	this.drAwesome(gfx, bunker);			}
		for (Iterator<Animation> animations = gameState.getAnimations().iterator(); animations.hasNext();) {
			Animation animation = animations.next();
			if(animation.getIndexOfLastFrame()-1 >= animation.getFrames()){
				animations.remove();
			}else{
				this.drawAnimation(gfx, animation);	
			}
		}
		
		
		
		// Top status bar, draw last to go on top
		Font font = new Font("Verdana", Font.PLAIN, 15);
		gfx.setFont(font);
		gfx.setColor(new Color(150,150,150,200));
		gfx.fillRect(0, 0, MainModel.SCREEN_WIDTH, topBarHeight); //DON'T DELETE topBarHeight, important for deletion of bullets that go too far
		gfx.setColor(Color.BLACK);
		gfx.drawString(String.format("Time: %s", Mathx.prettyTime(gameState.getTotalGameTime())), 12, 20);
		String playerString = String.format("Diff: %s, Inv: %s, Player: %s", 
                        gameModel.getActiveDifficulty().getName(), 
                        gameState.getInvaders().size(), 
                        gameModel.getPlayerName(PlayerIndex.One));
		Rectangle2D playerStringBounds = gfx.getFontMetrics(font).getStringBounds(playerString, gfx);
		gfx.drawString(playerString, (int) (MainModel.SCREEN_WIDTH - playerStringBounds.getWidth() - 20), 20);

		// Special cases of gameState's state
		if (gameState.getState() == GameStateState.Waiting) {
			Font fontBig = new Font("Verdana", Font.PLAIN, 25);
			gfx.setFont(fontBig);
			gfx.setColor(Color.RED);
			String anyKeyText = String.format("LEVEL %d - PRESS ANY KEY TO START", gameState.getId());
			Rectangle2D anyKeyTextBounds = gfx.getFontMetrics(fontBig).getStringBounds(anyKeyText, gfx);
			gfx.drawString(anyKeyText, (int) (MainModel.SCREEN_WIDTH / 2 - anyKeyTextBounds.getWidth() / 2),
					(int) (MainModel.SCREEN_HEIGHT / 2 - anyKeyTextBounds.getHeight() / 2));
		}
		
		// Empty the graphics buffer
		gfx.dispose();
		canvas.getBufferStrategy().show();
	}

	public void draw(Graphics g, String ref, Coordinate pos) {
		g.drawImage(SpriteHandler.getInstance().get(ref).getImage(), (int) pos.x, (int) pos.y, null);
	}
	
	public void drAwesome(Graphics g, GameElement gameElement){
		g.drawImage(SpriteHandler.getInstance().get(gameElement.getImageURL()).getImage(), (int) gameElement.getPosition().x, (int) gameElement.getPosition().y, null);
	}
	
	public void drawAnimation(Graphics g, Animation ani){
		Sprite spriteSheet = SpriteHandler.getInstance().get(ani.getImageURL());
		int aniPosX = (int) ani.getPosition().x;
		int aniPosY = (int) ani.getPosition().y;
		int lastIndex = ani.getIndexOfLastFrame();
		g.drawImage(spriteSheet.getImage(), aniPosX, aniPosY, aniPosX+spriteSheet.getWidth(), aniPosY+ani.getFrameHeight(), 0, (lastIndex-1)*ani.getFrameHeight(), spriteSheet.getWidth(), (lastIndex)*ani.getFrameHeight(), null);

		if (System.currentTimeMillis() - ani.getTimeOfLastFrame()> ani.getTimePerFrame()) {
			ani.setTimeOfLastFrame(System.currentTimeMillis());
			ani.setIndexOfLastFrame(ani.getIndexOfLastFrame()+1);
		}
		
		//TODO: make this... Use a SpriteSheet
	}

	public int getTopBarHeight() {
		return topBarHeight;
	}
}
