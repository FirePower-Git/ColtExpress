package game;

import entities.Locomotive;
import entities.Wagon;
import entities.character.Bandit;
import entities.character.Character;
import entities.character.Marshal;
import ui.GamePanel;

import java.util.ArrayList;

public class Board {

	// number of wagons
	public static final int NB_WAGONS = 4;

	// number of players
	public static int NB_PLAYERS = 4;

	// the game panel
	public static GamePanel gamePanel;

	// list of wagons
	public static ArrayList<Wagon> wagons = new ArrayList<Wagon>();

	// list of cards in the card pack
	public static ArrayList<Card> cardPack = new ArrayList<Card>();

	// list of characters
	public static ArrayList<Character> players = new ArrayList<Character>();

	/**
	 * Create new game
	 */
	public static void newGame() {

		// clear arrays
		cardPack.clear();
		wagons.clear();
		players.clear();

		// add locomotive
		wagons.add(new Locomotive());

		// add marshal
		players.add(new Marshal(wagons.get(0), Wagon.floor.DOWNSTAIRS, "MARSHAL"));
		wagons.get(0).addPlayer(players.get(0));

		// add wagons
		for (int i = 0; i < NB_WAGONS; i++)
			wagons.add(new Wagon());

		// add players
		for (int i = 0; i < NB_PLAYERS; i++) {
			players.add(new Bandit(wagons.get(wagons.size() - 1), Wagon.floor.DOWNSTAIRS, "JOUEUR_" + i));
			wagons.get(wagons.size() - 1).addPlayer(players.get(players.size() - 1));
		}
	}

	// is click on the Action ! button
	public static boolean onAction = false;

	/**
	 * Compute actions of characters
	 */
	public synchronized static void action() {
		// if the action is not ended
		if (onAction)return;

		// action start
		onAction = true;

		// if there is no card
		if (cardPack.size() == 0) {

			//ask for cards
			chooseCards();

			// end action
			onAction = false;
			return;
		}

		// compute action and remove used card
		cardPack.get(0).getCharacter().action(cardPack.get(0).getType());
		cardPack.remove(0);

		// delay
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// move the marshal
		players.get(0).action();

		// end action
		onAction = false;
	}

	/**
	 * Players choose cards
	 */
	public static void chooseCards() {
		for (int i = 0; i < Board.NB_PLAYERS * gamePanel.NB_ACTIONS; i++)
			cardPack.add(null);

		for (int i = 0; i < Board.NB_PLAYERS; i++) {
			gamePanel.chooseCard(i + 1);
		}
	}

	/**
	 * Add a card to the card pack
	 * @param type the type of card
	 * @param playerIndex the player index
	 */
	public static void addCardToPack(Card.TYPE type, int playerIndex) {
		for (int i = 0; i < gamePanel.NB_ACTIONS; i++)
			if (cardPack.get(i * NB_PLAYERS + playerIndex - 1) == null) {
				cardPack.set(i * NB_PLAYERS + playerIndex - 1, new Card(type, players.get(playerIndex)));
				break;
			}
	}

	/**
	 * Restart the game
	 */
	public static void restart() {
		newGame();
	}

}
