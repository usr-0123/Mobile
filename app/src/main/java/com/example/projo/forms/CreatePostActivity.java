package com.example.projo.forms;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projo.R;
import com.example.projo.models.PostModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

// CreatePostActivity.java
public class CreatePostActivity extends AppCompatActivity {

    private EditText titleEditText, contentEditText;
    private Button mediaButton, submitButton;
    private Uri mediaUri;
    private String mediaUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        titleEditText = findViewById(R.id.titleEditText);
        contentEditText = findViewById(R.id.contentEditText);
        mediaButton = findViewById(R.id.mediaButton);
        submitButton = findViewById(R.id.submitButton);

        mediaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectMedia();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });
    }

    private void selectMedia() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            mediaUri = data.getData();
        }
    }

    private void submitPost() {
        String title = titleEditText.getText().toString().trim();
        String content = contentEditText.getText().toString().trim();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        if (mediaUri != null) {
            uploadMediaAndSavePost(title, content, userId, date);
        } else {
            savePostToDatabase(title, content, userId, date, null);
        }
    }

    private void uploadMediaAndSavePost(String title, String content, String userId, String date) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("posts").child(System.currentTimeMillis() + "." + getFileExtension(mediaUri));
        storageReference.putFile(mediaUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        mediaUrl = uri.toString();
                        savePostToDatabase(title, content, userId, date, mediaUrl);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle error
            }
        });
    }

    private void savePostToDatabase(String title, String content, String userId, String date, String mediaUrl) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("posts");
        String postId = databaseReference.push().getKey();
        PostModel post = new PostModel(postId, userId, title, content, mediaUrl, date);
        databaseReference.child(postId).setValue(post).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CreatePostActivity.this, "Post created successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    // Handle error
                }
            }
        });
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}