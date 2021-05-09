package entities.character;

import entities.Wagon;
import entities.collectible.Collectible;
import game.Card;

import java.util.ArrayList;

public class Character {

	// the floor of the wagon
	protected Wagon.floor floor;

	// items collected by the character
	protected ArrayList<Collectible> collectibles = new ArrayList<Collectible>();

	// the wagon of the character
	protected Wagon wagon;

	// the name of the character
	protected String name;

	// the number of bullets in the gun
	protected int bullets = 6;

	/**
	 * Constructor of the character object
	 * @param wagon the wagon of the character
	 * @param floor the current floor
	 */
	public Character(Wagon wagon, Wagon.floor floor) {
		this.wagon = wagon;
		this.floor = floor;
	}

	/**
	 * Constructor of the character object
	 * @param wagon the wagon of the character
	 * @param floor the current floor
	 * @param name the name of the character
	 */
	public Character(Wagon wagon, Wagon.floor floor, String name) {
		this(wagon, floor);
		this.name = name;
	}

	/**
	 * Get the number of bullets
	 * @return the number of bullets
	 */
	public int getBullets() {
		return bullets;
	}

	/**
	 * Set the number of bullets
	 * @param bullets
	 */
	public void setBullets(int bullets) {
		this.bullets = bullets;
	}

	/**
	 * Add a collectible to the character
	 * @param collectible the collectible
	 */
	public void addCollectible(Collectible collectible) {
		this.collectibles.add(collectible);
	}

	/**
	 * remove a collectible to the character
	 * @param collectible the collectible
	 */
	public void removeCollectible(Collectible collectible) {
		this.collectibles.remove(collectible);
	}

	/**
	 * remove a collectible to the character
	 * @param index the index of the collectible
	 */
	public void removeCollectible(int index) {
		this.collectibles.remove(index);
	}

	/**
	 * Get the list of collectibles
	 * @return the list
	 */
	public ArrayList<Collectible> getCollectibles() {
		return this.collectibles;
	}

	/**
	 * get a collectible in the list
	 * @param index the index of the collectible
	 * @return the collectible
	 */
	public Collectible getCollectible(int index) {
		if (index < 0 || index >= this.collectibles.size())
			return null;
		return this.collectibles.get(index);
	}

	/**
	 * Clear the array of collectibles
	 */
	public void clearCollectibles() {
		this.collectibles.clear();
	}

	/**
	 * Get the name of the character
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the collectible
	 * @param name the name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * get the floor of the character
	 * @return the floor
	 */
	public Wagon.floor getFloor() {
		return floor;
	}

	/**
	 * Set the floor of the character
	 * @param floor the floor
	 */
	public void setFloor(Wagon.floor floor) {
		this.floor = floor;
	}

	/**
	 * Compute actions of the character
	 */
	public void action() {}

	/**
	 * Compute actions of the character
	 * @param type the type of action
	 */
	public void action(Card.TYPE type) {}

	/**
	 * If the character is shot
	 */
	public void shotByBandit() {}

	/**
	 * The type of character
	 */
	public enum CharacterType {
		NONE, BANDIT, MARSHAL;
	}

	/**
	 * Get the type of the character
	 * @return the type
	 */
	public CharacterType getType() {
		return CharacterType.NONE;
	}

	/**
	 * Directions to move the character
	 */
	public enum Direction {
		AVANT,
		ARRIERE,
		HAUT,
		BAS
	}
}
