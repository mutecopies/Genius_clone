package org.example.musicapp.models;

import java.util.List;

public class Admin extends Account {

    public Admin(String name, int age, String email, String username, String password) {
        super(name, age, email, username, password, Role.ADMIN);
    }

    @Override
    public void viewProfile() {
        System.out.println("Admin Profile: " + getName());
        System.out.println("Admin Username: " + getUsername());
        System.out.println("Admin Email: " + getEmail());
    }

    // Admin approving artist registration
    public void approveArtistRegistration(Artist artist) {
        if (artist != null) {
            // Approve the artist by adding to the database or whatever registration mechanism you're using
            System.out.println("Admin approved artist: " + artist.getName());
            // You may need to add additional logic to register the artist officially.
        } else {
            System.out.println("Artist approval failed. Artist not found.");
        }
    }

    // Admin rejecting artist registration
    public void rejectArtistRegistration(Artist artist) {
        if (artist != null) {
            // Logic for rejecting the artist can be added here (e.g., deleting from a pending list)
            System.out.println("Admin rejected artist: " + artist.getName());
        } else {
            System.out.println("Artist rejection failed. Artist not found.");
        }
    }

    // Admin approving user-submitted lyric edits when artists are inactive
    public void approveLyricEdit(EditRequest editRequest) {
        if (editRequest != null && editRequest.getSong() != null) {
            System.out.println("Admin approved lyric edit for: " + editRequest.getSong().getTitle());
            editRequest.getSong().setLyrics(editRequest.getNewLyrics());
        } else {
            System.out.println("Lyric edit approval failed. Invalid request or song not found.");
        }
    }

    // Admin rejecting user-submitted lyric edits
    public void rejectLyricEdit(EditRequest editRequest) {
        if (editRequest != null && editRequest.getSong() != null) {
            System.out.println("Admin rejected lyric edit for: " + editRequest.getSong().getTitle());
        } else {
            System.out.println("Lyric edit rejection failed. Invalid request or song not found.");
        }
    }

    // Admin can view all pending artist registrations (Example: from ArtistDatabase or any pending list)
    public void viewPendingArtists(List<Artist> pendingArtists) {
        if (pendingArtists.isEmpty()) {
            System.out.println("No pending artist registrations.");
        } else {
            System.out.println("Pending Artists:");
            for (Artist artist : pendingArtists) {
                System.out.println("- " + artist.getName());
            }
        }
    }

    // Admin can view all pending lyric edit requests (Example: from a LyricEditRequestDatabase or any pending list)
    public void viewPendingLyricEdits(List<EditRequest> pendingEdits) {
        if (pendingEdits.isEmpty()) {
            System.out.println("No pending lyric edit requests.");
        } else {
            System.out.println("Pending Lyric Edits:");
            for (EditRequest editRequest : pendingEdits) {
                System.out.println("- Edit request for song: " + editRequest.getSong().getTitle());
            }
        }
    }

    // Getter methods for any additional admin-related features can be added here if needed
}
