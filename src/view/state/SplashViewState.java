package view.state;

import java.util.ArrayList;
import java.util.Observable;
import javax.swing.JTextField;
import model.MainModel;
import model.HighscoreEntry;
import utils.GuiUtils;
import command.CommandFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * First splash view states shown when initializing the game
 */
@SuppressWarnings("serial")
public class SplashViewState extends AbstractMenuViewState {
    
        private JLabel label;
	private JTextField textf;
        private JButton btn;

	private HighscoreEntry[] entries;

	public SplashViewState() {
            
                // Label
                this.label = new JLabel("Enter your name:", JLabel.CENTER);
                this.label.setForeground(Color.WHITE);
                this.label.setAlignmentX(CENTER_ALIGNMENT);
		// TextField
                this.textf = new JTextField();
                this.textf.setPreferredSize(new Dimension(150, 25));
                this.textf.setMaximumSize(this.textf.getPreferredSize());
                this.textf.setAlignmentX(CENTER_ALIGNMENT);
                // Button
                this.btn = GuiUtils.createButtonWithCommand("GO",
				CommandFactory.createSetPlayerNameCommand(this.textf)
                                .chain(CommandFactory.createSetStateCommand(ViewState.Menu)));
                this.btn.setAlignmentX(CENTER_ALIGNMENT);
                
                // Add to panel
                this.add(this.label);
		this.add(this.textf);
		this.add(btn);
	}

	@Override
	public void update(Observable o, Object arg) {
		MainModel gameModel = (MainModel) o;
		this.textf.setText(gameModel.getGameConfig().getDefaultName());
	}

	public void printHighscores() {
		for (HighscoreEntry entry : entries) {
			System.out.println(entry.getPlayerName() + " " + entry.getScore());
		}
	}
}
