package main;

import audio.Music;
import entities.Locomotive;
import entities.Wagon;
import ui.Frame;
import ui.Util;

public class Main {

	public static Frame frame;

	public static void main(String[] args) {
		//Music music = new Music("music_1.wav", Music.SoundType.MUSIC);
		Util.init();
		Wagon.init();
		Locomotive.init();
		frame = new Frame();
	}

}
