package view.render;

import service.resources.Sprite;
import service.resources.SpriteHandler;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import model.MainModel;
import model.GameState;
import model.GameStateState;
import model.core.Coordinate;
import model.core.PlayerIndex;
import model.elements.Animation;
import model.elements.Bonus;
import model.elements.Bullet;
import model.elements.Bunker;
import model.elements.Cage;
import model.elements.GameElement;
import model.elements.Invader;
import model.elements.KillableGameElement;
import model.elements.NicholasCage;
import model.elements.Player;
import utils.Mathx;

public class GameStateRenderer {
	private int topBarHeight = 30;
	private int bottomBarHeight = 30; //TODO: Use this to display info about special-bullets remaining

	public GameStateRenderer() {
	}

	public void render(Canvas canvas, GameState gameState, MainModel gameModel) {
		int invadersFrozenTime = 0; //used in infoBar in the bottom of the screen
		// Setup graphics
		Graphics2D gfx[] = new Graphics2D[3];
		gfx[0] = (Graphics2D) canvas.getBufferStrategy().getDrawGraphics();
		gfx[0].setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gfx[2] = (Graphics2D) canvas.getBufferStrategy().getDrawGraphics();
		gfx[2].setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f));
		gfx[2].setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gfx[1] = (Graphics2D) canvas.getBufferStrategy().getDrawGraphics();
        gfx[1].setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25f));
        gfx[1].setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Clear the screen
		gfx[0].setColor(Color.BLACK);
