package com.example.projo.models;

import androidx.annotation.NonNull;

public class GroupModel {
    private String id;
    private String title;

    public GroupModel() {
        // Default constructor required for calls to DataSnapshot.getValue(Group.class)
    }

    public GroupModel(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @NonNull
    @Override
    public String toString() {
        return title;
    }
}
