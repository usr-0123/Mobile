package com.example.projo.models;

public class ChatModel {
    private String senderUid;
    private String message;
    private long timestamp;

    public ChatModel() {
        // Default constructor required for calls to DataSnapshot.getValue(Chat.class)
    }

    public ChatModel(String senderUid, String message, long timestamp) {
        this.senderUid = senderUid;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getSenderUid() {
        return senderUid;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }
}