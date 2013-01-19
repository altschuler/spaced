package controller.gamestatehandler;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

import service.resources.SoundHandler;
import utils.Input;
import model.GameState;
import model.core.BulletType;
import model.core.Difficulty;
import model.core.Direction;
import model.core.PlayerIndex;
import model.elements.Bullet;
import model.elements.Cage;
import model.elements.Invader;
import model.elements.KillableGameElement;
import model.elements.NicholasCage;
import model.elements.Player;

/**
 * Is called by {@link controller.GameController} to initialize {@link model.elements.Bullet}s in the current {@link model.GameState}
 */
public class ShootingHandler{
	
	public ShootingHandler() {
	}
	
	/**
	 * Calls all methods regarding bullet initializing 
	 * @param gameState
	 * @param timeDelta
	 * @param activeDifficulty
	 * @param currentTime
	 */
	public void createShots(GameState gameState, long timeDelta, Difficulty activeDifficulty, long currentTime){
		this.playerShoots(gameState, timeDelta, activeDifficulty);
		this.invadersShoot(gameState, currentTime, activeDifficulty);
		this.individualEnemiesShoot(gameState, timeDelta, activeDifficulty);
	}
	
	/**
	 * Initializes the player's bullets
	 * @param gameState
	 * @param timeDelta
	 * @param activeDifficulty
	 */
	private void playerShoots(GameState gameState, long timeDelta, Difficulty activeDifficulty){
		Player player = gameState.getPlayer(PlayerIndex.One);
				if (Input.getInstance().isKeyDown(KeyEvent.VK_SPACE)) {
					// the player can only shoot once per playerShotFrequency
					long currentTime = System.currentTimeMillis();
					if (currentTime - player.getTimeOfLastShot()> activeDifficulty.getPlayerShootFreq()) {
						player.setTimeOfLastShot(currentTime);
						SoundHandler.getInstance().playSound("zap01.wav", 0, 0,-1.0f);

						Bullet currentShot = new Bullet(Direction.Up, player.getWeapon(), "bullet.png");
						currentShot.setPosition(player.getPosition().clone());
						currentShot.getPosition().x += player.getWidth() / 2;
						switch(currentShot.getType()){
						case Fast:
							currentShot.setImageURL("fastBullet.png");
							currentShot.setSpeed(currentShot.getSpeed()*2);
							break;
						case Explosive:
							currentShot.setImageURL("missile.png");
							break;
						case Normal:
							break;
						case Homing:
							break;
					}
						gameState.getBullets().add(currentShot);
					}
				}
	}

	/**
	 * Initializes the Invaders' bullets.
	 * Only the Invader in front of each column may shoot.
	 * @param gameState
	 * @param currentTime
	 * @param activeDifficulty
	 */
	private void invadersShoot(GameState gameState, long currentTime, Difficulty activeDifficulty) {
		//check if invaders should shoot, THEN do all the other stuff
		if (currentTime - gameState.getLastInvaderShot() > activeDifficulty.getInvaderShootFreq()) {
			
			// map of lowest invaders in each x column
			HashMap<Double, Invader> lowest = new HashMap<Double, Invader>();

			for (Invader invader : gameState.getInvaders()) {
				double column = invader.getPosition().x;
				if (!lowest.containsKey(column)) {
					lowest.put(column, invader);
					continue;
				}

				for (Invader innerInvader : gameState.getInvaders()) {
					if(!invader.equals(innerInvader) && column == innerInvader.getPosition().x){
						if (invader.getPosition().y < innerInvader.getPosition().y) {
							lowest.remove(invader);
							lowest.put(column, innerInvader);
						}else if(invader.getPosition().y > innerInvader.getPosition().y){
							lowest.remove(innerInvader);
							lowest.put(column, invader);
						}
					}
				}
			}
			ArrayList<Invader> trimmed = new ArrayList<Invader>();
			for (Invader invader : lowest.values()) {
				trimmed.add(invader);
			}

			if (trimmed.size() == 0) {
				return;
			}

			Invader shootingInvader = trimmed.get((int) (Math.random() * trimmed.size()));
			
			gameState.setLastInvaderShot(currentTime);
			Bullet currentShot = new Bullet(Direction.Down, shootingInvader.getBulletType(),"bulletInvader.png");
			SoundHandler.getInstance().playSound("zap05.wav", 0, 0,2.0f);
			currentShot.setPosition(shootingInvader.getPosition().clone());
			currentShot.move(24, 50);
			
			switch(currentShot.getType()){
			case Homing:
				currentShot.setImageURL("bulletInvaderHoming.png");
				break;
			default:
				break;
			}
			
			gameState.getBullets().add(currentShot);
			return;
		}
	}


	/**
	 * Initializes the bullets for the individual moving enemies
	 * @param gameState
	 * @param timeDelta
	 * @param activeDifficulty
	 */
	private void individualEnemiesShoot(GameState gameState, long timeDelta, Difficulty activeDifficulty){
		for (KillableGameElement randomEnemy : gameState.getIndividualEnemies()) {
			if(randomEnemy instanceof NicholasCage){
				NicholasCage nCage = (NicholasCage) randomEnemy;
				for (Cage cage : nCage.getCages()) {
					if(Math.random() < 0.06 * timeDelta / activeDifficulty.getInvaderShootFreq()){
						Bullet currentShot = new Bullet(Direction.Down, BulletType.Homing,"bulletInvaderHoming.png");
						SoundHandler.getInstance().playSound("zap05.wav", 0, 0,2.0f);
						currentShot.setPosition(cage.getPosition().clone());
						currentShot.move(24, 50);
						gameState.getBullets().add(currentShot);
					}
				}
			}
			
		}
	}
}
