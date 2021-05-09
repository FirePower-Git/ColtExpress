package entities.character;

import audio.Music;
import audio.PlayList;
import entities.Wagon;
import entities.collectible.Collectible;
import game.Board;
import game.Card;

import java.util.ArrayList;

public class Bandit extends Character {

	/**
	 * Constructor of Bandit Object
	 * @param wagon the wagon where is the character
	 * @param floor in the wagon or on the roof
	 */
	public Bandit(Wagon wagon, Wagon.floor floor) {
		super(wagon, floor);
	}

	/**
	 * Constructor of Bandit Object
	 * @param wagon the wagon where is the character
	 * @param floor in the wagon or on the roof
	 * @param name the name of the character
	 */
	public Bandit(Wagon wagon, Wagon.floor floor, String name) {
		super(wagon, floor, name);
	}

	/**
	 * Get the character type
	 * @return the type
	 */
	@Override
	public CharacterType getType() {
		return CharacterType.BANDIT;
	}

	/**
	 * Run actions of the character
	 * @param type the card type
	 */
	@Override
	public void action(Card.TYPE type) {
		// card to move vertical
		if (type.equals(Card.TYPE.MOVE_VERTICAL))
			moveVertical((this.floor == Wagon.floor.DOWNSTAIRS) ? Direction.HAUT : Direction.BAS);

		// card to move horizontal
		else if (type == Card.TYPE.MOVE_HORIZONTAL) {
			// search wagon
			for (int i = 0; i < Board.wagons.size(); i++) {

				// if it's the user wagon
				if (Board.wagons.get(i).equals(wagon)) {

					// if it's the locomotive the character can't move forward
					if (i == 0) {
						moveHorizontal(Direction.ARRIERE);
						return;
					}

					// if it's the last wagon the character can't move backward
					else if (i == Board.wagons.size() - 1) {
						moveHorizontal(Direction.AVANT);
						return;
					}
				}
			}

			// else ask for a direction
			Direction direction = Board.gamePanel.askDirection();
			moveHorizontal(direction);
		}

		// shoot on bandit
		else if (type == Card.TYPE.SHOOT_ON_BANDIT)shootOnBandit();

		// collect loot on th floor
		else if (type == Card.TYPE.COLLECT_LOOT)collectLoot();
	}

	/**
	 * Move the character up and down
	 * @param direction the direction to move
	 */
	public void moveVertical(Direction direction) {
		// if the character is going down he goes downstairs
		if (direction == Direction.BAS)this.floor = Wagon.floor.DOWNSTAIRS;

		// if the character is going up he goes upstairs
		else this.floor = Wagon.floor.UPSTAIRS;

		// Show it on the graphic console
		Board.gamePanel.addLog(this.getName() + " est allé " +
				((direction.equals(Direction.BAS)) ?
						"dans le wagon." :
						"sur le toit du wagon."));
	}

	/**
	 * Move the character left and right
	 * @param direction the direction to move
	 */
	public void moveHorizontal(Direction direction) {
		// search wagon
		for (int i = 0; i < Board.wagons.size(); i++) {

			// if it's the user wagon
			if (Board.wagons.get(i).equals(wagon)) {
				// if Direction.AVANT
				if (direction == Direction.AVANT) {
					Board.wagons.get(i - 1).addPlayer(this); // add character to the forward wagon
					this.wagon = Board.wagons.get(i - 1); // change character's wagon
				} else {
					Board.wagons.get(i + 1).addPlayer(this); // add character to the forward wagon
					this.wagon = Board.wagons.get(i + 1); //  change character's wagon
				}
				Board.wagons.get(i).removePlayer(this); // remove it from the current wagon
			}
		}

		// Show it on the graphic console
		Board.gamePanel.addLog(this.getName() + " est allé dans le wagon " + direction.name() + ".");
	}

