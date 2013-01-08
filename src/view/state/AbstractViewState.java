package view.state;

import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import view.MainView;
import controller.MainController;

/**
 * Base class of states that can be rendered in the {@link MainView}
 */
@SuppressWarnings("serial")
abstract public class AbstractViewState extends JPanel implements Observer {
	
	protected MainController mainController;
	
	public AbstractViewState() {
	    this.setPreferredSize(new Dimension(300, 300));
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
}
