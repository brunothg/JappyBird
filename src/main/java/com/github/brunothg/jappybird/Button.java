package com.github.brunothg.jappybird;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import com.github.brunothg.game.engine.d2.commons.Point;
import com.github.brunothg.game.engine.d2.object.SceneObject;

public class Button extends SceneObject {

	private String text;

	private boolean isSeletcted;

	public Button(String text) {

		this.text = text;
		this.setSeletcted(false);
	}

	@Override
	protected void paint(Graphics2D g, long elapsed) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		if (isSeletcted()) {
			g.setColor(new Color(255, 0, 0));
		} else {
			g.setColor(Color.BLACK);
		}

		int arc = (int) (getWidth() * 0.1);
		g.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

		g.setColor(new Color(0xd9, 0xd9, 0xd9));
		int borderWidth = (int) (getWidth() * 0.01);
		g.fillRoundRect(borderWidth, borderWidth, getWidth() - 2 * borderWidth,
				getHeight() - 2 * borderWidth, arc, arc);

		g.setColor(new Color(0x53, 0x9c, 0x36));

		g.setFont(new Font(Font.SERIF, Font.BOLD, (int) (getHeight() * 0.5)));
		FontMetrics metrics = g.getFontMetrics();

		g.drawString(text,
				(int) (getWidth() * 0.5 - metrics.stringWidth(text) * 0.5),
				(int) ((metrics.getLeading() + metrics.getAscent() * 0.5) * 0.5
						+ getHeight() * 0.5));

	}

	@Override
	public Point getOrigin() {

		return new Point((int) (getWidth() * 0.5), (int) (getHeight() * 0.5));
	}

	public boolean isSeletcted() {
		return isSeletcted;
	}

	public void setSeletcted(boolean isSeletcted) {
		this.isSeletcted = isSeletcted;
	}

}
