package com.example.projo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projo.R;
import com.example.projo.models.ReportReplyModel;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ReportRepliesAdapter extends RecyclerView.Adapter<ReportRepliesAdapter.ReportReplyViewHolder> {

    private List<ReportReplyModel> replyList;

    public ReportRepliesAdapter(List<ReportReplyModel> replyList) {
        this.replyList = replyList;
    }

    @NonNull
    @Override
    public ReportReplyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_report_reply, parent, false);
        return new ReportReplyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportReplyViewHolder holder, int position) {
        ReportReplyModel reply = replyList.get(position);
        holder.replyMessage.setText(reply.getMessage());

        // Set the user's full name (first and last name) instead of userId
        if (reply.getUser() != null) {
            String fullName = reply.getUser().getFirstName() + " " + reply.getUser().getLastName();
            holder.replyUser.setText(fullName);
        } else {
            // Fallback in case user data is not available
            holder.replyUser.setText("Unknown User");
        }

        // Convert timestamp to formatted time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String formattedTime = sdf.format(reply.getTimestamp());
        holder.replyTime.setText(formattedTime);
    }

    @Override
    public int getItemCount() {
        return replyList.size();
    }

    static class ReportReplyViewHolder extends RecyclerView.ViewHolder {
        TextView replyMessage, replyUser, replyTime;

        public ReportReplyViewHolder(@NonNull View itemView) {
            super(itemView);
            replyMessage = itemView.findViewById(R.id.replyMessage);
            replyUser = itemView.findViewById(R.id.replyUser);
            replyTime = itemView.findViewById(R.id.replyTime);
        }
    }
}