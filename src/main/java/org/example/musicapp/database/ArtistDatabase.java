package org.example.musicapp.database;

import org.example.musicapp.models.Artist;
import org.example.musicapp.models.Album;
import org.example.musicapp.models.Song;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ArtistDatabase {
    private static final List<Artist> allArtists = new ArrayList<>();

    static {
        try {
            // Create artists with full info
            Artist drake = new Artist("Drake", 37, "drake@example.com", "drizzy", "password123");
            Artist taylor = new Artist("Taylor Swift", 34, "taylor@example.com", "tswift", "password456");
            Artist weeknd = new Artist("The Weeknd", 35, "weeknd@example.com", "weeknd", "password789");

            // Create songs
            Song godsPlan = new Song("God's Plan");
            Song shakeItOff = new Song("Shake It Off");
            Song blindingLights = new Song("Blinding Lights");

            // Add songs to artists
            drake.createSong(godsPlan);
            taylor.createSong(shakeItOff);
            weeknd.createSong(blindingLights);

            // Format dates
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            // Create albums with proper artist + release date
            Album scorpion = new Album("Scorpion", drake, formatter.parse("2018-06-29"));
            scorpion.addSong(godsPlan);

            Album swift1989 = new Album("1989", taylor, formatter.parse("2014-10-27"));
            swift1989.addSong(shakeItOff);

            Album afterHours = new Album("After Hours", weeknd, formatter.parse("2020-03-20"));
            afterHours.addSong(blindingLights);

            // Add albums to artists
            drake.addAlbum(scorpion);
            taylor.addAlbum(swift1989);
            weeknd.addAlbum(afterHours);

            // Add to database
            allArtists.addAll(Arrays.asList(drake, taylor, weeknd));

        } catch (ParseException e) {
            e.printStackTrace();
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
    }
}
