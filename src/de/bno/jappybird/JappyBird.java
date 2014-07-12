package de.bno.jappybird;

import javax.swing.UIManager;

import de.bno.jappybird.engine.Clock;
import de.bno.jappybird.engine.GameFrame;
import de.bno.jappybird.engine.InternalImage;

public class JappyBird {

	public static void main(String[] args) {
		setLaF();
		InternalImage.setRootFolder("/de/bno/jappybird/media/");

		Clock clk = new Clock(25);

		GameFrame gameFrame = new GameFrame("JappyBird");
		gameFrame.setSize(800, 600);
		gameFrame.setMinimumSize(gameFrame.getSize());
		gameFrame.setClk(clk);

		JappyBirdScene scene = new JappyBirdScene();
		gameFrame.setScene(scene);
		gameFrame.addKeyListener(scene.getKeyListener());

		gameFrame.setVisible(true);
		clk.start();
	}

	private static void setLaF() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {

		}
	}

}
