package com.example.projo.models;

// This model is used when doing registration of new user.

public class User {
    public String firstName;
    public String lastName;
    public String email;
    public String userId;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String firstName, String lastName, String email, String userId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userId = userId;
    }
}
