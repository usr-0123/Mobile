package com.example.projo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projo.R;
import com.example.projo.models.ChatsModel;

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
            chatName.setText(chat.getName());
            lastMessage.setText(chat.getLastMessage());
            itemView.setOnClickListener(v -> listener.onChatClick(chat));
        }
    }
}