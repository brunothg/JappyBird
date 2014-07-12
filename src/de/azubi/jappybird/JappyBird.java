package de.azubi.jappybird;

import javax.swing.UIManager;

import de.azubi.jappybird.engine.Clock;
import de.azubi.jappybird.engine.GameFrame;

public class JappyBird {

	public static void main(String[] args) {
		setLaF();

		Clock clk = new Clock(20);

		GameFrame gameFrame = new GameFrame("JappyBird");
		gameFrame.setClk(clk);
		gameFrame.setScene(new JappyBirdScene(true));
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
