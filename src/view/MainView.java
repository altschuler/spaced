package view;

import javax.swing.JFrame;

import utils.Input;

/**
 * The main window inside which states are rendered
 */

@SuppressWarnings("serial")
public class MainView extends JFrame {
	public MainView() {
            super("Space Invaders - HD, Widescreen Version");
            this.setResizable(false);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    this.setFocusable(true);
	    this.requestFocusInWindow();
	    this.addKeyListener(Input.getInstance());
	}
}
