package com.github.brunothg.jappybird.scene;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EventListener;
import java.util.List;

import com.github.brunothg.game.engine.d2.scene.Scene;
import com.github.brunothg.game.engine.image.InternalImage;
import com.github.brunothg.jappybird.JappyBird;
import com.github.brunothg.jappybird.dao.Score;

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
	public void paintScene(Graphics2D g, int width, int height, long elapsed) {

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

		int index = 0;
		for (Score s : scores) {

			g.setFont(new Font(Font.SERIF, Font.BOLD, (int) (_height * 0.8)));

			while (g.getFontMetrics()
					.stringWidth(s.getName() + "  " + s.getScore()) > _width) {
				g.setFont(new Font(Font.SERIF, Font.BOLD,
						g.getFont().getSize() - 1));
			}

			FontMetrics metrics = g.getFontMetrics();

			g.drawString(s.getName(), _x, _y + (int) (_height * 0.5 + 0.5
					* (0.5 * metrics.getAscent() + metrics.getLeading())));

			g.drawString(s.getScore() + "",
					_x + _width - metrics.stringWidth(s.getScore() + ""),
					_y + (int) (_height * 0.5 + 0.5 * (0.5 * metrics.getAscent()
							+ metrics.getLeading())));

			_y += _height;

			index++;
			if (index >= 10) {
				break;
			}
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
