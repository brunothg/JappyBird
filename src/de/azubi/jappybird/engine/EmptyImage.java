package de.azubi.jappybird.engine;

import java.awt.image.BufferedImage;

public class EmptyImage extends BufferedImage
{

	public EmptyImage()
	{
		super(1, 1, BufferedImage.TYPE_INT_ARGB);
		setRGB(0, 0, 0);
	}

}
