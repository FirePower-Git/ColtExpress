package ui;

import javax.swing.*;

public class LayeredPanel extends JLayeredPane {

	/**
	 * Constructor of the LayeredPanel object
	 */
	public LayeredPanel() {
		this.setLayout(null);
		this.add(new MenuPanel(this));
		this.setVisible(true);
	}
}
