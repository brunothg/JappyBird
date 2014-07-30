package de.bno.jappybird;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import de.bno.jappybird.engine.InternalImage;
import de.bno.jappybird.engine.Listeners;
import de.bno.jappybird.engine.Scene;

public class JappyBirdMenuScene implements Scene, KeyListener, MouseListener {

	private static final double BUTTON_WIDTH = 0.7;

	private static final double BUTTON_HEIGHT = 0.2;

	Image background;
	private List<Button> buttons;

	public JappyBirdMenuScene() {

		background = InternalImage.load("bg.png");

		buttons = new ArrayList<Button>(3);

		buttons.add(new Button("Start"));
	}

	@Override
	public void paintScene(Graphics2D g, int width, int height) {

		g.drawImage(background, 0, 0, width, height, 0, 0,
				background.getWidth(null), background.getHeight(null), null);

		paintButtons(g, width, height);
	}

	private void paintButtons(Graphics2D g, int width, int height) {

		resizeButtons(width, height);
		relocateButtons(width, height);

		for (Button b : buttons) {
			b.paintOnScene(g);
		}
	}

	private void relocateButtons(int width, int height) {

		for (Button b : buttons) {
			b.setPosition((int) (width * 0.5), (int) (height * 0.5));
		}
	}

	private void resizeButtons(int width, int height) {

		for (Button b : buttons) {
			b.setSize((int) (width * BUTTON_WIDTH),
					(int) (height * BUTTON_HEIGHT));
		}
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

		return new Listeners(this, this, null);
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

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}
