package com.example.android_project.appUI.object;

import java.util.List;

public class air {
    // Movie ID, e.g "mov_josee", caller can use this to access movie class hashmap
    private List<String> movie;
    // Movie room
    private List<String> room;
    // 1D array slot. If it has 12 slots and we need
    // to divide the room to 3 section then
    // the first 4 slots are for the first section
    // the next 4 slots are for the second section
    // and so on
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
