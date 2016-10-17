package com.github.brunothg.jappybird.object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;

import com.github.brunothg.game.engine.d2.commons.Point;
import com.github.brunothg.game.engine.d2.object.SceneObject;
import com.github.brunothg.game.engine.image.InternalImage;
import com.github.brunothg.game.engine.time.TimeUtils;

public class Heli extends SceneObject {

	private double ausrueckung;
	private double speed;

	private double umdrehung;

	public Heli() {
		this.ausrueckung = 0;
		this.speed = 0;
		this.umdrehung = 0;
	}

	@Override
	public void paint(Graphics2D g, long elapsed) {
		try {
			Image img = getHeliImage();
			g.drawImage(img, 0, 0, getWidth(), getHeight(), 0, 0,
					img.getWidth(null), img.getHeight(null), null);
		} catch (IOException e) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());
		}

		umdrehung += TimeUtils.Seconds(elapsed) * 8.5;
		if (umdrehung > 2) {
			umdrehung = umdrehung % 2;
		}
	}

	private Image getHeliImage() throws IOException {
		return InternalImage.fullLoad(
				(umdrehung >= 0 && umdrehung <= 1) ? "heli.png" : "heli2.png");
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
