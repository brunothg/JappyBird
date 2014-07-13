package de.bno.jappybird;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;

import de.bno.jappybird.engine.Point;
import de.bno.jappybird.engine.Scene;
import de.bno.jappybird.engine.Time;

public class JappyBirdMovingScene implements Scene, KeyListener {

	private static final Color COLOR_TEXT_POINTS = Color.WHITE;

	private static final double COLLISION_BOTTOM = 0.9;

	private static final double JUMP_SPEED = 0.4;

	private static final double G = 0.9;

	private boolean paused;
	private boolean stopped;
	private Time time;

	private long points;

	private Heli heli;

	public JappyBirdMovingScene(boolean start) {
		this.paused = !start;
		this.time = new Time();
		this.heli = new Heli(this.time);
		this.points = 0;
	}

	@Override
	public void paintScene(Graphics g, int width, int height) {

		Graphics2D g2d = null;
		if (g instanceof Graphics2D) {
			g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
		}

		if (!paused) {
			time.update();
			points += time.elapsedTime();
		}

		paintHeli(g, width, height);
		paintPoints(g2d, width, height);
	}

	private void paintPoints(Graphics2D g, int width, int height) {

		TextLayout text = new TextLayout("" + (int) Time.Seconds(points),
				new Font(Font.SANS_SERIF, Font.BOLD, 30),
				new FontRenderContext(null, true, false));

		AffineTransform textAt = new AffineTransform();
		textAt.translate(-text.getBounds().getWidth() / 2, text.getBounds()
				.getHeight());

		Shape shape = text.getOutline(textAt);

		g.setStroke(new BasicStroke(2.0f));
		AffineTransform pos = new AffineTransform();
		pos.translate(width / 2, 5);
		g.transform(pos);
		g.setColor(COLOR_TEXT_POINTS);
		g.fill(shape);
		g.setColor(invers(g.getColor()));
		g.draw(shape);
	}

	private Color invers(Color c) {
		return new Color(255 - c.getRed(), 255 - c.getGreen(),
				255 - c.getBlue());
	}

	private void paintHeli(Graphics g, int width, int height) {
		setHeliPosition(width, height);
		heli.setSize((int) (width * 0.08), (int) (height * 0.08));
		heli.paintOnScene(g);
	}

	private void setHeliPosition(int width, int height) {
		double seconds = Time.Seconds(time.elapsedTime());

		heli.setSpeed(heli.getSpeed() + (G * seconds));
		heli.setAusrueckung(heli.getAusrueckung() + (heli.getSpeed() * seconds));
		heli.setPosition((int) (width * 0.2),
				(int) (height * (0.5 + heli.getAusrueckung())));

		chkColision(height);
	}

	private void chkColision(int height) {
		Point position = heli.getTopLeftPosition();

		if (position.getY() < 0) {
			heli.setTopLeftPosition(new Point(position.getX(), 0));
			collide();
			return;
		}

		if (position.getY() + heli.getHeight() > height * COLLISION_BOTTOM) {
			heli.setTopLeftPosition(new Point(position.getX(),
					(int) (height * COLLISION_BOTTOM) - heli.getHeight()));
			collide();
			return;
		}
	}

	private void collide() {
		stop();
	}

	private void jump() {
		if (paused) {
			return;
		}

		heli.setSpeed(heli.getSpeed() - JUMP_SPEED);
	}

	@Override
	public void pause() {
		paused = true;
		time.reset();
	}

	@Override
	public void stop() {
		paused = true;
		stopped = true;

		time.reset();
		heli.setSpeed(0);
	}

	@Override
	public void start() {
		paused = false;

		if (stopped) {
			heli.setAusrueckung(0);
			points = 0;
			stopped = false;
		}

		time.reset();
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_SPACE:
			jump();
			break;
		}
	}

}
