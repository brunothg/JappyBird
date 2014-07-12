package de.bno.jappybird.engine;

import java.awt.image.BufferedImage;

/**
 * 
 * Leeres durchsichtiges BufferedImage
 * 
 * @author Marvin Bruns
 *
 */
public class EmptyImage extends BufferedImage {

	public EmptyImage() {
		super(1, 1, BufferedImage.TYPE_INT_ARGB);
		setRGB(0, 0, 0);
	}

}
