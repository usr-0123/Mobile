package com.example.projo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projo.R;
import com.example.projo.models.ReportModel;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.ReportViewHolder> {
    private List<ReportModel> postList;

    public ReportsAdapter(List<ReportModel> postList) {
        this.postList = postList;
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_report, parent, false);
        return new ReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        ReportModel post = postList.get(position);

        // Convert Firebase Timestamp to Date
        long date = post.getDatetime();

        // Format Date to yyyy-MM-dd
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(date);

        // Format Date to hh:mm
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String formattedTime = sdf.format(date);

        // Set the formatted time to the TextView
        holder.datetime.setText(formattedDate + " " + formattedTime);
        holder.location.setText(post.getLocation());
        holder.message.setText(post.getMessage());
        holder.reportTitle.setText(post.getReportTitle());
        holder.userEmail.setText(post.getUserEmail());

        // Load media
        if (post.getMediaUrls() != null && !post.getMediaUrls().isEmpty()) {
            // Assuming only one image for simplicity
            Glide.with(holder.itemView.getContext())
                    .load(post.getMediaUrls().get(0)) // Load the first image
                    .into(holder.imageView); // Use the correct imageView ID
        } else {
            // Optionally, you can hide or clear the ImageView if no media is available
            holder.imageView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    static class ReportViewHolder extends RecyclerView.ViewHolder {
        TextView datetime, location, message, reportTitle, userEmail;
        ImageView imageView; // Use the correct name

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            datetime = itemView.findViewById(R.id.datetime);
            location = itemView.findViewById(R.id.location);
            message = itemView.findViewById(R.id.message);
            reportTitle = itemView.findViewById(R.id.reportTitle);
            userEmail = itemView.findViewById(R.id.userEmail);
            imageView = itemView.findViewById(R.id.imageView); // Corrected name
        }
    }
}