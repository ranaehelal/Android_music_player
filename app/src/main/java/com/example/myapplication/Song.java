package com.example.myapplication;

public class Song {
    private String name;
    private int audioResId;
    private int imageResId;

    public Song(String name, int audioResId, int imageResId) {
        this.name = name;
        this.audioResId = audioResId;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public int getAudioResId() {
        return audioResId;
    }

    public int getImageResId() {
        return imageResId;
    }
}
