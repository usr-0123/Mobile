package com.example.projo.ui.home;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

import com.example.projo.NotificationHelper;
import com.example.projo.models.ReportModel;
import com.example.projo.models.ReportReplyModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReportViewModel extends ViewModel {

    private final DatabaseReference reportsRef = FirebaseDatabase.getInstance().getReference("reports");
    private final Context context;

    public ReportViewModel(Context context) {
        this.context = context;
        listenForNewReports();
    }

    private void listenForNewReports() {
        reportsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                // New report added
                ReportModel report = dataSnapshot.getValue(ReportModel.class);
                if (report != null) {
                    String reportMessage = report.getMessage();
                    NotificationHelper.showNotification(context, "New Report", "Report: " + reportMessage);
                }

                // Add listener for replies on this new report
                String reportId = dataSnapshot.getKey();
                listenForReplies(reportId);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    private void listenForReplies(String reportId) {
        DatabaseReference repliesRef = reportsRef.child(reportId).child("replies");
        repliesRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                // New reply added
                ReportReplyModel reply = dataSnapshot.getValue(ReportReplyModel.class);
                if (reply != null) {
                    String replyMessage = reply.getMessage();
                    NotificationHelper.showNotification(context, "New Reply", "Reply: " + replyMessage);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
}
