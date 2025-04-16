package org.example.musicapp.database;

import org.example.musicapp.models.User;
import org.example.musicapp.models.Artist;
import java.io.*;
import java.util.List;

public class UserDatabase {

    // Path to the user data file
    private static final String USER_DATA_FILE = "user_data.txt";

    public static void saveUserData(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE, true))) {
            // Save user details (e.g., name, followed artists)
            writer.write("User: " + user.getName() + "\n");

            // Save followed artists
            writer.write("Followed Artists:\n");
            for (Artist artist : user.getFollowedArtists()) {
                writer.write(artist.getName() + "\n");
            }

            // Optionally, you can save more details about the user here

            writer.write("---------\n"); // Separator for each user entry
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // A method to load all users (if needed)
    public static void loadUserData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Parse the user data and follow artists (this is a basic implementation)
                if (line.startsWith("User: ")) {
                    String userName = line.substring(6);
                    // Load other data like followed artists
                    while ((line = reader.readLine()) != null && !line.equals("---------")) {
                        if (line.startsWith("Followed Artists:")) {
                            // Parse followed artists here
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
