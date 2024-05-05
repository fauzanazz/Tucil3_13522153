package tubesstimaif.wordladder;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class BackgroundMusic {
    List<String> filePaths = List.of(new String[]{
            "/music/1.wav",
            "/music/2.wav",
            "/music/3.wav",
    });

    private final List<Clip> clips = new ArrayList<>();
    private int currentSongIndex = 0;
    private boolean isPaused = false;

    public BackgroundMusic() {
        try {
            for (String filePath : filePaths) {
                InputStream is = Objects.requireNonNull(getClass().getResourceAsStream(filePath));
                BufferedInputStream bis = new BufferedInputStream(is);
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bis);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP && !isPaused) {
                        next();
                    }
                });
                clips.add(clip);
            }
            currentSongIndex = new Random().nextInt(clips.size());
            play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        Clip currentClip = clips.get(currentSongIndex);
        if (!currentClip.isRunning()) {
            currentClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void pause() {
        Clip currentClip = clips.get(currentSongIndex);
        if (currentClip.isRunning()) {
            currentClip.stop();
            isPaused = true;
        }
    }

    public void resume() {
        Clip currentClip = clips.get(currentSongIndex);
        if (!currentClip.isRunning()) {
            currentClip.loop(Clip.LOOP_CONTINUOUSLY);
            isPaused = false;
        }
    }

    public void next() {
        pause();
        isPaused = false;
        currentSongIndex = (currentSongIndex + 1) % clips.size();
        play();
    }

    public void nextSongButton(){
        Clip currentClip = clips.get(currentSongIndex);
        currentClip.stop();
        currentClip.setMicrosecondPosition(0);
    }
}