package audio;

import java.util.ArrayList;

public class PlayList {

	// List of all songs played
	public static ArrayList<Music> musics = new ArrayList<Music>();

	/**
	 * Play a song with Music object
	 * @param title the song title
	 * @param TYPE music/effect song
	 */
	public static void run(String title, Music.SoundType TYPE) {
		musics.add(new Music(title, TYPE));
	}

	/**
	 * Stop a music
	 * @param title the title to stop
	 */
	public static void stop(String title) {
		for (Music m : musics) // get all music
			if (m.getTitle().equals(title)) // if recognize
				m.stop(); // stop the song
	}

	/**
	 * Stop all musics
	 */
	public static void stop() {
		if (musics.size() == 0)return;
		for (Music m : musics) {
			m.stop();
			musics.remove(m);
		}
	}

}
