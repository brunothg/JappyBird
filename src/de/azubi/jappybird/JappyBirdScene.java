package de.azubi.jappybird;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import de.azubi.jappybird.engine.Scene;
import de.azubi.jappybird.engine.Time;

public class JappyBirdScene implements Scene {

	private static final Color COLOR_GRAS = new Color(0x00, 0x66, 0x00);
	private static final Color COLOR_GRAS_BRIGHT = new Color(0x00, 0xAA, 0x00);

	private static final Color COLOR_SUN = Color.YELLOW;
	private static final Color COLOR_SUN_BORDER = new Color(0xAA, 0x00, 0x00);

	private static final Color COLOR_SKY_TOP = new Color(0x00, 0x00, 0x66);
	private static final Color COLOR_SKY_BOTTOM = new Color(0x00, 0x00, 0xAA);

	private static final double HORIZONT = 0.7;
	private static final double SUN_SIZE = 0.15;

	private Time time;

	private BufferedImage screen;
	private BufferedImage screenStatic;

	private long sunTime;

	private boolean paused;

	private JappyBirdMovingScene movingScene;

	public JappyBirdScene(boolean start) {
		paused = !start;
		time = new Time();
		movingScene = new JappyBirdMovingScene(start);
	}

	public JappyBirdScene() {
		this(true);
	}

	private void paintNonStatic(Graphics g, int width, int height) {
		paintSun(g, height);
		movingScene.paintScene(g, width, height);
	}

	private void paintSun(Graphics g, int height) {
		sunTime += time.elapsedTime();
		sunTime %= 24 * Time.NANOSECONDS_PER_SECOND;

		long realTime = sunTime - 12 * Time.NANOSECONDS_PER_SECOND;

		long sunAddSigned = (realTime / Time.NANOSECONDS_PER_SECOND) * 2;
		long sunAdd = (sunAddSigned > 0) ? sunAddSigned
				: 12 - (12 + sunAddSigned);

		int sunBlend = 5;

		g.setColor(COLOR_SUN_BORDER);
		for (int i = sunBlend; i > 0; i--) {
			g.setColor(blend(g.getColor(), COLOR_SUN_BORDER, COLOR_SUN,
					sunBlend));
			g.fillOval((int) (24 - i + sunAdd / 2),
					(int) (24 - i + sunAdd / 2),
					(int) (height * SUN_SIZE + sunAdd) + i * 2, (int) (height
							* SUN_SIZE + sunAdd)
							+ i * 2);
		}
	}

	private void paintStaticBackground(Graphics g, int width, int height) {
		paintSky(g, width, height);
		paintGround(g, width, height);
	}

	private void paintSky(Graphics g, int width, int height) {
		g.setColor(COLOR_SKY_TOP);

		int y_Ground_Start = (int) (height * HORIZONT);
		int groundBlend = 10;

		for (int y = 0; y < y_Ground_Start - groundBlend; y++) {
			g.drawLine(0, y, width, y);
		}

		for (int y = y_Ground_Start - groundBlend; y < y_Ground_Start; y++) {
			g.setColor(blend(g.getColor(), COLOR_SKY_BOTTOM, COLOR_GRAS,
					groundBlend));
			g.drawLine(0, y, width, y);
		}
	}

	private void paintGround(Graphics g, int width, int height) {
		g.setColor(COLOR_GRAS);

		int y_Ground_Start = (int) (height * HORIZONT);

		g.fillRect(0, y_Ground_Start, width, height);

		for (int y = (int) (height * HORIZONT) + 15; y < height; y++) {
			Color tmpCol = g.getColor();

			if (isDarker(tmpCol, COLOR_GRAS_BRIGHT) && y % 3 == 0) {
				g.setColor(getColor(tmpCol, 1));
			}

			g.fillOval(0, y, width, height);
		}
	}

	private Color blend(Color c, Color cs, Color ce, int step) {
		int addR, addG, addB;

		addR = (ce.getRed() - cs.getRed()) / step;
		addG = (ce.getGreen() - cs.getGreen()) / step;
		addB = (ce.getBlue() - cs.getBlue()) / step;

		return new Color(Math.min(255, Math.max(0, c.getRed() + addR)),
				Math.min(255, Math.max(0, c.getGreen() + addG)), Math.min(255,
						Math.max(0, c.getBlue() + addB)));
	}

	private boolean isDarker(Color c1, Color c2) {
		boolean ret = false;

		int col1 = c1.getBlue() + c1.getGreen() + c1.getRed();
		int col2 = c2.getBlue() + c2.getGreen() + c2.getRed();

		if ((col1 / 3.0) < (col2 / 3.0)) {
			ret = true;
		}

		return ret;
	}

	private Color getColor(Color tmpCol, int add) {
		return new Color(Math.min(255, Math.max(0, tmpCol.getRed() + add)),
				Math.min(255, Math.max(0, tmpCol.getGreen() + add)), Math.min(
						255, Math.max(0, tmpCol.getBlue() + add)));
	}

	@Override
	public void paintScene(Graphics g, int width, int height) {

		if (!paused) {
			time.update();
		}

		Graphics2D gr = getGraphics(width, height);
		paintNonStatic(gr, width, height);

		g.drawImage(screen, 0, 0, width, height, 0, 0, screen.getWidth(),
				screen.getHeight(), null);

		gr.finalize();
	}

	private Graphics2D getGraphics(int width, int height) {
		boolean paintBG = false;

		if (screenStatic == null || screenStatic.getWidth() != width
				|| screenStatic.getHeight() != height) {
			screenStatic = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_ARGB);
			paintBG = true;
		}

		if (screen == null || screen.getWidth() != width
				|| screen.getHeight() != height) {
			screen = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_ARGB);
		}

		Graphics2D gr = screenStatic.createGraphics();

		if (paintBG) {
			paintStaticBackground(gr, width, height);
		}

		Graphics2D ret = screen.createGraphics();
		ret.drawImage(screenStatic, 0, 0, screen.getWidth(),
				screen.getHeight(), 0, 0, screenStatic.getWidth(),
				screenStatic.getHeight(), null);

		gr.finalize();

		return ret;
	}

	@Override
	public void pause() {
		paused = true;
		time.reset();
		movingScene.pause();
	}

	@Override
	public void stop() {
		paused = true;
		time.reset();
		movingScene.stop();
	}

	@Override
	public void start() {
		paused = false;
		time.reset();
		movingScene.start();
	}
}
