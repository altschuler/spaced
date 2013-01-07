package views;

import java.awt.Dimension;

import javax.swing.JFrame;

@SuppressWarnings("serial")

public class GameView extends JFrame {
	public GameView() {
		super("Troll game");

		this.setSize(new Dimension(300, 300));
	}
}
