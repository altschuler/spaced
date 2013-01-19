package controller.gamestatehandler;

import java.util.Iterator;

import service.resources.SoundHandler;
import utils.Mathx;
import model.GameState;
import model.core.BulletType;
import model.core.Coordinate;
import model.core.Difficulty;
import model.core.Direction;
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

public class CollisionHandler {
	public CollisionHandler(){
		
	}
	
	/**
	 * Calls all the collisionCheck-methods
	 * @param gameState
	 * @param timeDelta
	 * @param activeDifficulty
	 */
	public void collisionCheck(GameState gameState, long timeDelta, Difficulty activeDifficulty){
		this.playerCollisions(gameState, timeDelta, activeDifficulty);
		this.playerBulletCollisions(gameState, timeDelta, activeDifficulty);
		this.otherBulletCollisions(gameState, timeDelta, activeDifficulty);
	}
	
	/**
	 * Checks what the player collides with, except from bullets
	 * @param gameState
	 * @param timeDelta
	 * @param activeDifficulty
	 */
	private void playerCollisions(GameState gameState, long timeDelta, Difficulty activeDifficulty){
		Player player = gameState.getPlayer(PlayerIndex.One);
		
		for(Iterator<Bonus> bonus = gameState.getBonuses().iterator(); bonus.hasNext();){
			Bonus collisionBonus = bonus.next();
			if(Mathx.intersects(player, collisionBonus)){
				switch(collisionBonus.getBonusType()){
				case Health:
					player.setLives(player.getLives()+1);
					break;
				case Score:
					player.setPoints(player.getPoints()+50*(activeDifficulty.getId()+1));
					break;
				case Slow:
					for (Invader invader : gameState.getInvaders()) {
						invader.setFrozenTime(5000+invader.getFrozenTime());
					}
					break;
				}
				collisionBonus.destroy();
				break;
			}
		}
	}

	/**
	 * Checks collisions for bullets heading upwards
	 * @param gameState
	 * @param timeDelta
	 * @param activeDifficulty
	 */
	private void playerBulletCollisions(GameState gameState, long timeDelta, Difficulty activeDifficulty){

		for (Bullet bullet : gameState.getBullets()) {
			if (bullet.getDirection() == Direction.Up) {
//Check for collision with invaders
				for (Iterator<Invader> invaders = gameState.getInvaders().iterator(); invaders.hasNext();) {
					Invader invader = invaders.next();
					if (Mathx.intersects(bullet, invader)) {
						
						if(bullet.getType() == BulletType.Explosive){ //in case an invader is hit by a missile
							double explosionRadius = 55.0;
							
							gameState.getAnimations().add(new Animation("explosionAoE.png", bullet.getPosition().clone(), 6,50, true));
							SoundHandler.getInstance().playSound("boom01.wav", 0, 0,-1.0f);
							
							for(Iterator<Invader> moreInvaders = gameState.getInvaders().iterator(); moreInvaders.hasNext();){
								Invader anotherInvader = moreInvaders.next();
								Coordinate explosionCenter = new Coordinate(bullet.getPosition().x+((double) bullet.getWidth()/2),
										bullet.getPosition().y+((double) bullet.getHeight()/2));

								if(Mathx.circleRectangleIntersects(anotherInvader, explosionCenter, explosionRadius)){
									anotherInvader.healthDown();
									if (anotherInvader.isDead()) {
										this.addExplosion(gameState, anotherInvader);
										anotherInvader.destroy();
										}
								}
							}
						}
						invader.healthDown();
						if (invader.isDead()) {
							this.addExplosion(gameState, invader);
							invader.destroy();
						}
						bullet.destroy();
						break;
					}
				}

//Random enemies
				for (Iterator<KillableGameElement> individualEnemies = gameState.getIndividualEnemies().iterator(); individualEnemies.hasNext();) {
					KillableGameElement randomEnemy = individualEnemies.next();

					if(randomEnemy instanceof NicholasCage){
						NicholasCage nCage = (NicholasCage) randomEnemy;
						if(Mathx.circleRectangleIntersects(bullet, nCage.getNicholasCenter(), nCage.getCageRadius()-60)){
							if(nCage.getCages().size() == 0){
								nCage.healthDown();
								if (nCage.isDead()) {
									this.addExplosion(gameState, nCage);
									nCage.destroy();
								}	
							}
							bullet.destroy();
							break;
						}else{
							for(Cage cage : nCage.getCages()){
								if (Mathx.intersects(bullet, cage)){
									cage.healthDown();
									if (cage.isDead()) {
										this.addExplosion(gameState, cage);
										cage.destroy();
									}
									bullet.destroy();
									break;
								}
							}
						}
					}

					if (Mathx.intersects(bullet, randomEnemy) && !(randomEnemy instanceof NicholasCage)) {
						randomEnemy.healthDown();
						bullet.destroy();
						break;
					}
				}
//Hvis man uheldigvis skyder sin bonus			
				for(Iterator<Bonus> bonus = gameState.getBonuses().iterator(); bonus.hasNext();){
					Bonus collisionBonus = bonus.next();
					if(Mathx.intersects(bullet, collisionBonus)){
						collisionBonus.destroy();
						bullet.destroy();
						break;
					}
				}
			
				//after for-loop
				// bunker collision
				for (Iterator<Bunker> innerBunker = gameState.getBunkers().iterator(); innerBunker.hasNext();) {
					Bunker collisionBunker = innerBunker.next();
					if(Mathx.intersects(bullet, collisionBunker)){
						collisionBunker.healthDown();
						if(collisionBunker.getHealth() < 2){
							collisionBunker.setImageURL("bunkerPartBrokenUpwardsBullet.png");
						}
						if (collisionBunker.isDead()) {
							collisionBunker.destroy();
						}
						bullet.destroy();
						break;
					}
				}
			}
		}
	
	}


