package com.example.projo.pages;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projo.R;

public class GroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        // Get the group id and title from the intent
        String groupId = getIntent().getStringExtra("GROUP_ID");
        String groupName = getIntent().getStringExtra("GROUP_NAME");

        // Set the title in the ActionBar (if using)
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(groupName);
        }

        // Implement additional logic for displaying the chatroom content here
    }
}
