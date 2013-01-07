package views;

import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * @author Simon
 * The main window inside which states are rendered
 */
@SuppressWarnings("serial")

public class MainView extends JFrame {
	public MainView() {
		super("Troll game");

		this.setSize(new Dimension(300, 300));
	}
}
