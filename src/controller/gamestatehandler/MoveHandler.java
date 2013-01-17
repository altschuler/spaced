package controller.gamestatehandler;

import java.awt.event.KeyEvent;

import utils.Input;
import utils.Mathx;
import model.GameState;
import model.GameStateState;
import model.MainModel;
import model.core.Coordinate;
import model.core.Difficulty;
import model.core.Direction;
import model.core.PlayerIndex;
import model.elements.Bonus;
import model.elements.Bullet;
import model.elements.Invader;
import model.elements.KillableGameElement;
import model.elements.NicholasCage;
import model.elements.Player;

public class MoveHandler{
	
	public MoveHandler() {
	}
	
	
	public void moveAll(GameState gameState, long timeDelta, Difficulty activeDifficulty){
		this.movePlayer(gameState, timeDelta, activeDifficulty);
		this.moveInvaders(gameState, timeDelta, activeDifficulty);
		this.moveIndividualEnemies(gameState, timeDelta, activeDifficulty);
		this.moveBullets(gameState, timeDelta, activeDifficulty);
		this.moveBonuses(gameState, timeDelta);
	}
	
	private void movePlayer(GameState gameState, long timeDelta, Difficulty activeDifficulty){
		Player player = gameState.getPlayer(PlayerIndex.One);
		
		if (Input.getInstance().isKeyDown(KeyEvent.VK_LEFT)) {
			player.getPosition().x -= Mathx.distance(timeDelta, player.getSpeed() * activeDifficulty.getPlayerSpeed());
		}

		if (Input.getInstance().isKeyDown(KeyEvent.VK_RIGHT)) {
			player.getPosition().x += Mathx.distance(timeDelta, player.getSpeed() * activeDifficulty.getPlayerSpeed());
		}

		//Avoid player moving outsite the screen
		player.getPosition().x = Math.max(0, player.getPosition().x);
		player.getPosition().x = Math.min(MainModel.SCREEN_WIDTH - player.getWidth(), player.getPosition().x);
	}

	private void moveInvaders(GameState gameState, long timeDelta, Difficulty activeDifficulty){
		boolean wallHit = false;
//Checking whether the game is lost
		for (Invader invader : gameState.getInvaders()) {
			if(invader.getPosition().y + invader.getHeight() >= gameState.getPlayer(PlayerIndex.One).getPosition().y){
				gameState.setState(GameStateState.Lost);
				gameState.getPlayer(PlayerIndex.One).setLives(0);
				return;
			}
			
//Checking if move will cause the wall to be hit (wallHit)
			if (gameState.getMoveInvadersRight()) {
				if(invader.getPosition().x+invader.getWidth() +Mathx.distance(timeDelta, (invader.getSpeed() * activeDifficulty.getInvaderSpeed()/10)) > MainModel.SCREEN_WIDTH){
					wallHit = true;
					gameState.setMoveInvadersRight(false);
					break;
				}
			}else if(invader.getPosition().x -Mathx.distance(timeDelta, (invader.getSpeed() * activeDifficulty.getInvaderSpeed()/10)) < 0){
					wallHit = true;
					gameState.setMoveInvadersRight(true);
					break;
			}
		}
//if the wall is hit: move down
		if (wallHit) {
			for (Invader invader : gameState.getInvaders()) {
				invader.move(0, 15); // TODO y coord to diff
			}
		}
//Check if invaders are slowed
//move Invaders!
		for (Invader invader : gameState.getInvaders()) {
			double speedMultiplier = invader.getSpeedMultiplier(timeDelta);
			if (gameState.getMoveInvadersRight()) {
				invader.move(Mathx.distance(timeDelta, (invader.getSpeed() * speedMultiplier * activeDifficulty.getInvaderSpeed()/10)), 0);
			} else {
				invader.move(-Mathx.distance(timeDelta, (invader.getSpeed() * speedMultiplier * activeDifficulty.getInvaderSpeed()/10)), 0);
			}
		}
	
	}

	private void moveIndividualEnemies(GameState gameState, long timeDelta, Difficulty activeDifficulty){
		for (KillableGameElement randomEnemy : gameState.getIndividualEnemies()) {
			if(randomEnemy instanceof NicholasCage){
				NicholasCage nCage = (NicholasCage) randomEnemy;
				nCage.updateNicholas(timeDelta);
				nCage.move(nCage.getDirectionMultiplier()*Math.cos(nCage.getNicholasTime()/1000.0)*timeDelta/3.0,
						Math.sin(nCage.getNicholasTime()/500.0)*timeDelta/10);
				nCage.moveCages();
			}
		}
	}

	private void moveBullets(GameState gameState, long timeDelta, Difficulty activeDifficulty){
		for (Bullet bullet : gameState.getBullets()) {
			// moving the bullet
			if (bullet.getDirection() == Direction.Up) {
				bullet.move(0, -Mathx.distance(timeDelta, bullet.getSpeed()));
				if (bullet.getPosition().y <= 30) { //30 is the top bar height
					bullet.destroy();
				}
			} else {		//reminder: these are ALIENS' shots!!!
				switch(bullet.getType()){
					case Normal:
						bullet.move(0, Mathx.distance(timeDelta, bullet.getSpeed()));
						break;
					case Homing:
						Coordinate target = gameState.getPlayer(PlayerIndex.One).getPosition().clone();
						target.x += gameState.getPlayer(PlayerIndex.One).getWidth() / 2;
						Coordinate vector = Mathx.getDirectionVector(target, bullet.getPosition());
						vector.normalize();
						bullet.move(vector.x * Mathx.distance(timeDelta, bullet.getSpeed()) * 0.75, Mathx.distance(timeDelta, bullet.getSpeed()));
						break;
					default:
						break;
				}
				if (bullet.getPosition().y >= 670) { //670 is the SCREEN_HEIGHT - bottomInfoLineHeight
					bullet.destroy();
				}
			}
		}
	}

	private void moveBonuses(GameState gameState, long timeDelta){
		for(Bonus bonus : gameState.getBonuses()){
			bonus.move(0, Mathx.distance(timeDelta, bonus.getSpeed()));
		}
	}
}
