package com.github.brunothg.jappybird.scene;

import static com.github.brunothg.jappybird.strings.Strings.*;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EventListener;

import com.github.brunothg.game.engine.d2.scene.Scene;
import com.github.brunothg.game.engine.image.InternalImage;
import com.github.brunothg.jappybird.JappyBird;
import com.github.brunothg.jappybird.object.NumberButton;
import com.github.brunothg.jappybird.object.SelectionButton;
import com.github.brunothg.jappybird.settings.Settings;

public class SettingsScene implements Scene, KeyListener {

	private static final double BUTTON_HEIGHT = 0.2;
	private static final double BUTTON_WIDTH = 0.7;

	private Image background;

	private SelectionButton fpsshow;
	private NumberButton fps;

	public SettingsScene() {

		background = InternalImage.load("bg.png");

		fpsshow = new SelectionButton(SETTINGS_SHOW_FPS);
		fpsshow.setActive(
				Settings.getBooleanValue(JappyBird.SCENE_SHOW_FPS, false));

		fps = new NumberButton(SETTINGS_FPS);
		fps.setNumber((int) Settings.getLongValue(JappyBird.SCENE_FPS, 30));
		fps.setSeletcted(true);
	}

	@Override
	public void paintScene(Graphics2D g, int width, int height, long elapsed) {

		g.drawImage(background, 0, 0, width, height, 0, 0,
				background.getWidth(null), background.getHeight(null), null);

		fpsshow.setSize((int) (width * BUTTON_WIDTH),
				(int) (height * BUTTON_HEIGHT));
		fps.setSize((int) (width * BUTTON_WIDTH),
				(int) (height * BUTTON_HEIGHT));

		fps.setPosition((int) (width * 0.5), (int) (height * 0.25));
		fpsshow.setPosition((int) (width * 0.5), (int) (height * 0.75));

		fps.paintOnScene(g, elapsed);
		fpsshow.paintOnScene(g, elapsed);
	}

	private void right() {
		if (fpsshow.isSeletcted()) {
			toogleFpsshow();
		}

		if (fps.isSeletcted()) {
			changeFps(+1);
		}
	}

	private void left() {
		if (fpsshow.isSeletcted()) {
			toogleFpsshow();
		}

		if (fps.isSeletcted()) {
			changeFps(-1);
		}
	}

	private void toogleFpsshow() {

		fpsshow.setActive(!fpsshow.isActive());

		Settings.set(JappyBird.SCENE_SHOW_FPS, "" + fpsshow.isActive());
	}

	private void changeFps(int i) {

		fps.setNumber(Math.min(Math.max(fps.getNumber() + i, 1), 60));

		Settings.set(JappyBird.SCENE_FPS, "" + fps.getNumber());
	}

	private void esc() {
		JappyBird.gotoMenu();
	}

	@Override
	public EventListener[] getEventListeners() {
		return new EventListener[] { this };
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_UP:
			fps.setSeletcted(!fps.isSeletcted());
			fpsshow.setSeletcted(!fpsshow.isSeletcted());
			break;
		case KeyEvent.VK_LEFT:
			left();
			break;
		case KeyEvent.VK_RIGHT:
			right();
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
			esc();
			break;
		}
	}

}
