package game;

import entities.Locomotive;
import entities.Wagon;
import entities.character.Bandit;
import entities.character.Character;
import entities.character.Marshal;
import ui.GamePanel;

import java.util.ArrayList;

public class Board {

	public static final int NB_WAGONS = 4;
	public static int NB_PLAYERS = 4;
	public static GamePanel gamePanel;

	public static ArrayList<Wagon> wagons = new ArrayList<Wagon>();
	public static ArrayList<Card> cardPack = new ArrayList<Card>();
	public static ArrayList<Character> players = new ArrayList<Character>();

	public static void newGame() {
		wagons.clear();
		wagons.add(new Locomotive());
		players.clear();
		players.add(new Marshal(wagons.get(0), Wagon.floor.DOWNSTAIRS, "MARSHALL"));
		wagons.get(0).addPlayer(players.get(0));
		for (int i = 0; i < NB_WAGONS; i++)
			wagons.add(new Wagon());

		for (int i = 0; i < NB_PLAYERS; i++) {
			players.add(new Bandit(wagons.get(wagons.size() - 1), Wagon.floor.DOWNSTAIRS, "JOUEUR_" + i));
			wagons.get(wagons.size() - 1).addPlayer(players.get(players.size() - 1));
			//System.out.println(wagons.get(wagons.size() - 1).players.size());
		}

		cardPack.clear();

		//while (!detectEnd()) {
			//playTurn();
		//}
	}

	public static boolean onAction = false;

	public static void playTurn() {
		//onTurn = true;
		//chooseCards();
		/*for (Card card : cardPack) {
			card.getCharacter().action(card.getType());
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			players.get(0).action();
		}*/
		//cardPack.clear();
		//onTurn = false;
	}

	public synchronized static void action() {
		System.out.println(onAction + " " + cardPack.size());
		if (onAction)return;
		onAction = true;

		if (cardPack.size() == 0) {
			chooseCards();
			onAction = false;
			return;
		}

		cardPack.get(0).getCharacter().action(cardPack.get(0).getType());
		cardPack.remove(0);

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		players.get(0).action();
		onAction = false;
	}

	public static void chooseCards() {
		for (int i = 0; i < Board.NB_PLAYERS; i++) {
			gamePanel.chooseCard(i + 1);
		}
	}

	public static void addCardToPack(Card.TYPE type, int playerIndex) {
		//System.out.println(type.name() + " " + players.get(playerIndex).getName());
		cardPack.add(new Card(type, players.get(playerIndex)));
	}

	public static boolean detectEnd() {
		return false;
	}

	public static void restart() {
		newGame();
	}


}
