package view;

import javax.swing.JFrame;

import service.resources.SpriteHandler;
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
		this.setIconImage(SpriteHandler.getInstance().get("alien.png").getImage());
		this.setVisible(true);
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.addKeyListener(Input.getInstance());
	}
}
