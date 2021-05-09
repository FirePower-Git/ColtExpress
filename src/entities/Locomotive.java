package entities;

import entities.collectible.MoneyBag;
import io.Images;
import ui.Util;

import java.awt.image.BufferedImage;

public class Locomotive extends Wagon {

	private static BufferedImage image;

	public Locomotive() {
		super();
		this.collectibles.clear();
		this.collectibles.add(new MoneyBag(1000));
	}

	public TYPE getType() {
		return TYPE.LOCOMOTIVE;
	}

	public static void init() {
		image = Images.getImage("locomotive.png", (int) (0.5 * Util.height), (int) (0.3 * Util.height));
	}

	@Override
	public BufferedImage getImage() {
		return image;
	}

}
