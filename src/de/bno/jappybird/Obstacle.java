package de.bno.jappybird;

import java.awt.Color;
import java.awt.Graphics;

import de.bno.jappybird.engine.Point;
import de.bno.jappybird.engine.SceneObject;

public class Obstacle extends SceneObject {

	public static final int ORIENTATION_TOP = 0x01;
	public static final int ORIENTATION_BOTTOM = 0x02;

	private int orientation;

	private double rel_position;
	private double rel_size;

	public Obstacle(int orientation) {
		this.orientation = orientation;
		this.rel_position = 1;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, getWidth(), getHeight());
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
