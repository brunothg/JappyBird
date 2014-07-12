package de.azubi.jappybird.engine;

public class Time {

	public static final long NANOSECONDS_PER_SECOND = 1_000_000_000;
	public static final long MILLISECONDS_PER_SECOND = 1_000;

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
