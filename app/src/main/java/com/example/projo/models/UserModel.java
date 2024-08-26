package com.example.projo.models;

public class UserModel {
    private String userId;
    private String email;
    private String recipientId;
    private String recipientEmail;
    private String firstName;
    private String lastName;

    // Default constructor
    public UserModel() {
        // Default constructor required for calls to DataSnapshot.getValue(UserModel.class)
    }

    // Constructor for current user only
    public UserModel(String userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    // Constructor for both current user and recipient
    public UserModel(String firstName, String lastName, String userId, String email, String recipientId, String recipientEmail) {
        this.userId = userId;
        this.email = email;
        this.recipientId = recipientId;
        this.recipientEmail = recipientEmail;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Getter and setter methods for current user
    public String getUserId() {
        return userId;
    }

    public String getId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and setter methods for recipient user
    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    // Getter and setter methods for first name
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Getter and setter methods for last name
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}