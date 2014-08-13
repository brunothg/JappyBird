package de.bno.jappybird;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import static de.bno.jappybird.strings.Strings.*;
import de.bno.jappybird.engine.Listeners;
import de.bno.jappybird.engine.Point;
import de.bno.jappybird.engine.Scene;
import de.bno.jappybird.engine.Time;
import de.bno.jappybird.settings.Settings;

public class JappyBirdMovingScene implements Scene, KeyListener {

	private static final Color COLOR_START_STRING = Color.WHITE;

	private static final double DISTANCE_OBSTACLES = 0.7;

	private static final double SPEED_OBSTACLE = 0.1;

	private static final double WIDTH_OBSTACLE = 0.08;

	private static final Color COLOR_TEXT_POINTS = Color.WHITE;

	private static final double COLLISION_BOTTOM = 0.9;

	private static final double MAX_HEIGHT = 0.7;
	private static final double MIN_HEIGHT = 0.1;

	private static double JUMP_SPEED = 0.4;

	private static double G = 0.9;

	private boolean paused;
	private boolean stopped;
	private Time time;

	private boolean spaceStatus;

	private long points;

	private Heli heli;
	private Wall wall;
	private Result result;

	private LinkedList<Obstacle> obstacles;

	private Random rand;

	boolean started;
	boolean collision;

	public JappyBirdMovingScene() {

		this.started = false;
		this.time = new Time();
		this.heli = new Heli(this.time);
		this.wall = new Wall();
		this.result = new Result(0, this);
		this.points = 0;
		this.obstacles = new LinkedList<Obstacle>();
		this.rand = new Random(System.nanoTime());

		pause();
	}

	@Override
	public void paintScene(Graphics2D g, int width, int height) {

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		if (!paused) {
			time.update();
			points += time.elapsedTime();
		}

		updateObstacles(width, height);
		paintHeli(g, width, height);
		paintObstacles(g, width, height);
		paintWall(g, width, height);
		paintPoints(g, width, height);

		if (!started) {
			paintStartString(g, width, height);
		}

		if (collision) {
			paintResult(g, width, height);
		}
	}

	private void paintWall(Graphics2D g, int width, int height) {
		g.setColor(Color.BLACK);

		wall.setPosition(0, (int) (height * COLLISION_BOTTOM));
		wall.setSize(width, (int) (height * 1 - COLLISION_BOTTOM));
		wall.paintOnScene(g);
	}

	private void paintResult(Graphics2D g, int width, int height) {

		result.setSize((int) (width * 0.45), (int) (height * 0.45));
		result.setPosition((int) (width * 0.5), (int) (height * 0.5));

		result.paintOnScene(g);
	}

	private void paintStartString(Graphics2D g, int width, int height) {

		g.setColor(COLOR_START_STRING);

		g.setFont(new Font(Font.SERIF, Font.BOLD, (int) (height * 0.1)));
		FontMetrics metrics;

		while ((metrics = g.getFontMetrics()).stringWidth(START_STRING) > width) {
			g.setFont(new Font(Font.SERIF, Font.BOLD, g.getFont().getSize() - 1));
		}

		g.drawString(START_STRING, (int) (width * 0.5 - metrics
				.stringWidth(START_STRING) * 0.5),
				(int) (height * 0.5 + (metrics.getLeading() + metrics
						.getAscent() * 0.5) * 0.5));
	}

	private void updateObstacles(int width, int height) {

		moveObstacles(width, height);
		resizeObstacles(width, height);
		addAndRemoveObstacles(width, height);

	}

	private void addAndRemoveObstacles(int width, int height) {

		addObstacles(width);
		removeObstacles();
	}

	private void addObstacles(int width) {

		if (!(obstacles.isEmpty() || obstacles.getLast().getTopLeftPosition()
				.getX() <= (int) (width * DISTANCE_OBSTACLES))) {
			return;
		}

		if (Time.Seconds(points) <= 30) {

			addOneObstacle();
		} else {

			addTwoObstacles();
		}

	}

	private void addTwoObstacles() {

		Obstacle o = new Obstacle(Obstacle.ORIENTATION_TOP);
		Obstacle u = new Obstacle(Obstacle.ORIENTATION_BOTTOM);

		double size = Math.min(
				Math.max(rand.nextDouble() * MAX_HEIGHT, MIN_HEIGHT),
				MAX_HEIGHT - MIN_HEIGHT);

		o.setRel_size(size);
		u.setRel_size(MAX_HEIGHT - size);

		obstacles.add(o);
		obstacles.add(u);
	}