	/**
	 * shoot an bandit
	 */
	public void shootOnBandit() {
		// if there is no bullets the character can't shoot
		if (bullets <= 0) {

			// Show it on the graphic console
			Board.gamePanel.addLog(this.getName() + " n'a plus de balles.");
			return;
		}

		// if the character is downstairs
		if (this.floor.equals(Wagon.floor.DOWNSTAIRS)) {

			// list of bandits in the current wagon
			ArrayList<Integer> playerIndexes = new ArrayList<Integer>();

			// search them
			for (int i = 0; i < wagon.players.size(); i++) {

				// if the character is downstairs, is not itself and is not the marshall
				if (wagon.players.get(i).floor.equals(this.floor) &&
						!this.wagon.players.get(i).equals(this) &&
						this.wagon.players.get(i).getType().equals(CharacterType.BANDIT)) {

					// add it to the list
					playerIndexes.add(i);
				}
			}

			// if there is nobody on the list
			if (playerIndexes.size() == 0) {

				// Show it on the graphic console
				Board.gamePanel.addLog(this.getName() + " n'a tiré sur personne.");
				return;
			}

			// else choose a bandit to shoot
			int index = (int) (Math.random() * (double)playerIndexes.size());
			wagon.players.get(playerIndexes.get(index)).shotByBandit();

			// remove one bullet
			bullets--;

			// Show it on the graphic console
			Board.gamePanel.addLog(this.getName() + " a tiré sur " + wagon.players.get(index).getName());

			// play shoot noise
			PlayList.run("gun_noise.wav", Music.SoundType.EFFECT);
		}

		// if the character is on the roof
		else {

			// list of bandits in the current wagon
			ArrayList<Integer> playerIndexes = new ArrayList<Integer>();

			// search them
			for (int i = 0; i < wagon.players.size(); i++) {
				// if the character is on the roof, is not itself and is not the marshall
				if (wagon.players.get(i).floor.equals(floor) &&
						!wagon.players.get(i).equals(this) &&
						this.wagon.players.get(i).getType().equals(CharacterType.BANDIT))

					// add it to the list
					playerIndexes.add(i);
			}

			// if their is somebody
			if (playerIndexes.size() != 0) {

				// choose a bandit to shoot
				int index = (int) (Math.random() * playerIndexes.size());
				wagon.players.get(index).shotByBandit();

				// remove one bullet
				bullets--;

				// Show it on the graphic console
				Board.gamePanel.addLog(this.getName() + " a tiré sur " + wagon.players.get(index).getName());

				// play shoot noise
				PlayList.run("gun_noise.wav", Music.SoundType.EFFECT);
				return;
			}

			// else choose a director to shoot
			Direction direction = null;

			for (int i = 0; i < Board.wagons.size(); i++) {
				if (Board.wagons.get(i).equals(wagon)) {

					// if the wagon is the locomotive shoot to the back
					if (i == 0)direction = Direction.ARRIERE;

					// if the wagon is the last wagon shoot forward
					else if (i == Board.wagons.size() - 1)direction = Direction.AVANT;
				}
			}

			// else ask to choose a direction
			if (direction == null)direction = Board.gamePanel.askDirection();

			// get wagon index
			int wagonIndex = 0;
			for (int i = 0; i < Board.wagons.size(); i++) {
				if (Board.wagons.get(i).equals(this.wagon))wagonIndex = i;
			}

			// the index of the wagon where the character shoot bandits
			int wagonIndex2 = -1;

			if (direction.equals(Direction.AVANT)) {

				// list all wagons before
				for (int i = wagonIndex - 1; i >= 0; i--) {

					// if the wagon is empty
					if (Board.wagons.get(i).players.size() == 0)continue;

					// list all players of the wagon
					for (int j = 0; j < Board.wagons.get(i).players.size(); j++) {

						// if there is bandit that can be shot
						if (Board.wagons.get(i).players.get(j).floor.equals(Wagon.floor.UPSTAIRS) &&
							Board.wagons.get(i).players.get(j).getType().equals(CharacterType.BANDIT)) {

							// set the wagon index
							wagonIndex2 = i;

							// end loops
							i = -1;
							j = Board.wagons.get(i).players.size() + 1;
						}
					}
				}
			} else {

				// list all wagons after
				for (int i = wagonIndex + 1; i < Board.wagons.size(); i++) {

					// if the wagon is empty
					if (Board.wagons.get(i).players.size() == 0)continue;

					// if there is bandit that can be shot
					for (int j = 0; j < Board.wagons.get(i).players.size(); j++) {
						if (Board.wagons.get(i).players.get(j).floor.equals(Wagon.floor.UPSTAIRS) &&
								Board.wagons.get(i).players.get(j).getType().equals(CharacterType.BANDIT)) {

							// set the wagon index
							wagonIndex2 = i;

							// end loops
							i = Board.wagons.size() + 1;
							j = Board.wagons.get(i).players.size() + 1;
						}
					}
				}
			}

			// if therer is nobody
			if (wagonIndex2 == -1) {

				// Show it on the graphic console
				Board.gamePanel.addLog(this.getName() + " n'a tiré sur personne.");
				return;
			}

			// list characters that can be shot
			for (int i = 0; i < Board.wagons.get(wagonIndex2).players.size(); i++) {
				if (Board.wagons.get(wagonIndex2).players.get(i).floor.equals(Wagon.floor.UPSTAIRS) &&
						Board.wagons.get(wagonIndex2).players.get(i).getType().equals(CharacterType.BANDIT))
					playerIndexes.add(i);
			}

			// if there is a character to shoot
			if (playerIndexes.size() != 0) {

				// choose a wagon
				int index = (int) (Math.random() * playerIndexes.size());
				Board.wagons.get(wagonIndex2).players.get(index).shotByBandit();

				// remove one bullet
				bullets--;

				// Show it on the graphic console
				Board.gamePanel.addLog(this.getName() + " a tiré sur " + wagon.players.get(index).getName());

				// play shoot noise
				PlayList.run("gun_noise.wav", Music.SoundType.EFFECT);
			} else {

				// Show it on the graphic console
				Board.gamePanel.addLog(this.getName() + " n'a tiré sur personne.");
			}
		}
	}

