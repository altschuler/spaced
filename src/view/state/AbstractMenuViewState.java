package view.state;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.MainModel;
import service.resources.SpriteHandler;

abstract public class AbstractMenuViewState extends AbstractViewState {
    
    
    private JPanel panel;
    private JLabel logo;
    private static final String LOGO = "logo.png";
    private Image bg;
    private static final String BG = "hubble.jpg";
    private SpriteHandler spriteHandler;

    public AbstractMenuViewState() {
        super();
        this.spriteHandler = SpriteHandler.getInstance();
        
        // Config panel
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        // Background
        this.bg = this.spriteHandler.get(BG).getImage();
        
        // Logo
        this.logo = new JLabel(new ImageIcon(this.spriteHandler.get(LOGO).getImage()));
        this.logo.setAlignmentX(CENTER_ALIGNMENT);
        // Blank panel
        this.panel = new JPanel();
        this.panel.setMaximumSize(new Dimension(0, (MainModel.SCREEN_HEIGHT/5)-this.logo.getHeight()));
        this.panel.setOpaque(false);
        
        // Add to panel
        this.add(this.panel);
        this.add(this.logo);
        
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(bg, 0, 0, null);
    }

}
