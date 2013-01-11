package view.state;

import java.util.Observable;
import javax.swing.JLabel;
import command.CommandFactory;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import model.GameModel;
import model.core.Difficulty;
import model.core.PlayerIndex;
import utils.GuiUtils;

/**
 * The main menu view state
 */
@SuppressWarnings("serial")
public class MenuViewState extends AbstractViewState {

	private JLabel logo;
        private static final String LOGO_URL = "view/sprites/logo.png";
        private JLabel label;
        private JComboBox dropd;
        private JButton btn;

	public MenuViewState() {
		super();
                // Config panel
                this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
                this.setBackground(Color.BLACK);
                
                // Logo
                this.logo = new JLabel(new ImageIcon(this.getClass().getClassLoader().getResource(LOGO_URL)));
                this.logo.setAlignmentX(CENTER_ALIGNMENT);
		// Label
                this.label = new JLabel();
                this.label.setAlignmentX(CENTER_ALIGNMENT);
                this.label.setForeground(Color.WHITE);
                // ComboBox / Drop Down
                this.dropd = new JComboBox();
                this.dropd.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(("index: " + ((JComboBox) e.getSource()).getSelectedItem()));
                    }});
                this.dropd.setAlignmentX(CENTER_ALIGNMENT);
                // Button
                this.btn = GuiUtils.createButtonWithCommand("Start new game", 
				CommandFactory.createStartNewGameCommand().chain(CommandFactory.createSetStateCommand(ViewState.Game)));
		this.btn.setAlignmentX(CENTER_ALIGNMENT);
                
                // Add to panel
                this.add(this.logo);
		this.add(this.label);
                this.add(this.dropd);
		this.add(this.btn);
	}

	@Override
	public void update(Observable o, Object arg) {
		GameModel gm = (GameModel) o;
		this.label.setText(String.format("Hi %s!", gm.getPlayerName(PlayerIndex.One)));
                for (Difficulty diff : gm.getGameConfig().getDifficulties()) {
                    this.dropd.addItem(diff.getName());
                }
	}
}