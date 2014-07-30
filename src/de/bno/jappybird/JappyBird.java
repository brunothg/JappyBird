package de.bno.jappybird;

import javax.swing.UIManager;

import de.bno.jappybird.engine.Clock;
import de.bno.jappybird.engine.GameFrame;
import de.bno.jappybird.engine.InternalImage;

public class JappyBird {

	public static final Clock clk = new Clock(10);
	private static final GameFrame gameFrame = new GameFrame("JappyBird");

	public static void main(String[] args) {
		setLaF();
		InternalImage.setRootFolder("/de/bno/jappybird/media/");

		gameFrame.setSize(800, 600);
		gameFrame.setMinimumSize(gameFrame.getSize());
		gameFrame.setIconImage(InternalImage.load("logo.png"));
		gameFrame.setClk(clk);

		gotoMenu();

		gameFrame.setVisible(true);
		clk.start();
	}

	private static void setLaF() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {

		}
	}

	public static void gotoMenu() {

		clk.setFramesPerSecond(10);

		JappyBirdMenuScene scene = new JappyBirdMenuScene();
		gameFrame.setScene(scene);
	}

	public static void gotoGame() {

		clk.setFramesPerSecond(30);

		JappyBirdScene scene = new JappyBirdScene();
		gameFrame.setScene(scene);
	}

}
