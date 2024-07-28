package com.example.projo.models;

public class ChatUserModel {
    private String uid;
    private String name;
    private String profileImageUrl;

    public ChatUserModel() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public ChatUserModel(String uid, String name, String profileImageUrl) {
        this.uid = uid;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }
}
