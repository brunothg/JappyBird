package de.bno.jappybird;

import java.awt.Color;
import java.awt.Graphics;

import de.bno.jappybird.engine.Point;
import de.bno.jappybird.engine.SceneObject;

public class Obstacle extends SceneObject {

	public static final int ORIENTATION_TOP = 0x01;
	public static final int ORIENTATION_BOTTOM = 0x02;

	private static final Color C_BORDER = Color.BLACK;
	private static final Color C_MAIN = new Color(116, 191, 46);
	private static final Color C_DARK = new Color(81, 135, 23);
	private static final Color C_BRIGHT = new Color(156, 230, 91);

	private int orientation;

	private double rel_position;
	private double rel_size;

	public Obstacle(int orientation) {
		this.orientation = orientation;
		this.rel_position = 1;
	}

	@Override
	public void paint(Graphics g, boolean onScreen) {

		g.setColor(C_BORDER);
		g.fillRect(0, 0, getWidth(), getHeight());

		int getHeight = (getOrientation() == ORIENTATION_TOP) ? getHeight()
				- (int) (getHeight() * 0.01) : getHeight();
		int y = (getOrientation() == ORIENTATION_BOTTOM) ? (int) (getHeight() * 0.01)
				: 0;

		int getWidth = getWidth() - (int) (getWidth() * 0.04);
		int width = (int) (getWidth * 0.04);

		g.setColor(C_BRIGHT);
		int widtht = (int) (getWidth * 0.2);
		g.fillRect(width, y, widtht, getHeight);
		width += widtht;

		g.setColor(C_MAIN);
		widtht = (int) (getWidth * 0.05);
		g.fillRect(width, y, widtht, getHeight);
		width += widtht;

		g.setColor(C_BRIGHT);
		widtht = (int) (getWidth * 0.05);
		g.fillRect(width, y, widtht, getHeight);
		width += widtht;

		g.setColor(C_MAIN);
		widtht = (int) (getWidth * 0.4);
		g.fillRect(width, y, widtht, getHeight);
		width += widtht;

		g.setColor(C_DARK);
		widtht = (int) (getWidth * 0.05);
		g.fillRect(width, y, widtht, getHeight);
		width += widtht;

		g.setColor(C_MAIN);
		widtht = (int) (getWidth * 0.05);
		g.fillRect(width, y, widtht, getHeight);
		width += widtht;

		g.setColor(C_DARK);
		widtht = (int) (getWidth - width);
		g.fillRect(width, y, widtht, getHeight);

	}

	@Override
	public Point getOrigin() {
		return new Point(0, (orientation == ORIENTATION_TOP) ? 0 : getHeight());
	}

	public int getOrientation() {
		return orientation;
	}

	public double getRelPosition() {
		return rel_position;
	}

	public void setRelPosition(double rel_position) {
		this.rel_position = rel_position;
	}

	public double getRel_size() {
		return rel_size;
	}

	public void setRel_size(double rel_size) {
		this.rel_size = rel_size;
	}
}
