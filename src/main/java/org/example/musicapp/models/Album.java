package org.example.musicapp.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Album {
    private String title;
    private Artist artist;  // Artist who created the album
    private Date releaseDate;  // Release date of the album
    private List<Song> tracklist;  // Ordered list of songs in the album

    // Constructor
    public Album(String title, Artist artist, Date releaseDate) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Album title cannot be null or empty.");
        }

        this.title = title;
        this.artist = artist;
        this.releaseDate = (releaseDate != null) ? releaseDate : new Date(); // Use current date if null
        this.tracklist = new ArrayList<>();
    }

    // Add song to album
    public void addSong(Song song) {
        if (song == null) {
            System.out.println("Song cannot be null.");
            return;
        }

        if (!tracklist.contains(song)) {
            tracklist.add(song);
            System.out.println("Song added to album: " + song.getTitle());
        } else {
            System.out.println("This song is already in the album: " + song.getTitle());
        }
    }

    // Remove song from album
    public boolean removeSong(Song song) {
        return tracklist.remove(song);
    }

    // Get album title
    public String getTitle() {
        return title;
    }

    // Get artist
    public Artist getArtist() {
        return artist;
    }

    // Get release date
    public Date getReleaseDate() {
        return releaseDate;
    }

    // ✅ Unified getter: getSongs instead of getTracklist for clarity
    public List<Song> getSongs() {
        return tracklist;
    }

    // Get song by index
    public Song getSong(int index) {
        if (index >= 0 && index < tracklist.size()) {
            return tracklist.get(index);
        }
        return null;
    }

    // Generate full album info
    public String getAlbumInfo() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

        StringBuilder info = new StringBuilder();
        info.append("Album Title: ").append(title).append("\n")
                .append("Artist: ").append(artist != null ? artist.getName() : "Unknown").append("\n")
                .append("Release Date: ").append(formatter.format(releaseDate)).append("\n")
                .append("Tracklist:\n");

        if (tracklist.isEmpty()) {
            info.append("No songs in this album yet.\n");
        } else {
            for (int i = 0; i < tracklist.size(); i++) {
                info.append(i + 1).append(". ").append(tracklist.get(i).getTitle()).append("\n");
            }
        }

        return info.toString();
    }

    // For UI display (e.g. ListView)
    @Override
    public String toString() {
        return title + " (" + new SimpleDateFormat("yyyy").format(releaseDate) + ")";
    }
}
