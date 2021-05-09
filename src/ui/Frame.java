package ui;

import javax.swing.*;

public class Frame extends JFrame {

	public Frame() {
		this.setTitle("Colt Express");
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(new LayeredPanel());
		this.setVisible(true);
	}

}
