package model;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JPanel;
import view.render.Sprite;

public class Game extends Canvas {

	final static String title = "Space Invaders";
	final static int[] dims = {800,600};
	private final static Dimension dim = new Dimension(dims[0],dims[1]);
        
	public Game() {
		JFrame frame = new JFrame(title);
		JPanel panel = (JPanel) frame.getContentPane();
		panel.setPreferredSize(dim);
		panel.setLayout(null);
		
		setBounds(0,0,dims[0],dims[1]);
		panel.add(this);
                this.setBackground(Color.black);
		
		setIgnoreRepaint(true);
		
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);

                //System.exit(0);
		
	}
        

        private void initEntity() {
            
        }
}
