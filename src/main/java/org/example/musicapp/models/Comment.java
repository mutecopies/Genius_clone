package org.example.musicapp.models;

import java.util.Date;

public class Comment implements Comparable<Comment> {
    private User user;
    private Song song;  // Add a field for the Song associated with the comment
    private String text;
    private Date date;

    // Constructor
    public Comment(User user, Song song, String text) {
        this.user = user;
        this.song = song;  // Set the song for the comment
        this.text = text;
        this.date = new Date();  // Sets the current date and time
    }

    // Get the text of the comment
    public String getText() {
        return text;
    }

    // Set the text of the comment
    public void setText(String text) {
        this.text = text;
    }

    // Get the user who made the comment
    public User getUser() {
        return user;
    }

    // Set the user who made the comment
    public void setUser(User user) {
        this.user = user;
    }

    // Get the username of the commenter
    public String getUsername() {
        return user.getUsername();
    }

    // Get the date of the comment
    public Date getDate() {
        return date;
    }

    // Get the song associated with the comment
    public Song getSong() {
        return song;
    }

    // Set the song associated with the comment
    public void setSong(Song song) {
        this.song = song;
    }

    // Method to sort comments by date (latest first)
    @Override
    public int compareTo(Comment otherComment) {
        return otherComment.getDate().compareTo(this.date);  // Sort by date descending
    }

    @Override
    public String toString() {
        String songTitle = (song != null) ? song.getTitle() : "Unknown Song";
        return getUsername() + " (" + date + "): " + text + " - on song: " + songTitle;
    }
}
