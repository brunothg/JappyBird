package de.bno.jappybird.engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * Gepufferte Swing Komponente um Scenes zu zeichnen
 * 
 * @author Marvin Bruns
 */
public class Stage extends JPanel {

	private static final long serialVersionUID = -1523091347460518863L;
	private BufferedImage offScreen;
	private Scene scene;

	public Stage() {
		setOpaque(true);
		setBackground(Color.BLACK);
	}

	/**
	 * Setzt die sichtbare Szene
	 * 
	 * @param scene
	 *            Szene, die gezeichnet werden soll
	 */
	public void setScene(Scene scene) {
		this.scene = scene;

		repaint();
	}

	public Scene getScene() {
		return this.scene;
	}

	private Graphics2D getOffScreenGraphics() {
		if (offScreen == null || offScreen.getWidth() != getWidth()
				|| offScreen.getHeight() != getHeight()) {
			offScreen = new BufferedImage(getWidth(), getHeight(),
					BufferedImage.TYPE_INT_RGB);
		}

		return offScreen.createGraphics();
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (scene == null) {
			super.paintComponent(g);
			return;
		}

		Graphics2D graphics = getOffScreenGraphics();
		scene.paintScene(graphics, getWidth(), getHeight());

		g.drawImage(offScreen, 0, 0, getWidth(), getHeight(), 0, 0,
				offScreen.getWidth(), offScreen.getHeight(), null);

		graphics.finalize();
	}
}
