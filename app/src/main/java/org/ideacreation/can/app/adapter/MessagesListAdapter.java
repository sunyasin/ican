package org.ideacreation.can.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.ideacreation.can.R;

import java.util.List;

/**
 */

public class MessagesListAdapter extends ArrayAdapter<LentaMessageListRowItem> {

    public MessagesListAdapter(Context context, List<LentaMessageListRowItem> items) {
        super(context, R.layout.lenta_message_list_item_layout);

        addAll(items);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater contextInflater = LayoutInflater.from(getContext());
        View customView = contextInflater.inflate(R.layout.lenta_message_list_item_layout, parent, false);
        TextView headerText = customView.findViewById(R.id.messageHeader);
        TextView messageText = customView.findViewById(R.id.messageText);
        TextView statusText = customView.findViewById(R.id.messageStatus);
        ImageView messageImg = customView.findViewById(R.id.messageImage);
        ImageView messageStatusImg = customView.findViewById(R.id.messageStatusImage);
        Button messageStatusBtn = customView.findViewById(R.id.messageStatusBtn);

        LentaMessageListRowItem item = getItem(position);
        headerText.setText(item.getAuthorName() + " " + (item.getPublishDate() == null ? "" : item.getPublishDate().toString()));
        messageText.setText(item.getText());
        statusText.setText("Поделиться (+" + item.getBonusesForRepost() + " Bns)");

        statusText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "statusText.setOnClickListener", Toast.LENGTH_SHORT).show();

            }
        });
        messageStatusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "messageStatusBtn.setOnClickListener", Toast.LENGTH_SHORT).show();

            }
        });
        return customView;
    }
}
