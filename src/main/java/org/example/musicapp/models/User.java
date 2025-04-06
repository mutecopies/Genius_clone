package org.example.musicapp.models;

import java.util.ArrayList;
import java.util.List;
import org.example.musicapp.models.Role;

public class User extends Account {
    private List<Artist> followedArtists = new ArrayList<>(); // Store Artist objects instead of names
    private List<Comment> comments = new ArrayList<>();

    public User(String name, int age, String email, String username, String password) {
        super(name, age, email, username, password, Role.USER);
    }

    // Follow an artist (add the Artist object to the list)
    public void followArtist(Artist artist) {
        followedArtists.add(artist); // Add the entire Artist object to the list
        System.out.println("You are now following " + artist.getName());
    }

    // Comment on a song (add the comment to the song and the user's comment list)
    public void commentOnSong(Song song, String commentText) {
        Comment comment = new Comment(this, song , commentText); // Create a new Comment
        song.addComment(comment); // Add the comment to the song's list of comments
        comments.add(comment); // Add the comment to the user's own comment list
    }

    // Display user's profile details
    @Override
    public void viewProfile() {
        System.out.println("User Profile: " + getName());
        System.out.println("Following artists: ");
        for (Artist artist : followedArtists) {
            System.out.println(artist.getName()); // Display artist names (or other details)
        }

        System.out.println("Your Comments: ");
        for (Comment comment : comments) {
            System.out.println(comment.getText() + " - on song: " + comment.getSong().getTitle());
        }
    }

    // Get the list of followed artists (return Artist objects)
    public List<Artist> getFollowedArtists() {
        return followedArtists;
    }

    // Get the list of comments made by the user
    public List<Comment> getComments() {
        return comments;
    }
}
