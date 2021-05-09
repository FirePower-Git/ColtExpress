package game;

import entities.character.Character;

public class Card {

	private TYPE type;

	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	private Character character;

	public Card(TYPE type, Character character) {
		this.type = type;
		this.character = character;
	}

	public TYPE getType() {
		return type;
	}

	public void setType(TYPE type) {
		this.type = type;
	}

	public static enum TYPE {
		MOVE_VERTICAL,
		MOVE_HORIZONTAL,
		COLLECT_LOOT,
		SHOOT_ON_BANDIT,
		NONE
	}

}
