package ui;

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

	private BufferedImage background;
	private Timer timer = new Timer();

	public MenuPanel(LayeredPanel layeredPanel) {
		this.setBounds(0, 0, Util.width, Util.height);
		//this.setBackground(new Color(0, 0, 0));
		this.setLayout(null);
		this.setVisible(true);
		background = new BufferedImage(Util.width, Util.height, BufferedImage.TYPE_INT_RGB);
		BufferedImage temp = Images.loadImage("main_image.jpg");
		Graphics g = background.createGraphics();
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.drawImage(temp, 0, 0, Util.width, Util.height, null);
		g2d.dispose();

		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				repaint();
			}
		}, 16, 16);

		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getX() > getWidth() / 2 - getWidth() / 6 &&
						e.getX() < getWidth() / 2 - getWidth() / 6 + getWidth() / 8 &&
						e.getY() > getHeight() / 2 &&
						e.getY() < getHeight() / 2 + getWidth() / 20) {
					layeredPanel.add(new GamePanel(layeredPanel), 1);
					layeredPanel.remove(0);
					PlayList.stop();
					Thread t = new Thread(new Runnable() {
						@Override
						public void run() {
							Board.newGame();
						}
					});
					t.start();

				}

				else if (e.getX() > getWidth() / 2 + getWidth() / 24 &&
						e.getX() < getWidth() / 2 + getWidth() / 24 + getWidth() / 8 &&
						e.getY() > getHeight() / 2 &&
						e.getY() < getHeight() / 2 + getWidth() / 20) {
					System.exit(0);
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
				onPlay = e.getX() > getWidth() / 2 - getWidth() / 6 &&
						e.getX() < getWidth() / 2 - getWidth() / 6 + getWidth() / 8 &&
						e.getY() > getHeight() / 2 &&
						e.getY() < getHeight() / 2 + getWidth() / 20;
				onExit = e.getX() > getWidth() / 2 + getWidth() / 24 &&
						e.getX() < getWidth() / 2 + getWidth() / 24 + getWidth() / 8 &&
						e.getY() > getHeight() / 2 &&
						e.getY() < getHeight() / 2 + getWidth() / 20;
			}
		});
	}

	public boolean onPlay, onExit;

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		Graphics2D g2d = (Graphics2D) g;

		g2d.drawImage(background, 0, 0, null);

		g2d.setColor(onPlay ? new Color(20, 20, 20) : new Color(40, 40, 40));
		g2d.fillRect(this.getWidth() / 2 - this.getWidth() / 6, this.getHeight() / 2, this.getWidth() / 8, this.getWidth() / 20);
		g2d.setColor(onExit ? new Color(20, 20, 20) : new Color(40, 40, 40));
		g2d.fillRect(this.getWidth() / 2 + this.getWidth() / 24, this.getHeight() / 2, this.getWidth() / 8, this.getWidth() / 20);

		g2d.setColor(new Color(245, 246, 250, 255));
		g2d.setFont(new Font("Arial", Font.PLAIN, (int) (getHeight() * 0.02)));
		g2d.drawString("JOUER", this.getWidth() / 2 - this.getWidth() / 6 + this.getWidth() / 16 - g2d.getFontMetrics().stringWidth("JOUER") / 2,
				this.getHeight() / 2 + this.getHeight() / 20);
		g2d.drawString("QUITTER", this.getWidth() / 2 + this.getWidth() / 24 + this.getWidth() / 16 - g2d.getFontMetrics().stringWidth("QUITTER") / 2,
				this.getHeight() / 2 + this.getHeight() / 20);
	}
}
