package controller;

import java.awt.event.KeyEvent;
import java.util.Iterator;
import javax.swing.Timer;
import model.GameState;
import model.GameStateState;
import model.MainModel;
import model.core.BulletType;
import model.core.PlayerIndex;
import model.elements.Bonus;
import model.elements.Bullet;
import model.elements.Bunker;
import model.elements.Cage;
import model.elements.Invader;
import model.elements.KillableGameElement;
import model.elements.NicholasCage;
import model.elements.Player;
import service.resources.SoundHandler;
import utils.Input;
import view.MainView;
import view.render.GameStateRenderer;
import view.state.GameViewState;
import view.state.ViewState;
import command.CommandFactory;
import command.CommandListener;
import controller.gamestatehandler.CollisionHandler;
import controller.gamestatehandler.MoveHandler;
import controller.gamestatehandler.ShootingHandler;

/**
 * The Controller in charge of the actual game.
 * 
 * Contains methods for updating the current {@link model.GameState} and starting/stopping the {@link Timer} for the game-loop.
 */
public class GameController extends AbstractController {

	private Timer timer;
	private GameStateRenderer renderer;
	private MoveHandler mover;
	private ShootingHandler shooter;
	private CollisionHandler collider;

	public GameController(MainView gw, MainModel gm) {
		super(gw, gm);

		this.renderer = new GameStateRenderer();
		this.mover = new MoveHandler();
		this.shooter = new ShootingHandler();
		this.collider = new CollisionHandler();
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
		if(timeDelta >= 16){	timeDelta = 16;	}
		
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
		//Player input besides movement
		this.updatePlayer(gameState, timeDelta);
		
		// Update elements
		this.mover.moveAll(gameState, timeDelta, this.gameModel.getActiveDifficulty());
		this.shooter.createShots(gameState, timeDelta, this.gameModel.getActiveDifficulty(), currentTime);
		this.collider.collisionCheck(gameState, timeDelta, this.gameModel.getActiveDifficulty());
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

	/**
	 * Removes all {@link model.elements.GameElement}s marked as "Destroyed" from the {@link GameState}.
	 * @param gameState
	 */
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
}
