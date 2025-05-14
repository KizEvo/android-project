package com.example.android_project.appUI.object;

import java.util.List;

public class air {
    private List<String> movie;
    private List<String> room;
    private List<Boolean> slot;

    public air (List<String> movie,List<String> room, List<Boolean> slot) {
        this.movie = movie;
        this.room = room;
        this.slot = slot;
    }
    public List<String> getMovie() {
        return movie;
    }
    public List<String> getRoom() {
        return room;
    }
    public List<Boolean> getSlot() {
        return slot;
    }
    public String getMovieAt(int idx) {
        return movie.get(idx);
    }
    public String getRoomAt(int idx) {
        return room.get(idx);
    }
    public boolean getSlotStatusAt(int idx){
        return slot.get(idx);
    }
}
