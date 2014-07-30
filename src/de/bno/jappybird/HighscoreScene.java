package de.bno.jappybird;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import de.bno.jappybird.dao.Score;
import de.bno.jappybird.engine.InternalImage;
import de.bno.jappybird.engine.Listeners;
import de.bno.jappybird.engine.Scene;

public class HighscoreScene implements Scene, KeyListener {

	private static final Color C2 = new Color(100, 100, 100, 150);
	private static final Color C1 = new Color(170, 170, 170, 150);

	private Image background;
	private List<Score> scores;

	public HighscoreScene() {

		background = InternalImage.load("bg.png");
		scores = JappyBird.getDAO().getBestenliste();
	}

	@Override
	public void paintScene(Graphics2D g, int width, int height) {

		g.drawImage(background, 0, 0, width, height, 0, 0,
				background.getWidth(null), background.getHeight(null), null);

		paintTable(g, width, height);
	}

	private void paintTable(Graphics2D g, int width, int height) {

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g.setColor(new Color(0, 0, 0, 150));
		g.fillRect((int) (width * 0.1), (int) (height * 0.1),
				(int) (width * 0.8), (int) (height * 0.8));

		int _x = (int) (width * 0.12);
		int _y = (int) (height * 0.12);
		int _width = (int) (width * 0.76);
		int _height = (int) (height * 0.76 * 0.1);

		for (int i = 0; i < 10; i++) {
			g.setColor((i % 2 == 0) ? C1 : C2);

			g.fillRect(_x, _y, _width, _height);

			_y += _height;
		}

		_x = (int) (width * 0.14);
		_y = (int) (height * 0.12);
		_width = (int) (width * 0.72);

		g.setColor(Color.BLACK);
		g.setFont(new Font(Font.SERIF, Font.BOLD, (int) (_height * 0.8)));
		FontMetrics metrics = g.getFontMetrics();

		int index = 0;
		for (Score s : scores) {

			g.drawString(
					s.getName(),
					_x,
					_y
							+ (int) (_height * 0.5 + 0.5 * (0.5 * metrics
									.getAscent() + metrics.getLeading())));

			g.drawString(
					s.getScore() + "",
					_x + _width - metrics.stringWidth(s.getScore() + ""),
					_y
							+ (int) (_height * 0.5 + 0.5 * (0.5 * metrics
									.getAscent() + metrics.getLeading())));

			_y += _height;

			index++;
			if (index >= 10) {
				break;
			}
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

		switch (e.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
			JappyBird.gotoMenu();
			break;
		}
	}

}
