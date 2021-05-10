package ui;

import audio.Music;
import audio.PlayList;
import game.Board;
import io.Images;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class MenuPanel extends JPanel {

	// the background image
	private BufferedImage background;

	// timer to display frames
	private Timer timer = new Timer();

	// on buttons
	public boolean onPlay, onExit;

	/**
	 * Constructor of the MenuPanel object
	 * @param layeredPanel
	 */
	public MenuPanel(LayeredPanel layeredPanel) {

		// initialise panel
		this.setBounds(0, 0, Util.width, Util.height);
		this.setLayout(null);
		this.setVisible(true);

		// initialize background image
		background = Images.getImage("main_image.jpg", Util.width, Util.height);

		// add music
		PlayList.run("music_3.wav", Music.SoundType.MUSIC);

		// initialize timer
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				repaint();
			}
		}, 16, 16);

		// add mouse listener
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {

				// clic on play
				if (e.getX() > getWidth() / 2 - getWidth() / 6 &&
						e.getX() < getWidth() / 2 - getWidth() / 6 + getWidth() / 8 &&
						e.getY() > getHeight() / 2 &&
						e.getY() < getHeight() / 2 + getWidth() / 20) {

					// change to game panel
					layeredPanel.add(new GamePanel(layeredPanel), 1);
					layeredPanel.remove(0);

					// set new game
					Thread t = new Thread(new Runnable() {
						@Override
						public void run() {
							Board.newGame();
						}
					});
					t.start();
				}

				// clic on exit
				else if (e.getX() > getWidth() / 2 + getWidth() / 24 &&
						e.getX() < getWidth() / 2 + getWidth() / 24 + getWidth() / 8 &&
						e.getY() > getHeight() / 2 &&
						e.getY() < getHeight() / 2 + getWidth() / 20) {
					System.exit(0);
				}
			}

			@Override
			public void mousePressed(MouseEvent e) { }

			@Override
			public void mouseReleased(MouseEvent e) { }

			@Override
			public void mouseEntered(MouseEvent e) { }

			@Override
			public void mouseExited(MouseEvent e) { }
		});

		// add mouse motion listener
		this.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				mouseMoved(e);
			}

			@Override
			public void mouseMoved(MouseEvent e) {

				// on play shape
				onPlay = e.getX() > getWidth() / 2 - getWidth() / 6 &&
						e.getX() < getWidth() / 2 - getWidth() / 6 + getWidth() / 8 &&
						e.getY() > getHeight() / 2 &&
						e.getY() < getHeight() / 2 + getWidth() / 20;

				// on exit shape
				onExit = e.getX() > getWidth() / 2 + getWidth() / 24 &&
						e.getX() < getWidth() / 2 + getWidth() / 24 + getWidth() / 8 &&
						e.getY() > getHeight() / 2 &&
						e.getY() < getHeight() / 2 + getWidth() / 20;
			}
		});
	}

	/**
	 * repaint the component
	 * @param g
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);

		// create graphics 2d
		Graphics2D g2d = (Graphics2D) g;

		// draw background image
		g2d.drawImage(background, 0, 0, null);

		// draw buttons
		g2d.setColor(onPlay ? new Color(20, 20, 20) : new Color(40, 40, 40));
		g2d.fillRect(this.getWidth() / 2 - this.getWidth() / 6, this.getHeight() / 2, this.getWidth() / 8, this.getWidth() / 20);
		g2d.setColor(onExit ? new Color(20, 20, 20) : new Color(40, 40, 40));
		g2d.fillRect(this.getWidth() / 2 + this.getWidth() / 24, this.getHeight() / 2, this.getWidth() / 8, this.getWidth() / 20);

		// draw text on buttons
		g2d.setColor(new Color(245, 246, 250, 255));
		g2d.setFont(new Font("Arial", Font.PLAIN, (int) (getHeight() * 0.02)));
		g2d.drawString("JOUER", this.getWidth() / 2 - this.getWidth() / 6 + this.getWidth() / 16 - g2d.getFontMetrics().stringWidth("JOUER") / 2,
				this.getHeight() / 2 + this.getHeight() / 20);
		g2d.drawString("QUITTER", this.getWidth() / 2 + this.getWidth() / 24 + this.getWidth() / 16 - g2d.getFontMetrics().stringWidth("QUITTER") / 2,
				this.getHeight() / 2 + this.getHeight() / 20);
	}
}
