package de.bno.jappybird;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import de.bno.jappybird.engine.InternalImage;
import de.bno.jappybird.engine.Listeners;
import de.bno.jappybird.engine.Scene;

public class SettingsScene implements Scene, KeyListener {

	private static final double BUTTON_HEIGHT = 0.2;
	private static final double BUTTON_WIDTH = 0.7;
	private Image background;
	private SelectionButton button;

	public SettingsScene() {

		background = InternalImage.load("bg.png");

		button = new SelectionButton("FPS Anzeigen");

	}

	@Override
	public void paintScene(Graphics2D g, int width, int height) {

		g.drawImage(background, 0, 0, width, height, 0, 0,
				background.getWidth(null), background.getHeight(null), null);

		button.setSize((int) (width * BUTTON_WIDTH),
				(int) (height * BUTTON_HEIGHT));

		button.setPosition((int) (width * 0.5), (int) (height * 0.5));
		button.paintOnScene(g);
	}

	@Override
	public void pause() {
	}

	@Override
	public void stop() {
	}

	@Override
	public void start() {
	}

	@Override
	public Listeners getListener() {

		return new Listeners(this, null, null);
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

}
