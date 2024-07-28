package com.example.projo.ui.slideshow;

import android.content.Intent;
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
import com.example.projo.adapters.UsersAdapter;
import com.example.projo.models.UserModel;
import com.example.projo.pages.ChatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

// UsersFragment.java
public class UsersFragment extends Fragment {
    private RecyclerView recyclerView;
    private UsersAdapter adapter;
    private List<UserModel> userList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userList = new ArrayList<>();
        adapter = new UsersAdapter(userList, new UsersAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(UserModel user) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("userId", user.getId());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        fetchUsers();
        return view;
    }

    private void fetchUsers() {
        FirebaseDatabase.getInstance().getReference("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserModel user = dataSnapshot.getValue(UserModel.class);
                    userList.add(user);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors.
            }
        });
    }
}