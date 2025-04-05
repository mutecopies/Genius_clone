package org.example.musicapp.models;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Song {
    private String title;
    private String lyrics;
    private List<Artist> artists;  // List of artists (primary and featured artists)
    private Album album;  // Can be null if the song is a single
    private String genre;
    private List<String> tags;
    private int viewsCount;
    private List<Comment> comments;
    private String releaseDate;

    // Constructor
    public Song(String title, String lyrics, List<Artist> artists, String genre, List<String> tags, String releaseDate) {
        this.title = title;
        this.lyrics = lyrics;
        this.artists = artists;
        this.genre = genre;
        this.tags = tags;
        this.releaseDate = releaseDate;
        this.album = null;  // Default is not in an album
        this.viewsCount = 0;
        this.comments = new ArrayList<>();
    }

    // Set album, if the song is part of an album
    public void setAlbum(Album album) {
        this.album = album;
    }

    // Increase the view count each time the song is viewed
    public void viewSong() {
        viewsCount++;
        System.out.println("Viewing " + title + ". Views: " + viewsCount);
    }

    // Allow artists to edit their lyrics
    public void editLyrics(Artist artist, String newLyrics) {
        if (artists.contains(artist)) {
            this.lyrics = newLyrics;
            System.out.println(artist.getName() + " has updated the lyrics of " + title);
        } else {
            System.out.println(artist.getName() + " is not authorized to edit the lyrics of " + title);
        }
    }

    // Suggest lyric edits (users can suggest edits, but artists must approve them)
    public void suggestLyricEdit(User user, String newLyrics) {
        System.out.println(user.getName() + " suggested a lyric edit for " + title);
        // This could be a request to the artist for approval
    }

    // Add a comment to the song
    public void addComment(Comment comment) {
        comments.add(comment);
        System.out.println("Comment added to " + title + ": " + comment.getText());
    }

    // View all comments on the song (sorted by date, latest first)
    public void viewComments() {
        System.out.println("Comments on " + title + ":");
        List<Comment> sortedComments = new ArrayList<>(comments);
        Collections.sort(sortedComments); // Sorts by date (latest first)

        for (Comment comment : sortedComments) {
            System.out.println("- " + comment.getText() + " (by " + comment.getUser().getUsername() + ")");
        }
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public Album getAlbum() {
        return album;
    }

    public String getGenre() {
        return genre;
    }

    public List<String> getTags() {
        return tags;
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
