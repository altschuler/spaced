package views;

import java.awt.Rectangle;

import javax.swing.JFrame;

/**
 * The main window inside which states are rendered
 */

@SuppressWarnings("serial")
public class MainView extends JFrame {
	public MainView() {
		super("Space Invaders");
		this.setBounds(new Rectangle(0, 0, 300, 300));
		this.setResizable(false);
	}
}
