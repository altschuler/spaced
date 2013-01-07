package views.state;

import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import controllers.GameController;

@SuppressWarnings("serial")
abstract public class AbstractViewState extends JPanel implements Observer {
	
	protected GameController gc;
	
	public AbstractViewState() {
	    this.setPreferredSize(new Dimension(300, 300));
	}

	/* 
	 * Called when the model has been updated, as this is a method provided 
	 * by Java, we cannot type the observable "o".
	 */
	@Override
	public void update(Observable o, Object arg) {}
}
