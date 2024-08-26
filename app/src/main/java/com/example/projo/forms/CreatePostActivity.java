package com.example.projo.forms;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreatePostActivity extends Activity {

    private static final int REQUEST_CODE_ATTACH_FILE = 1;

    private EditText editTextPostMessage;
    private EditText locationEditText;
    private EditText editTextPostTitle;

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private List<Uri> attachmentUris = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        // Initialize database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("reports");

        editTextPostMessage = findViewById(R.id.contentEditText);
        editTextPostTitle = findViewById(R.id.titleEditText);
        locationEditText = findViewById(R.id.locationEditText);
        Button buttonAttachFile = findViewById(R.id.mediaButton);
        Button buttonCreatePost = findViewById(R.id.submitButton);

        buttonAttachFile.setOnClickListener(v -> attachFile());
        buttonCreatePost.setOnClickListener(v -> createPost());
    }

    private void attachFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, REQUEST_CODE_ATTACH_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ATTACH_FILE && resultCode == RESULT_OK) {
            if (data.getClipData() != null) {
                // Handle multiple files
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri fileUri = data.getClipData().getItemAt(i).getUri();
                    attachmentUris.add(fileUri);
                }
                Toast.makeText(this, "Multiple attachments selected", Toast.LENGTH_SHORT).show();
            } else if (data.getData() != null) {
                // Handle single file
                Uri attachmentUri = data.getData();
                attachmentUris.add(attachmentUri);
                Toast.makeText(this, "Single attachment selected", Toast.LENGTH_SHORT).show();
                previewFile(attachmentUri);
            }
        }
    }

    private void previewFile(Uri fileUri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(fileUri, getContentResolver().getType(fileUri));
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(intent, "Preview File"));
    }

    private void createPost() {
        String postMessage = editTextPostMessage.getText().toString().trim();
        String locationDetails = locationEditText.getText().toString().trim();
        String reportTitle = editTextPostTitle.getText().toString().trim();

        if (postMessage.isEmpty()) {
            Toast.makeText(this, "Please enter a post message", Toast.LENGTH_SHORT).show();
            return;
        }

        if (locationDetails.isEmpty()) {
            Toast.makeText(this, "Please enter location details", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get userId from current user
        String userId = (currentUser != null) ? currentUser.getUid() : "unknown_user";

        DatabaseReference newPostRef = databaseReference.push();
        newPostRef.child("location").setValue(locationDetails);
        newPostRef.child("message").setValue(postMessage);
        newPostRef.child("userId").setValue(userId);
        newPostRef.child("reportTitle").setValue(reportTitle);
        newPostRef.child("datetime").setValue(System.currentTimeMillis());

        if (!attachmentUris.isEmpty()) {
            uploadAttachments(attachmentUris, newPostRef);
        } else {
            savePost(newPostRef);
        }
    }

    private void uploadAttachments(List<Uri> attachmentUris, final DatabaseReference newPostRef) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        int totalAttachments = attachmentUris.size();
        int[] completedUploads = {0}; // Counter for completed uploads

        for (Uri attachmentUri : attachmentUris) {
            String fileExtension = getFileExtension(attachmentUri);
            String folderName = getFolderName(fileExtension);

            // Use timestamp and original file name for unique file naming
            String timestamp = String.valueOf(System.currentTimeMillis());
            String fileName = "ReportFile" + timestamp + "_" + Objects.requireNonNull(attachmentUri.getLastPathSegment());
            StorageReference fileRef = storageRef.child(folderName).child(fileName);

            fileRef.putFile(attachmentUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            newPostRef.child("attachmentURLs").push().setValue(uri.toString());

                            // Check if all files have been uploaded
                            completedUploads[0]++;
                            if (completedUploads[0] == totalAttachments) {
                                savePost(newPostRef);
                                showToast();
                            }
                        }).addOnFailureListener(e -> {
                            // Log or handle the failure to get download URL
                            Toast.makeText(CreatePostActivity.this, "Failed to get download URL", Toast.LENGTH_SHORT).show();
                        });
                    })
                    .addOnFailureListener(e -> {
                        // Log or handle the failure to upload file
                        Toast.makeText(CreatePostActivity.this, "Failed to upload attachment", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void showToast() {
        Toast.makeText(this, "Attachments uploaded successfully", Toast.LENGTH_SHORT).show();
    }

    private String getFileExtension(Uri uri) {
        return Objects.requireNonNull(getContentResolver().getType(uri)).split("/")[1];
    }

    private String getFolderName(String fileExtension) {
        switch (fileExtension.toLowerCase()) {
            case "jpg":
            case "jpeg":
            case "png":
                return "images";
            case "mp4":
            case "avi":
            case "mov":
                return "videos";
            case "pdf":
            case "doc":
            case "docx":
                return "documents";
            case "mp3":
            case "wav":
            case "flac":
            case "aac":
            case "alac":
            case "ogg":
            case "aiff":
                return "music";
            default:
                return "others";
        }
    }

    private void savePost(DatabaseReference newPostRef) {
        Toast.makeText(this, "Post created successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}