	private void addOneObstacle() {
		Obstacle o = new Obstacle(
				rand.nextBoolean() ? Obstacle.ORIENTATION_BOTTOM
						: Obstacle.ORIENTATION_TOP);
		o.setRel_size(0.5);
		obstacles.add(o);
	}

	private void removeObstacles() {
		Iterator<Obstacle> iterator = obstacles.iterator();
		while (iterator.hasNext()) {
			Obstacle o = iterator.next();

			if (o.getTopLeftPosition().getX() + o.getWidth() < 0) {
				iterator.remove();
			}
		}
	}

	private void resizeObstacles(int width, int height) {

		for (Obstacle o : obstacles) {
			o.setSize((int) (width * WIDTH_OBSTACLE),
					(int) (height * o.getRel_size()));
		}
	}

	private void moveObstacles(int width, int height) {

		for (Obstacle o : obstacles) {
			o.setRelPosition(o.getRelPosition()
					- (Time.Seconds(time.elapsedTime()) * SPEED_OBSTACLE));
			o.setPosition((int) (width * o.getRelPosition()), (o
					.getOrientation() == Obstacle.ORIENTATION_TOP) ? 0
					: (int) (height * COLLISION_BOTTOM));
		}
	}

	private void paintObstacles(Graphics2D g, int width, int height) {

		for (Obstacle o : obstacles) {
			o.paintOnScene(g);
		}
	}

	private void paintPoints(Graphics2D g, int width, int height) {

		AffineTransform resetTransform = g.getTransform();

		TextLayout text = new TextLayout("" + (int) Time.Seconds(points),
				new Font(Font.SANS_SERIF, Font.BOLD, 30),
				new FontRenderContext(null, true, false));

		AffineTransform textAt = new AffineTransform();
		textAt.translate(-text.getBounds().getWidth() / 2, text.getBounds()
				.getHeight());

		Shape shape = text.getOutline(textAt);

		g.setStroke(new BasicStroke(2.0f));
		AffineTransform pos = new AffineTransform();
		pos.translate(width / 2, 5);
		g.transform(pos);
		g.setColor(COLOR_TEXT_POINTS);
		g.fill(shape);
		g.setColor(invers(g.getColor()));
		g.draw(shape);

		g.setTransform(resetTransform);
	}

	private Color invers(Color c) {
		return new Color(255 - c.getRed(), 255 - c.getGreen(),
				255 - c.getBlue());
	}

	private void paintHeli(Graphics2D g, int width, int height) {
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

		if (stopped || paused) {
			return;
		}

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

		for (Obstacle o : obstacles) {
			if (heli.collides(o)) {
				collide();
				return;
			}
		}
	}

	private void collide() {

		if (stopped) {
			return;
		}

		collision = true;
		result.reset();
		result.setPunkte((int) Time.Seconds(points));
		result.setInput(Settings.getValue("username",
				System.getProperty("user.name", RESULT_DEFAULT_NAME)));
		result.setHighscore(JappyBird.getDAO().isHighscore(result.getPunkte()));

		stop();
	}

	private void space() {

		if (!started) {
			started = true;
			start();
		}

		jump();
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
			points = 0;
			stopped = false;
			obstacles.clear();
			started = false;
			collision = false;

			pause();
		}

		time.reset();

	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (collision) {
			result.keyTyped(e);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (collision) {
			result.keyPressed(e);
		}

		switch (e.getKeyCode()) {
		case KeyEvent.VK_SPACE:
			spaceStatus = true;
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

		if (collision) {
			result.keyReleased(e);
		}

		switch (e.getKeyCode()) {
		case KeyEvent.VK_SPACE:
			if (spaceStatus) {
				spaceStatus = false;
				space();
			}
			break;
		}
	}

	@Override
	public Listeners getListener() {
		return new Listeners(this, null, null);
	}

	public static void setG(double g) {
		if (g < 0) {
			g = -g;
		}

		G = g;
	}

	public static void setJumpSpeed(double js) {
		if (js < 0) {
			js = -js;
		}

		JUMP_SPEED = js;
	}

}
