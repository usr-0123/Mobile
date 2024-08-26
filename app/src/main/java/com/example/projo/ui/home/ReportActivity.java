package com.example.projo.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projo.R;
import com.example.projo.adapters.ReportRepliesAdapter;
import com.example.projo.models.ReportModel;
import com.example.projo.models.ReportReplyModel;
import com.example.projo.models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class ReportActivity extends AppCompatActivity {

    private TextView reportTitle, reportLocation, reportMessage, reportDatetime;
    private RecyclerView repliesRecyclerView;
    private List<ReportReplyModel> replyList;
    private ReportRepliesAdapter repliesAdapter;
    private EditText inputReply;
    private Button sendButton;
    private DatabaseReference repliesRef;
    private String reportId;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        // Initialize Firebase Auth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // Get userId from current user
        userId = (currentUser != null) ? currentUser.getUid() : "unknown_user";

        // Initialize views
        reportTitle = findViewById(R.id.reportTitle);
        reportLocation = findViewById(R.id.reportLocation);
        reportMessage = findViewById(R.id.reportMessage);
        reportDatetime = findViewById(R.id.reportDatetime);
        repliesRecyclerView = findViewById(R.id.reportReplyRecyclerView);
        inputReply = findViewById(R.id.input_reply);
        sendButton = findViewById(R.id.send_button);

        // Initialize RecyclerView
        replyList = new ArrayList<>();
        repliesAdapter = new ReportRepliesAdapter(replyList);
        repliesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        repliesRecyclerView.setAdapter(repliesAdapter);

        // Get the reportId from the intent
        reportId = getIntent().getStringExtra("reportId");

        if (reportId != null) {
            fetchReportDetails(reportId);
            fetchReplies(reportId);
        }

        // Get the reference to the replies node under the specific report
        repliesRef = FirebaseDatabase.getInstance().getReference("reports").child(reportId).child("replies");

        // Set click listener for the send button
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendReply();
            }
        });
    }

    private void fetchReportDetails(String reportId) {
        DatabaseReference reportRef = FirebaseDatabase.getInstance().getReference("reports").child(reportId);
        reportRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ReportModel report = dataSnapshot.getValue(ReportModel.class);
                if (report != null) {
                    // Set report details in UI
                    reportTitle.setText("Title: " + report.getReportTitle());
                    reportLocation.setText("Location: " + report.getLocation());
                    reportMessage.setText("Message: " + report.getMessage());

                    // Convert timestamp to formatted date and time
                    long datetime = report.getDatetime();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                    reportDatetime.setText(sdf.format(datetime));

                    // Handle attachments
                    if (report.getMediaUrls() != null && !report.getMediaUrls().isEmpty()) {
                        for (String mediaUrl : report.getMediaUrls()) {
                            addAttachmentToView(mediaUrl);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.
                Log.e("FetchReportDetails", "Error fetching report details", databaseError.toException());
            }
        });
    }

    private void addAttachmentToView(String mediaUrl) {
        ImageView attachmentView = new ImageView(this);
        attachmentView.setPadding(0, 8, 0, 8);
        Glide.with(this).load(mediaUrl).placeholder(R.drawable.ic_menu_gallery).into(attachmentView);
        attachmentView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(mediaUrl), "image/*"); // Adjust MIME type as necessary
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        });

        // Assuming you have a layout to add these views to, e.g., a LinearLayout
        LinearLayout attachmentsLayout = findViewById(R.id.attachmentsLayout);
        attachmentsLayout.addView(attachmentView);
    }

    private void fetchReplies(String reportId) {
        DatabaseReference repliesRef = FirebaseDatabase.getInstance().getReference("reports").child(reportId).child("replies");
        repliesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                replyList.clear();
                for (DataSnapshot replySnapshot : dataSnapshot.getChildren()) {
                    ReportReplyModel reply = replySnapshot.getValue(ReportReplyModel.class);
                    if (reply != null) {
                        // Fetch user details using the userId from the reply
                        fetchUserDetails(reply.getUserId(), reply);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.
            }
        });
    }

    private void fetchUserDetails(String userId, ReportReplyModel reply) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel user = dataSnapshot.getValue(UserModel.class);
                if (user != null) {
                    // Attach the user information to the reply model
                    reply.setUser(user); // Assuming you have a setUser(UserModel user) method in your ReportReplyModel

                    // Add the reply with user details to the list
                    replyList.add(reply);
                    repliesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.
            }
        });
    }


    private void sendReply() {
        // Get userId from current user
        String replyText = inputReply.getText().toString().trim();
        if (!replyText.isEmpty()) {
            // Generate a unique reply ID
            String replyId = UUID.randomUUID().toString();

            // Create a new reply object
            ReportReplyModel reply = new ReportReplyModel(replyId, reportId, userId, replyText, System.currentTimeMillis());

            // Push the reply to Firebase
            repliesRef.child(replyId).setValue(reply)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(ReportActivity.this, "Reply sent", Toast.LENGTH_SHORT).show();
                            inputReply.setText(""); // Clear the input field
                        } else {
                            Toast.makeText(ReportActivity.this, "Failed to send reply", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Please enter a reply", Toast.LENGTH_SHORT).show();
        }
    }
}