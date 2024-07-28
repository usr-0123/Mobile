package com.example.projo.ui.groups;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projo.R;
import com.example.projo.models.GroupModel;
import com.example.projo.pages.GroupActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GroupListFragment extends Fragment {

    private ListView listView;
    private List<GroupModel> groupList;
    private ArrayAdapter<GroupModel> adapter;
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);

        listView = view.findViewById(R.id.fragment_group_listView);
        groupList = new ArrayList<>();
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, groupList);
        listView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("groups");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groupList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    GroupModel group = postSnapshot.getValue(GroupModel.class);
                    groupList.add(group);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GroupModel selectedGroup = groupList.get(position);
                Intent intent = new Intent(getActivity(), GroupActivity.class);
                intent.putExtra("GROUP_ID", selectedGroup.getId());
                intent.putExtra("GROUP_NAME", selectedGroup.getName());
                startActivity(intent);
            }
        });

        return view;
    }
}