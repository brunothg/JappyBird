package com.github.brunothg.jappybird.scene;

import static com.github.brunothg.jappybird.strings.Strings.*;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import com.github.brunothg.game.engine.d2.scene.Scene;
import com.github.brunothg.game.engine.image.InternalImage;
import com.github.brunothg.jappybird.JappyBird;
import com.github.brunothg.jappybird.object.Button;

public class JappyBirdMenuScene implements Scene, KeyListener {

	private static final double BUTTON_WIDTH = 0.7;

	private static final double BUTTON_HEIGHT = 0.2;

	private static final int BTN_START = 0;
	private static final int BTN_EINSTELLUNGEN = 1;
	private static final int BTN_HIGHSCORE = 2;

	Image background;
	private List<Button> buttons;
	private int active;

	public JappyBirdMenuScene() {

		background = InternalImage.load("bg.png");

		buttons = new ArrayList<Button>(3);

		active = BTN_START;

		buttons.add(BTN_START, new Button(MENU_START));
		buttons.add(BTN_EINSTELLUNGEN, new Button(MENU_SETTINGS));
		buttons.add(BTN_HIGHSCORE, new Button(MENU_HIGHSCORE));

	}

	private void enter() {

		switch (active) {
		case BTN_START:
			startGame();
			break;
		case BTN_HIGHSCORE:
			JappyBird.gotoHighscore();
			break;
		case BTN_EINSTELLUNGEN:
			JappyBird.gotoSettings();
			break;

		}
	}

	private void startGame() {

		JappyBird.gotoGame();
	}

	@Override
	public void paintScene(Graphics2D g, int width, int height, long elapsed) {

		g.drawImage(background, 0, 0, width, height, 0, 0,
				background.getWidth(null), background.getHeight(null), null);

		paintButtons(g, width, height, elapsed);
	}

	private void paintButtons(Graphics2D g, int width, int height,
			long elapsed) {

		resizeButtons(width, height);
		relocateButtons(width, height);

		int index = 0;
		for (Button b : buttons) {

			b.setSeletcted(index == active);

			b.paintOnScene(g, elapsed);

			index++;
		}
	}

	private void relocateButtons(int width, int height) {

		double y = height * 0.2;

		for (Button b : buttons) {
			b.setPosition((int) (width * 0.5), (int) (y));
			y += b.getHeight() + height * 0.1;
		}
	}

	private void resizeButtons(int width, int height) {

		for (Button b : buttons) {
			b.setSize((int) (width * BUTTON_WIDTH),
					(int) (height * BUTTON_HEIGHT));
		}
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
			active = Math.min(++active, buttons.size() - 1);
			break;
		case KeyEvent.VK_UP:
			active = Math.max(--active, 0);
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_ENTER:
			enter();
			break;
		}
	}

}
