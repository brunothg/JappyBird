package de.bno.jappybird;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import de.bno.jappybird.dao.Score;
import de.bno.jappybird.engine.Point;
import de.bno.jappybird.engine.Scene;
import de.bno.jappybird.engine.SceneObject;
import de.bno.jappybird.engine.Time;

public class Result extends SceneObject implements KeyListener {

	private int punkte;

	private String input;

	private boolean highscore;

	private long stateOfBlink;

	private Time time;

	private boolean neustart;

	private Scene parent;

	public Result(int punkte, Scene parent) {

		this.parent = parent;
		setInput("");
		this.stateOfBlink = 0;
		this.setPunkte(punkte);
		this.neustart = true;
		this.time = new Time();
	}

	@Override
	protected void paint(Graphics2D g, boolean onScreen) {

		time.update();
		stateOfBlink += time.elapsedTime();
		stateOfBlink = stateOfBlink % (Time.NANOSECONDS_PER_SECOND);

		g.setColor(new Color(0, 0, 0, 150));

		int arc = (int) (getWidth() * 0.1);
		g.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

		g.setColor(new Color(200, 200, 200, 200));

		g.fillRoundRect((int) (getWidth() * 0.01), (int) (getHeight() * 0.01),
				(int) (getWidth() * 0.98), (int) (getHeight() * 0.98), arc, arc);

		int x = (int) (getWidth() * 0.05);
		int y = (int) (getHeight() * 0.05);
		int _y = y;
		int width = (int) (getWidth() * 0.90);
		int height = (int) (getHeight() * 0.90);

		g.setColor(Color.BLACK);

		g.setFont(new Font(Font.SERIF, Font.BOLD, (int) (height * 0.1)));
		FontMetrics metrics = g.getFontMetrics();

		String str = "!!! CRASH !!!";
		g.drawString(str,
				(int) (x + width * 0.5 - metrics.stringWidth(str) * 0.5), y
						+ metrics.getLeading() + metrics.getAscent());
		y += 2 * metrics.getHeight();

		str = getPunkte() + " Punkt" + ((punkte != 1) ? "e" : "");
		g.drawString(str,
				(int) (x + width * 0.5 - metrics.stringWidth(str) * 0.5), y
						+ metrics.getLeading() + metrics.getAscent());
		y += metrics.getHeight();

		if (highscore) {
			str = "!--- NEW HIGHSCORE ---!";
			g.drawString(str,
					(int) (x + width * 0.5 - metrics.stringWidth(str) * 0.5), y
							+ metrics.getLeading() + metrics.getAscent());
			y += metrics.getHeight();

			str = "Name: "
					+ input
					+ ((stateOfBlink > (Time.NANOSECONDS_PER_SECOND * 0.5)) ? "_"
							: "");
			g.drawString(str,
					(int) (x + width * 0.5 - metrics.stringWidth(str) * 0.5), y
							+ metrics.getLeading() + metrics.getAscent());
			y += metrics.getHeight();
		}

		g.setColor((neustart) ? Color.RED : Color.BLACK);
		str = "Neustart";
		g.drawString(str, x, _y + height);

		g.setColor((!neustart) ? Color.RED : Color.BLACK);
		str = "Menü";
		g.drawString(str, x + width - metrics.stringWidth(str), _y + height);
	}

	private void enter() {

		if (highscore) {
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
		time.reset();
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
			if (input.length() < 50
					&& e.getKeyChar() != KeyEvent.CHAR_UNDEFINED)
				input += e.getKeyChar();
			break;
		}
	}

	public String getInput() {
		return input;
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
