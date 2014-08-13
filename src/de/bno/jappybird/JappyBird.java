package de.bno.jappybird;

import java.io.IOException;

import javax.swing.UIManager;

import static de.bno.jappybird.strings.Strings.*;
import de.bno.jappybird.dao.BestenlisteDAO;
import de.bno.jappybird.dao.BestenlisteFileDAO;
import de.bno.jappybird.engine.Clock;
import de.bno.jappybird.engine.FPSScene;
import de.bno.jappybird.engine.GameFrame;
import de.bno.jappybird.engine.InternalImage;
import de.bno.jappybird.settings.Settings;

public class JappyBird {

	public static final String SCENE_FPS = "SCENE-FPS";
	public static final String SCENE_SHOW_FPS = "SCENE-SHOW-FPS";

	private static final float MENU_FPS = (float) Settings.getDoubleValue(
			"CLOCK-MENU-FPS", 10);
	public static final Clock clk = new Clock(MENU_FPS);
	private static final GameFrame gameFrame = new GameFrame(GAME_FRAME_TITLE);

	public static void main(String[] args) {
		setLaF();
		registerShutdownHook();

		InternalImage.setRootFolder("/de/bno/jappybird/media/");

		gameFrame.setSize(800, 600);
		gameFrame.setMinimumSize(gameFrame.getSize());
		gameFrame.setIconImage(InternalImage.load("logo.png"));
		gameFrame.setClk(clk);

		gotoMenu();

		gameFrame.setVisible(true);
		clk.start();
	}

	private static void registerShutdownHook() {

		Runtime.getRuntime().addShutdownHook(new Thread() {

			public void run() {

				try {
					Settings.save();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

	}

	private static void setLaF() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
	}

	public static void gotoMenu() {

		clk.setFramesPerSecond(MENU_FPS);

		JappyBirdMenuScene scene = new JappyBirdMenuScene();
		gameFrame.setScene(scene);
	}

	public static void gotoGame() {

		clk.setFramesPerSecond((int) Settings.getLongValue(JappyBird.SCENE_FPS,
				30));

		JappyBirdScene scene = new JappyBirdScene();

		if (Settings.getBooleanValue(SCENE_SHOW_FPS, false)) {
			gameFrame.setScene(new FPSScene(scene));
		} else {
			gameFrame.setScene(scene);
		}
	}

	public static void gotoSettings() {

		clk.setFramesPerSecond(MENU_FPS);

		SettingsScene scene = new SettingsScene();
		gameFrame.setScene(scene);
	}

	public static void gotoHighscore() {

		clk.setFramesPerSecond(MENU_FPS);

		HighscoreScene scene = new HighscoreScene();
		gameFrame.setScene(scene);
	}

	public static BestenlisteDAO getDAO() {

		return new BestenlisteFileDAO();
	}
}
