package de.bno.jappybird.engine;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.EventListener;
import java.util.LinkedList;

public class Listeners extends LinkedList<EventListener> {

	private static final long serialVersionUID = 1L;

	public Listeners(EventListener... listeners) {

		this();

		for (EventListener evtl : listeners) {
			add(evtl);
		}
	}

	public Listeners() {

		super();
	}

	public EventListener getListener(Class<? extends EventListener> type) {

		return getListener(type, this);
	}

	public static <T extends EventListener> T getListener(Class<T> type,
			Listeners ls) {

		for (Object o : ls) {
			if (type.isInstance(o)) {

				return type.cast(o);
			}
		}

		return null;
	}

	public KeyListener getKeyListener() {

		return getListener(KeyListener.class, this);
	}

	public MouseListener getMouseListener() {

		return getListener(MouseListener.class, this);
	}

	public MouseMotionListener getMouseMotionListener() {

		return getListener(MouseMotionListener.class, this);
	}

}
