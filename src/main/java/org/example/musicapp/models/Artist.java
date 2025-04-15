package org.example.musicapp.models;

import javafx.stage.Stage;
import org.example.musicapp.views.ArtistPage;

import java.util.ArrayList;
import java.util.List;
import org.example.musicapp.database.ArtistDatabase;

public class Artist extends Account {
    private List<Song> songs = new ArrayList<>();
    private List<Album> albums = new ArrayList<>();
    private List<User> followers = new ArrayList<>();  // List of users following the artist

    public Artist(String name, int age, String email, String username, String password) {
        super(name, age, email, username, password, Role.ARTIST);
    }

    // Artists can create songs (both singles and album songs)
    public void createSong(Song song) {
        songs.add(song);
        System.out.println("New song added: " + song.getTitle());
    }

    // Create a single (song without an album)
    public void createSingle(String title, String lyrics, String genre, List<String> tags, String releaseDate) {
        Song single = new Song(title, lyrics, new ArrayList<>(), genre, tags, releaseDate);
        songs.add(single);
        System.out.println("Single created: " + single.getTitle());
    }

    // Add an album (if album doesn't already exist)
    public void addAlbum(Album album) {
        if (album != null && !albums.contains(album)) {
            albums.add(album);
            System.out.println("New album added: " + album.getTitle());
        }
    }

    // Edit song lyrics (only for songs created by the artist)
    public void editSongLyrics(Song song, String newLyrics) {
        if (songs.contains(song)) {
            song.setLyrics(newLyrics);
            System.out.println("Lyrics updated for: " + song.getTitle());
        } else {
            System.out.println("You can only edit lyrics for your own songs.");
        }
    }

    // Approve or reject a user's edit request
    public void approveEdit(EditRequest editRequest) {
        System.out.println("Approving edit for: " + editRequest.getSong().getTitle());
        editRequest.getSong().setLyrics(editRequest.getNewLyrics());
    }

    public void rejectEdit(EditRequest editRequest) {
        System.out.println("Rejecting edit for: " + editRequest.getSong().getTitle());
    }

    // Add a follower
    public void addFollower(User user) {
        if (!followers.contains(user)) {
            followers.add(user);
            System.out.println(user.getUsername() + " is now following " + getName());
        }
    }

    // Remove a follower
    public void removeFollower(User user) {
        if (followers.contains(user)) {
            followers.remove(user);
            System.out.println(user.getUsername() + " unfollowed " + getName());
        }
    }

    @Override
    public void viewProfile() {
        System.out.println("Artist Profile: " + getName());
        System.out.println("Age: " + getAge());
        System.out.println("Email: " + getEmail());
        System.out.println("Most Popular Songs: ");
        for (int i = 0; i < Math.min(3, songs.size()); i++) {
            System.out.println("- " + songs.get(i).getTitle());
        }
        System.out.println("All Songs: ");
        for (Song song : songs) {
            System.out.println("- " + song.getTitle());
        }
        System.out.println("Albums: ");
        for (Album album : albums) {
            System.out.println("- " + album.getTitle());
        }

        System.out.println("Followers: ");
        for (User follower : followers) {
            System.out.println("- " + follower.getUsername());
        }
    }

    // Getter methods
    public List<Song> getSongs() {
        return songs;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public List<User> getFollowers() {
        return followers;
    }

    // Go to profile page for Artist
    public void goToProfilePage(Stage primaryStage) {
        // Create the ArtistPage and pass the artist instance
        ArtistPage artistPage = new ArtistPage(primaryStage, this);
        primaryStage.setScene(artistPage.getScene()); // Set the scene to the artist's profile page
    }

    // Static method to get artist by name
    public static Artist getArtistByName(String name) {
        for (Artist artist : ArtistDatabase.getAllArtists()) {
            if (artist.getName().equalsIgnoreCase(name)) {
                return artist;
            }
        }
        return null;
    }
    public void addSong(Song song) {
        if (songs == null) {
            songs = new ArrayList<>();
        }
        songs.add(song);
        System.out.println("Song added: " + song.getTitle());
    }
}