//		gfx.fillRect(0, 0, MainModel.SCREEN_WIDTH, MainModel.SCREEN_HEIGHT);
		this.draw(gfx[0], "hubble.jpg", new Coordinate(0, 0));

		// Draw player
		Player player = gameState.getPlayer(PlayerIndex.One);
		for (int i = 0; i < player.getLives(); i++) {
			this.draw(gfx[0], "player_life.png", new Coordinate(4 + i * 30, MainModel.SCREEN_HEIGHT - 20 - this.bottomBarHeight));
		}
		boolean playerReloading = gameModel.getActiveDifficulty().getPlayerShootFreq() > System.currentTimeMillis() - player.getTimeOfLastShot();
		
		if(playerReloading){
			double reloadFraction = (double) (System.currentTimeMillis() - player.getTimeOfLastShot()) / (double) gameModel.getActiveDifficulty().getPlayerShootFreq();
			gfx[0].setColor(new Color(150,150,150,200));
			gfx[0].fillRect((int) player.getPosition().x, (int) player.getPosition().y + player.getHeight(),
					(int) (reloadFraction*player.getWidth()) , 6);
		}
		this.drAwesome(gfx[0], gameState.getPlayer(PlayerIndex.One));

		// Draws everything else (Invaders, player, bullets, bunkers, bonuses & animations)
		for (Iterator<Animation> animations = gameState.getAnimations().iterator(); animations.hasNext();) {
			Animation animation = animations.next();
			if(animation.getIndexOfLastFrame()-1 >= animation.getFrames()){
				animations.remove();
			}else{
				this.drawAnimation(gfx[0], animation);	
			}
		}
		for (Invader invader : gameState.getInvaders()) {	
			invadersFrozenTime = invader.getFrozenTime();
			this.drAwesome(gfx[0], invader);	
			if(invadersFrozenTime > 0){
				this.drawBlueInvader(gfx[2],invader);
			}
			if(invader.getHealth() > 2){
				this.drawRedInvader(gfx[1],invader);
			}else if(invader.getHealth() == 2){
				this.drawRedInvader(gfx[2],invader);
			}
		}
		for (Bullet bullet : gameState.getBullets()) {		this.drAwesome(gfx[0], bullet);		}
		for (Bunker bunker : gameState.getBunkers()){	this.drAwesome(gfx[0], bunker);			}
		for (Bonus bonus : gameState.getBonuses()){	this.drAwesome(gfx[0], bonus);			}
		for (KillableGameElement randomEnemy : gameState.getIndividualEnemies()){	//bosses
			if(randomEnemy instanceof NicholasCage){
				this.drAwesome(gfx[0], randomEnemy);
				
				//Gives Nicolas a glory which becomes more transparent as he losses his cages
				int noOfCages = ((NicholasCage) randomEnemy).getCages().size();
				if(noOfCages > 0){
					int opacity = (185/((NicholasCage) randomEnemy).getNoOfCages()) * noOfCages;
					gfx[0].setStroke(new BasicStroke(10));
					gfx[0].setColor(new Color(200,200,00,opacity));
					gfx[0].draw(new Ellipse2D.Double(randomEnemy.getPosition().x+15, randomEnemy.getPosition().y-20, 150, 50));
				}
				
				for(Cage cage : ((NicholasCage) randomEnemy).getCages()){
					this.drAwesome(gfx[0], cage);
				}
			}
		}
		Font font = new Font("Verdana", Font.PLAIN, 15);
		
		// Top status bar, draw last to go on top
		this.drawTopBar(gfx[0], gameState, gameModel, font);
		
		this.drawBottomBar(gfx[0], gameState, gameModel, font, invadersFrozenTime);
		
		// Special cases of gameState's state
		if (gameState.getState() == GameStateState.Waiting) {
			Font fontBig = new Font("Verdana", Font.PLAIN, 25);
			gfx[0].setFont(fontBig);
			gfx[0].setColor(Color.RED);
			String anyKeyText = String.format("LEVEL %d - PRESS ENTER TO START", gameState.getId());
			Rectangle2D anyKeyTextBounds = gfx[0].getFontMetrics(fontBig).getStringBounds(anyKeyText, gfx[0]);
			gfx[0].drawString(anyKeyText, (int) (MainModel.SCREEN_WIDTH / 2 - anyKeyTextBounds.getWidth() / 2),
					(int) (MainModel.SCREEN_HEIGHT / 2 - anyKeyTextBounds.getHeight() / 2));
		}
		
		// Empty the graphics buffer
		gfx[0].dispose();
		gfx[1].dispose();
		gfx[2].dispose();
		canvas.getBufferStrategy().show();
	}

	public void draw(Graphics g, String ref, Coordinate pos) {
		g.drawImage(SpriteHandler.getInstance().get(ref).getImage(), (int) pos.x, (int) pos.y, null);
	}
	
	public void drAwesome(Graphics g, GameElement gameElement){
		g.drawImage(SpriteHandler.getInstance().get(gameElement.getImageURL()).getImage(), (int) gameElement.getPosition().x, (int) gameElement.getPosition().y, null);
	}
	
	public void drawBlueInvader(Graphics g, Invader invader) {  
		String ref = "aBlue.png";
        switch(invader.getType()){
	        case B:	    ref = "bBlue.png";	   	break;
	        case C:     ref = "cBlue.png";	   	break;
	        default:	break;
        }
        g.drawImage(SpriteHandler.getInstance().get(ref).getImage(),(int)invader.getPosition().x,(int)invader.getPosition().y,null);
    }
	
	public void drawRedInvader(Graphics g, Invader invader) {  
		String ref = "aRed.png";
        switch(invader.getType()){
	        case B:	    ref = "bRed.png";	   	break;
	        case C:     ref = "cRed.png";	   	break;
	        default:	break;
        }
        g.drawImage(SpriteHandler.getInstance().get(ref).getImage(),(int)invader.getPosition().x,(int)invader.getPosition().y,null);
    }
	
	public void drawAnimation(Graphics g, Animation ani){
		Sprite spriteSheet = SpriteHandler.getInstance().get(ani.getImageURL());
		int aniPosX = (int) ani.getPosition().x;
		int aniPosY = (int) ani.getPosition().y;
		int lastIndex = ani.getIndexOfLastFrame();
		g.drawImage(spriteSheet.getImage(), aniPosX, aniPosY, 
                        aniPosX+spriteSheet.getWidth(), aniPosY+ani.getFrameHeight(), 0, 
                        (lastIndex-1)*ani.getFrameHeight(), spriteSheet.getWidth(), 
                        (lastIndex)*ani.getFrameHeight(), null);

		if (System.currentTimeMillis() - ani.getTimeOfLastFrame()> ani.getTimePerFrame()) {
			ani.setTimeOfLastFrame(System.currentTimeMillis());
			ani.setIndexOfLastFrame(ani.getIndexOfLastFrame()+1);
		}
	}
	
	private void drawTopBar(Graphics2D gfx, GameState gameState, MainModel gameModel, Font font){
		gfx.setFont(font);
		gfx.setColor(new Color(150,150,150,200));
		gfx.fillRect(0, 0, MainModel.SCREEN_WIDTH, topBarHeight); //DON'T DELETE topBarHeight, important for deletion of bullets that go too far
		gfx.fillRect(0, MainModel.SCREEN_HEIGHT-bottomBarHeight, MainModel.SCREEN_WIDTH, MainModel.SCREEN_HEIGHT);
		gfx.setColor(Color.BLACK);
		gfx.drawString(String.format("Time: %s", Mathx.prettyTime(gameState.getTotalGameTime())), 12, 20);
		String playerString = String.format("Level: %s, Diff: %s, ", 
                        gameState.getId(),
						gameModel.getActiveDifficulty().getName());
		if(gameState.getInvaders().size() > 0){
			playerString += String.format("Inv: %s, ", gameState.getInvaders().size());
		}else{
			playerString += String.format("Boss: %s, ", gameState.getIndividualEnemies().size());
		}
		playerString += String.format("Player: %s, Score: %s",
                gameModel.getPlayerName(PlayerIndex.One),
                gameState.getPlayer(PlayerIndex.One).getPoints());
		Rectangle2D playerStringBounds = gfx.getFontMetrics(font).getStringBounds(playerString, gfx);
		gfx.drawString(playerString, (int) (MainModel.SCREEN_WIDTH - playerStringBounds.getWidth() - 20), 20);
	}
	
	private void drawBottomBar(Graphics2D gfx, GameState gameState, MainModel gameModel, Font font, int invadersFrozenTime){
		gfx.drawString(String.format("Press ESC to pause, numbers 1-3 to change weapons, SPACE to shoot and use the arrows to move."), 12, MainModel.SCREEN_HEIGHT - 10);
		String infoString = String.format("Current weapon: %s", gameState.getPlayer(PlayerIndex.One).getWeapon());
		if(invadersFrozenTime > 0){
			infoString += String.format("  Invaders frozen: %s", Mathx.prettyTime(invadersFrozenTime));
		}
		Rectangle2D infoStringBounds = gfx.getFontMetrics(font).getStringBounds(infoString, gfx);
		gfx.drawString(infoString, (int) (MainModel.SCREEN_WIDTH - infoStringBounds.getWidth() - 20), MainModel.SCREEN_HEIGHT - 10);
	}

	public int getTopBarHeight() {
		return topBarHeight;
	}

	public int getBottomBarHeight() {
		return bottomBarHeight;
	}
}
