package com.example.projo.ui.home;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.projo.R;

public class ReportActivity extends AppCompatActivity {
    private TextView ReportId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_report);

        // Get the reportId from the intent
        String reportId = getIntent().getStringExtra("reportId");

        ReportId = findViewById(R.id.reportId);

        if (reportId != null) {
            ReportId.setText(reportId);
        }
    }
}