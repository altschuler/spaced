package controller;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;

import model.GameModel;
import model.GameState;
import model.core.Coordinate;
import model.core.Direction;
import model.elements.Bullet;
import model.elements.Invader;
import model.elements.Player;
import model.elements.PlayerIndex;
import utils.Input;
import utils.Mathx;
import view.MainView;
import view.render.GameStateRenderer;
import view.state.GameViewState;
import view.state.ViewState;

import command.CommandFactory;
import command.CommandListener;

public class GameController extends AbstractController {

	private Timer timer;
	private GameStateRenderer renderer;

	public GameController(MainView gw, GameModel gm) {
		super(gw, gm);

		this.renderer = new GameStateRenderer();
	}

	/**
	 * Sets the enabled state of the game loop, effectively starting and
	 * stopping a game session
	 * 
	 * @param enabled
	 *            Whether the loop will run or not
	 */
	public void setGameLoopEnabled(boolean enabled) {
		if (enabled) {
			this.getTimer().start();
		} else {
			this.getTimer().stop();
		}
	}

	/**
	 * @param gameState
	 *            The {@link GameState} whose values are to be stepped forward
	 *            in time.
	 */
	public void updateGameState(GameState gameState) {
		if (!(mainView.getContentPane() instanceof GameViewState)) {
			return;
		}

		GameViewState gameView = (GameViewState) mainView.getContentPane();

		long currentTime = System.currentTimeMillis();
		long timeDelta = currentTime - gameState.getLastUpdateTime();

		// Escape triggers in-game menu
		if (Input.getInstance().isKeyDown(KeyEvent.VK_ESCAPE)) {
			CommandFactory.createSetStateCommand(ViewState.PauseMenu).execute();
			return;
		}

		// Update elements
		this.updatePlayer(gameState, timeDelta);
		this.updateInvaders(gameState, timeDelta);
		this.invadersShoot(gameState, currentTime);
		this.updateShots(gameState, timeDelta);

		// Check if player has won or lost. Exit early if true.
		if (this.checkGameOver(gameState)) {
			return;
		}

		// Render the game state
		this.renderer.render(gameView.getDisplay(), gameState);

		// Update total and last time
		gameState.setLastUpdateTime(currentTime);
		gameState.addTotalGameTime(timeDelta);
	}

	private boolean checkGameOver(GameState gameState) {
		// Player has no more lives = loose
		if (gameState.getPlayer(PlayerIndex.One).getLives() <= 0) {
			CommandFactory.createSetStateCommand(ViewState.GameOver).execute();
			return true;
		}

		// All invaders gone = win
		if (gameState.getInvaders().size() == 0) {
			CommandFactory.createSetStateCommand(ViewState.GameOver).execute();
			return true;
		}

		return false;
	}

	public void updatePlayer(GameState gameState, long timeDelta) {
		Player player = gameState.getPlayer(PlayerIndex.One);

		if (Input.getInstance().isKeyDown(KeyEvent.VK_LEFT)) {
			player.getPosition().x -= Mathx.distance(timeDelta, player.getSpeed());
		}

		if (Input.getInstance().isKeyDown(KeyEvent.VK_RIGHT)) {
			player.getPosition().x += Mathx.distance(timeDelta, player.getSpeed());
		}

		if (Input.getInstance().isKeyDown(KeyEvent.VK_SPACE)) {
			// the player can only shoot once per playerShotFrequency
			long currentTime = System.currentTimeMillis();
			if (player.getTimeOfLastShot() - currentTime < -player.getMaxShootFrequency()) {
				player.setTimeOfLastShot(currentTime);
				SoundController.playSound(new File("leftright.wav"), 1, 75);

				Bullet currentShot = new Bullet(Direction.Up);
				currentShot.setPosition(player.getPosition().clone());
				currentShot.getPosition().x += 24;
				gameState.getBullets().add(currentShot);
			}
		}

		player.getPosition().x = Math.max(0, player.getPosition().x);
		player.getPosition().x = Math.min(GameModel.SCREEN_WIDTH - player.getWidth(), player.getPosition().x);
	}

