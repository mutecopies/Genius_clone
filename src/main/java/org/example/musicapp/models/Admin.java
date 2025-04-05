package org.example.musicapp.models;
public class Admin extends Account {

    public Admin(String name, int age, String email, String username, String password) {
        super(name, age, email, username, password, Role.ADMIN);
    }

    @Override
    public void viewProfile() {
        System.out.println("Admin Profile: " + getName());
    }

    // Admin approving artist registration
    public void approveArtistRegistration(Artist artist) {
        System.out.println("Admin approved artist: " + artist.getName());
    }

    // Admin approving user-submitted lyric edits when artists are inactive
    public void approveLyricEdit(EditRequest editRequest) {
        System.out.println("Admin approved lyric edit for: " + editRequest.getSong().getTitle());
        editRequest.getSong().setLyrics(editRequest.getNewLyrics());
    }
}
