package com.example.projo.models;

public class User {
    public String email;
    public String userId;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String email, String userId) {
        this.email = email;
        this.userId = userId;
    }
}
