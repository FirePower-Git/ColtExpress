package entities.character;

import entities.Wagon;
import game.Board;
import game.Card;

import java.util.ArrayList;

public class Marshal extends Character {

	public Marshal(Wagon wagon, Wagon.floor floor) {
		super(wagon, floor);
	}

	public Marshal(Wagon wagon, Wagon.floor floor, String name) {
		super(wagon, floor, name);
	}

	@Override
	public CharacterType getType() {
		return CharacterType.MARSHAL;
	}

	@Override
	public void action() {
		if (Math.random() < 0.3D) {
			Direction direction = (Math.random() > 0.5D) ? Direction.AVANT : Direction.ARRIERE;
			move(direction);
		}
		shootOnBandit();
	}

	public void move(Direction direction) {
		for (int i = 0; i < Board.wagons.size(); i++) {
			if (Board.wagons.get(i).equals(wagon)) {
				if (direction.equals(Direction.AVANT)) {
					if (i == 0) {
						move(Direction.ARRIERE);
						return;
					}
					Board.wagons.get(i - 1).addPlayer(this);
					this.wagon = Board.wagons.get(i - 1);
				} else {
					if (i == Board.wagons.size() - 1) {
						move(Direction.AVANT);
						return;
					}
					Board.wagons.get(i + 1).addPlayer(this);
					this.wagon = Board.wagons.get(i + 1);
				}
				Board.wagons.get(i).removePlayer(this);
			}
		}

		Board.gamePanel.addLog("Le marchall est allé dans le wagon " + direction.name() + ".");
	}

	public void shootOnBandit() {
		if (wagon.players.size() == 0)return;

		ArrayList<Integer> playerIndexes = new ArrayList<Integer>();
		for (int i = 0; i < wagon.players.size(); i++) {
			if (wagon.players.get(i).floor.equals(floor) && !wagon.players.get(i).equals(this))
				playerIndexes.add(i);
		}

		for (int index : playerIndexes) {
			wagon.players.get(index).shotByBandit();
			Board.gamePanel.addLog("Le marshall a tiré sur " + wagon.players.get(index).getName() + ".");
		}
	}
}
