package de.bno.jappybird.engine;

import java.awt.Graphics;

public interface Scene {

	/**
	 * Zeichnet die Szene im aktuellen Zustand
	 * 
	 * @param g
	 *            Graphics Objekt auf das gezeichnet wird
	 * @param width
	 *            Breite der Zeichenfläche
	 * @param height
	 *            Höhe der Zeichenfläche
	 */
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
