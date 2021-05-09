package io;

import ui.Util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Images {

	/**
	 * load an image inside jar file
	 * @param s name of the file
	 * @return an image
	 */
	public static BufferedImage loadImage(String s) {

		// libs/images/ is the location of images
		URL url = Images.class.getResource("/libs/images/" + s);
		try {
			return ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedImage getImage(String s, int width, int height) {
		BufferedImage res = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
		BufferedImage temp = Images.loadImage(s);
		Graphics2D g = res.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.drawImage(temp, 0, 0, width, height, null);
		g.dispose();

		return res;
	}

}
