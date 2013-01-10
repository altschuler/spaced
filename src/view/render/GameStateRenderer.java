package view.render;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import model.GameModel;
import model.GameState;
import model.GameStateState;
import model.core.Coordinate;
import model.core.PlayerIndex;
import model.elements.Bullet;
import model.elements.Bunker;
import model.elements.GameElement;
import model.elements.Invader;
import model.elements.Player;
import utils.Mathx;

public class GameStateRenderer {

	public GameStateRenderer() {
	}

	public void render(Canvas canvas, GameState gameState, GameModel gameModel) {
		// Setup graphics
		Graphics2D gfx = (Graphics2D) canvas.getBufferStrategy().getDrawGraphics();
		gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Clear the screen
		gfx.setColor(Color.BLACK);
		gfx.fillRect(0, 0, GameModel.SCREEN_WIDTH, GameModel.SCREEN_HEIGHT);

		// Top status bar
		Font font = new Font("Verdana", Font.PLAIN, 15);
		gfx.setFont(font);
		gfx.setColor(Color.ORANGE);
		gfx.fillRect(0, 0, GameModel.SCREEN_WIDTH, 30);
		gfx.setColor(Color.BLACK);
		gfx.drawString(String.format("Time: %s", Mathx.prettyTime(gameState.getTotalGameTime())), 12, 20);
		String playerString = String.format("Inv: %s, Player: %s", gameState.getInvaders().size(), gameModel.getPlayerName(PlayerIndex.One));
		Rectangle2D playerStringBounds = gfx.getFontMetrics(font).getStringBounds(playerString, gfx);
		gfx.drawString(playerString, (int) (GameModel.SCREEN_WIDTH - playerStringBounds.getWidth() - 20), 20);

		// Draw player
		Player player = gameState.getPlayer(PlayerIndex.One);
		for (int i = 0; i < player.getLives(); i++) {
			this.draw(gfx, "view/sprites/player_life.png", new Coordinate(4 + i * 30, GameModel.SCREEN_HEIGHT - 20));
		}

		// Draws everything else
		this.drAwesome(gfx, gameState.getPlayer(PlayerIndex.One));
		for (Bullet bullet : gameState.getBullets()) {		this.drAwesome(gfx, bullet);		}
		for (Invader invader : gameState.getInvaders()) {	this.drAwesome(gfx, invader);		}
		for (Bunker bunker : gameState.getBunkers()){	this.drAwesome(gfx, bunker);			}

		// Special cases of gameState's state
		if (gameState.getState() == GameStateState.Waiting) {
			Font fontBig = new Font("Verdana", Font.PLAIN, 25);
			gfx.setFont(fontBig);
			gfx.setColor(Color.RED);
			String anyKeyText = String.format("LEVEL %d - PRESS ANY KEY TO START", gameState.getId() + 1);
			Rectangle2D anyKeyTextBounds = gfx.getFontMetrics(fontBig).getStringBounds(anyKeyText, gfx);
			gfx.drawString(anyKeyText, (int) (GameModel.SCREEN_WIDTH / 2 - anyKeyTextBounds.getWidth() / 2),
					(int) (GameModel.SCREEN_HEIGHT / 2 - anyKeyTextBounds.getHeight() / 2));
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
}
