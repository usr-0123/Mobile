package com.example.projo.ui.chats;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projo.R;
import com.example.projo.adapters.ChatsAdapter;
import com.example.projo.models.ChatsModel;
import com.example.projo.pages.ChatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatListFragment extends Fragment {

    private ChatsAdapter chatAdapter;
    private List<ChatsModel> chatList;
    private DatabaseReference chatDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        chatList = new ArrayList<>();
        chatAdapter = new ChatsAdapter(chatList, chat -> {
            // Handle chat click
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            intent.putExtra("chatRoomId", chat.getRecipientId());
            intent.putExtra("recipientEmail", chat.getRecipientEmail());
            startActivity(intent);
        });
        recyclerView.setAdapter(chatAdapter);

        // Initialize chatDatabase reference to "chats" node
        chatDatabase = FirebaseDatabase.getInstance().getReference("chats");

        fetchChats();

        return view;
    }

    private void fetchChats() {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        chatDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ChatsModel chat = snapshot.getValue(ChatsModel.class);
                    if (chat != null && (chat.getSenderId().equals(currentUserId) || chat.getRecipientId().equals(currentUserId))) {
                        chatList.add(chat);
                    }
                }
                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.
            }
        });
    }
}
