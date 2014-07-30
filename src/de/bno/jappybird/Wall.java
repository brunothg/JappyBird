package de.bno.jappybird;

import java.awt.Color;
import java.awt.Graphics2D;

import de.bno.jappybird.engine.Point;
import de.bno.jappybird.engine.SceneObject;

public class Wall extends SceneObject {

	private static final Point ORIGIN = new Point(0, 0);
	private static final Color C_BORDER = Color.BLACK;
	private static final Color C_MAIN = new Color(116, 191, 46);
	private static final Color C_DARK = new Color(81, 135, 23);

	@Override
	protected void paint(Graphics2D g, boolean onScreen) {

		g.setColor(C_BORDER);
		g.fillRect(0, 0, getWidth(), getHeight());

		int x = 0;
		int y = (int) (getHeight() * 0.01);
		int width = getWidth();
		int height = getHeight() - y;

		paintWall(g, x, y, width, height);
	}

	private void paintWall(Graphics2D g, int x, int y, int width, int height) {

		g.setColor(C_DARK);
		g.fillRect(x, y, width, height);

		int _y = (int) (y + height * 0.02);

		double streifen_height = height * 0.01;

		g.setColor(C_MAIN);
		int _height = (int) streifen_height;
		g.fillRect(x, _y, width, _height);
		_y += _height;

		g.setColor(C_DARK);
		_height = (int) (height * 0.04);
		g.fillRect(x, _y, width, _height);
		_y += _height;

		g.setColor(C_MAIN);
		g.fillRect(x, (int) (_y - _height * 0.5 - 0.5 * streifen_height),
				width, (int) streifen_height);

		g.setColor(C_MAIN);
		_height = (int) streifen_height;
		g.fillRect(x, _y, width, _height);
		_y += _height;

	}

	@Override
	public Point getOrigin() {

		return ORIGIN;
	}

}
