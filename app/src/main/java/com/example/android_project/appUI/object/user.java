package com.example.android_project.appUI.object;

import java.util.List;

public class user {
    private List<String> booked;
    private List<String> movId;
    private List<String> airId;
    private List<String> room;
    private List<String> slot;

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
