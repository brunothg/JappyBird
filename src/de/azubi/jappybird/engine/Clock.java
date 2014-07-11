package de.azubi.jappybird.engine;

import java.util.LinkedList;
import java.util.List;

public class Clock extends Thread
{

	private static final int NANOSECONDS_PER_MILLISECOND = 1000000;
	private static final int NANOSECONDS_PER_SECOND = 1000000000;
	public static final int FPS_AS_FAST_AS_POSSIBLE = -1;
	public static final int FPS_STOP = 0;

	private volatile double framesPerSecond;
	private volatile boolean running;

	private long lastTime;

	private List<ClockListener> listeners;

	public Clock()
	{
		this(-1);
	}

	public Clock(float framesPerSecond)
	{
		this.framesPerSecond = framesPerSecond;
		this.listeners = new LinkedList<ClockListener>();
	}

	public void run()
	{
		running = true;
		setActualTime();

		while (running)
		{
			doWork();
		}

	}

	private void setActualTime()
	{
		lastTime = System.nanoTime();
	}

	private void doWork()
	{
		double fps = getFramesPerSecond();

		if (fps < FPS_STOP)
		{
			tick();
			return;
		}
		else if (fps == FPS_STOP)
		{
			return;
		}

		long time = System.nanoTime();
		long elapsedTime = time - lastTime;
		int timeForOneFrame = (int) (NANOSECONDS_PER_SECOND / fps);

		if (elapsedTime >= timeForOneFrame)
		{
			long ticks = elapsedTime / timeForOneFrame;

			for (int i = 0; i < ticks && i >= 0; i++)
			{
				tick();
			}

			lastTime += ticks * timeForOneFrame;
		}
		else
		{
			long timeToTick = timeForOneFrame - elapsedTime;

			try
			{
				Thread
					.sleep(timeToTick / NANOSECONDS_PER_MILLISECOND, (int) (timeToTick % NANOSECONDS_PER_MILLISECOND));
			}
			catch (InterruptedException e)
			{
			}
		}
	}

	private void tick()
	{
		synchronized (listeners)
		{
			for (ClockListener cl : listeners)
			{
				try
				{
					cl.tick();
				}
				catch (Exception e)
				{
				}
			}
		}
	}

	public void addClockListener(ClockListener cl)
	{
		synchronized (listeners)
		{
			listeners.add(cl);
		}
	}

	public void removeClockListener(ClockListener cl)
	{
		synchronized (listeners)
		{
			listeners.remove(cl);
		}
	}

	public synchronized double getFramesPerSecond()
	{
		return framesPerSecond;
	}

	public synchronized void setFramesPerSecond(double framesPerSecond)
	{
		setActualTime();

		this.framesPerSecond = framesPerSecond;
	}

	public void interrupt()
	{
		running = false;

		try
		{
			super.interrupt();
		}
		catch (Exception e)
		{
		}
	}

}