	/**
	 * Checks collisions for bullets heading downwards
	 * @param gameState
	 * @param timeDelta
	 * @param activeDifficulty
	 */
	private void otherBulletCollisions(GameState gameState, long timeDelta, Difficulty activeDifficulty){
		for (Bullet bullet : gameState.getBullets()) {
			if(bullet.getDirection() == Direction.Down){

				for (Iterator<Bullet> innerBullets = gameState.getBullets().iterator(); innerBullets.hasNext();) {
					Bullet collisionBullet = innerBullets.next();
					// don't check collision with self...
					if (!bullet.equals(collisionBullet) && collisionBullet.getDirection() == Direction.Up && Mathx.intersects(bullet, collisionBullet)) {
						bullet.destroy();
						collisionBullet.destroy();
						break;
					}
				}

// player collision
				if (bullet.getDirection() == Direction.Down && Mathx.intersects(bullet, gameState.getPlayer(PlayerIndex.One))) {
					gameState.getPlayer(PlayerIndex.One).livesDown();
					SoundHandler.getInstance().playSound("sheep01.wav", 0, 0,6.0f);
					// TODO fire some command to pause and respawn the player
					bullet.destroy();
				}
				
// bunker collision
				for (Iterator<Bunker> innerBunker = gameState.getBunkers().iterator(); innerBunker.hasNext();) {
					Bunker collisionBunker = innerBunker.next();
					if(Mathx.intersects(bullet, collisionBunker)){
						collisionBunker.healthDown();
						if(collisionBunker.getHealth() < 2){
							collisionBunker.setImageURL("bunkerPartBroken.png");
						}
						if (collisionBunker.isDead()) {
							collisionBunker.destroy();
						}
						bullet.destroy();
						break;
					}
				}
			}
		}
	}

	
	
	/**
	 * Creates a new Animation if a Missle has hit an Invader
	 * @param gameState
	 * @param gameElement
	 */
	private void addExplosion(GameState gameState, GameElement gameElement){
		gameState.getAnimations().add(new Animation("explosionSprite.png", gameElement.getPosition().clone(), 6,120, false));
	}
}
