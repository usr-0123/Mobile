package com.example.projo.models;

public class PostModel {
    private String title;
    private String content;
    private String id;
    private String userId;
    private String mediaUrl;
    private String date;

    // Required empty constructor for Firebase
    public PostModel() {}

    public PostModel(String id, String userId, String title, String content, String mediaUrl, String date) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.mediaUrl = mediaUrl;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}