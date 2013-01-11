package view.state;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Observable;
import javax.swing.JButton;
import javax.swing.JLabel;
import model.HighscoreEntry;
import model.MainModel;
import utils.GuiUtils;

public class HighscoreViewState extends AbstractMenuViewState {
    
        private ArrayList<HighscoreEntry> entries;
        private ArrayList<JLabel> labels;
        private static final int LIMIT = 10;
        private JButton btn;

	public HighscoreViewState() {
            // Button
            this.btn = GuiUtils.createButtonWithStateCommand("Main Menu", ViewState.Menu);
            this.btn.setAlignmentX(CENTER_ALIGNMENT);
            this.add(this.btn);
	}

	@Override
	public void update(Observable o, Object arg) {
            MainModel gm = (MainModel) o;
            
            this.entries = mainController.getHighscoreController().getEntries();
            for (HighscoreEntry entry : entries) {
                JLabel label = new JLabel(entry.getPlayerName()
                        + " " + entry.getScore()
                        + " " + entry.getDifficulty()
                        + " " + entry.getCreationDate().toString());
                label.setAlignmentX(CENTER_ALIGNMENT);
                label.setForeground(Color.WHITE);
                this.add(label);
            }
	}
}
