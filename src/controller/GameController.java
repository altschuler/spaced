package controller;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.Timer;

import model.GameModel;
import model.GameState;
import model.core.BulletType;
import model.core.Coordinate;
import model.core.Direction;
import model.core.PlayerIndex;
import model.elements.Bullet;
import model.elements.Bunker;
import model.elements.Invader;
import model.elements.Player;
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

		// Sweep destroyed elements
		this.sweep(gameState);

		// Check if player has won or lost. Exit early if so.
		if (this.checkGameOver(gameState)) {
			return;
		}

		// Render the game state
		this.renderer.render(gameView.getDisplay(), gameState, this.gameModel);

		// Update total and last time
		gameState.setLastUpdateTime(currentTime);
		gameState.addTotalGameTime(timeDelta);
	}

	private void sweep(GameState gameState) {
		for (Iterator<Bullet> bullets = gameState.getBullets().iterator(); bullets.hasNext();) {
			Bullet bullet = bullets.next();
			if (bullet.isDestroyed()) {
				bullets.remove();
			}
		}

		for (Iterator<Invader> invaders = gameState.getInvaders().iterator(); invaders.hasNext();) {
			Invader invader = invaders.next();
			if (invader.isDestroyed()) {
				invaders.remove();
			}
		}

		for (Iterator<Bunker> bunkers = gameState.getBunkers().iterator(); bunkers.hasNext();) {
			Bunker bunker = bunkers.next();
			if (bunker.isDestroyed()) {
				bunkers.remove();
			}
		}
	}

	/**
	 * Check if game is over
	 * 
	 * @return True if game is over for any reason
	 */
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

				Bullet currentShot = new Bullet(Direction.Up, BulletType.Normal);
				currentShot.setPosition(player.getPosition().clone());
				currentShot.getPosition().x += player.getWidth() / 2;
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
		for (Bullet bullet : gameState.getBullets()) {
			// moving the bullet
			if (bullet.getDirection() == Direction.Up) {
				bullet.move(0, -Mathx.distance(timeDelta, bullet.getSpeed()));
			} else {
				if (bullet.getType() == BulletType.Normal) {
					bullet.move(0, Mathx.distance(timeDelta, bullet.getSpeed()));
				} else if (bullet.getType() == BulletType.Homing) {
					Coordinate target = gameState.getPlayer(PlayerIndex.One).getPosition().clone();
					target.x += gameState.getPlayer(PlayerIndex.One).getWidth() / 2;
					Coordinate vector = Mathx.angle(gameState.getPlayer(PlayerIndex.One).getPosition(), bullet.getPosition());
					vector.normalize();

					bullet.move(vector.x * Mathx.distance(timeDelta, bullet.getSpeed()) * 0.75, Mathx.distance(timeDelta, bullet.getSpeed()));
				}
			}

			if (bullet.getPosition().y <= 0) {
				bullet.destroy();
			}
			// collision detection
			if (bullet.getDirection() == Direction.Up) {
				for (Iterator<Invader> invaders = gameState.getInvaders().iterator(); invaders.hasNext();) {
					Invader invader = invaders.next();

					if (Mathx.intersects(bullet, invader)) {
						bullet.destroy();

						invader.healthDown();
						if (invader.isDead()) {
							invader.destroy();
						}
						break;
					}
				}
			}

			for (Iterator<Bullet> innerBullets = gameState.getBullets().iterator(); innerBullets.hasNext();) {
				Bullet collisionBullet = innerBullets.next();
				// don't check collision with self...
				if (!bullet.equals(collisionBullet) && Mathx.intersects(bullet, collisionBullet)) {
					bullet.destroy();
					collisionBullet.destroy();
					break;
				}
			}

			// player collision
			if (bullet.getDirection() == Direction.Down && Mathx.intersects(bullet, gameState.getPlayer(PlayerIndex.One))) {
				gameState.getPlayer(PlayerIndex.One).livesDown();
				// TODO fire some command to pause and respawn the player
				bullet.destroy();
			}
		}
	}

	private void updateInvaders(GameState gameState, long timeDelta) {
		boolean wallHit = false;
		for (Invader invader : gameState.getInvaders()) {
			if (gameState.getMoveInvadersRight()) {
				invader.move(Mathx.distance(timeDelta, invader.getSpeed()), 0);
			} else {
				invader.move(-Mathx.distance(timeDelta, invader.getSpeed()), 0);
			}

			wallHit = wallHit || (invader.getPosition().x + invader.getWidth() > GameModel.SCREEN_WIDTH) || (invader.getPosition().x < 0);
		}

		if (wallHit) {
			gameState.setMoveInvadersRight(!gameState.getMoveInvadersRight());
			for (Invader invader : gameState.getInvaders()) {
				invader.move(0, 15); // TODO y coord to diff
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
		// map of lowest invaders in each x column
		HashMap<Double, Invader> lowest = new HashMap<Double, Invader>();

		for (Invader invader : gameState.getInvaders()) {
			double column = invader.getPosition().x;
			if (!lowest.containsKey(column)) {
				lowest.put(column, invader);
				continue;
			}

			for (Invader innerInvader : gameState.getInvaders()) {
				if (!invader.equals(innerInvader) && column == innerInvader.getPosition().x && invader.getPosition().y < innerInvader.getPosition().y) {
					lowest.remove(invader);
					lowest.put(column, innerInvader);
				}
			}
		}

		ArrayList<Invader> trimmed = new ArrayList<Invader>();
		for (Invader invader : lowest.values()) {
			trimmed.add(invader);
		}
		// nu har man array med forreste invaders
		Invader shootingInvader = trimmed.get((int) (Math.random() * trimmed.size()));
		if (gameState.getLastInvaderShot() - currentTime < -1000) { // shoot!
			gameState.setLastInvaderShot(currentTime);
			Bullet currentShot = new Bullet(Direction.Down, shootingInvader.getBulletType());
			currentShot.setPosition(shootingInvader.getPosition().clone());
			currentShot.move(24, 50);
			gameState.getBullets().add(currentShot);
		}
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
