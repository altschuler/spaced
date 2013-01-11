package view.state;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.MainModel;
import view.render.SpriteHandler;

abstract public class AbstractMenuViewState extends AbstractViewState {
    
    
    private JPanel panel;
    private JLabel logo;
    private static final String LOGO_URL = "view/sprites/logo.png";
    private Image bg;
    private static final String BG_URL = "view/sprites/hubble.jpg";
    private SpriteHandler spriteHandler;

    public AbstractMenuViewState() {
        super();
        this.spriteHandler = SpriteHandler.getInstance();
        
        // Config panel
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        // Background
        this.bg = this.spriteHandler.get(BG_URL).getImage();
        
        // Logo
        this.logo = new JLabel(new ImageIcon(this.spriteHandler.get(LOGO_URL).getImage()));
        this.logo.setAlignmentX(CENTER_ALIGNMENT);
        // Blank panel
        this.panel = new JPanel();
        this.panel.setMaximumSize(new Dimension(0, (MainModel.SCREEN_HEIGHT/3)-this.logo.getHeight()));
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
