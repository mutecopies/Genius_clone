package org.example.musicapp.database;

import com.google.gson.reflect.TypeToken;
import org.example.musicapp.models.Artist;
import org.example.musicapp.models.Album;
import org.example.musicapp.models.Song;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ArtistDatabase {
    private static final String ARTIST_FILE_PATH = "data/artists.json";
    private static final String PENDING_ARTIST_FILE_PATH = "data/pending_artists.json";

    private static final List<Artist> allArtists = new ArrayList<>();
    private static final List<Artist> pendingArtists = new ArrayList<>();

    static {
        // Try loading from file first
        loadFromJson();

        // If no data exists, add sample artists
        if (allArtists.isEmpty()) {
            try {
                Artist drake = new Artist("Drake", 37, "drake@example.com", "drizzy", "password123");
                Artist taylor = new Artist("Taylor Swift", 34, "taylor@example.com", "tswift", "password456");
                Artist weeknd = new Artist("The Weeknd", 35, "weeknd@example.com", "weeknd", "password789");

                Song godsPlan = new Song("God's Plan");
                Song shakeItOff = new Song("Shake It Off");
                Song blindingLights = new Song("Blinding Lights");

                drake.createSong(godsPlan);
                taylor.createSong(shakeItOff);
                weeknd.createSong(blindingLights);

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

                Album scorpion = new Album("Scorpion", drake, formatter.parse("2018-06-29"));
                scorpion.addSong(godsPlan);

                Album swift1989 = new Album("1989", taylor, formatter.parse("2014-10-27"));
                swift1989.addSong(shakeItOff);

                Album afterHours = new Album("After Hours", weeknd, formatter.parse("2020-03-20"));
                afterHours.addSong(blindingLights);

                drake.addAlbum(scorpion);
                taylor.addAlbum(swift1989);
                weeknd.addAlbum(afterHours);

                allArtists.addAll(Arrays.asList(drake, taylor, weeknd));

                saveToJson(); // Save for next time
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Artist> getAllArtists() {
        return allArtists;
    }

    public static Artist getArtistByName(String name) {
        for (Artist artist : allArtists) {
            if (artist.getName().equalsIgnoreCase(name)) {
                return artist;
            }
        }
        return null;
    }

    public static void addArtist(Artist artist) {
        allArtists.add(artist);
        saveToJson();
    }

    public static void addPendingArtist(Artist artist) {
        pendingArtists.add(artist);
        saveToJson();
    }

    public static void approveArtist(Artist artist) {
        if (pendingArtists.contains(artist)) {
            pendingArtists.remove(artist);
            allArtists.add(artist);
            saveToJson();
        }
    }

    public static void rejectArtist(Artist artist) {
        pendingArtists.remove(artist);
        saveToJson();
    }

    public static List<Artist> getPendingArtists() {
        return pendingArtists;
    }

    // JSON SAVE / LOAD
    private static void saveToJson() {
        Type artistListType = new TypeToken<List<Artist>>() {}.getType();

        new JsonDatabase<List<Artist>>(ARTIST_FILE_PATH, artistListType).saveData(allArtists);
        new JsonDatabase<List<Artist>>(PENDING_ARTIST_FILE_PATH, artistListType).saveData(pendingArtists);
    }

    private static void loadFromJson() {
        Type artistListType = new TypeToken<List<Artist>>() {}.getType();

        List<Artist> loadedAll = new JsonDatabase<List<Artist>>(ARTIST_FILE_PATH, artistListType).loadData();
        List<Artist> loadedPending = new JsonDatabase<List<Artist>>(PENDING_ARTIST_FILE_PATH, artistListType).loadData();

        if (loadedAll != null) allArtists.addAll(loadedAll);
        if (loadedPending != null) pendingArtists.addAll(loadedPending);
    }
}