	/**
	 * @param gameState
	 *            Moves the bullets upwards
	 */
	private void updateShots(GameState gameState, long timeDelta) {

		for (Iterator<Bullet> bullets = gameState.getBullets().iterator(); bullets.hasNext();) {
			Bullet bullet = bullets.next();
			// moving the bullet
			if (bullet.getDirection() == Direction.Up) {
				bullet.move(0, -Mathx.distance(timeDelta, bullet.getSpeed()));
			} else {

				// bullet.move(0, Mathx.distance(timeDelta, bullet.getSpeed()));
				// - normal, vertical movement

				/*
				 * Making heat-seeking shots TODO: use only on bosses?
				 */
				// Make math method to calculate DirectionVector?
				double xDirectionToPlayer = (gameState.getPlayer(PlayerIndex.One).getPosition().x + 24 - bullet.getPosition().x);
				double yDirectionToPlayer = (gameState.getPlayer(PlayerIndex.One).getPosition().y - bullet.getPosition().y);
				Coordinate vector = new Coordinate(xDirectionToPlayer, yDirectionToPlayer); // directionVector
				vector.normalize();
				bullet.move(vector.x * Mathx.distance(timeDelta, bullet.getSpeed()) * 0.75, Mathx.distance(timeDelta, bullet.getSpeed()));
			}

			if (bullet.getPosition().y <= 0) {
				bullets.remove();
			}
			// collision detection
			for(Iterator<Invader> invaders = gameState.getInvaders().iterator(); invaders.hasNext();) {
				Invader invader = invaders.next();

				if (Mathx.intersects(bullet, invader)) {
					bullets.remove();

					invader.healthDown();
					if (invader.isDead()) {
						invaders.remove();
					}
					break;
				}
			}
			
			for (Iterator<Bullet> innerBullets = gameState.getBullets().iterator(); innerBullets.hasNext();) {
				Bullet collisionBullet = innerBullets.next();
				// don't check collision with self...
				if (!bullet.equals(collisionBullet) && Mathx.intersects(bullet, collisionBullet)) { 
					bullets.remove();
					innerBullets.remove();
					break;
				}
			}
			
			// player collision
			if (bullet.getDirection() == Direction.Down && Mathx.intersects(bullet, gameState.getPlayer(PlayerIndex.One))) { 
				gameState.getPlayer(PlayerIndex.One).livesDown();
				//TODO fire some command to pause and respawn the player
				bullets.remove();
			}
		}
	}

	private void updateInvaders(GameState gameState, long timeDelta) {
		ArrayList<Invader> invaders = gameState.getInvaders();
		boolean wallHit = false;

		for (int i = 0; i < invaders.size(); i++) {
			Invader invader = invaders.get(i);

			if (gameState.getMoveInvadersRight()) {
				invader.move(Mathx.distance(timeDelta, invader.getSpeed()), 0);
			} else {
				invader.move(-Mathx.distance(timeDelta, invader.getSpeed()), 0);
			}

			wallHit = wallHit || (invader.getPosition().x + invader.getWidth() > GameModel.SCREEN_WIDTH) || (invader.getPosition().x < 0);
		}

		if (wallHit) {
			gameState.setMoveInvadersRight(!gameState.getMoveInvadersRight());
			for (Invader invader : invaders) {
				invader.move(0, 15); // TODO refactor y-coordinate out to
										// something
										// like DifficultyConfiguration
			}
		}
	}

	/**
	 * @param gameState
	 * 
	 *            Find de invaders der er placeret nederst, og lad en random en
	 *            af dem skyde.
	 */
	private void invadersShoot(GameState gameState, long currentTime) {
		// array med laveste invaders
		ArrayList<Invader> lowestInvaders = gameState.getLowestInvaders();

		ArrayList<Invader> invaders = gameState.getInvaders();
		for (int i = 0; i < invaders.size(); i++) {
			if (lowestInvaders.size() == 0) {// hvis der endnu ikke er en
												// nederst invader
				lowestInvaders.add(invaders.get(0));
			}

			if (invaders.get(i).getPosition().y == lowestInvaders.get(0).getPosition().y) { // på
																							// niveau
																							// med
																							// den
																							// forreste
																							// invader
				lowestInvaders.add(invaders.get(i));
			} else if (invaders.get(i).getPosition().y > lowestInvaders.get(0).getPosition().y) { // længere
																									// fremme
																									// end
																									// den
				lowestInvaders.clear();
				lowestInvaders.add(invaders.get(i));
			}
		}
		// nu har man array med forreste invaders
		int shootingInvader = (int) (Math.random() * lowestInvaders.size());

		if (gameState.getLastInvaderShot() - currentTime < -1000) { // shoot!
			gameState.setLastInvaderShot(currentTime);
			Bullet currentShot = new Bullet(Direction.Down);
			currentShot.setPosition(gameState.getLowestInvaders().get(shootingInvader).getPosition().clone());
			currentShot.move(24, 50);
			gameState.getBullets().add(currentShot);
		}
		lowestInvaders.clear();
	}

	/**
	 * Lazily gets the game loop timer
	 */
	private Timer getTimer() {
		if (this.timer == null) {
			this.timer = new Timer(20, new CommandListener(CommandFactory.createUpdateGameStateCommand(gameModel.getActiveGameState())));
		}
		return timer;
	}
}