	/**
	 * When the bandit is shot by another bandit
	 */
	@Override
	public void shotByBandit() {

		// Show it on the graphic console
		Board.gamePanel.addLog(this.getName() + " s'est fait tiré dessus.");

		// if the character has some items on itself
		if (collectibles.size() != 0) {

			// Show it on the graphic console
			Board.gamePanel.addLog(this.getName() + " lache un butin par terre.");

			// choose a collectible to remove
			int index = (int) (Math.random() * this.collectibles.size());
			Collectible collectible = this.collectibles.get(index);
			wagon.addCollectible(collectible);
			this.collectibles.remove(index);
		}

		// move on the roof or inside the wagon
		moveVertical((this.floor == Wagon.floor.DOWNSTAIRS) ? Direction.HAUT : Direction.BAS);
	}

	/**
	 * Collect the loot in wagons
	 */
	public void collectLoot() {

		// if there is collectibles in the wagon and the character is inside thhe wagon
		if (wagon.collectibles.size() == 0 || this.floor.equals(Wagon.floor.UPSTAIRS)) {

			// Show it on the graphical consle
			Board.gamePanel.addLog(this.getName() + " n'a récupéré de buttin.");
			return;
		}

		// choose a collectible
		int index = (int) (Math.random() * wagon.collectibles.size());
		Collectible collectible = wagon.collectibles.get(index);
		this.collectibles.add(collectible);
		wagon.collectibles.remove(index);

		// Show it on the graphical console
		Board.gamePanel.addLog(this.getName() + " a récupéré un buttin d'une valeur de " + collectible.getValue() + "$.");
	}
}
