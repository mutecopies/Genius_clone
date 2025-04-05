package org.example.musicapp.models;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Album {
    private String title;
    private Artist artist;  // Artist who created the album
    private Date releaseDate;  // Release date of the album
    private List<Song> tracklist;  // Ordered list of songs in the album

    // Constructor to initialize album
    public Album(String title, Artist artist, Date releaseDate) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Album title cannot be null or empty.");
        }
        this.title = title;
        this.artist = artist;
        this.releaseDate = releaseDate != null ? releaseDate : new Date(); // Default to current date if releaseDate is null
        this.tracklist = new ArrayList<>();
    }

    // Add a song to the album, ensuring no duplicates
    public void addSong(Song song) {
        if (song == null) {
            System.out.println("Song cannot be null.");
            return;
        }
        if (!tracklist.contains(song)) {
            tracklist.add(song);  // Add the song to the tracklist
            System.out.println("Song added to album: " + song.getTitle());
        } else {
            System.out.println("This song is already in the album: " + song.getTitle());
        }
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public Artist getArtist() {
        return artist;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public List<Song> getTracklist() {
        return tracklist;
    }

    // Optional: Get a specific song from the tracklist by index
    public Song getSong(int index) {
        if (index >= 0 && index < tracklist.size()) {
            return tracklist.get(index);
        }
        return null;  // If the index is out of bounds
    }

    // Display album information (title, artist, release date, and tracklist)
    public String albumInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Album Title: ").append(title).append("\n")
                .append("Artist: ").append(artist.getName()).append("\n")
                .append("Release Date: ").append(releaseDate).append("\n")
                .append("Tracklist: \n");

        if (tracklist.isEmpty()) {
            info.append("No songs in this album yet.\n");
        } else {
            for (int i = 0; i < tracklist.size(); i++) {
                info.append(i + 1).append(". ").append(tracklist.get(i).getTitle()).append("\n");
            }
        }

        return info.toString();
    }

    // Display a formatted list of all songs in the album
    public void displayTracklist() {
        System.out.println("Tracklist for " + title + ":");
        for (int i = 0; i < tracklist.size(); i++) {
            System.out.println((i + 1) + ". " + tracklist.get(i).getTitle());
        }
    }
}
