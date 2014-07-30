package de.bno.jappybird;

import java.awt.Color;
import java.awt.Graphics2D;

import de.bno.jappybird.engine.Point;
import de.bno.jappybird.engine.SceneObject;

public class Button extends SceneObject {

	private String text;

	public Button(String text) {

		this.text = text;
	}

	@Override
	protected void paint(Graphics2D g, boolean onScreen) {

		g.setColor(new Color(200, 200, 200));

		int arc = (int) (getWidth() * 0.1);
		g.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
	}

	@Override
	public Point getOrigin() {

		return new Point((int) (getWidth() * 0.5), (int) (getHeight() * 0.5));
	}

}
