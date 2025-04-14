package org.example.musicapp.database;

import org.example.musicapp.models.EditRequest;

import java.util.ArrayList;
import java.util.List;

public class LyricEditRequestDatabase {

    // Static list to hold pending lyric edit requests
    private static List<EditRequest> pendingEditRequests = new ArrayList<>();

    // Add a new lyric edit request to the list
    public static void addEditRequest(EditRequest editRequest) {
        pendingEditRequests.add(editRequest);
    }

    // Get all pending lyric edit requests
    public static List<EditRequest> getPendingRequests() {
        return pendingEditRequests;
    }

    // Remove a lyric edit request after approval or rejection
    public static void removeEditRequest(EditRequest editRequest) {
        pendingEditRequests.remove(editRequest);
    }
}
