package de.azubi.jappybird;

import java.awt.Graphics;

import de.azubi.jappybird.engine.Scene;
import de.azubi.jappybird.engine.Time;

public class JappyBirdMovingScene implements Scene {

	private boolean paused;
	private Time time;

	public JappyBirdMovingScene(boolean start) {
		this.paused = !start;
		this.time = new Time();
	}

	@Override
	public void paintScene(Graphics g, int width, int height) {

		if (!paused) {
			time.update();
		}

	}

	@Override
	public void pause() {
		paused = true;
		time.reset();
	}

	@Override
	public void stop() {
		paused = true;
		time.reset();
	}

	@Override
	public void start() {
		paused = false;
		time.reset();
	}

}
