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
import model.core.Coordinate;
import model.core.PlayerIndex;
import model.elements.Bullet;
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
		String playerString = String.format("Player: %s", gameModel.getPlayerName(PlayerIndex.One));
		
		Rectangle2D playerStringBounds = gfx.getFontMetrics(font).getStringBounds(playerString, gfx);
		gfx.drawString(playerString, (int) (GameModel.SCREEN_WIDTH - playerStringBounds.getWidth() - 20), 20);

		// Draw player
		Player player = gameState.getPlayer(PlayerIndex.One);
		for (int i = 0; i < player.getLives(); i++) {
			this.draw(gfx, "view/sprites/player_life.png", new Coordinate(4 + i * 30, GameModel.SCREEN_HEIGHT - 20));
		}
		this.draw(gfx, "view/sprites/player.png", gameState.getPlayer(PlayerIndex.One).getPosition());

		// Draw shots
		for (Bullet bullet : gameState.getBullets()) {
			this.draw(gfx, "view/sprites/bullet.png", bullet.getPosition());
		}

		// Draw invaders
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
