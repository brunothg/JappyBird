package com.github.brunothg.jappybird.object;

import static com.github.brunothg.jappybird.strings.Strings.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.github.brunothg.game.engine.d2.commons.Point;
import com.github.brunothg.game.engine.d2.object.SceneObject;
import com.github.brunothg.game.engine.time.TimeUtils;
import com.github.brunothg.jappybird.JappyBird;
import com.github.brunothg.jappybird.dao.Score;
import com.github.brunothg.jappybird.scene.PausableScene;
import com.github.brunothg.jappybird.settings.Settings;

public class Result extends SceneObject implements KeyListener {

	private static final double FONT_SIZE = 0.08;

	private int punkte;

	private String input;

	private boolean highscore;

	private long stateOfBlink;

	private boolean neustart;

	private PausableScene parent;

	public Result(int punkte, PausableScene parent) {

		setInput("");
		this.parent = parent;
		this.stateOfBlink = 0;
		this.setPunkte(punkte);
		this.neustart = true;
	}

	@Override
	protected void paint(Graphics2D g, long elapsed) {

		stateOfBlink += elapsed;
		stateOfBlink = stateOfBlink % (TimeUtils.NANOSECONDS_PER_SECOND);

		g.setColor(new Color(0, 0, 0, 150));

		int arc = (int) (getWidth() * 0.1);
		g.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

		g.setColor(new Color(200, 200, 200, 200));

		g.fillRoundRect((int) (getWidth() * 0.01), (int) (getHeight() * 0.01),
				(int) (getWidth() * 0.98), (int) (getHeight() * 0.98), arc,
				arc);

		int x = (int) (getWidth() * 0.05);
		int y = (int) (getHeight() * 0.05);
		int _y = y;
		int width = (int) (getWidth() * 0.90);
		int height = (int) (getHeight() * 0.90);

		g.setColor(Color.BLACK);
		FontMetrics metrics;

		String str = RESULT_CRASH;
		drawString(g, x, y, width, height, str);
		metrics = g.getFontMetrics();
		y += 2 * metrics.getHeight();

		str = String.format("%d %s", getPunkte(), (getPunkte() != 1)
				? RESULT_POINTS_PLURAL : RESULT_POINTS_SINGLE);
		drawString(g, x, y, width, height, str);
		metrics = g.getFontMetrics();
		y += metrics.getHeight();

		if (highscore) {
			str = RESULT_HIGHSCORE;
			drawString(g, x, y, width, height, str);
			metrics = g.getFontMetrics();
			y += 2 * metrics.getHeight();

			str = RESULT_NAME;
			drawString(g, x, y, width, height, str);
			metrics = g.getFontMetrics();
			y += metrics.getHeight();

			str = input
					+ ((stateOfBlink > (TimeUtils.NANOSECONDS_PER_SECOND * 0.5))
							? "_" : "");
			drawString(g, x, y, width, height, str);
			metrics = g.getFontMetrics();
			y += metrics.getHeight();

		}

		g.setFont(new Font(Font.SERIF, Font.BOLD, (int) (height * FONT_SIZE)));
		setFittingFontSize(RESULT_RESTART + RESULT_MENU + "  ", g, width,
				height);
		metrics = g.getFontMetrics();

		g.setColor((neustart) ? Color.RED : Color.BLACK);
		str = RESULT_RESTART;
		g.drawString(str, x, _y + height);

		g.setColor((!neustart) ? Color.RED : Color.BLACK);
		str = RESULT_MENU;
		g.drawString(str, x + width - metrics.stringWidth(str), _y + height);
	}

	private void drawString(Graphics2D g, int x, int y, int width, int height,
			String str) {
		g.setFont(new Font(Font.SERIF, Font.BOLD, (int) (height * FONT_SIZE)));
		setFittingFontSize(str, g, width, height);
		FontMetrics metrics = g.getFontMetrics();
		g.drawString(str,
				(int) (x + width * 0.5 - metrics.stringWidth(str) * 0.5),
				y + metrics.getLeading() + metrics.getAscent());
	}

	private void setFittingFontSize(String s, Graphics2D g, int width,
			int height) {

		while (g.getFontMetrics().stringWidth(s) > width) {
			g.setFont(
					new Font(Font.SERIF, Font.BOLD, g.getFont().getSize() - 1));
		}
	}

	private void enter() {

		if (highscore) {
			Settings.set("username", getInput());
			JappyBird.getDAO().insertScore(new Score(getInput(), getPunkte()));
		}

		if (isNeustart()) {
			parent.start();
		} else {
			JappyBird.gotoMenu();
		}
	}

	@Override
	public Point getOrigin() {

		return new Point((int) (getWidth() * 0.5), (int) (getHeight() * 0.5));
	}

	public int getPunkte() {
		return punkte;
	}

	public void setPunkte(int punkte) {
		this.punkte = punkte;
	}

	public void reset() {
		punkte = 0;
		neustart = true;
		setInput("");
		stateOfBlink = 0;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_ENTER:
			enter();
			break;
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_RIGHT:
			neustart = !neustart;
			break;
		case KeyEvent.VK_BACK_SPACE:
			if (!input.isEmpty())
				input = input.substring(0, input.length() - 1);
			break;
		default:
			input(e);
			break;
		}
	}

	private void input(KeyEvent e) {
		if (input.length() < 50 && e.getKeyChar() != KeyEvent.CHAR_UNDEFINED
				&& e.getKeyCode() != KeyEvent.VK_ESCAPE) {
			input += e.getKeyChar();
		}
	}

	public String getInput() {
		return input.trim();
	}

	public void setInput(String input) {
		this.input = input;
	}

	public boolean isHighscore() {
		return highscore;
	}

	public void setHighscore(boolean highscore) {
		this.highscore = highscore;
	}

	public boolean isNeustart() {
		return neustart;
	}

}
