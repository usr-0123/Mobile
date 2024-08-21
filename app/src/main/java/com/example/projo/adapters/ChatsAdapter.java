package com.example.projo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projo.R;
import com.example.projo.models.ChatsModel;
import com.example.projo.models.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ChatViewHolder> {

    private final List<ChatsModel> chatList;
    private final OnChatClickListener onChatClickListener;

    public interface OnChatClickListener {
        void onChatClick(ChatsModel chat);
    }

    public ChatsAdapter(List<ChatsModel> chatList, OnChatClickListener onChatClickListener) {
        this.chatList = chatList;
        this.onChatClickListener = onChatClickListener;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chats, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatsModel chat = chatList.get(position);
        holder.bind(chat, onChatClickListener);
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView chatName;
        TextView lastMessage;

        public ChatViewHolder(View itemView) {
            super(itemView);
            chatName = itemView.findViewById(R.id.chatName);
            lastMessage = itemView.findViewById(R.id.lastMessage);
        }

        public void bind(@NonNull final ChatsModel chat, final OnChatClickListener listener) {
            // Fetch user details based on recipientId
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(chat.getRecipientId());
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        UserModel user = dataSnapshot.getValue(UserModel.class);
                        if (user != null) {
                            String recipientName = user.getFirstName() + " " + user.getLastName();
                            chatName.setText(recipientName);
                        } else {
                            chatName.setText(chat.getRecipientEmail());
                        }
                    } else {
                        chatName.setText(chat.getRecipientEmail());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle possible errors.
                }
            });

            lastMessage.setText(chat.getLastMessage());
            itemView.setOnClickListener(v -> listener.onChatClick(chat));
        }
    }
}