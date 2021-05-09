package entities;

import entities.collectible.MoneyBag;
import io.Images;
import ui.Util;

import java.awt.image.BufferedImage;

public class Locomotive extends Wagon {

	// the image of the locomotive
	private static BufferedImage image;

	/**
	 * Constructor of the locomotive object
	 */
	public Locomotive() {
		super();
		this.collectibles.clear();
		this.collectibles.add(new MoneyBag(1000));
	}

	/**
	 * Get the type of the wagon
	 * @return the type
	 */
	public TYPE getType() {
		return TYPE.LOCOMOTIVE;
	}

	/**
	 * Init static attributes of the object
	 */
	public static void init() {
		image = Images.getImage("locomotive.png", (int) (0.5 * Util.height), (int) (0.3 * Util.height));
	}

	/**
	 * get the object's image
	 * @return the image
	 */
	@Override
	public BufferedImage getImage() {
		return image;
	}

}
