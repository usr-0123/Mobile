package com.example.projo.models;

// Chat.java
public class ChatsModel {
    private String id;
    private String name;
    private String lastMessage;

    public ChatsModel() {
        // Default constructor required for calls to DataSnapshot.getValue(Chat.class)
    }

    public ChatsModel(String id, String name, String lastMessage) {
        this.id = id;
        this.name = name;
        this.lastMessage = lastMessage;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastMessage() {
        return lastMessage;
    }
}
