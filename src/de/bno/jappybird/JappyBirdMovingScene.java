package de.bno.jappybird;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import de.bno.jappybird.engine.Point;
import de.bno.jappybird.engine.Scene;
import de.bno.jappybird.engine.Time;

public class JappyBirdMovingScene implements Scene, KeyListener {

	private static final double COLLISION_BOTTOM = 0.9;

	private static final double JUMP_SPEED = 0.4;

	private static final double G = 0.9;

	private boolean paused;
	private boolean stopped;
	private Time time;

	private Heli heli;

	public JappyBirdMovingScene(boolean start) {
		this.paused = !start;
		this.time = new Time();
		this.heli = new Heli(this.time);
	}

	@Override
	public void paintScene(Graphics g, int width, int height) {

		if (!paused) {
			time.update();
		}

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
