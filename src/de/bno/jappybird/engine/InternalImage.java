package de.bno.jappybird.engine;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

/**
 * Klasse um Bilddateien aus dem Jar zu laden. Einmal geladene Bilder werden Im
 * Speicher gehalten um für spätere Zugriffe direkt verfügbar zu sein.
 * 
 * @author Marvin Bruns
 *
 */
public class InternalImage {

	static Map<String, Image> loadedImages;
	static String root = "";

	static {
		loadedImages = new HashMap<String, Image>();
	}

	/**
	 * @see #fullLoad(String)
	 * @param s
	 * @return Image or null if any Exception is thrown
	 */
	public static Image load(String s) {

		try {
			return fullLoad(s);
		} catch (IOException e) {
		}

		return null;
	}

	/**
	 * Reloads the images
	 * 
	 * @param s
	 *            Path to image relative to root folder
	 * @return The Image
	 * @throws IOException
	 *             if an error occurs during reading
	 */
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
		if (s == null) {
			root = "";
			return;
		}

		root = s;

		if (!root.isEmpty() && root.endsWith("/")) {
			root += "/";
		}
	}

	/**
	 * Load an image from given path relative to root folder. If it has been
	 * loaded before a reference is returned.
	 * 
	 * @param s
	 *            Path to image relative to root folder
	 * @return The image
	 * @throws IOException
	 *             if an error occurs during reading
	 */
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

	/**
	 * Tries to {@link #fullLoad(String)} the image and resize it. If the
	 * loading process fails this method returns the same as
	 * {@link #load(String)} and may be null. <br>
	 * If the resizing succeeds a new instance is returned. Otherwise the return
	 * value is a reference.
	 * 
	 * @param s
	 *            Path to image relative to root folder
	 * @param width
	 *            Desired width of Image
	 * @param height
	 *            Desired height of Image
	 * @return null or the resized Image.
	 */
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

	public static void clearMemory() {
		loadedImages.clear();
	}

	public static void removeImage(String img) {
		loadedImages.remove(img);
	}
}
