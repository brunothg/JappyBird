package de.bno.jappybird;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import de.bno.jappybird.engine.Point;
import de.bno.jappybird.engine.SceneObject;

public class NumberButton extends SceneObject {

	private static final Color BG = new Color(0xd9, 0xd9, 0xd9);

	private static final Color FG = new Color(0x53, 0x9c, 0x36);

	private String text;

	private boolean isSeletcted;
	private int number;

	public NumberButton(String text) {

		this.text = text;
		this.setSeletcted(false);
	}

	@Override
	protected void paint(Graphics2D g, boolean onScreen) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		if (isSeletcted()) {
			g.setColor(new Color(255, 0, 0));
		} else {
			g.setColor(Color.BLACK);
		}

		int arc = (int) (getWidth() * 0.1);
		g.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

		g.setColor(BG);
		int borderWidth = (int) (getWidth() * 0.01);
		g.fillRoundRect(borderWidth, borderWidth, getWidth() - 2 * borderWidth,
				getHeight() - 2 * borderWidth, arc, arc);

		g.setColor(FG);

		g.setFont(new Font(Font.SERIF, Font.BOLD, (int) (getHeight() * 0.5)));
		FontMetrics metrics = g.getFontMetrics();

		int widtht = (int) (getWidth() * 0.7);

		g.drawString(
				text,
				(int) (widtht * 0.5 - metrics.stringWidth(text) * 0.5),
				(int) ((metrics.getLeading() + metrics.getAscent() * 0.5) * 0.5 + getHeight() * 0.5));

		g.setColor(Color.BLACK);
		g.fillRect(widtht, (int) (borderWidth + getWidth() * 0.01),
				(int) (getWidth() - widtht - arc),
				(int) (getHeight() - 2 * (borderWidth + getWidth() * 0.01)));

		g.setColor(BG);

		g.fillRect(widtht + borderWidth,
				(int) (borderWidth + getWidth() * 0.01) + borderWidth,
				(int) (getWidth() - widtht - arc) - borderWidth * 2,
				(int) (getHeight() - 2 * (borderWidth + getWidth() * 0.01))
						- borderWidth * 2);

		g.setColor(FG);

		int x, width, height;
		x = widtht + borderWidth + borderWidth;
		width = (int) (getWidth() - widtht - arc) - borderWidth * 2
				- borderWidth * 2;
		height = (int) (getHeight() - 2 * (borderWidth + getWidth() * 0.01))
				- borderWidth * 2 - borderWidth * 2;

		Font font = new Font(Font.SERIF, Font.BOLD, height);

		while ((metrics = g.getFontMetrics(font)).stringWidth(number + "") > width) {
			font = new Font(Font.SERIF, Font.BOLD, font.getSize() - 1);
		}

		g.drawString(
				number + "",
				x
						+ (int) (width * 0.5 - metrics.stringWidth("" + number) * 0.5),
				(int) (height * 0.5 + metrics.getLeading() + metrics
						.getAscent() * 0.5));
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

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

}
