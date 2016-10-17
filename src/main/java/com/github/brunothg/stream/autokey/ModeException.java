package com.github.brunothg.stream.autokey;

/**
 * Wird geschmissen, wenn versucht wird den AutoKey im falschen Modus zu
 * benutzen.
 * 
 * @author Marvin Bruns
 * 
 */
public class ModeException extends RuntimeException {

	private static final long serialVersionUID = 7260657493664173260L;

	public ModeException(String msg) {
		super(msg);
	}
}
