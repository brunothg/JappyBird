package de.azubi.jappybird.engine;

public class Time {

	private long lastTime;
	private long elapsedTime;

	public Time() {
		reset();
	}

	public void reset() {
		lastTime = System.nanoTime();
		elapsedTime = 0;
	}

	public void update() {
		long time = System.nanoTime();
		elapsedTime = time - lastTime;
		lastTime = time;
	}

	public long elapsedTime() {
		return elapsedTime;
	}

}
