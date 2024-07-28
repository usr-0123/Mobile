package com.example.projo.models;

public class UserModel {
    private String id;
    private String name;

    public UserModel() { } // Empty constructor needed for Firestore

    public UserModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
