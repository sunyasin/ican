package org.ideacreation.can.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.ideacreation.can.R;

import java.util.List;

/**
 */

public class ProfileListAdapter extends ArrayAdapter<ProfileListRowItem> {

    public ProfileListAdapter(Context context, List<ProfileListRowItem> items) {
        super(context, R.layout.profile_list_item_layout);

        addAll(items);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater contextInflater = LayoutInflater.from(getContext());
        View customView = contextInflater.inflate(R.layout.profile_list_item_layout, parent, false);
        TextView text = customView.findViewById(R.id.blockText);
        TextView textTotal = customView.findViewById(R.id.totalLabel);
        ProfileListRowItem item = getItem(position);
        text.setText(item.getName());
        textTotal.setText("Новых: " + item.getUnreadCount());
        return customView;
    }
}
