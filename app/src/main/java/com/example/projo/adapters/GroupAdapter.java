package com.example.projo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.projo.R;
import com.example.projo.models.GroupModel;

import java.util.List;

public class GroupAdapter extends BaseAdapter {

    private Context context;
    private List<GroupModel> groupList;

    public GroupAdapter(Context context, List<GroupModel> groupList) {
        this.context = context;
        this.groupList = groupList;
    }

    @Override
    public int getCount() {
        return groupList.size();
    }

    @Override
    public Object getItem(int position) {
        return groupList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_group, parent, false);
        }

        TextView groupNameTextView = convertView.findViewById(R.id.group_name);
        GroupModel group = groupList.get(position);
        groupNameTextView.setText(group.getTitle());

        return convertView;
    }
}

