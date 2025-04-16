package org.example.musicapp.models;

import java.util.ArrayList;
import java.util.List;

public class TrendingData {
    private static List<Song> trendingSongs = new ArrayList<>();
    private static List<Album> trendingAlbums = new ArrayList<>();

    // Add a new song to trending songs
    public static void addSongToTrending(Song song) {
        trendingSongs.add(song);
    }

    // Add a new album to trending albums
    public static void addAlbumToTrending(Album album) {
        trendingAlbums.add(album);
    }

    // Get the list of trending songs
    public static List<Song> getTrendingSongs() {
        return trendingSongs;
    }

    // Get the list of trending albums
    public static List<Album> getTrendingAlbums() {
        return trendingAlbums;
    }
}
