package view.state;

import java.awt.Color;
import java.util.Observable;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JLabel;
import model.GameStateState;
import model.MainModel;

import utils.GuiUtils;

/**
 * The main menu view state
 */
@SuppressWarnings("serial")
public class GameOverViewState extends AbstractViewState {
    
        private JLabel logo;
        private static final String LOGO_URL = "view/sprites/logo.png";
        private JLabel label;
        private JButton btn;
        

	public GameOverViewState() {
		super();
                // Config panel
                this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
                this.setBackground(Color.BLACK);
                
                // Logo
                this.logo = new JLabel(new ImageIcon(this.getClass().getClassLoader().getResource(LOGO_URL)));
                this.logo.setAlignmentX(CENTER_ALIGNMENT);
                // Label
                this.label = new JLabel("GAME OVER", JLabel.CENTER);
                this.label.setForeground(Color.WHITE);
                this.label.setAlignmentX(CENTER_ALIGNMENT);
                // Button
                this.btn = GuiUtils.createButtonWithStateCommand("Back to menu", ViewState.Menu);
                this.btn.setAlignmentX(CENTER_ALIGNMENT);
                
                // Add to panel
                this.add(this.logo);
                this.add(this.label);
		this.add(btn);
	}

	@Override
	public void update(Observable o, Object arg) {
		MainModel gm = (MainModel) o;
                this.label.setText("You " + String.valueOf(gm.getActiveGameState().getState()) + "!");
	}
}