package view.state;

import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JLabel;

import model.HighscoreEntry;
import model.MainModel;
import utils.GuiUtils;

@SuppressWarnings("serial")
public class HighscoreViewState extends AbstractMenuViewState {

	private static final int LIMIT = 10;
	private HighscoreEntry[] entries;
	private JLabel[] labels;
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
		
		if (gm.isOfflineMode()) {
			JLabel label = new JLabel("NO INTERNET CONNECTION");
			label.setAlignmentX(CENTER_ALIGNMENT);
			label.setForeground(Color.RED);
			this.add(label);
			return;
		}
		
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		Arrays.sort(this.entries);
		this.labels = new JLabel[this.entries.length];
		for (HighscoreEntry entry : entries) {
			JLabel label = new JLabel(String.format("%s on %s (%s): %d", 
					entry.getPlayerName(),
					gm.getGameConfig().getDifficulty(entry.getDifficulty()).getName(),
					df.format(entry.getCreationDate()),
					entry.getScore()));
			label.setAlignmentX(CENTER_ALIGNMENT);
			label.setForeground(Color.WHITE);
			this.add(label);
		}
	}
}
