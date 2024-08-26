package com.example.projo.adapters;

import android.annotation.SuppressLint;
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
import com.example.projo.models.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        ReportModel post = postList.get(position);

        // Convert Firebase Timestamp to Date
        long date = post.getDatetime();

        // Format Date to yyyy-MM-dd
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(date);

        // Format Date to hh:mm
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String formattedTime = sdf.format(date);

        // Fetch user details from Firebase using userId
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(post.getUserId());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    UserModel user = dataSnapshot.getValue(UserModel.class);
                    if (user != null) {
                        String recipientName = user.getFirstName() + " " + user.getLastName();
                        holder.senderNames.setText(recipientName);
                    } else {
                        holder.senderNames.setText("Unknown User");
                    }
                } else {
                    holder.senderNames.setText("Unknown User");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.
                holder.senderNames.setText("Error fetching user");
            }
        });

        // Set other data
        holder.datetime.setText(formattedDate + " " + formattedTime);
        holder.location.setText(post.getLocation());
        holder.message.setText(post.getMessage());
        holder.reportTitle.setText(post.getReportTitle());

        // Load media
        if (post.getMediaUrls() != null && !post.getMediaUrls().isEmpty()) {
            // Assuming only one image for simplicity
            Glide.with(holder.itemView.getContext())
                    .load(post.getMediaUrls().get(0)) // Load the first image
                    .into(holder.imageView);
        } else {
            holder.imageView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    static class ReportViewHolder extends RecyclerView.ViewHolder {
        TextView datetime, location, message, reportTitle, senderNames;
        ImageView imageView;

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            datetime = itemView.findViewById(R.id.datetime);
            location = itemView.findViewById(R.id.location);
            message = itemView.findViewById(R.id.message);
            reportTitle = itemView.findViewById(R.id.reportTitle);
            senderNames = itemView.findViewById(R.id.senderNames);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}