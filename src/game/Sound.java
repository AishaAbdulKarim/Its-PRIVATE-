package game;

import java.io.File;
import javax.sound.sampled.*;

public class Sound {
    private byte[] audioData;
    private AudioFormat format;
    private DataLine.Info info;

    // Dedicated persistent clip for loop/stop
    private Clip persistentClip;

    public Sound(String fileName) {
        try {
            File file = new File("audio/" + fileName);
            if (file.exists()) {
                AudioInputStream stream = AudioSystem.getAudioInputStream(file);
                format = stream.getFormat();
                audioData = stream.readAllBytes();
                stream.close();

                info = new DataLine.Info(Clip.class, format);

                // Create persistent clip
                persistentClip = (Clip) AudioSystem.getLine(info);
                persistentClip.open(format, audioData, 0, audioData.length);

            } else {
                throw new RuntimeException("Sound file not found: " + fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading sound: " + e);
        }
    }

    // Supports overlapping sounds
    public void play() {
        try {
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(format, audioData, 0, audioData.length);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loop() {
        if (persistentClip != null) {
            persistentClip.setFramePosition(0);
            persistentClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop() {
        if (persistentClip != null && persistentClip.isRunning()) {
            persistentClip.stop();
        }
    }
}
