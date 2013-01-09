package view.state;

import java.awt.Canvas;
import java.util.Observable;

import model.GameModel;

import command.CommandFactory;

/**
 * The main game view. This is where the actual game is rendered.
 */
@SuppressWarnings("serial")
public class GameViewState extends AbstractViewState {
	
	private Canvas display;
	
	public GameViewState() {
		super();
		
		this.setLayout(null);
		
		this.display = new Canvas();
		this.display.setIgnoreRepaint(true);
		this.display.setBounds(0, 0, GameModel.SCREEN_WIDTH, GameModel.SCREEN_HEIGHT);
		this.display.setVisible(true);
		
		this.add(this.getDisplay());
	}

	@Override
	public void update(Observable o, Object arg) {
		//TODO figure out why this cant be moved to initialize
		CommandFactory.createGameLoopEnabledCommand(true).execute();
		//GameModel gm = (GameModel) o;
	}
	
	@Override
	public void initialize() {
		this.display.createBufferStrategy(2);
	}
	
	@Override
	public void dispose() {
		CommandFactory.createGameLoopEnabledCommand(false).execute();
	}

	public Canvas getDisplay() {
		return display;
	}
}
