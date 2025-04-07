package org.example.musicapp.persistence;

import org.example.musicapp.models.Album;
import org.example.musicapp.models.Song;
import org.example.musicapp.models.Artist;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataManager {

    private static final String ALBUM_FILE = "data/albums.txt";
    private static final String SONG_FILE = "data/songs.txt";  // File for storing songs

    // Save a song to the file
    public static void saveSong(Song song) {
        try (FileWriter writer = new FileWriter(SONG_FILE, true)) {
            writer.write("Song: " + song.getTitle() + "\n");
            writer.write("Lyrics: " + song.getLyrics() + "\n");
            writer.write("Artists: ");
            for (Artist artist : song.getArtists()) {
                writer.write(artist.getName() + ", ");
            }
            writer.write("\n");
            writer.write("Genre: " + song.getGenre() + "\n");
            writer.write("Release Date: " + song.getReleaseDate() + "\n");
            writer.write("Tags: ");
            for (String tag : song.getTags()) {
                writer.write(tag + ", ");
            }
            writer.write("\n");
            writer.write("Views: " + song.getViewsCount() + "\n");
            writer.write("-----\n");
            System.out.println("Song saved to file.");
        } catch (IOException e) {
            System.out.println("Failed to save song: " + e.getMessage());
        }
    }

    // Load all songs from the file (basic reader)
    public static void loadSongs() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SONG_FILE))) {
            String line;
            System.out.println("=== Saved Songs ===");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Failed to read songs: " + e.getMessage());
        }
    }

    // Load all albums from the file (basic reader)
    public static void loadAlbums() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ALBUM_FILE))) {
            String line;
            System.out.println("=== Saved Albums ===");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Failed to read albums: " + e.getMessage());
        }
    }

}
