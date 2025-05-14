package com.example.android_project.appUI.object;

public class util {
    static public int getSlotNumb(String slot) {
        return Integer.parseInt(slot.replace("slot_", ""));
    }
    static public int getRoomNumb(String room) {
        return Integer.parseInt(room.replace("room_", ""));
    }
}
