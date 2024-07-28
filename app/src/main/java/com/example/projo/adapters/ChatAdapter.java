package com.example.projo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.projo.R;

import java.util.List;

public class ChatAdapter extends ArrayAdapter<String> {

    public ChatAdapter(Context context, List<String> messages) {
        super(context, 0, messages);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String message = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_chat, parent, false);
        }

        TextView messageTextView = convertView.findViewById(R.id.chat_message);
        messageTextView.setText(message);

        return convertView;
    }
}
