package de.bno.jappybird;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import de.bno.jappybird.engine.InternalImage;
import de.bno.jappybird.engine.Point;
import de.bno.jappybird.engine.SceneObject;
import de.bno.jappybird.engine.Time;

public class Heli extends SceneObject {

	private Time time;
	private double ausrueckung;
	private double speed;

	private double umdrehung;

	public Heli(Time time) {
		this.time = time;
		this.ausrueckung = 0;
		this.speed = 0;
		this.umdrehung = 0;
	}

	@Override
	public void paint(Graphics g, boolean onScreen) {
		try {
			Image img = getHeliImage();
			g.drawImage(img, 0, 0, getWidth(), getHeight(), 0, 0,
					img.getWidth(null), img.getHeight(null), null);
		} catch (IOException e) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());
		}

		if (onScreen) {

			umdrehung += Time.Seconds(time.elapsedTime()) * 8.5;
			if (umdrehung > 2) {
				umdrehung = umdrehung % 2;
			}
		}
	}

	private Image getHeliImage() throws IOException {
		return InternalImage
				.fullLoad((umdrehung >= 0 && umdrehung <= 1) ? "heli.png"
						: "heli2.png");
	}

	@Override
	public Point getOrigin() {
		return new Point(getWidth() / 2, getHeight());
	}

	public double getAusrueckung() {
		return ausrueckung;
	}

	public void setAusrueckung(double ausrueckung) {
		this.ausrueckung = ausrueckung;
	}

	@Override
	public void setPosition(Point p) {
		super.setPosition(new Point(p.getX(), p.getY()));
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

}
