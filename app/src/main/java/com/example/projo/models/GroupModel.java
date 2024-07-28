package com.example.projo.models;

import androidx.annotation.NonNull;

public class GroupModel {
    private String id;
    private String name;

    public GroupModel() {
        // Default constructor required for calls to DataSnapshot.getValue(Group.class)
    }

    public GroupModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
