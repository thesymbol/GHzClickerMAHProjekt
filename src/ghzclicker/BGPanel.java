package ghzclicker;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BGPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private Image image = null;

    /**
     * Class used to override JPanel, allowing to set image as background
     * 
     * @param filename the background path
     */
    public BGPanel(String filename) {
        this.image = new ImageIcon(filename).getImage();

    }

    /**
     * Overrides the original method, allowing for setting a picture as background
     * 
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, BGPanel.this.getWidth(), BGPanel.this.getHeight(), null);
    }
}
