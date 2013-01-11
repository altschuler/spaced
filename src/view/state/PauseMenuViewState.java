package view.state;

import java.awt.Color;
import java.util.Observable;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import utils.GuiUtils;

/**
 * The main menu view state
 */
@SuppressWarnings("serial")
public class PauseMenuViewState extends AbstractMenuViewState {
    
        private JLabel label;
        private JButton btnResume;
        private JButton btnMenu;

	public PauseMenuViewState() {
            
		// Label
                this.label = new JLabel("Game paused");
                this.label.setForeground(Color.WHITE);
                this.label.setAlignmentX(CENTER_ALIGNMENT);
                // Buttons
                this.btnResume = GuiUtils.createButtonWithStateCommand("Resume game", ViewState.Game);
                this.btnMenu = GuiUtils.createButtonWithStateCommand("Back to menu", ViewState.Menu);
                this.btnResume.setAlignmentX(CENTER_ALIGNMENT);
                this.btnMenu.setAlignmentX(CENTER_ALIGNMENT);
                
                // Add to panel
		this.add(this.label);
                this.add(this.btnResume);
		this.add(this.btnMenu);
	}

	@Override
	public void update(Observable o, Object arg) {
		//GameModel gm = (GameModel) o;
	}
}