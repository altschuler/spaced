package view.state;

import java.util.Observable;

import javax.swing.JTextField;

import model.GameModel;
import utils.GuiUtils;

import command.CommandFactory;
import controller.HighscoreController;
import java.util.ArrayList;
import model.HighscoreEntry;

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
                        CommandFactory.createSetPlayerNameCommand(this.textf)
                        .chain(CommandFactory.createSetStateCommand(ViewState.Menu))));
	}
	
	@Override 
	public void update(Observable o, Object arg) {
		GameModel gameModel = (GameModel) o;
		this.textf.setText(gameModel.getGameConfig().getDefaultName());
                this.loadEntries();  
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
            System.out.println("Entry: name=" + entry.getPlayerName()
                    + " score=" + entry.getScore());
            
        }
    }

}
