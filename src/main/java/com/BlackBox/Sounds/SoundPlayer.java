package com.BlackBox.Sounds;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class SoundPlayer {
    private static volatile boolean playing = true; // Flag to control playback
    private static volatile boolean paused = false; // Flag to control pause

    public SoundPlayer() {
    }

    // Method to play a sound file asynchronously
    public static void playSoundFile(String relativeFilePath) {
        new Thread(() -> {
            try {
                System.out.println("Attempting to play sound: " + relativeFilePath);
                InputStream inputStream = SoundPlayer.class.getResourceAsStream(relativeFilePath);
                if (inputStream == null) {
                    throw new FileNotFoundException("Resource not found: " + relativeFilePath);
                }

                // Wrap the InputStream with BufferedInputStream to support mark/reset
                BufferedInputStream bufferedIn = new BufferedInputStream(inputStream);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
                AudioFormat format = audioStream.getFormat();
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
                line.open(format);
                line.start();

                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = audioStream.read(buffer)) != -1 && playing) {
                    if (!paused) {
                        line.write(buffer, 0, bytesRead);
                    }
                }

                line.drain();
                line.stop();
                line.close();
                audioStream.close();
                System.out.println("Sound finished: " + relativeFilePath);
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }).start();
        playing = true;
    }

    // Method to play a sound file synchronously
    public static void playSoundFileAndWait(String relativeFilePath) {
        try {
            System.out.println("Attempting to play sound: " + relativeFilePath);
            InputStream inputStream = SoundPlayer.class.getResourceAsStream(relativeFilePath);
            if (inputStream == null) {
                throw new FileNotFoundException("Resource not found: " + relativeFilePath);
            }

            // Wrap the InputStream with BufferedInputStream to support mark/reset
            BufferedInputStream bufferedIn = new BufferedInputStream(inputStream);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = audioStream.read(buffer)) != -1) {
                line.write(buffer, 0, bytesRead);
            }

            line.drain();
            line.stop();
            line.close();
            audioStream.close();
            System.out.println("Sound finished: " + relativeFilePath);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void stopSound() {
        playing = false; // Set the flag to stop playback
    }

    public static void pauseSound() {
        paused = true; // Set the flag to pause playback
    }

    public static void resumeSound() {
        playing = true; // Set the flag to resume playback
    }
}
