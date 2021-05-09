package entities.character;

import entities.Wagon;
import entities.collectible.Collectible;
import game.Card;

import java.util.ArrayList;

public class Character {

	protected Wagon.floor floor;
	protected ArrayList<Collectible> collectibles = new ArrayList<Collectible>();
	protected Wagon wagon;
	protected String name;

	public Character(Wagon wagon, Wagon.floor floor) {
		this.wagon = wagon;
		this.floor = floor;
	}

	public Character(Wagon wagon, Wagon.floor floor, String name) {
		this(wagon, floor);
		this.name = name;
	}

	public int getBullets() {
		return bullets;
	}

	public void setBullets(int bullets) {
		this.bullets = bullets;
	}

	protected int bullets = 6;

	public void addCollectible(Collectible collectible) {
		this.collectibles.add(collectible);
	}

	public void removeCollectible(Collectible collectible) {
		this.collectibles.remove(collectible);
	}

	public void removeCollectible(int index) {
		this.collectibles.remove(index);
	}

	public ArrayList<Collectible> getCollectibles() {
		return this.collectibles;
	}

	public Collectible getCollectibles(int index) {
		return this.collectibles.get(index);
	}

	public void clearCollectibles() {
		this.collectibles.clear();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Wagon.floor getFloor() {
		return floor;
	}

	public void setFloor(Wagon.floor floor) {
		this.floor = floor;
	}

	public void action() {}

	public void action(Card.TYPE type) {}

	public void shotByBandit() {}

	public enum CharacterType {
		NONE, BANDIT, MARSHAL;
	}

	public CharacterType getType() {
		return CharacterType.NONE;
	}

	public enum SHOOT_TYPE {
		SHOOT_LEFT,
		SHOOT_RIGHT
	}

	public enum Direction {
		AVANT,
		ARRIERE,
		HAUT,
		BAS
	}
}
