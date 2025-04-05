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

    // Get the user who made the comment
    public User getUser() {
        return user;
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

    // Method to sort comments by date (latest first)
    @Override
    public int compareTo(Comment otherComment) {
        return otherComment.getDate().compareTo(this.date);  // Sort by date descending
    }

    @Override
    public String toString() {
        return getUsername() + " (" + date + "): " + text;
    }
}
