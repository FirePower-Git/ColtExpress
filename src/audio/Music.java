package audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

public class Music {

	// the song title
	private String title;

	// type of song
	private SoundType TYPE;

	// pan and volume
	private FloatControl pan, volume;

	// is the song is running
	private boolean isRunning = false;

	// pan and volume main values
	private static float panMainValue = 0.0f, volumeMainValue = -10.0f;

	/**
	 * Constructor of music object
	 * @param title the name of the file
	 * @param TYPE the song type
	 */
	public Music(String title, SoundType TYPE) {
		this.title = title;
		this.TYPE = TYPE;
		play();
	}

	/**
	 * Get the title of the song
	 * @return a String
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Get the type of the music
	 * @return the music type
	 */
	public SoundType getTYPE() {
		return TYPE;
	}

	// Sound types
	public enum SoundType {
		MUSIC, EFFECT
	}

	/**
	 * Play a sound
	 */
	public void play() {
		// Create new thread for parallels tasks and songs
		Thread t = new Thread(new Runnable() {

			@SuppressWarnings("unlikely-arg-type")
			@Override
			public void run() {
				// Create audio objects
				SourceDataLine line;
				AudioInputStream audioInputStream;
				AudioFormat audioFormat;

				// Create input stream to file
				InputStream inputStream = Music.class.getResourceAsStream("/libs/audio/" + title);

				// Create Audio stream with input stream
				try {
					audioInputStream = AudioSystem.getAudioInputStream(inputStream);
				} catch (UnsupportedAudioFileException | IOException e) {
					e.printStackTrace();
					return;
				}

				// Get data line and audio format
				audioFormat = audioInputStream.getFormat();
				DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
				try {
					line = (SourceDataLine) AudioSystem.getLine(info);
					line.open(audioFormat);
				} catch (LineUnavailableException e) {
					e.printStackTrace();
					return;
				}

				// start playing song
				line.start();
				isRunning = true;

				// if we can adjust volume
				if (line.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
					volume = (FloatControl)line.getControl(FloatControl.Type.MASTER_GAIN);

					// if we can adjust pan
					if (line.isControlSupported(FloatControl.Type.PAN)) {
						pan = (FloatControl)line.getControl(FloatControl.Type.PAN);

						// Read x bytes to play (here 5000)
						byte[] bytes = new byte[5000];
						int bytesRead =  0;

						// Set again volume if we want to change it
						pan.setValue(panMainValue);
						volume.setValue(volumeMainValue);

						// While audio file is playing
						try {
							while ((bytesRead = audioInputStream.read(bytes, 0, bytes.length)) != -1 && isRunning) {
								if (pan.getValue() != panMainValue)pan.setValue(panMainValue);
								if (volume.getValue() != volumeMainValue)volume.setValue(volumeMainValue);
								line.write(bytes, 0, bytesRead); // Output audio to speakers
							}
						} catch (IOException e) {
							e.printStackTrace();
							return;
						}

						// If song if finnish we close the input line
						line.close();
						isRunning = false;
					}
				}

				// Remove Music object from array to prevent to much memory used
				PlayList.musics.remove(this);

				// play another one
				PlayList.run("music_" + ((int) (Math.random() * 4.0D)) + ".wav", Music.SoundType.MUSIC);
			}
		});

		// Start thread
		t.start();
	}

	/**
	 * Stop music
	 */
	public void stop() {
		isRunning = false; // Stop while loop
	}

}
