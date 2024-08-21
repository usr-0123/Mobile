package com.example.projo.models;

public class ChatsModel {
    private String chatRoomId;
    private String senderId;
    private String senderEmail;
    private String recipientId;
    private String recipientEmail;
    private String lastMessage;
    private long createdAt;
    private String firstName;
    private String lastName;

    // Default constructor required for calls to DataSnapshot.getValue(Chat.class)
    public ChatsModel() {
    }

    // Constructor with parameters
    public ChatsModel(String chatRoomId, String senderId, String senderEmail, String recipientId, String recipientEmail, String firstName, String lastName, String lastMessage, long createdAt) {
        this.chatRoomId = chatRoomId;
        this.senderId = senderId;
        this.senderEmail = senderEmail;
        this.recipientId = recipientId;
        this.recipientEmail = recipientEmail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.lastMessage = lastMessage;
        this.createdAt = createdAt;
    }

    // Getters
    public String getChatRoomId() {
        return chatRoomId;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public String getRecipientFirstName() {
        return firstName;
    }

    public String getRecipientLastName() {
        return lastName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    // Setters
    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public void setRecipientFirstName(String recipientFirstName) {
        this.firstName = recipientFirstName;
    }

    public void setRecipientLastName(String recipientLastName) {
        this.lastName = recipientLastName;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
