package de.bno.jappybird.engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class SceneObject {

	private Point position;
	private Size size;
	private boolean drawBoundingBox;

	public SceneObject() {
		position = new Point(0, 0);
		size = new Size(0, 0);
		drawBoundingBox = false;
	}

	/**
	 * Paint this SceneObject
	 * 
	 * @param g
	 *            Graphics Object for painting
	 * @param onScreen
	 *            true wenn diese Methode aufgerufen wird um das SceneObject auf
	 *            dem Bildschirm zu zeichnen. False otherwise - Zum Beispiel
	 *            wenn die Kollision geprueft wird.
	 */
	protected abstract void paint(Graphics g, boolean onScreen);

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
		paint(object, true);

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

	/**
	 * Überprüfe, ob die beiden SceneObjects kollidieren. Zunächst wird nach
	 * dem BoundingBox Verfahren nach Kollision gesucht. Sollte Kollision noch
	 * nicht ausgeschlossen sein wird genau geprüft.
	 * 
	 * @param obj
	 *            Das zweite SceneObject
	 * @return true wenn die beiden SceneObjects kollidieren
	 */
	public boolean collides(SceneObject obj) {

		boolean ret;

		Rectangle intersection = collidesBoundingBox(obj);
		ret = intersection != null;

		if (ret) {
			ret = collidesExactly(obj, intersection);
		}

		return ret;
	}

	/**
	 * Überprüft genau, ob die beiden SceneObjects kollidieren. Das Ergebnis
	 * ist nur korrekt, wenn {@link #collidesBoundingBox(SceneObject)} true
	 * liefert.
	 * 
	 * @param obj
	 * @param intersection
	 *            Bereich in dem die Kollisoin möglicherweise statt findet
	 * @return true wenn die beiden SceneObjects kollidieren
	 */
	public boolean collidesExactly(SceneObject obj, Rectangle intersection) {

		boolean ret = false;

		BufferedImage img1 = new BufferedImage(getWidth(), getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g1 = img1.createGraphics();
		paint(g1, false);
		g1.finalize();

		BufferedImage img2 = new BufferedImage(obj.getWidth(), obj.getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = img2.createGraphics();
		obj.paint(g2, false);
		g2.finalize();

		Point topLeftPosition1 = getTopLeftPosition();
		Point topLeftPosition2 = obj.getTopLeftPosition();

		int xOffset1 = -topLeftPosition1.getX();
		int yOffset1 = -topLeftPosition1.getY();

		int xOffset2 = -topLeftPosition2.getX();
		int yOffset2 = -topLeftPosition2.getY();

		loop: for (int y = intersection.y; y < intersection.height
				+ intersection.y; y++) {
			for (int x = (int) intersection.x; x < intersection.width
					+ intersection.x; x++) {

				int a1 = (img1.getRGB(x + xOffset1, y + yOffset1) >> 24) & 0xFF;
				int a2 = (img2.getRGB(x + xOffset2, y + yOffset2) >> 24) & 0xFF;

				if (a1 == a2 && a1 != 0) {
					ret = true;
					break loop;
				}
			}
		}

		return ret;
	}

	/**
	 * Überprüfe, ob die beiden SceneObjects nach dem BoundingBox Verfahren
	 * kollidieren.
	 * 
	 * @param obj
	 *            Das zweite SceneObject
	 * @return true wenn die beiden SceneObjects kollidieren
	 */
	public Rectangle collidesBoundingBox(SceneObject obj) {

		Rectangle2D rect = getRectangle()
				.createIntersection(obj.getRectangle());

		Rectangle ret = null;

		if (rect.getWidth() >= 0 && rect.getWidth() >= 0) {
			ret = new Rectangle((int) rect.getX(), (int) rect.getY(),
					(int) rect.getWidth(), (int) rect.getHeight());
		}

		return ret;
	}

	private Rectangle getRectangle() {

		Point position = getTopLeftPosition();

		return new Rectangle(position.getX(), position.getY(), getWidth(),
				getHeight());
	}

}
