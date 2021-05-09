package entities.character;

import entities.Wagon;
import game.Board;
import game.Card;

import java.util.ArrayList;

public class Marshal extends Character {

	// Probability of the marshall to move
	private final double NERVOSITE_MARSHALL = 0.3D;

	/**
	 * Constructor of the Marshal object
	 * @param wagon the wagon of the user
	 * @param floor the current floor
	 */
	public Marshal(Wagon wagon, Wagon.floor floor) {
		super(wagon, floor);
	}

	/**
	 * Constructor of the Marshal object
	 * @param wagon the wagon of the user
	 * @param floor the current floor
	 * @param name the name of the character
	 */
	public Marshal(Wagon wagon, Wagon.floor floor, String name) {
		super(wagon, floor, name);
	}

	/**
	 * Get the type of the character
	 * @return the type
	 */
	@Override
	public CharacterType getType() {
		return CharacterType.MARSHAL;
	}

	/**
	 * Compute actions of the character
	 */
	@Override
	public void action() {
		// if the marshal move
		if (Math.random() < NERVOSITE_MARSHALL) {
			Direction direction = (Math.random() > 0.5D) ? Direction.AVANT : Direction.ARRIERE;
			move(direction);
		}

		// look if he can shoot on bandit
		shootOnBandit();
	}

	/**
	 * Move the character left and right
	 * @param direction the direction to move
	 */
	public void move(Direction direction) {

		// search wagon
		for (int i = 0; i < Board.wagons.size(); i++) {

			// if it's the user wagon
			if (Board.wagons.get(i).equals(wagon)) {

				// if Direction.AVANT
				if (direction.equals(Direction.AVANT)) {

					// if it's the locomotive
					if (i == 0) {

						// move back
						move(Direction.ARRIERE);
						return;
					}

					// add player to new wagon
					Board.wagons.get(i - 1).addPlayer(this);

					// change user wagon
					this.wagon = Board.wagons.get(i - 1);
				} else {

					// if it's the last wagon
					if (i == Board.wagons.size() - 1) {

						// move forward
						move(Direction.AVANT);
						return;
					}

					// add player to new wagon
					Board.wagons.get(i + 1).addPlayer(this);

					// change user wagon
					this.wagon = Board.wagons.get(i + 1);
				}

				// Remove player from current wagon
				Board.wagons.get(i).removePlayer(this);
			}
		}

		// Show it on the graphic console
		Board.gamePanel.addLog("Le marchall est allé dans le wagon " + direction.name() + ".");
	}

	/**
	 * shot every bandits in the wagon
	 */
	public void shootOnBandit() {

		// if there is no bandits in the wagon
		if (wagon.players.size() == 0)return;

		// list of bandits to shot
		ArrayList<Integer> playerIndexes = new ArrayList<Integer>();
		for (int i = 0; i < wagon.players.size(); i++) {

			// if bandits are in the wagon and are not itself
			if (wagon.players.get(i).floor.equals(floor) && !wagon.players.get(i).equals(this))

				// add it to the list
				playerIndexes.add(i);
		}

		// shoot every bandit
		for (int index : playerIndexes) {
			wagon.players.get(index).shotByBandit();

			// Show it on the graphic console
			Board.gamePanel.addLog("Le marshall a tiré sur " + wagon.players.get(index).getName() + ".");
		}
	}
}
