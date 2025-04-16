    package org.example.musicapp.models;

    import java.util.ArrayList;
    import java.util.List;

    public class Song {
        private String title;
        private String lyrics;
        private List<Artist> artists;  // List of artists (primary and featured artists)
        private Album album;  // Can be null if the song is a single
        private String genre;
        private List<String> tags;
        private int viewsCount;
        private List<Comment> comments;
        private String releaseDate;
        private List<EditRequest> editRequests;  // List to store lyric edit requests

        // Constructor that accepts only the title for trending songs
        public Song(String title) {
            this.title = title;
            this.lyrics = "";  // Empty lyrics for now
            this.artists = new ArrayList<>();  // Empty list for now
            this.album = null;  // No album by default
            this.genre = "Unknown";  // Default genre
            this.tags = new ArrayList<>();  // Empty list for tags
            this.viewsCount = 0;  // Default view count
            this.comments = new ArrayList<>();  // Empty comment list
            this.releaseDate = "Unknown";  // Default release date
            this.editRequests = new ArrayList<>();  // Initialize the list of edit requests
        }

        // Regular constructor with all attributes (for songs with more details)
        public Song(String title, String lyrics, List<Artist> artists, String genre, List<String> tags, String releaseDate) {
            this.title = title;
            this.lyrics = lyrics;
            this.artists = artists;
            this.genre = genre;
            this.tags = tags;
            this.releaseDate = releaseDate;
            this.album = null;  // Default is not in an album
            this.viewsCount = 0;
            this.comments = new ArrayList<>();
            this.editRequests = new ArrayList<>();
        }

        // Getter for the title
        public String getTitle() {
            return title;
        }

        // Getters and Setters for the rest of the attributes
        public String getLyrics() {
            return lyrics;
        }

        public void setLyrics(String lyrics) {
            this.lyrics = lyrics;
        }

        public List<Artist> getArtists() {
            return artists;
        }

        public Album getAlbum() {
            return album;
        }

        public String getGenre() {
            return genre;
        }

        public List<String> getTags() {
            return tags;
        }

        public int getViewsCount() {
            return viewsCount;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        // Method to view song (increase views)
        public void viewSong() {
            viewsCount++;
            System.out.println("Viewing " + title + ". Views: " + viewsCount);
        }

        // Method to edit lyrics by an authorized artist
        public void editLyrics(Artist artist, String newLyrics) {
            if (artists.contains(artist)) {
                this.lyrics = newLyrics;
                System.out.println(artist.getName() + " has updated the lyrics of " + title);
            } else {
                System.out.println(artist.getName() + " is not authorized to edit the lyrics of " + title);
            }
        }

        // Method to suggest lyric edits by a user
        public void suggestLyricEdit(User user, String newLyrics) {
            System.out.println(user.getName() + " suggested a lyric edit for " + title);
        }

        // Method to add a comment to the song
        public void addComment(Comment comment) {
            comments.add(comment); // Adds the comment to the song's comment list
            System.out.println("Comment added to " + title + ": " + comment.getText());
        }

        // Method to view all comments (sorted by date)
        public void viewComments() {
            System.out.println("Comments on " + title + ":");
            List<Comment> sortedComments = new ArrayList<>(comments);
            sortedComments.sort((c1, c2) -> c2.getDate().compareTo(c1.getDate())); // Sort by date (latest first)

            for (Comment comment : sortedComments) {
                System.out.println("- " + comment.getText() + " (by " + comment.getUser().getUsername() + ")");
            }
        }

        // Method to add an edit request for the song's lyrics
        public void addEditRequest(EditRequest request) {
            editRequests.add(request); // Adds the edit request to the song's list
            System.out.println("Edit request added for " + title + ": " + request.getNewLyrics());
        }

        // Method to view all edit requests (sorted by date)
        public void viewEditRequests() {
            System.out.println("Edit Requests for " + title + ":");
            for (EditRequest request : editRequests) {
                System.out.println("- " + request.getOldLyrics() + " to " + request.getNewLyrics() + " (by " + request.getUser().getUsername() + ")");
            }
        }

        @Override
        public String toString() {
            return title;
        }
    }
