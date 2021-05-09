package entities;

import entities.character.Character;
import entities.collectible.Collectible;
import entities.collectible.Gem;
import entities.collectible.MoneyBag;
import io.Images;
import ui.Util;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Wagon {

	// the image of the locomotive
	private static BufferedImage image;

	// collectibles of the wagon
	public ArrayList<Collectible> collectibles = new ArrayList<Collectible>();

	// players inside the wagon
	public ArrayList<Character> players = new ArrayList<Character>();

	/**
	 * Constructor of the wagon object
	 */
	public Wagon() {

		// generate collectibles
		if (Math.random() > 0.4D) {
			collectibles.add(new Gem());
			collectibles.add(new MoneyBag());
			collectibles.add(new MoneyBag());
		} else {
			collectibles.add(new MoneyBag());
			collectibles.add(new MoneyBag());
			collectibles.add(new MoneyBag());
			collectibles.add(new MoneyBag());
		}
	}

	/**
	 * Add a collectible to the wagon
	 * @param collectible the collectible
	 */
	public void addCollectible(Collectible collectible) {
		collectibles.add(collectible);
	}

	/**
	 * remove a collectible from the wagon
	 * @param collectible the collectible
	 */
	public void removeCollectible(Collectible collectible) {
		collectibles.remove(collectible);
	}

	/**
	 * Add a character to the wagon
	 * @param character the character
	 */
	public void addPlayer(Character character) {
		players.add(character);
	}

	/**
	 * remove a character of the wagon
	 * @param character the character
	 */
	public void removePlayer(Character character) {
		players.remove(character);
	}

	/**
	 * floors of the wagon
	 */
	public enum floor {
		DOWNSTAIRS,
		UPSTAIRS
	}

	/**
	 * type of wagon
	 */
	public enum TYPE {
		LOCOMOTIVE,
		WAGON
	}

	/**
	 * Get the type of wagon
	 * @return the type
	 */
	public TYPE getType() {
		return TYPE.WAGON;
	}

	/**
	 * Init static attributes of the object
	 */
	public static void init() {
		image = Images.getImage("wagon.png", (int) ( 0.5 * 0.612 * Util.height), (int) (0.5 * 0.491 * Util.height));
	}

	/**
	 * get the object's image
	 * @return the image
	 */
	public BufferedImage getImage() {
		return image;
	}

}
