package com.example.asus.songrecognition.model;

public class History {
    private int id;
    private String songTitle;
    private String date;

    public void setId(int id) {
        this.id = id;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public String getDate() {
        return date;
    }
}
