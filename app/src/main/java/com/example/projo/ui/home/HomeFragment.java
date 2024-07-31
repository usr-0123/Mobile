package com.example.projo.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projo.R;
import com.example.projo.adapters.PostAdapter;
import com.example.projo.adapters.ReportsAdapter;
import com.example.projo.models.PostModel;
import com.example.projo.models.ReportModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private List<ReportModel> postList;
    private ReportsAdapter reportsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recycler_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize post list and adapter
        postList = new ArrayList<>();
        reportsAdapter = new ReportsAdapter(postList);
        recyclerView.setAdapter(reportsAdapter);

        // Setup Firebase database reference
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("reports");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    ReportModel post = postSnapshot.getValue(ReportModel.class);
                    if (post != null) {
                        postList.add(post);
                    }
                }
                reportsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Log the error or show a message to the user
                // Log.e("HomeFragment", "Database error: " + error.getMessage());
                // Optionally, you can show a Toast message to inform the user
            }
        });

        // Return the inflated view
        return view;
    }
}