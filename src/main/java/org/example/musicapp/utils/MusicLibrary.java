package org.example.musicapp.utils;

import org.example.musicapp.models.Artist;
import org.example.musicapp.models.Album;
import org.example.musicapp.models.Song;
import org.example.musicapp.models.Comment;
import org.example.musicapp.models.User;

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
        // Example songs, replace with actual data

        // For trending songs, use the constructor with just the title
        songs.add(new Song("Trending Song 1"));
        songs.add(new Song("Trending Song 2"));

        // For songs with full details, use the constructor that accepts all attributes
        List<Artist> song1Artists = new ArrayList<>();
        song1Artists.add(new Artist("Artist 1", 25, "artist1@email.com", "artist1", "password123"));
        songs.add(new Song("Song 1", "Lyrics of song 1", song1Artists, "Pop", List.of("Tag1", "Tag2"), "2023-03-01"));

        List<Artist> song2Artists = new ArrayList<>();
        song2Artists.add(new Artist("Artist 2", 30, "artist2@email.com", "artist2", "password456"));
        songs.add(new Song("Song 2", "Lyrics of song 2", song2Artists, "Rock", List.of("Tag3", "Tag4"), "2023-03-05"));

        return songs;
    }
}
