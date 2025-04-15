package org.example.musicapp.utils;

import org.example.musicapp.models.Artist;
import org.example.musicapp.models.Album;
import org.example.musicapp.models.Song;

import java.util.List;
import java.util.ArrayList;

public class MusicLibrary {

    // Sample method to get artists
    public static List<Artist> getAllArtists() {
        List<Artist> artists = new ArrayList<>();
        // Example artists, replace with actual data
        artists.add(new Artist("Artist 1", 25, "artist1@email.com", "artist1", "password123"));
        artists.add(new Artist("Artist 2", 30, "artist2@email.com", "artist2", "password456"));
        return artists;
    }

    // Sample method to get albums
    public static List<Album> getAllAlbums() {
        List<Album> albums = new ArrayList<>();
        // Example albums, replace with actual data
        albums.add(new Album("Album 1", new Artist("Artist 1", 25, "artist1@email.com", "artist1", "password123"), null));
        albums.add(new Album("Album 2", new Artist("Artist 2", 30, "artist2@email.com", "artist2", "password456"), null));
        return albums;
    }

    // Sample method to get songs
    public static List<Song> getAllSongs() {
        List<Song> songs = new ArrayList<>();
        // Replace with actual song names and their details

        songs.add(new Song("Blinding Lights")); // New Song 1
        songs.add(new Song("Levitating"));      // New Song 2
        songs.add(new Song("Save Your Tears")); // New Song 3

        // Adding full song details
        List<Artist> song1Artists = new ArrayList<>();
        song1Artists.add(new Artist("The Weeknd", 30, "theweeknd@email.com", "theweeknd", "password123"));
        songs.add(new Song("Blinding Lights", "Lyrics of Blinding Lights", song1Artists, "Pop", List.of("Pop", "Synthwave"), "2020-03-20"));

        List<Artist> song2Artists = new ArrayList<>();
        song2Artists.add(new Artist("Dua Lipa", 27, "dualipa@email.com", "dualipa", "password456"));
        songs.add(new Song("Levitating", "Lyrics of Levitating", song2Artists, "Pop", List.of("Pop", "Dance"), "2020-07-27"));

        List<Artist> song3Artists = new ArrayList<>();
        song3Artists.add(new Artist("The Weeknd", 30, "theweeknd@email.com", "theweeknd", "password123"));
        songs.add(new Song("Save Your Tears", "Lyrics of Save Your Tears", song3Artists, "Pop", List.of("Pop", "Synthwave"), "2020-03-09"));

        return songs;
    }
}
