package controller;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.Timer;
import model.GameState;
import model.GameStateState;
import model.MainModel;
import model.core.BulletType;
import model.core.Coordinate;
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
import service.resources.SoundHandler;
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

	public GameController(MainView gw, MainModel gm) {
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
		if(timeDelta > 30){	timeDelta = 30;	}
		
		// Consider gameState's state
		if (gameState.getState() == GameStateState.Waiting) {
			if (Input.getInstance().isKeyDown(KeyEvent.VK_ENTER)) {
				gameState.setState(GameStateState.Running);
			}else {
				this.renderer.render(gameView.getDisplay(), gameState, this.gameModel);
				gameState.setLastUpdateTime(currentTime);
				return;
			}
		}
		
		if (gameState.getState() == GameStateState.Paused) {
			this.renderer.render(gameView.getDisplay(), gameState, this.gameModel);
			gameState.setLastUpdateTime(currentTime);
			return;
		}
		
		// Escape triggers in-game menu
		if (Input.getInstance().isKeyDown(KeyEvent.VK_ESCAPE)) {
			CommandFactory.createSetStateCommand(ViewState.PauseMenu).execute();
			gameState.setLastUpdateTime(currentTime);
			return;
		}

		// Update elements
		this.updatePlayer(gameState, timeDelta);
		this.updateInvaders(gameState, timeDelta);
		this.updateIndividualEnemies(gameState, timeDelta);
		this.updateBonuses(gameState, timeDelta);
		this.individualEnemiesShoot(gameState, timeDelta);
		this.invadersShoot(gameState, currentTime);
		this.updateBullets(gameState, timeDelta);

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
		
		for (Iterator<Bonus> bonuses = gameState.getBonuses().iterator(); bonuses.hasNext();) {
			Bonus bonus = bonuses.next();
			if (bonus.isDestroyed()) {
				bonuses.remove();
			}
		}

		boolean hasInvaderDied = false;
		double bonusThreshold = 0.15; //TODO: make the difficulties decide this, perhaps?

		for (Iterator<Invader> invaders = gameState.getInvaders().iterator(); invaders.hasNext();) {
			Invader invader = invaders.next();
			if (invader.isDestroyed()) {
				hasInvaderDied = true;
//Spawns bonus and grants the player points
				if(Math.random() < bonusThreshold){	gameState.getBonuses().add(new Bonus(invader.getPosition().clone()));	}
				gameState.getPlayer(PlayerIndex.One).setPoints(gameState.getPlayer(PlayerIndex.One).getPoints()+invader.getPoints()*(1+this.gameModel.getActiveDifficulty().getId()));
				invaders.remove();
			}
		}
		//Patty quick find
		for(Iterator<KillableGameElement> randomEnemies = gameState.getIndividualEnemies().iterator(); randomEnemies.hasNext();){
			KillableGameElement randomEnemy = randomEnemies.next();

			if(randomEnemy instanceof NicholasCage){  //checking Nicholas' cages
				for(Iterator<Cage> cages = ((NicholasCage) randomEnemy).getCages().iterator(); cages.hasNext();){
					Cage cage = cages.next();
					if(cage.isDestroyed()){
						hasInvaderDied = true;
						gameState.getPlayer(PlayerIndex.One).setPoints(gameState.getPlayer(PlayerIndex.One).getPoints()+cage.getPoints()*(1+this.gameModel.getActiveDifficulty().getId()));
						cages.remove();
					}
				}
			}
			
			if(randomEnemy.isDestroyed()){
				hasInvaderDied = true;
				gameState.getPlayer(PlayerIndex.One).setPoints(gameState.getPlayer(PlayerIndex.One).getPoints()+randomEnemy.getPoints()*(1+this.gameModel.getActiveDifficulty().getId()));
				randomEnemies.remove();
			}
		}
		
		if(hasInvaderDied){	//making this check to avoid the annoying effect of the same sound being played milliseconds apart!
			SoundHandler.getInstance().playSound("boom03.wav", 0, 0,-1.0f);
		}
		

		for (Iterator<Bunker> bunkers = gameState.getBunkers().iterator(); bunkers.hasNext();) {
			Bunker bunker = bunkers.next();
			if (bunker.isDestroyed()) {
				bunkers.remove();
			}
		}
	}

	private void updateBonuses(GameState gameState, long timeDelta){
		for(Bonus bonus : gameState.getBonuses()){
			bonus.move(0, Mathx.distance(timeDelta, bonus.getSpeed()));
		}
	}
	
	/**
	 * Check if game is over
	 * 
	 * @return True if game is over for any reason
	 */
	private boolean checkGameOver(GameState gameState) {
		// Player has no more lives = loose
		if (gameState.getState() == GameStateState.Lost) {
			CommandFactory.createSetStateCommand(ViewState.GameOver).execute();
			return true;
		}

		// All invaders gone = win
		if (gameState.getInvaders().size() == 0 && gameState.getIndividualEnemies().size() == 0) {
			gameState.setState(GameStateState.Won);
			CommandFactory.createLoadNextLevelCommand().execute();
			return true;
		}

		return false;
	}

	public void updatePlayer(GameState gameState, long timeDelta) {
		Player player = gameState.getPlayer(PlayerIndex.One);
		
		if (gameState.getPlayer(PlayerIndex.One).getLives() <= 0) {
			gameState.setState(GameStateState.Lost);
		}
		
		if (Input.getInstance().isKeyDown(KeyEvent.VK_LEFT)) {
			player.getPosition().x -= Mathx.distance(timeDelta, player.getSpeed() * gameModel.getActiveDifficulty().getPlayerSpeed());
		}

		if (Input.getInstance().isKeyDown(KeyEvent.VK_RIGHT)) {
			player.getPosition().x += Mathx.distance(timeDelta, player.getSpeed() * gameModel.getActiveDifficulty().getPlayerSpeed());
		}
		
		if (Input.getInstance().isKeyDown(KeyEvent.VK_1)) {
			player.setWeapon(BulletType.Normal);
		}
		if (Input.getInstance().isKeyDown(KeyEvent.VK_2)) {
			player.setWeapon(BulletType.Fast);
		}
		if (Input.getInstance().isKeyDown(KeyEvent.VK_3)) {
			player.setWeapon(BulletType.Explosive);
		}
// clears the current level of invaders (debug)
		if (Input.getInstance().isKeyDown(KeyEvent.VK_4) && Input.getInstance().isKeyDown(KeyEvent.VK_5)
				&& Input.getInstance().isKeyDown(KeyEvent.VK_UP)) {
			for(Iterator<Invader> moreInvaders = gameState.getInvaders().iterator(); moreInvaders.hasNext();){
				Invader anotherInvader = moreInvaders.next();
				anotherInvader.destroy();
			}
		}
		
		//Player shoots
		if (Input.getInstance().isKeyDown(KeyEvent.VK_SPACE)) {
			// the player can only shoot once per playerShotFrequency
			long currentTime = System.currentTimeMillis();
			if (currentTime - player.getTimeOfLastShot()> gameModel.getActiveDifficulty().getPlayerShootFreq()) {
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

		for(Iterator<Bonus> bonus = gameState.getBonuses().iterator(); bonus.hasNext();){
			Bonus collisionBonus = bonus.next();
			if(Mathx.intersects(player, collisionBonus)){
				switch(collisionBonus.getBonusType()){
				case Health:
					player.setLives(player.getLives()+1);
					break;
				case Score:
					player.setPoints(player.getPoints()+50*(gameModel.getActiveDifficulty().getId()+1));
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
		
		
//Avoid player moving outsite the screen
		player.getPosition().x = Math.max(0, player.getPosition().x);
		player.getPosition().x = Math.min(MainModel.SCREEN_WIDTH - player.getWidth(), player.getPosition().x);
	}

	/**
	 * @param gameState
	 *            Moves the bullets upwards
	 */
	private void updateBullets(GameState gameState, long timeDelta) {
		for (Bullet bullet : gameState.getBullets()) {
			// moving the bullet
			if (bullet.getDirection() == Direction.Up) {
				bullet.move(0, -Mathx.distance(timeDelta, bullet.getSpeed()));
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
			}

			if (bullet.getPosition().y <= renderer.getTopBarHeight()) {
				bullet.destroy();
			}
			// collision detection
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
						bullet.destroy();
						invader.healthDown();
						if (invader.isDead()) {
							this.addExplosion(gameState, invader);
							invader.destroy();
						}
						break;
					}
				}

				//Random enemies
				for (Iterator<KillableGameElement> individualEnemies = gameState.getIndividualEnemies().iterator(); individualEnemies.hasNext();) {
					KillableGameElement randomEnemy = individualEnemies.next();
					//Patty quick find
					if(randomEnemy instanceof NicholasCage){
						NicholasCage nCage = (NicholasCage) randomEnemy;
						if(Mathx.circleRectangleIntersects(bullet, nCage.getNicholasCenter(), nCage.getCageRadius()-60)){
							bullet.destroy();
							if(nCage.getCages().size() == 0){
								nCage.healthDown();
								if (nCage.isDead()) {
									this.addExplosion(gameState, nCage);
									nCage.destroy();
								}	
							}
						}else{
							for(Cage cage : nCage.getCages()){
								if (Mathx.intersects(bullet, cage)){
									bullet.destroy();
									cage.healthDown();
									if (cage.isDead()) {
										this.addExplosion(gameState, cage);
										cage.destroy();
									}
								}
							}
						}
					}

					if (Mathx.intersects(bullet, randomEnemy) && !(randomEnemy instanceof NicholasCage)) {
						bullet.destroy();
						randomEnemy.healthDown();
					}
				}
			}

			for (Iterator<Bullet> innerBullets = gameState.getBullets().iterator(); innerBullets.hasNext();) {
				Bullet collisionBullet = innerBullets.next();
				// don't check collision with self...
				if (!bullet.equals(collisionBullet) && bullet.getDirection() != collisionBullet.getDirection() && Mathx.intersects(bullet, collisionBullet)) {
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
						switch(bullet.getDirection()){
						case Down:
							collisionBunker.setImageURL("bunkerPartBroken.png");
							break;
						default:
							collisionBunker.setImageURL("bunkerPartBrokenUpwardsBullet.png");							
							break;
						}
						
					}
					if (collisionBunker.isDead()) {
						collisionBunker.destroy();
					}
					bullet.destroy();
					break;
				}
			}
//Man bliver for sur n�r en invader skyder ens bonus!			
			if(bullet.getDirection() == Direction.Up){
				for(Iterator<Bonus> bonus = gameState.getBonuses().iterator(); bonus.hasNext();){
					Bonus collisionBonus = bonus.next();
					if(Mathx.intersects(bullet, collisionBonus)){
						collisionBonus.destroy();
						bullet.destroy();
						break;
					}
				}
			}
		}
	}

	private void updateIndividualEnemies(GameState gameState, long timeDelta){
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

	private void updateInvaders(GameState gameState, long timeDelta) {
		boolean wallHit = false;
//Checking if move will cause wallHit
		for (Invader invader : gameState.getInvaders()) {
			if(invader.getPosition().y + invader.getHeight() >= gameState.getPlayer(PlayerIndex.One).getPosition().y){
				gameState.setState(GameStateState.Lost);
				break;
			}
			
			if (gameState.getMoveInvadersRight()) {
				if(invader.getPosition().x+invader.getWidth() +Mathx.distance(timeDelta, (invader.getSpeed() * gameModel.getActiveDifficulty().getInvaderSpeed()/10)) > MainModel.SCREEN_WIDTH){
					wallHit = true;
					gameState.setMoveInvadersRight(false);
					break;
				}
			}else if(invader.getPosition().x -Mathx.distance(timeDelta, (invader.getSpeed() * gameModel.getActiveDifficulty().getInvaderSpeed()/10)) < 0){
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
				invader.move(Mathx.distance(timeDelta, (invader.getSpeed() * speedMultiplier * gameModel.getActiveDifficulty().getInvaderSpeed()/10)), 0);
			} else {
				invader.move(-Mathx.distance(timeDelta, (invader.getSpeed() * speedMultiplier * gameModel.getActiveDifficulty().getInvaderSpeed()/10)), 0);
			}
		}
	}
	
	private void individualEnemiesShoot(GameState gameState, long timeDelta){
		for (KillableGameElement randomEnemy : gameState.getIndividualEnemies()) {
			if(randomEnemy instanceof NicholasCage){
				NicholasCage nCage = (NicholasCage) randomEnemy;
				for (Cage cage : nCage.getCages()) {
					if(Math.random() < 0.06 * timeDelta / this.gameModel.getActiveDifficulty().getInvaderShootFreq()){
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

	/**
	 * @param gameState
	 * 
	 *            Find de invaders der er placeret nederst, og lad en random en
	 *            af dem skyde.
	 */
	private void invadersShoot(GameState gameState, long currentTime) {
		//check if invaders should shoot, THEN do all the other stuff
		if (currentTime - gameState.getLastInvaderShot() > this.gameModel.getActiveDifficulty().getInvaderShootFreq()) {
			
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
			return; //Patty quick find
		}
	}

	/**
	 * Lazily gets the game loop timer
	 */
	private Timer getTimer() {
		if (this.timer == null) {
			this.timer = new Timer(MainModel.TIMER_DELAY, new CommandListener(CommandFactory.createUpdateGameStateCommand()));
		}
		return timer;
	}
	
	private void addExplosion(GameState gameState, GameElement gameElement){
		gameState.getAnimations().add(new Animation("explosionSprite.png", gameElement.getPosition().clone(), 6,120, false));
	}
}
