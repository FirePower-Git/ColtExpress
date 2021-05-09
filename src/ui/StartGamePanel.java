package ui;

import io.Images;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class StartGamePanel extends JPanel {

	private BufferedImage background;
	private Timer timer = new Timer();

	public StartGamePanel(LayeredPanel layeredPanel) {
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

			}
		});
	}


	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		Graphics2D g2d = (Graphics2D) g;

		g2d.drawImage(background, 0, 0, null);
	}
}
