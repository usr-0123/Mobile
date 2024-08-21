package com.example.projo.pages;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projo.R;
import com.example.projo.adapters.ChatAdapter;
import com.example.projo.models.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private String chatRoomId;
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

        // Get the chat room ID from the intent
        chatRoomId = getIntent().getStringExtra("chatRoomId");

        chatTitle = findViewById(R.id.chatTitle);

        chatListView = findViewById(R.id.chat_list_view);
        inputMessage = findViewById(R.id.input_message);
        sendButton = findViewById(R.id.send_button);

        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(this, chatMessages);
        chatListView.setAdapter(chatAdapter);

        // Fetch recipient user details
        fetchRecipientDetails(chatRoomId);

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

    private void fetchRecipientDetails(String recipientUserId) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(recipientUserId);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel recipientUser = dataSnapshot.getValue(UserModel.class);
                if (recipientUser != null) {
                    // Set the chat title to the recipient's name
                    chatTitle.setText(recipientUser.getFirstName() + " " + recipientUser.getLastName());  // or use recipientUser.getEmail()
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
            }
        });
    }
}
