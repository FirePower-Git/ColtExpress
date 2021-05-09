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
			for (int i = 0; i < Board.wagons.size(); i++) {
				if (Board.wagons.get(i).equals(wagon)) {
					if (i == 0) {
						moveHorizontal(Direction.ARRIERE);
						return;
					}
					else if (i == Board.wagons.size() - 1) {
						moveHorizontal(Direction.AVANT);
						return;
					}
				}
			}

			Direction direction = Board.gamePanel.askDirection();
			moveHorizontal(direction);
		}
		else if (type == Card.TYPE.SHOOT_ON_BANDIT)shootOnBandit();
		else if (type == Card.TYPE.COLLECT_LOOT)collectLoot();
	}

	public void moveVertical(Direction direction) {
		if (direction == Direction.BAS)this.floor = Wagon.floor.DOWNSTAIRS;
		else this.floor = Wagon.floor.UPSTAIRS;

		Board.gamePanel.addLog(this.getName() + " est allé " +
				((direction.equals(Direction.BAS)) ?
						"dans le wagon." :
						"sur le toit du wagon."));
	}

	public void moveHorizontal(Direction direction) {
		for (int i = 0; i < Board.wagons.size(); i++) {
			if (Board.wagons.get(i).equals(wagon)) {
				if (direction == Direction.AVANT) {
					Board.wagons.get(i - 1).addPlayer(this);
					this.wagon = Board.wagons.get(i - 1);
				} else {
					Board.wagons.get(i + 1).addPlayer(this);
					this.wagon = Board.wagons.get(i + 1);
				}
				Board.wagons.get(i).removePlayer(this);
			}
		}

		Board.gamePanel.addLog(this.getName() + " est allé dans le wagon " + direction.name() + ".");
	}

	public void shootOnBandit() {
		if (bullets <= 0) {
			Board.gamePanel.addLog(this.getName() + " n'a plus de balles.");
			return;
		}

		if (this.floor.equals(Wagon.floor.DOWNSTAIRS)) {
			ArrayList<Integer> playerIndexes = new ArrayList<Integer>();
			for (int i = 0; i < wagon.players.size(); i++) {
				if (wagon.players.get(i).floor.equals(this.floor) &&
						!this.wagon.players.get(i).equals(this) &&
						this.wagon.players.get(i).getType().equals(CharacterType.BANDIT)) {
					playerIndexes.add(i);
					//System.out.println(i);
				}

			}

			if (playerIndexes.size() == 0) {
				Board.gamePanel.addLog(this.getName() + " n'a tiré sur personne.");
				return;
			}

			int index = (int) (Math.random() * (double)playerIndexes.size());
			wagon.players.get(playerIndexes.get(index)).shotByBandit();
			bullets--;

			Board.gamePanel.addLog(this.getName() + " a tiré sur " + wagon.players.get(index).getName());
			PlayList.run("gun_noise.wav", Music.SoundType.EFFECT);
		} else {
			ArrayList<Integer> playerIndexes = new ArrayList<Integer>();
			for (int i = 0; i < wagon.players.size(); i++) {
				if (wagon.players.get(i).floor.equals(floor) &&
						!wagon.players.get(i).equals(this) &&
						this.wagon.players.get(i).getType().equals(CharacterType.BANDIT))
					playerIndexes.add(i);
			}

			if (playerIndexes.size() != 0) {
				int index = (int) (Math.random() * playerIndexes.size());
				wagon.players.get(index).shotByBandit();
				bullets--;
				Board.gamePanel.addLog(this.getName() + " a tiré sur " + wagon.players.get(index).getName());
				PlayList.run("gun_noise.wav", Music.SoundType.EFFECT);
				return;
			}

			Direction direction = null;
			for (int i = 0; i < Board.wagons.size(); i++) {
				if (Board.wagons.get(i).equals(wagon)) {
					if (i == 0)direction = Direction.ARRIERE;
					else if (i == Board.wagons.size() - 1)direction = Direction.AVANT;
				}
			}

			if (direction == null)direction = Board.gamePanel.askDirection();

			int wagonIndex = 0;
			for (int i = 0; i < Board.wagons.size(); i++) {
				if (Board.wagons.get(i).equals(this.wagon))wagonIndex = i;
			}

			// TODO

			int wagonIndex2 = 0;
			if (direction.equals(Direction.AVANT)) {
				for (int i = wagonIndex - 1; i >= 0; i--) {
					for (int j = 0; j < Board.wagons.get(i).players.size(); j++) {
						if (Board.wagons.get(i).players.get(i).floor.equals(floor) &&
								!Board.wagons.get(i).players.get(i).equals(this) &&
								this.wagon.players.get(i).getType().equals(CharacterType.BANDIT)) {
							wagonIndex2 = i;
							playerIndexes.add(j);
						}
					}
				}
			} else {
				for (int i = wagonIndex + 1; i < Board.wagons.size(); i++) {
					for (int j = 0; j < Board.wagons.get(i).players.size(); j++) {
						if (Board.wagons.get(i).players.get(i).floor.equals(floor) &&
								!Board.wagons.get(i).players.get(i).equals(this) &&
								this.wagon.players.get(i).getType().equals(CharacterType.BANDIT))
							playerIndexes.add(i);
					}
				}
			}
			if (playerIndexes.size() != 0) {
				int index = (int) (Math.random() * playerIndexes.size());
				wagon.players.get(index).shotByBandit();
				bullets--;
				Board.gamePanel.addLog(this.getName() + " a tiré sur " + wagon.players.get(index).getName());
				PlayList.run("gun_noise.wav", Music.SoundType.EFFECT);
			} else {
				Board.gamePanel.addLog(this.getName() + " n'a tiré sur personne.");
			}
		}
	}

	@Override
	public void shotByBandit() {
		Board.gamePanel.addLog(this.getName() + " s'est fait tiré dessus.");
		if (collectibles.size() != 0) {
			Board.gamePanel.addLog(this.getName() + " lache un butin par terre.");
			int index = (int) (Math.random() * this.collectibles.size());
			Collectible collectible = this.collectibles.get(index);
			wagon.addCollectible(collectible);
			this.collectibles.remove(index);
		}

		moveVertical((this.floor == Wagon.floor.DOWNSTAIRS) ? Direction.HAUT : Direction.BAS);
	}

	public void collectLoot() {
		if (wagon.collectibles.size() == 0 || this.floor.equals(Wagon.floor.UPSTAIRS)) {
			Board.gamePanel.addLog(this.getName() + " n'a récupéré de buttin.");
			return;
		}

		int index = (int) (Math.random() * wagon.collectibles.size());
		Collectible collectible = wagon.collectibles.get(index);
		this.collectibles.add(collectible);
		wagon.collectibles.remove(index);

		Board.gamePanel.addLog(this.getName() + " a récupéré un buttin d'une valeur de " + collectible.getValue() + "$.");
	}
}
