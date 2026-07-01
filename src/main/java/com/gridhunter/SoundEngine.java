package com.gridhunter;

import javax.sound.sampled.*;

public class SoundEngine {

    private static void playTone(int frequency, int durationMs, float volume) {
        new Thread(() -> {
            try {
                float sampleRate = 44100f;
                int samples = (int) (sampleRate * durationMs / 1000);
                byte[] buffer = new byte[samples * 2];

                for (int i = 0; i < samples; i++) {
                    double angle = 2.0 * Math.PI * i * frequency / sampleRate;
                    double fade = 1.0 - (double) i / samples;
                    short val = (short) (Math.sin(angle) * fade * Short.MAX_VALUE * volume);
                    buffer[2 * i] = (byte) (val & 0xFF);
                    buffer[2 * i + 1] = (byte) ((val >> 8) & 0xFF);
                }

                AudioFormat format = new AudioFormat(sampleRate, 16, 1, true, false);
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
                line.open(format);
                line.start();
                line.write(buffer, 0, buffer.length);
                line.drain();
                line.close();
            } catch (Exception e) {
            }
        }).start();
    }

    public static void playMove() {
        playTone(300, 60, 0.2f); // Short soft blip
    }

    public static void playEnemyMove() {
        playTone(150, 80, 0.15f); // Low dull thud
    }

    public static void playLevelUp() {
        // Ascending 3-note fanfare
        new Thread(() -> {
            try {
                playTone(400, 120, 0.4f);
                Thread.sleep(100);
                playTone(550, 120, 0.4f);
                Thread.sleep(100);
                playTone(700, 200, 0.5f);
            } catch (InterruptedException ignored) {
            }
        }).start();
    }

    public static void playWarning() {
        // Rapid double beep — enemy is 2 cells away
        new Thread(() -> {
            try {
                playTone(600, 80, 0.3f);
                Thread.sleep(80);
                playTone(600, 80, 0.3f);
            } catch (InterruptedException ignored) {
            }
        }).start();
    }

    public static void playGameOver() {
        // Descending sad tones
        new Thread(() -> {
            try {
                playTone(500, 200, 0.5f);
                Thread.sleep(180);
                playTone(380, 200, 0.5f);
                Thread.sleep(180);
                playTone(250, 400, 0.6f);
            } catch (InterruptedException ignored) {
            }
        }).start();
    }

    public static void playNewEnemy() {
        // Harsh alert buzz
        new Thread(() -> {
            try {
                playTone(900, 100, 0.5f);
                Thread.sleep(80);
                playTone(700, 100, 0.5f);
                Thread.sleep(80);
                playTone(900, 150, 0.5f);
            } catch (InterruptedException ignored) {
            }
        }).start();
    }
}