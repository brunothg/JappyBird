package de.azubi.jappybird.engine;

import java.awt.Graphics;

public abstract class SceneObject {

	private Point position;
	private Point origin;
	private Size size;

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

		int x_topLeft = -getOrigin().getX();
		int y_topLeft = -getOrigin().getY();
		int width = getWidth();
		int height = getHeight();

		Graphics object = g.create(x_topLeft, y_topLeft, width, height);
		paint(object);
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
	 * Set the Position of this ScreenObject
	 * 
	 * @param position
	 *            The Position of this ScreenObject
	 */
	public void setPosition(Point position) {
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
	public Point getOrigin() {
		return origin;
	}

	/**
	 * Set the Origin of this ScreenObject
	 * 
	 * @param origin
	 *            The Origin of this ScreenObject
	 */
	public void setOrigin(Point origin) {
		this.origin = origin;
	}

	/**
	 * Set the Origin of this ScreenObject
	 * 
	 * @see #setOrigin(Point)
	 * @param x
	 *            The X-Coordinate of this ScreenObjects Origin
	 * @param y
	 *            The Y-Coordinate of this ScreenObjects Origin
	 */
	public void setOrigin(int x, int y) {
		setOrigin(new Point(x, y));
	}

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

}
