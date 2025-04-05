package org.example.musicapp.models;
public class EditRequest {
    private User user;
    private Song song;
    private String oldLyrics;
    private String newLyrics;

    public EditRequest(User user, Song song, String oldLyrics, String newLyrics) {
        this.user = user;
        this.song = song;
        this.oldLyrics = oldLyrics;
        this.newLyrics = newLyrics;
    }

    public Song getSong() {
        return song;
    }

    public String getNewLyrics() {
        return newLyrics;
    }
}
