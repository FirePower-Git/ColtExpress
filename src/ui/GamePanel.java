package ui;

import audio.Music;
import audio.PlayList;
import entities.Wagon;
import entities.character.Bandit;
import entities.character.Character;
import entities.collectible.Collectible;
import game.Board;
import game.Card;
import io.Images;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GamePanel extends JPanel {

	private BufferedImage background;
	private BufferedImage locomotive;
	//private BufferedImage card;
	private BufferedImage wagon;
	private BufferedImage moneyBag;
	private BufferedImage gem;
	private BufferedImage[] players = new BufferedImage[Board.NB_PLAYERS + 1];
	private BufferedImage[] cards = new BufferedImage[4];
	private BufferedImage arrowLeft, arrowRight;
	private Timer timer = new Timer();
	private ArrayList<String> logs = new ArrayList<String>();

	private boolean onAction = false;

	public GamePanel(LayeredPanel layeredPanel) {
		this.setBounds(0, 0, Util.width, Util.height);
		//this.setBackground(new Color(0, 0, 0));
		this.setLayout(null);
		this.setVisible(true);

		background = Images.getImage("game_background.png", Util.width, Util.height);
		locomotive = Images.getImage("locomotive.png", (int) (0.5 * Util.height), (int) (0.3 * Util.height));
		//card = Images.getImage("card.png", (int) (0.7 * 0.354 * getHeight()), (int) (0.7 * 0.498 * getHeight()));
		//wagon = Images.getImage("wagon.png", (int) (0.5 * 0.612 * getHeight()), (int) (0.5 * 0.491 * getHeight()));
		moneyBag = Images.getImage("money_bag.png", (int) (0.05 * 0.62 * getHeight()), (int) (0.05 * getHeight()));
		gem = Images.getImage("gem.png", (int) (0.05 * getHeight()), (int) (0.05 * getHeight()));
		for (int i = 0; i < Board.NB_PLAYERS; i++)
			players[i + 1] = Images.getImage("player_" + ((i % 4) + 1) + ".png", (int) (0.1 * getHeight()), (int) (0.1 * getHeight()));
		players[0] = Images.getImage("marshall.png", (int) (0.1 * getHeight()), (int) (0.1 * getHeight()));

		cards = new BufferedImage[]{
				Images.getImage("card_vertical.png", (int) (0.7 * 0.354 * getHeight()), (int) (0.7 * 0.498 * getHeight())),
				Images.getImage("card_horizontal.png", (int) (0.7 * 0.354 * getHeight()), (int) (0.7 * 0.498 * getHeight())),
				Images.getImage("card_gun.png", (int) (0.7 * 0.354 * getHeight()), (int) (0.7 * 0.498 * getHeight())),
				Images.getImage("card_collect.png", (int) (0.7 * 0.354 * getHeight()), (int) (0.7 * 0.498 * getHeight()))
		};

		arrowLeft = Images.getImage("arrow_left.png", getHeight() / 4, getHeight() / 4);
		arrowRight = Images.getImage("arrow_right.png", getHeight() / 4, getHeight() / 4);

		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				repaint();
				if (Math.random() < 0.0004)
					PlayList.run("train_noise.wav", Music.SoundType.EFFECT);
			}
		}, 16, 16);

		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (cardChoice) {
					for (int i = 0; i < 4; i++) {
						if (!(e.getX() > getHeight() - 3 * getHeight() / 4 + i * getHeight() / 3 &&
						e.getX() < getHeight() - 3 * getHeight() / 4 + i * getHeight() / 3 + cards[0].getWidth() &&
						e.getY() > getHeight() / 2 - cards[0].getHeight() / 2 &&
						e.getY() < getHeight() / 2 + cards[0].getHeight() / 2)) {
							continue;
						}

						switch (i) {
							case 0:
								Board.addCardToPack(Card.TYPE.MOVE_VERTICAL, playerIndex);
								break;
							case 1:
								Board.addCardToPack(Card.TYPE.MOVE_HORIZONTAL, playerIndex);
								break;
							case 2:
								Board.addCardToPack(Card.TYPE.SHOOT_ON_BANDIT, playerIndex);
								break;
							case 3:
								Board.addCardToPack(Card.TYPE.COLLECT_LOOT, playerIndex);
								break;
							default:
								break;
						}

						cardChoiceBuffer++;
						if (cardChoiceBuffer >= 4) {
							cardChoice = false;
						}
					}
				} else if (horizontalChoice) {
					if (e.getX() < getWidth() / 2)choiceLeft = true;
					else choiceRight = true;
				} else {
					if (e.getX() > 0 && e.getX() < getHeight() / 6 && e.getY() > 0 && e.getY() < getHeight() / 10) {
						Thread t = new Thread(new Runnable() {
							@Override
							public void run() {
								Board.action();
							}
						});
						t.start();
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}
		});

		this.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				mouseMoved(e);
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				onAction = (e.getX() > 0 && e.getX() < getHeight() / 6 && e.getY() > 0 && e.getY() < getHeight() / 10);
			}
		});
		Board.gamePanel = this;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		Graphics2D g2d = (Graphics2D) g;

		g2d.drawImage(background, 0, 0, null);
		//g2d.drawImage(locomotive, 0, (int) ((double)getHeight() * 0.4D), null);

		for (int i = 0; i < Board.wagons.size(); i++) {
			Wagon wagon = Board.wagons.get(i);

			if (wagon.getType() == Wagon.TYPE.LOCOMOTIVE)
				g2d.drawImage(wagon.getImage(), 0, (int) ((double)getHeight() * 0.4D), null);
			else g2d.drawImage(wagon.getImage(), (int) (locomotive.getWidth() + getHeight() * 0.31f * (i - 1)), (int) ((double)getHeight() * 0.46D), null);

			/*g2d.drawImage(wagon, (int) (locomotive.getWidth() + getHeight() * 0.31f * i), (int) ((double)getHeight() * 0.46D), null);

			*/for (int j = 0; j < Board.wagons.get(i).players.size(); j++) {
				int y = Board.wagons.get(i).players.get(j).getFloor() == Wagon.floor.DOWNSTAIRS ?
						(int) ((double)getHeight() * 0.5D) : (int) ((double)getHeight() * 0.45D);

				String name = Board.wagons.get(i).players.get(j).getName();
				if (name.equals("MARSHALL")) {
					g2d.drawImage(players[0], (int) (locomotive.getWidth() + getHeight() * 0.31f * (i - 1)) + j * 60, y, null);
				} else if (name.equals("JOUEUR_0")) {
					g2d.drawImage(players[1], (int) (locomotive.getWidth() + getHeight() * 0.31f * (i - 1)) + j * 60, y, null);
				} else if (name.equals("JOUEUR_1")) {
					g2d.drawImage(players[2], (int) (locomotive.getWidth() + getHeight() * 0.31f * (i - 1)) + j * 60, y, null);
				} else if (name.equals("JOUEUR_2")) {
					g2d.drawImage(players[3], (int) (locomotive.getWidth() + getHeight() * 0.31f * (i - 1)) + j * 60, y, null);
				} else if (name.equals("JOUEUR_3")) {
					g2d.drawImage(players[4], (int) (locomotive.getWidth() + getHeight() * 0.31f * (i - 1)) + j * 60, y, null);
				}

				//g2d.fillRect((int) (locomotive.getWidth() + getHeight() * 0.31f * (i - 1)) + j * 60, y, 50, 50);
			}

			for (int j = 0; j < Board.wagons.get(i).collectibles.size(); j++) {
				if (Board.wagons.get(i).collectibles.get(j).getType().equals(Collectible.CollectibleType.GEM))
					g2d.drawImage(gem, (int) (locomotive.getWidth() + getHeight() / 40 + getHeight() * 0.31f * (i - 1)) + j * 60,
							(int) ((double)getHeight() * 0.6D), null);
				else
					g2d.drawImage(moneyBag, (int) (locomotive.getWidth() + getHeight() / 40 + getHeight() * 0.31f * (i - 1)) + j * 60,
							(int) ((double)getHeight() * 0.6D), null);
			}
		}

		g2d.setColor(new Color(0, 0, 0, 128));
		g2d.fillRect(2 * getWidth() / 3, 3 * getHeight() / 4, getWidth() / 3, getHeight() / 4);
		g2d.setColor(new Color(245, 246, 250, 255));
		g2d.setFont(new Font("Arial", Font.PLAIN, (int) (getHeight() * 0.02)));

		for (int i = 0; i < this.logs.size(); i++) {
			g2d.drawString(this.logs.get(i), 2 * getWidth() / 3 + getWidth() / 30,
					3 * getHeight() / 4 + i * getHeight() / 40 + getHeight() / 20);
		}

		g2d.setColor(new Color(0, 0, 0, 192));
		g2d.fillRect(0, 3 * getHeight() / 4, Board.NB_PLAYERS * getWidth() / 6, getHeight() / 4);
		g2d.setFont(new Font("Arial", Font.PLAIN, (int) (getHeight() * 0.03)));
		g2d.setColor(new Color(245, 246, 250, 255));
		for (int i = 0; i < Board.players.size() - 1; i++) {
			g2d.drawString(Board.players.get(i + 1).getName(),
					getWidth() / 12 + i * getWidth() / 6 - g2d.getFontMetrics().stringWidth(Board.players.get(i + 1).getName()) / 2,
					3 * getHeight() / 4 + getHeight() / 24);

			g2d.drawString("Balles : " + Board.players.get(i + 1).getBullets(),
					i * getWidth() / 6 + getWidth() / 24,
					3 * getHeight() / 4 + 3 * getHeight() / 24);

			int count = 0;
			for (int j = 0; j < Board.players.get(i + 1).getCollectibles().size(); j++)
				count += Board.players.get(i + 1).getCollectibles().get(j).getValue();

			g2d.drawString("Buttin : " + count + "$",
					i * getWidth() / 6 + getWidth() / 24,
					3 * getHeight() / 4 + 4 * getHeight() / 24);
		}

		g2d.setColor(onAction ? new Color(20, 20 , 20, 255) : new Color(50, 50, 50, 255));
		g2d.fillRect(0, 0, getHeight() / 6, getHeight() / 10);
		g2d.setColor(new Color(245, 245 , 250, 255));
		g2d.drawString("Action !", getHeight() / 12 - g2d.getFontMetrics().stringWidth("Action !") / 2,
				getWidth() / 30);

		if (cardChoice) {
			g2d.setFont(new Font("Arial", Font.PLAIN, (int) (getHeight() * 0.05)));
			g2d.setColor(new Color(0, 0, 0, 127));
			g2d.fillRect(0, 0, getWidth(), getHeight());

			g2d.setColor(new Color(255, 255, 255, 255));
			g2d.drawString("Joueur " + playerIndex + ": choix de cartes", getWidth() / 2 - g2d.getFontMetrics().stringWidth("Joueur " + playerIndex + ": choix de cartes") / 2, (float) (getHeight() * 0.1));
			g2d.drawString("Restantes: " + (4 - cardChoiceBuffer), getWidth() / 2 - g2d.getFontMetrics().stringWidth("Restantes: " + (4 - cardChoiceBuffer)) / 2, (float) (getHeight() * 0.15));

			for (int i = 0; i < 4; i++) {
				g2d.drawImage(cards[i], getHeight() - 3 * getHeight() / 4 + i * getHeight() / 3, getHeight() / 2 - cards[0].getHeight() / 2, null);
				//System.out.println(wagons.get(wagons.size() - 1).players.size());
			}
		} else if (horizontalChoice) {
			g2d.setFont(new Font("Arial", Font.PLAIN, (int) (getHeight() * 0.05)));
			g2d.setColor(new Color(0, 0, 0, 127));
			g2d.fillRect(0, 0, getWidth(), getHeight());

			g2d.setColor(new Color(255, 255, 255, 255));
			g2d.drawString("Joueur " + playerIndex + ": choix de la direction", getWidth() / 2 - g2d.getFontMetrics().stringWidth("Joueur " + playerIndex + ": choix de cartes") / 2, (float) (getHeight() * 0.1));
			g2d.drawImage(arrowLeft, getWidth() / 2 - arrowLeft.getWidth(), getHeight() / 2 - arrowLeft.getHeight() / 2, null);
			g2d.drawImage(arrowRight, getWidth() / 2, getHeight() / 2 - arrowRight.getHeight() / 2, null);
		}

	}

	private boolean cardChoice = false;
	private int playerIndex = 0;
	private int cardChoiceBuffer = 0;

	public void chooseCard(int i) {
		this.cardChoice = true;
		this.playerIndex = i;
		this.cardChoiceBuffer = 0;

		//int j = 0;
		while (cardChoice) {
			//j = (j + 1) % 2;
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean horizontalChoice = false;
	private boolean choiceLeft = false, choiceRight = false;

	public Character.Direction askDirection() {
		horizontalChoice = true;
		choiceLeft = false;
		choiceRight = false;

		while (!choiceLeft || !choiceRight) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Character.Direction direction = choiceLeft ? Character.Direction.AVANT : Character.Direction.ARRIERE;

		choiceLeft = false;
		choiceRight = false;

		return direction;
	}

	public void addLog(String s) {
		this.logs.add(s);
		if (this.logs.size() > 8)
			this.logs.remove(0);
		System.out.println(s);
	}
}
