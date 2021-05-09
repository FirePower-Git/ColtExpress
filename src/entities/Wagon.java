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

	private static BufferedImage image;
	public ArrayList<Collectible> collectibles = new ArrayList<Collectible>();
	public ArrayList<Character> players = new ArrayList<Character>();

	public Wagon() {
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

	public void addCollectible(Collectible collectible) {
		collectibles.add(collectible);
	}

	public void removeCollectible(Collectible collectible) {
		collectibles.remove(collectible);
	}

	public void addPlayer(Character player) {
		players.add(player);
	}

	public void removePlayer(Character player) {
		players.remove(player);
	}

	public enum floor {
		DOWNSTAIRS,
		UPSTAIRS
	}

	public enum TYPE {
		LOCOMOTIVE,
		WAGON
	}

	public TYPE getType() {
		return TYPE.WAGON;
	}

	public static void init() {
		image = Images.getImage("wagon.png", (int) ( 0.5 * 0.612 * Util.height), (int) (0.5 * 0.491 * Util.height));
	}

	public BufferedImage getImage() {
		return image;
	}

}
