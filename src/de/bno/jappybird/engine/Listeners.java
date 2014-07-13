package de.bno.jappybird.engine;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Listeners {

	private KeyListener kl;
	private MouseListener ml;
	private MouseMotionListener mml;

	public Listeners(KeyListener kl, MouseListener ml, MouseMotionListener mml) {
		this.kl = kl;
		this.ml = ml;
		this.mml = mml;
	}

	public Listeners() {
		this(null, null, null);
	}

	public KeyListener getKeyListener() {
		return kl;
	}

	public MouseListener getMouseListener() {
		return ml;
	}

	public MouseMotionListener getMouseMotionListener() {
		return mml;
	}

}
