package com.github.brunothg.jappybird.scene;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.EventListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.brunothg.game.engine.time.TimeUtils;
import com.github.brunothg.game.engine.time.Timer;

public class JappyBirdScene implements PausableScene {
	private static final Logger LOG = LoggerFactory
			.getLogger(JappyBirdScene.class);

	private static final Color COLOR_GRAS = new Color(0x00, 0x66, 0x00);
	private static final Color COLOR_GRAS_BRIGHT = new Color(0x00, 0xAA, 0x00);

	private static final Color COLOR_SUN = Color.YELLOW;
	private static final Color COLOR_SUN_BORDER = new Color(0xAA, 0x00, 0x00);

	private static final Color COLOR_SKY_TOP = new Color(0x55, 0x55, 0xAA);
	private static final Color COLOR_SKY_BOTTOM = new Color(0x55, 0x55, 0xFF);

	private static final double HORIZONT = 0.7;
	private static final double SUN_SIZE = 0.15;

	private BufferedImage screen;
	private BufferedImage screenStatic;

	private long sunTime;

	private boolean paused;
	private Timer timer;

	private JappyBirdMovingScene movingScene;

	public JappyBirdScene() {

		this.paused = false;
		timer = new Timer();
		movingScene = new JappyBirdMovingScene();
	}

	private void paintNonStatic(Graphics2D g, int width, int height,
			long elapsed) {
		paintSun(g, height, elapsed);
		movingScene.paintScene(g, width, height, elapsed);
	}

	private void paintSun(Graphics2D g, int height, long elapsed) {

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		sunTime += timer.elapsedTime();
		sunTime %= 24 * TimeUtils.NANOSECONDS_PER_SECOND;

		long realTime = sunTime - 12 * TimeUtils.NANOSECONDS_PER_SECOND;

		long sunAddSigned = (realTime / TimeUtils.NANOSECONDS_PER_SECOND) * 2;
		long sunAdd = (sunAddSigned > 0) ? sunAddSigned
				: 12 - (12 + sunAddSigned);

		int sunBlend = 5;

		g.setColor(COLOR_SUN_BORDER);
		for (int i = sunBlend; i > 0; i--) {
			g.setColor(
					blend(g.getColor(), COLOR_SUN_BORDER, COLOR_SUN, sunBlend));
			g.fillOval((int) (24 - i + sunAdd / 2), (int) (24 - i + sunAdd / 2),
					(int) (height * SUN_SIZE + sunAdd) + i * 2,
					(int) (height * SUN_SIZE + sunAdd) + i * 2);
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
				Math.min(255, Math.max(0, c.getGreen() + addG)),
				Math.min(255, Math.max(0, c.getBlue() + addB)));
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
				Math.min(255, Math.max(0, tmpCol.getGreen() + add)),
				Math.min(255, Math.max(0, tmpCol.getBlue() + add)));
	}

	@Override
	public void paintScene(Graphics2D g, int width, int height, long elapsed) {

		if (!paused) {
			timer.update();
		}

		Graphics2D gr = getGraphics(width, height);
		paintNonStatic(gr, width, height, elapsed);

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
		ret.drawImage(screenStatic, 0, 0, screen.getWidth(), screen.getHeight(),
				0, 0, screenStatic.getWidth(), screenStatic.getHeight(), null);

		gr.finalize();

		return ret;
	}

	@Override
	public EventListener[] getEventListeners() {
		return new EventListener[] { movingScene };
	}

	@Override
	public void pause() {
		LOG.info("pause");

		paused = true;
		timer.reset();
		movingScene.pause();
	}

	@Override
	public void stop() {
		LOG.info("stop");

		paused = true;
		timer.reset();
		movingScene.stop();
	}

	@Override
	public void start() {
		LOG.info("start");

		paused = false;
		timer.reset();
		movingScene.start();
	}

}
