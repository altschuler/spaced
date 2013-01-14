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
//		this.loadEntries(); // For testing
	}

	public void printHighscores() {
		for (HighscoreEntry entry : entries) {
			System.out.println(entry.getPlayerName() + " " + entry.getScore());
		}
	}

	private void loadEntries() {
		// TEST
		entries = this.mainController.getHighscoreController().getEntries();
		for (HighscoreEntry entry : entries) {
			System.out.println("Entry: name=" + entry.getPlayerName() + " score=" + entry.getScore());
		}
                this.mainController.getHighscoreController().add(new HighscoreEntry("markuzz_the_giant_TARD",666,69,new Date()));
                this.mainController.getHighscoreController().reloadEntries();
                this.entries = this.mainController.getHighscoreController().getEntries();
		for (HighscoreEntry entry : entries) {
			System.out.println("Entry: name=" + entry.getPlayerName() + " score=" + entry.getScore());
		}
                
	}

}
