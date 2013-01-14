package view.state;

import java.util.Observable;
import javax.swing.JLabel;
import command.CommandFactory;
import model.MainModel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import model.core.Difficulty;
import model.core.PlayerIndex;
import utils.GuiUtils;

/**
 * The main menu view state
 */
@SuppressWarnings("serial")
public class MenuViewState extends AbstractMenuViewState {

	private JLabel label;
	private JComboBox dropd;
	private JButton btnGame;
	private JButton btnHighscore;

	public MenuViewState() {
		// Label
		this.label = new JLabel();
		this.label.setAlignmentX(CENTER_ALIGNMENT);
		this.label.setForeground(Color.WHITE);
		// ComboBox / Drop Down
		this.dropd = new JComboBox();
		this.dropd.setPreferredSize(new Dimension(150, 50));
		this.dropd.setMaximumSize(this.dropd.getPreferredSize());
		this.dropd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CommandFactory.createSetDifficultyCommand(
						String.valueOf(((JComboBox) e.getSource())
								.getSelectedItem())).execute();
			}
		});
		this.dropd.setAlignmentX(CENTER_ALIGNMENT);
		// Buttons
		this.btnGame = GuiUtils.createButtonWithCommand(
				"New Game",
				CommandFactory.createStartNewGameCommand().chain(
						CommandFactory.createSetStateCommand(ViewState.Game)));
		this.btnHighscore = GuiUtils.createButtonWithStateCommand(
				"View Highscores", ViewState.Highscore);
		this.btnGame.setAlignmentX(CENTER_ALIGNMENT);
		this.btnHighscore.setAlignmentX(CENTER_ALIGNMENT);

		// Add to panel
		this.add(this.label);
		this.add(this.dropd);
		this.add(this.btnGame);
		this.add(this.btnHighscore);
	}

	@Override
	public void update(Observable o, Object arg) {
		MainModel gm = (MainModel) o;
		this.label.setText(String.format("Hi %s!",
				gm.getPlayerName(PlayerIndex.One)));
		for (Difficulty diff : gm.getGameConfig().getDifficulties()) {
			this.dropd.addItem(diff.getName());
		}
	}
}