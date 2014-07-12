package de.azubi.jappybird.engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class FPSScene implements Scene {

	private Scene scene;
	private Time time;
	private boolean paused;
	private Color textColor;
	private Font font;

	public FPSScene(Scene scene, boolean start) {
		this.scene = scene;
		this.time = new Time();
		this.paused = !start;
		this.textColor = Color.BLACK;
		this.font = new Font(Font.SANS_SERIF, Font.BOLD, 12);
	}

	public FPSScene(Scene scene) {
		this(scene, true);
	}

	@Override
	public void paintScene(Graphics g, int width, int height) {
		scene.paintScene(g, width, height);

		if (!paused) {
			time.update();
		}

		float fps = Time.NANOSECONDS_PER_SECOND / (float) time.elapsedTime();
		if (Float.isNaN(fps) || Float.isInfinite(fps)) {
			fps = 0;
		}

		String status = (paused) ? "(paused)" : "";

		g.setColor(textColor);
		g.setFont(font);
		g.drawString(String.format("%.2f%s FPS", fps, status), 2, 2 + g
				.getFontMetrics().getHeight());
	}

	@Override
	public void pause() {
		scene.pause();
		time.reset();
	}

	@Override
	public void stop() {
		scene.stop();
		time.reset();
	}

	@Override
	public void start() {
		scene.start();
		time.reset();
	}

	public Color getTextColor() {
		return textColor;
	}

	public void setTextColor(Color textColor) {
		if (textColor == null) {
			return;
		}

		this.textColor = textColor;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		if (font == null) {
			return;
		}

		this.font = font;
	}

}
