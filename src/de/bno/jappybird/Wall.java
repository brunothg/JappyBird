package de.bno.jappybird;

import java.awt.Color;
import java.awt.Graphics2D;

import de.bno.jappybird.engine.Point;
import de.bno.jappybird.engine.SceneObject;

public class Wall extends SceneObject {

	private static final Point ORIGIN = new Point(0, 0);

	@Override
	protected void paint(Graphics2D g, boolean onScreen) {

		g.setColor(Color.BLACK);

		g.fillRect(0, 0, getWidth(), getHeight());
	}

	@Override
	public Point getOrigin() {

		return ORIGIN;
	}

}
