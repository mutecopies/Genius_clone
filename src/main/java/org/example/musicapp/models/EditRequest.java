package org.example.musicapp.models;

public class EditRequest {
    private User user;
    private Song song;
    private String oldLyrics;
    private String newLyrics;

    // Constructor
    public EditRequest(User user, Song song, String oldLyrics, String newLyrics) {
        this.user = user;
        this.song = song;
        this.oldLyrics = oldLyrics;
        this.newLyrics = newLyrics;
    }

    // Getters and Setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public String getOldLyrics() {
        return oldLyrics;
    }

    public void setOldLyrics(String oldLyrics) {
        this.oldLyrics = oldLyrics;
    }

    public String getNewLyrics() {
        return newLyrics;
    }

    public void setNewLyrics(String newLyrics) {
        this.newLyrics = newLyrics;
    }

    // Utility method to get the song title
    public String getSongTitle() {
        return song != null ? song.getTitle() : "No Song Title"; // Returns song title or a placeholder if null
    }

    // Utility method to get the edited lyrics
    public String getEditedLyrics() {
        return newLyrics != null ? newLyrics : "No Edited Lyrics"; // Returns edited lyrics or a placeholder if null
    }

    @Override
    public String toString() {
        return "EditRequest{" +
                "user=" + user.getUsername() + // Get username of the user making the request
                ", song=" + song.getTitle() + // Get song title
                ", oldLyrics='" + oldLyrics + '\'' +
                ", newLyrics='" + newLyrics + '\'' +
                '}';
    }
}
