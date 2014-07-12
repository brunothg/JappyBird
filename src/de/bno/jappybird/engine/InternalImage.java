package de.bno.jappybird.engine;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class InternalImage {

	static Map<String, Image> loadedImages;
	static String root = "";

	static {
		loadedImages = new HashMap<String, Image>();
	}

	public static Image load(String s) {

		try {
			return fullLoad(s);
		} catch (IOException e) {
		}

		return null;
	}

	public static Image reloadFull(String s) throws IOException {
		Image ret;

		ret = ImageIO
				.read(InternalImage.class.getResource(getRootFolder() + s));

		synchronized (loadedImages) {
			loadedImages.put(s, ret);
		}

		return ret;

	}

	public static String getRootFolder() {
		return root;
	}

	public static void setRootFolder(String s) {
		root = s;
	}

	public static Image fullLoad(String s) throws IOException {

		Image ret;

		synchronized (loadedImages) {
			ret = loadedImages.get(s);
		}

		if (ret != null) {
			return ret;
		}

		ret = ImageIO
				.read(InternalImage.class.getResource(getRootFolder() + s));

		synchronized (loadedImages) {
			loadedImages.put(s, ret);
		}

		return ret;
	}

	public static Image load(String s, int width, int height) {
		Image img;

		synchronized (loadedImages) {
			img = loadedImages.get(s);
		}

		if (img == null) {
			try {
				img = fullLoad(s);
			} catch (IOException e) {
				return load(s);
			}
		}

		BufferedImage ret = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);

		Graphics graphics = ret.getGraphics();

		ImageObserver observer = null;

		graphics.drawImage(img, 0, 0, width, height, 0, 0,
				img.getWidth(observer), img.getHeight(observer), observer);

		return ret;
	}
}
