package view.state;

import java.awt.Color;
import java.util.Observable;
import javax.swing.JButton;
import javax.swing.JLabel;
import model.MainModel;

import utils.GuiUtils;

/**
 * The main menu view state
 */
@SuppressWarnings("serial")
public class GameOverViewState extends AbstractMenuViewState {
    
        private JLabel label;
        private JButton btn;    

	public GameOverViewState() {
                // Label
                this.label = new JLabel(new String(), JLabel.CENTER);
                this.label.setForeground(Color.WHITE);
                this.label.setAlignmentX(CENTER_ALIGNMENT);
                // Button
                this.btn = GuiUtils.createButtonWithStateCommand("Back to Menu", ViewState.Menu);
                this.btn.setAlignmentX(CENTER_ALIGNMENT);
                
                // Add to panel
                this.add(this.label);
		this.add(this.btn);
	}

	@Override
	public void update(Observable o, Object arg) {
		MainModel gm = (MainModel) o;
                this.label.setText("You " + String.valueOf(gm.getActiveGameState().getState()) + "!");
	}
}