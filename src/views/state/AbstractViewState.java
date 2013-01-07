package views.state;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import controlllers.GameController;

@SuppressWarnings("serial")
abstract public class AbstractViewState extends JPanel implements Observer {
	
	protected GameController gc;

	@Override
	public void update(Observable o, Object arg) {}
}
