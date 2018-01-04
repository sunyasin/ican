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

public class CustomListAdapter extends ArrayAdapter<GroupedListRowItem> {

    public CustomListAdapter(Context context, List<GroupedListRowItem> items) {
        super(context, R.layout.custom_list_item_layout);

        addAll(items);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater contextInflater = LayoutInflater.from(getContext());
        View customView = contextInflater.inflate(R.layout.custom_list_item_layout, parent, false);
        TextView text = customView.findViewById(R.id.blockText);
        TextView textTotal = customView.findViewById(R.id.totalValues);
        GroupedListRowItem item = getItem(position);
        text.setText(item.getText());
        textTotal.setText(item.getTotal() + "\n" + item.getUnread());
        return customView;
    }


}
