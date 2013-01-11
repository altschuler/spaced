package view.state;

import java.util.ArrayList;
import java.util.Observable;

import javax.swing.JTextField;

import model.MainModel;
import model.HighscoreEntry;
import utils.GuiUtils;

import command.CommandFactory;
import java.util.Date;

/**
 * First splash view states shown when initializing the game
 */
@SuppressWarnings("serial")
public class SplashViewState extends AbstractViewState {

	private JTextField textf;

	private ArrayList<HighscoreEntry> entries;

	public SplashViewState() {
		super();

		this.textf = new JTextField();
		this.add(this.textf);

		this.add(GuiUtils.createButtonWithCommand("GO",
				CommandFactory.createSetPlayerNameCommand(this.textf).chain(CommandFactory.createSetStateCommand(ViewState.Menu))));
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
