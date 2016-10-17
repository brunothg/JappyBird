package com.github.brunothg.jappybird;

import static com.github.brunothg.jappybird.strings.Strings.GAME_FRAME_TITLE;

import java.io.IOException;

import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.brunothg.game.engine.d2.frame.SwingGameFrame;
import com.github.brunothg.game.engine.d2.scene.FPSScene;
import com.github.brunothg.game.engine.image.InternalImage;
import com.github.brunothg.jappybird.dao.BestenlisteDAO;
import com.github.brunothg.jappybird.dao.BestenlisteFileDAO;
import com.github.brunothg.jappybird.scene.HighscoreScene;
import com.github.brunothg.jappybird.scene.JappyBirdMenuScene;
import com.github.brunothg.jappybird.scene.JappyBirdScene;
import com.github.brunothg.jappybird.scene.SettingsScene;
import com.github.brunothg.jappybird.settings.Settings;

public class JappyBird {
	private static final Logger LOG = LoggerFactory.getLogger(JappyBird.class);

	public static final String SCENE_FPS = "SCENE-FPS";
	public static final String SCENE_SHOW_FPS = "SCENE-SHOW-FPS";

	private static final int MENU_FPS = Settings.getIntValue("CLOCK-MENU-FPS",
			10);
	private static final SwingGameFrame gameFrame = new SwingGameFrame(
			GAME_FRAME_TITLE);

	public static void main(String[] args) {
		setLaF();
		registerShutdownHook();

		InternalImage.setRootFolder("/com/github/brunothg/jappybird/media/");

		gameFrame.setSize(800, 600);
		gameFrame.setLocationRelativeTo(null);
		gameFrame.setMinimumSize(gameFrame.getSize());
		gameFrame.setIconImage(InternalImage.load("logo.png"));

		gotoMenu();

		gameFrame.setVisible(true);
	}

	private static void registerShutdownHook() {
		LOG.debug("register shutdown hook");
		Runtime.getRuntime().addShutdownHook(new Thread() {

			public void run() {

				try {
					LOG.info("Save settings ...");
					Settings.save();
					LOG.info("... saved.");
				} catch (IOException e) {
					LOG.warn("Saving settings failed", e);
				}
			}
		});

	}

	private static void setLaF() {
		LOG.debug("Set look and feel");

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			LOG.warn("Could not set LaF", e);
		}
	}

	public static void gotoMenu() {
		LOG.info("go to menu");

		gameFrame.setFramesPerSecond(MENU_FPS);

		JappyBirdMenuScene scene = new JappyBirdMenuScene();
		gameFrame.setScene(scene);
	}

	public static void gotoGame() {
		LOG.info("go to game");

		gameFrame.setFramesPerSecond(
				(int) Settings.getLongValue(JappyBird.SCENE_FPS, 30));

		JappyBirdScene scene = new JappyBirdScene();

		if (Settings.getBooleanValue(SCENE_SHOW_FPS, false)) {
			gameFrame.setScene(new FPSScene(scene));
		} else {
			gameFrame.setScene(scene);
		}
	}

	public static void gotoSettings() {
		LOG.info("go to settings");

		gameFrame.setFramesPerSecond(MENU_FPS);

		SettingsScene scene = new SettingsScene();
		gameFrame.setScene(scene);
	}

	public static void gotoHighscore() {
		LOG.info("go to highscore");

		gameFrame.setFramesPerSecond(MENU_FPS);

		HighscoreScene scene = new HighscoreScene();
		gameFrame.setScene(scene);
	}

	public static BestenlisteDAO getDAO() {

		return new BestenlisteFileDAO();
	}
}
