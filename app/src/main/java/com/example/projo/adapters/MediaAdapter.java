package com.example.projo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projo.R;

import java.util.List;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.MediaViewHolder> {
    private List<String> attachmentURLs; // Use attachmentURLs

    public MediaAdapter(List<String> attachmentURLs) {
        this.attachmentURLs = attachmentURLs; // Initialize the class variable
    }

    @NonNull
    @Override
    public MediaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_media, parent, false);
        return new MediaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MediaViewHolder holder, int position) {
        String attachmentURL = attachmentURLs.get(position); // Use attachmentURL
        Glide.with(holder.itemView.getContext())
                .load(attachmentURL)
                .into(holder.mediaImage);
    }

    @Override
    public int getItemCount() {
        return attachmentURLs.size(); // Return the size of attachmentURLs
    }

    static class MediaViewHolder extends RecyclerView.ViewHolder {
        ImageView mediaImage;

        public MediaViewHolder(@NonNull View itemView) {
            super(itemView);
            mediaImage = itemView.findViewById(R.id.media_image);
        }
    }
}