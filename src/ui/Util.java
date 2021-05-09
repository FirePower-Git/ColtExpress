package ui;

import java.awt.*;

public class Util {

	public static int width, height;

	public static void init() {
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

		// width will store the width of the screen
		width = (int)size.getWidth();

		// height will store the height of the screen
		height = (int)size.getHeight();
	}

}
