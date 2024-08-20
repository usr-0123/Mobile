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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UsersFragment extends Fragment {
    private UsersAdapter adapter;
    private List<UserModel> userList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userList = new ArrayList<>();
        adapter = new UsersAdapter(userList, new UsersAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(UserModel user) {
                checkChatRoomExists(user);
            }
        });
        recyclerView.setAdapter(adapter);
        fetchUsers();
        return view;
    }

    private void checkChatRoomExists(final UserModel user) {
        String currentUserId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        DatabaseReference chatRoomsRef = FirebaseDatabase.getInstance().getReference("chats");

        chatRoomsRef.orderByChild("participants/" + currentUserId).equalTo(true)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean chatRoomExists = false;
                        String chatRoomId = null;

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if (snapshot.child("participants").child(user.getId()).exists()) {
                                chatRoomExists = true;
                                chatRoomId = snapshot.getKey();
                                break;
                            }
                        }

                        if (chatRoomExists) {
                            openChatRoom(chatRoomId, user);
                        } else {
                            createChatRoom(user);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle possible errors
                    }
                });
    }

    private void createChatRoom(UserModel user) {
        String currentUserId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        DatabaseReference chatRoomsRef = FirebaseDatabase.getInstance().getReference("chats");

        String chatRoomId = chatRoomsRef.push().getKey();
        Map<String, Object> chatRoom = new HashMap<>();
        chatRoom.put("participants_" + currentUserId, true);
        chatRoom.put("participants_" + user.getId(), true);

        assert chatRoomId != null;
        chatRoomsRef.child(chatRoomId).setValue(chatRoom).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    openChatRoom(chatRoomId, user);
                } else {
                    // Handle the error
                }
            }
        });
    }

    private void openChatRoom(String chatRoomId, UserModel user) {
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtra("chatRoomId", chatRoomId);
        intent.putExtra("userId", user.getId());
        intent.putExtra("email", user.getEmail());
        startActivity(intent);
    }

    private void fetchUsers() {
        FirebaseDatabase.getInstance().getReference("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserModel user = dataSnapshot.getValue(UserModel.class);
                    if (user != null) {
                        userList.add(user);
                    }
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