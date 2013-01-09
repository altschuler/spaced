package controller;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.Timer;

import model.GameModel;
import model.GameState;
import model.GameStateFactory;
import model.elements.Bullet;
import model.elements.BulletDirection;
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
        private GameStateFactory factory;

	public GameController(MainView gw, GameModel gm) {
		super(gw, gm);

		this.renderer = new GameStateRenderer();
                this.factory = new GameStateFactory();

		// TODO dont init this here!
		factory.createLevelOne();
		gameState.setLastUpdateTime(System.currentTimeMillis());
		gm.setActiveGameState(gameState);
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
		this.updateShots(gameState, timeDelta);
		this.updateInvaders(gameState, timeDelta);

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
		// Player is dead = loose
		for (Invader invader : gameState.getInvaders()) {
			if (Mathx.intersects(invader, gameState.getPlayer())) {
				CommandFactory.createSetStateCommand(ViewState.GameOver).execute();
				return true;
			}
		}

		// All invaders gone = win
		if (gameState.getInvaders().size() == 0) {
			CommandFactory.createSetStateCommand(ViewState.GameOver).execute();
			return true;
		}

		return false;
	}

	public void updatePlayer(GameState gameState, long timeDelta) {
		Player player = gameState.getPlayer();

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

				Bullet currentShot = new Bullet(BulletDirection.Up);
				currentShot.setPosition(player.getPosition().clone());
				currentShot.getPosition().x += 24;
				gameState.getShots().add(currentShot);
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
		int noOfShots = gameState.getShots().size();
		for (int i = 0; i < noOfShots; i++) {
			Bullet bullet = gameState.getShots().get(i);

			if (bullet.getDirection() == BulletDirection.Up) {
				bullet.getPosition().y -= Mathx.distance(timeDelta, bullet.getSpeed());
			} else {
				bullet.getPosition().y += Mathx.distance(timeDelta, bullet.getSpeed());
			}

			if (bullet.getPosition().y <= 0) {
				gameState.getShots().remove(i);
				noOfShots--;
			}

			int noOfInvaders = gameState.getInvaders().size();
			for (int j = 0; j < noOfInvaders; j++) {
				Invader invader = gameState.getInvaders().get(j);

				if (Mathx.intersects(bullet, invader)) {
					gameState.getShots().remove(i);
					gameState.getInvaders().remove(j);
					noOfInvaders--;
					noOfShots--;
					break;
				}
			}
		}
	}

	private void updateInvaders(GameState gameState, long timeDelta) {
		ArrayList<Invader> invaders = gameState.getInvaders();
		boolean wallHit = false;

		for (int i = 0; i < invaders.size(); i++) {
			Invader invader = invaders.get(i);

			if (gameState.getMoveInvadersRight()) {
				invader.getPosition().x += Mathx.distance(timeDelta, invader.getSpeed());
			} else {
				invader.getPosition().x -= Mathx.distance(timeDelta, invader.getSpeed());
			}

			wallHit = wallHit || (invader.getPosition().x + invader.getWidth() > GameModel.SCREEN_WIDTH) || (invader.getPosition().x < 0);
		}

		if (wallHit) {
			gameState.setMoveInvadersRight(!gameState.getMoveInvadersRight());
			for (Invader invader : invaders) {
				invader.getPosition().y += 15; // TODO refactor out to something
												// like DifficultyConfiguration
			}
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
