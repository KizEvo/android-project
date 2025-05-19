package com.example.android_project.appUI.object;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class user {
    // String hold info of movie id, air id, room and
    // slot when a user book a ticket
    private List<String> booked;
    // Movie ID, e.g mov_josee
    private List<String> movId = new ArrayList<>();
    // Air ID, e.g air_202514051300
    private List<String> airId = new ArrayList<>();
    // Room, e.g room_1
    private List<String> room = new ArrayList<>();
    // slot, e.g slot_1
    private List<String> slot = new ArrayList<>();

    public user(List<String> booked) {
        this.booked = booked;
        for (String b : booked) {
            String[] parts = b.split("-");
            this.movId.add(parts[0]);
            this.airId.add(parts[1]);
            this.room.add(parts[2]);
            this.slot.add(parts[3]);
        }
    }
    public List<String> getSlot() {
        return slot;
    }
    public List<String> getAirId() {
        return airId;
    }
    public List<String> getBooked() {
        return booked;
    }
    public List<String> getMovId() {
        return movId;
    }
    public List<String> getRoom() {
        return room;
    }
}
