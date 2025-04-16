package com.example.android_project;

public class Message {
    private String content; // The message text
    private boolean isSentByUser; // To differentiate between sender and receiver

    public Message(String content, boolean isSentByUser) {
        this.content = content;
        this.isSentByUser = isSentByUser;
    }

    public String getContent() {
        return content;
    }

    public boolean isSentByUser() {
        return isSentByUser;
    }
}
