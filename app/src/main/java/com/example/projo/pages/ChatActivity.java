package com.example.projo.pages;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projo.R;
import com.example.projo.adapters.ChatAdapter;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private String userId;
    private String userName;
    private TextView chatTitle;

    private ListView chatListView;
    private EditText inputMessage;
    private Button sendButton;
    private ChatAdapter chatAdapter;
    private ArrayList<String> chatMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Get the user data from the intent
        userId = getIntent().getStringExtra("userId");
        userName = getIntent().getStringExtra("email");

        chatTitle = findViewById(R.id.chatTitle);
        chatTitle.setText("Chat with " + userName);

        chatListView = findViewById(R.id.chat_list_view);
        inputMessage = findViewById(R.id.input_message);
        sendButton = findViewById(R.id.send_button);

        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(this, chatMessages);
        chatListView.setAdapter(chatAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = inputMessage.getText().toString();
                if (!message.isEmpty()) {
                    chatMessages.add(message);
                    chatAdapter.notifyDataSetChanged();
                    inputMessage.setText("");
                }
            }
        });
    }
}
