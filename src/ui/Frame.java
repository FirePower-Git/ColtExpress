package ui;

import javax.swing.*;

public class Frame extends JFrame {

	/**
	 * Constructor of the frame object
	 */
	public Frame() {

		// set title
		this.setTitle("Colt Express");

		// set fullscreen
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		// set undecorated
		this.setUndecorated(true);

		// place to the center
		this.setLocationRelativeTo(null);

		// exit when close frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// set the content panel
		this.setContentPane(new LayeredPanel());

		// show
		this.setVisible(true);
	}

}
