package com.natanaelvich.main;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {

    private AudioClip clip;
    public static final Sound musicBack = new Sound("/music.wav");
    public static final Sound hurt = new Sound("/hurt.wav");

    private Sound(String name) {
        try {
            clip = Applet.newAudioClip(Sound.class.getResource(name));
        } catch (Throwable e) {
        }
    }

    public void play() {
        try {
            new Thread() {
                @Override
                public void run() {
                    clip.play();
                }
            }.start();
        } catch (Exception e) {
        }
    }

    public void loop() {
        try {
            new Thread() {
                @Override
                public void run() {
                    clip.loop();
                }
            }.start();
        } catch (Exception e) {
        }
    }
}
