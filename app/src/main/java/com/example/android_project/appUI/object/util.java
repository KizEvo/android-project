package com.example.android_project.appUI.object;

public class util {
    // Help parse String slot to an Integer
    static public int getSlotNumb(String slot) {
        return Integer.parseInt(slot.replace("slot_", ""));
    }
    // Help parse String room to an Integer
    static public int getRoomNumb(String room) {
        return Integer.parseInt(room.replace("room_", ""));
    }
}
