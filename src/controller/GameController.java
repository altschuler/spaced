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
		// Player is dead = loose
		for (Invader invader : gameState.getInvaders()) {
			if (Mathx.intersects(invader, gameState.getPlayer(PlayerIndex.One))) {
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

				Bullet currentShot = new Bullet(BulletDirection.Up);
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
		int noOfShots = gameState.getBullets().size();
		for (int i = 0; i < noOfShots; i++) {
			Bullet bullet = gameState.getBullets().get(i);
//moving the bullet
			if (bullet.getDirection() == BulletDirection.Up) {
				bullet.move(0,-Mathx.distance(timeDelta, bullet.getSpeed()));
			} else {
				bullet.move(0, Mathx.distance(timeDelta, bullet.getSpeed()));
			}

			if (bullet.getPosition().y <= 0) {
				gameState.getBullets().remove(i);
				noOfShots--;
			}
//collision detection
			int noOfInvaders = gameState.getInvaders().size();
			for (int j = 0; j < noOfInvaders; j++) { 		//invader-collisions
				Invader invader = gameState.getInvaders().get(j);

				if (Mathx.intersects(bullet, invader)) {
					gameState.getBullets().remove(i);
					gameState.getInvaders().remove(j);
					noOfInvaders--;
					noOfShots--;
					break;
				}
			}

			for (int j = 0; j < noOfShots; j++) {			//bullet-collisions
				Bullet collisionBullet = gameState.getBullets().get(j);
				if(i != j && Mathx.intersects(bullet, collisionBullet)){ //don't check collision with self...
					System.out.println("Collision! i: "+i+" j: "+j);
					gameState.getBullets().remove(i);
					if(i < j){
						gameState.getBullets().remove(j-1);
					}else{
						gameState.getBullets().remove(j);
					}
/*
 * Patricks-note-to-self: remove(j) fucker op, for hvis i er mindre end j, så er den søgte j = j-1...					
 */					
					
					
					noOfShots--;
					noOfShots--;
					break;
				}
			}
			
			//TODO add a check for whether bullets hit one another
		}
	}

	private void updateInvaders(GameState gameState, long timeDelta) {
		ArrayList<Invader> invaders = gameState.getInvaders();
		boolean wallHit = false;

		for (int i = 0; i < invaders.size(); i++) {
			Invader invader = invaders.get(i);

			if (gameState.getMoveInvadersRight()) {
				invader.move(Mathx.distance(timeDelta, invader.getSpeed()),0);
			} else {
				invader.move(-Mathx.distance(timeDelta, invader.getSpeed()),0);
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
	 * @param gameState
	 * 
	 * Find de invaders der er placeret nederst, og lad en random en af dem skyde.
	 */
	private void invadersShoot(GameState gameState, long currentTime){
		//array med laveste invaders
		ArrayList<Invader> lowestInvaders = gameState.getLowestInvaders();
		
		ArrayList<Invader> invaders = gameState.getInvaders();
		for (int i = 0; i < invaders.size(); i++) {
			if(lowestInvaders.size()==0){//hvis der endnu ikke er en nederst invader
				lowestInvaders.add(invaders.get(0));
			}
			
			if(invaders.get(i).getPosition().y == lowestInvaders.get(0).getPosition().y){ //på niveau med den forreste invader
				lowestInvaders.add(invaders.get(i));
			}else if(invaders.get(i).getPosition().y > lowestInvaders.get(0).getPosition().y){ //længere fremme end den
				lowestInvaders.clear();
				lowestInvaders.add(invaders.get(i));
			}
		}
		//nu har man array med forreste invaders
		int shootingInvader = (int) (Math.random()*lowestInvaders.size());
		
		if(gameState.getLastInvaderShot() - currentTime < -1000) { //shoot!
			gameState.setLastInvaderShot(currentTime);
			Bullet currentShot = new Bullet(BulletDirection.Down);
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
