package de.azubi.jappybird.engine;

import java.awt.Graphics;

public interface Scene {

	public void paintScene(Graphics g, int width, int height);

	/**
	 * Hält die Szene an.
	 */
	public void pause();

	/**
	 * Beendet die Szene
	 */
	public void stop();

	/**
	 * Beginnt die Szene am Anfang, wenn sie zum erstem Mal abgespielt wird oder
	 * gestoppt wurde. Wurde die Szene pausiert wird die Szene an entsprechender
	 * Stelle fortgeführt.
	 */
	public void start();

}
