package com.Anas.onlineradio;

import android.media.Image;

public class Track {
    private String title;
    private String artist;
    private int Image;

    public Track(String title, String artist, int image) {
        this.title = title;
        this.artist = artist;
        Image = image;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }
}
