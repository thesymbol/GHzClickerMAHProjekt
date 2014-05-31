package ghzclicker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.TextLayout;

import javax.swing.JLabel;

/**
 * Class used to create text with outline
 * 
 * @author Viktor Saltarski
 * 
 */
public class GraphicsText extends JLabel {
    private static final long serialVersionUID = 1L;
    private String text = "0";
	private int size;
	private boolean antialiasing = true;

	/**
	 * Used for creating a textobject with specified text and size
	 * 
	 * @param text the text to be shown
	 * @param size the size of the text
	 */
	public GraphicsText(String text, int size) {
		this.text = text;
		this.size = size;
	}

	/**
	 * Overriding the original method to be able to outline the text
	 * 
	 * @param g the Graphics component to use
	 */
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Font f = new Font("Arial", Font.BOLD, size);

		if (antialiasing) {
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		}
		for (String line : text.split("\n")) {
			TextLayout tl = new TextLayout(line, f, g2.getFontRenderContext());
			Shape shape = tl.getOutline(null);
			g2.translate(0, size - 5);
			g2.setColor(Color.WHITE);
			g2.fill(shape);
			g2.setColor(Color.BLACK);
			g2.draw(shape);
		}
	}

	/**
	 * Sets the text in the text object
	 * 
	 * @param text the text to be set
	 */
	public void setText(String text) {
		this.text = text;

	}
}
