package org.example.musicapp.models;
import java.util.ArrayList;
import java.util.List;
import org.example.musicapp.models.Role;

public class Artist extends Account {
    private List<Song> songs = new ArrayList<>();
    private List<Album> albums = new ArrayList<>();
    private List<User> followers = new ArrayList<>();  // List of users following the artist

    public Artist(String name, int age, String email, String username, String password) {
        super(name, age, email, username, password, Role.ARTIST);
    }

    // Artists can create songs
    public void createSong(Song song) {
        songs.add(song);
        System.out.println("New song added: " + song.getTitle());
    }

    // Artists can create albums
    public void createAlbum(Album album) {
        albums.add(album);
        System.out.println("New album added: " + album.getTitle());
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
        // No changes are made to the song lyrics
    }

    // Add a follower
    public void addFollower(User user) {
        if (!followers.contains(user)) {
            followers.add(user);
            System.out.println(user.getUsername() + " is now following " + getName());
        }
    }

    @Override
    public void viewProfile() {
        System.out.println("Artist Profile: " + getName());
        System.out.println("Age: " + getAge());
        System.out.println("Email: " + getEmail());
        System.out.println("Most Popular Songs: ");
        // Assuming you have a method to sort or find popular songs
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
    }

    // Getter methods for songs and albums
    public List<Song> getSongs() {
        return songs;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    // Getter method for followers
    public List<User> getFollowers() {
        return followers;
    }
}
