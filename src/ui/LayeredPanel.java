package ui;

import javax.swing.*;

public class LayeredPanel extends JLayeredPane {

	public LayeredPanel() {
		this.setLayout(null);
		this.add(new MenuPanel(this));
		this.setVisible(true);
	}
}
