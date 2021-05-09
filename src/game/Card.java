package game;

import entities.character.Character;

public class Card {

	// the type of the card
	private TYPE type;

	// the character associated to the card
	private Character character;

	/**
	 * Constructor of the Card object
	 * @param type the type of the card
	 * @param character the character
	 */
	public Card(TYPE type, Character character) {
		this.type = type;
		this.character = character;
	}

	/**
	 * Get the type of the card
	 * @return the type
	 */
	public TYPE getType() {
		return type;
	}

	/**
	 * Set the type of the card
	 * @param type the type
	 */
	public void setType(TYPE type) {
		this.type = type;
	}

	/**
	 * Get the character associated to the card
	 * @return the character
	 */
	public Character getCharacter() {
		return character;
	}

	/**
	 * Set the character associated to the card
	 * @param character the character
	 */
	public void setCharacter(Character character) {
		this.character = character;
	}

	/**
	 * card types
	 */
	public static enum TYPE {
		MOVE_VERTICAL,
		MOVE_HORIZONTAL,
		COLLECT_LOOT,
		SHOOT_ON_BANDIT,
		NONE
	}

}
