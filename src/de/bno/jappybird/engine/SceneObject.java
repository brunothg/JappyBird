package de.bno.jappybird.engine;

import java.awt.Color;
import java.awt.Graphics;

public abstract class SceneObject {

	private Point position;
	private Size size;
	private boolean drawBoundingBox;

	public SceneObject() {
		position = new Point(0, 0);
		size = new Size(0, 0);
		setDrawBoundingBox(false);
	}

	/**
	 * Paint this SceneObject
	 * 
	 * @param g
	 *            Graphics Object for painting
	 */
	public abstract void paint(Graphics g);

	/**
	 * Paints this SceneObject with its origin
	 * 
	 * @param g
	 *            Graphics Object for painting
	 */
	public void paintOnScene(Graphics g) {

		int x_topLeft = getTopLeftPosition().getX();
		int y_topLeft = getTopLeftPosition().getY();
		int width = getWidth();
		int height = getHeight();

		Graphics object = g.create(x_topLeft, y_topLeft, width + 1, height + 1);
		paint(object);

		if (isDrawBoundingBox()) {
			object.setColor(Color.BLACK);
			object.drawRect(0, 0, width, height);
		}
	}

	/**
	 * The Position of this SceneObjects top left corner
	 * 
	 * @return The Position of this SceneObjects top left corner
	 */
	public Point getTopLeftPosition() {
		return new Point(getX() - getOrigin().getX(), getY()
				- getOrigin().getY());
	}

	/**
	 * Set the Position of this SceneObjects top left corner
	 */
	public void setTopLeftPosition(Point p) {
		setPosition(new Point(p.getX() + getOrigin().getX(), p.getY()
				+ getOrigin().getY()));
	}

	/**
	 * The Position of this ScreenObject
	 * 
	 * @return The Position of this ScreenObject
	 */
	public Point getPosition() {
		return position;
	}

	/**
	 * The Y-Coordinate of this ScreenObject
	 * 
	 * @see #getPosition()
	 * @return The Y-Coordinate of this ScreenObject
	 */
	public int getY() {
		return getPosition().getY();
	}

	/**
	 * The X-Coordinate of this ScreenObject
	 * 
	 * @see #getPosition()
	 * @return The X-Coordinate of this ScreenObject
	 */
	public int getX() {
		return getPosition().getX();
	}

	/**
	 * Set the Position of this ScreenObject
	 * 
	 * @param position
	 *            The Position of this ScreenObject
	 */
	public void setPosition(Point position) {
		if (position == null) {
			throw new NullPointerException(
					"This SceneObject must have a position");
		}

		this.position = position;
	}

	/**
	 * Set the Position of this ScreenObject
	 * 
	 * @see #setPosition(Point)
	 * @param x
	 *            The X-Coordinate of this ScreenObject
	 * @param y
	 *            The Y-Coordinate of this ScreenObject
	 */
	public void setPosition(int x, int y) {
		setPosition(new Point(x, y));
	}

	/**
	 * The Origin of this ScreenObject
	 * 
	 * @return The Origin of this ScreenObject
	 */
	public abstract Point getOrigin();

	/**
	 * The Size of this ScreenObject
	 * 
	 * @return The Size of this ScreenObject
	 */
	public Size getSize() {
		return size;
	}

	/**
	 * The Height of this ScreenObject
	 * 
	 * @see #getSize()
	 * @return The Height of this ScreenObject
	 */
	public int getHeight() {
		return getSize().getHeight();
	}

	/**
	 * The Width of this ScreenObject
	 * 
	 * @see #getSize()
	 * @return The Width of this ScreenObject
	 */
	public int getWidth() {
		return getSize().getWidth();
	}

	/**
	 * Set the size of this ScreenObject
	 * 
	 * @param size
	 *            Size of this ScreenObject
	 */
	public void setSize(Size size) {
		if (size == null || size.getHeight() < 0 || size.getWidth() < 0) {
			throw new NullPointerException(
					"This SceneObject must have a positive size");
		}

		this.size = size;
	}

	/**
	 * @see #setSize(Size)
	 * @param width
	 *            Width of this ScreenObject
	 * @param height
	 *            Height of this ScreenObject
	 */
	public void setSize(int width, int height) {
		setSize(new Size(width, height));
	}

	public boolean isDrawBoundingBox() {
		return drawBoundingBox;
	}

	public void setDrawBoundingBox(boolean drawBoundingBox) {
		this.drawBoundingBox = drawBoundingBox;
	}

}
