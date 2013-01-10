package view.state;

import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import model.GameModel;
import view.MainView;
import controller.MainController;

/**
 * Base class of states that can be rendered in the {@link MainView}
 */
@SuppressWarnings("serial")
abstract public class AbstractViewState extends JPanel implements Observer {
	
	protected MainController mainController;
	
	public AbstractViewState() {
	    this.setPreferredSize(new Dimension(GameModel.SCREEN_WIDTH, GameModel.SCREEN_HEIGHT));
	}

	/* 
	 * Called when the model has been updated, as this is a method provided 
	 * by Java, we cannot type the observable "o".
	 */
	@Override
	public void update(Observable o, Object arg) {}

	/**
	 * Lets a view state dispose its resources before being removed
	 */
	public void dispose() {}

	/**
	 * Lets a view state initialize itself after it has been added to a frame
	 */
	public void initialize() {}

        public void setMainController(MainController mainController) {
            this.mainController = mainController;
        }
        
        
}
