package org.example.musicapp.database;

import com.google.gson.reflect.TypeToken;
import org.example.musicapp.models.Song;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SongDatabase {
    private static final String SONG_FILE_PATH = "data/songs.json";
    private static final Type SONG_LIST_TYPE = new TypeToken<List<Song>>() {}.getType();
    private static final JsonDatabase<List<Song>> jsonDatabase = new JsonDatabase<>(SONG_FILE_PATH, SONG_LIST_TYPE);
    private static List<Song> songs = new ArrayList<>();

    static {
        loadSongs();
    }

    public static List<Song> getAllSongs() {
        return songs;
    }

    public static void addSong(Song song) {
        songs.add(song);
        saveSongs();
    }

    public static void removeSong(Song song) {
        songs.remove(song);
        saveSongs();
    }

    public static Song getSongByTitle(String title) {
        for (Song song : songs) {
            if (song.getTitle().equalsIgnoreCase(title)) {
                return song;
            }
        }
        return null;
    }

    private static void loadSongs() {
        List<Song> loaded = jsonDatabase.loadData();
        if (loaded != null) {
            songs = loaded;
        }
    }

    private static void saveSongs() {
        jsonDatabase.saveData(songs);
    }
}
