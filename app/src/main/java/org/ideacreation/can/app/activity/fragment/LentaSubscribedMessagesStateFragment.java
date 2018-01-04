package org.ideacreation.can.app.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.ideacreation.can.R;
import org.ideacreation.can.app.activity.ContextHolder;
import org.ideacreation.can.app.adapter.LentaMessageListRowItem;
import org.ideacreation.can.app.adapter.MessagesListAdapter;
import org.ideacreation.can.app.adapter.StatePagerAdapter;

import java.util.ArrayList;

import static org.ideacreation.can.app.activity.fragment.LentaGroupedStateFragment.AUTHOR_ID;
import static org.ideacreation.can.app.activity.fragment.LentaGroupedStateFragment.ITEM_ID;
import static org.ideacreation.can.app.activity.fragment.TabState.LENTA_GROUPED;
import static org.ideacreation.can.app.activity.fragment.TabState.LENTA_SUBSCRIBED_PROFILES;
import static org.ideacreation.can.app.activity.fragment.TabState.VIEW_MESSAGE;

public class LentaSubscribedMessagesStateFragment extends CommonStateFragment {

    private ListView myListView;

    public static LentaSubscribedMessagesStateFragment instance(ContextHolder contextHolder, StatePagerAdapter stateHolder) {
        LentaSubscribedMessagesStateFragment instance = new LentaSubscribedMessagesStateFragment();
        instance.setContext(contextHolder);
        instance.setStateHolder(stateHolder);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lenta_group_content, container, false);
        myListView = view.findViewById(R.id.myListView);
        myListView.setAdapter(new MessagesListAdapter(getContext(), new ArrayList()));
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                messageClicked(position, id);
            }
        });
        Button btnProfiles = view.findViewById(R.id.buttonProfiles);
        Button btnGrouped = view.findViewById(R.id.buttonGrouped);
        btnGrouped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStateHolder().setCurrentState(LENTA_GROUPED, null);
            }
        });

        btnProfiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStateHolder().setCurrentState(LENTA_SUBSCRIBED_PROFILES, null);
            }
        });

        Button btnMessages = view.findViewById(R.id.buttonMessages);
        btnMessages.setVisibility(View.GONE); // скрыть на странице сообщений

        return view;
    }

    private void messageClicked(int position, long id) {
        Toast.makeText(getContext(), "messageClicked pos=" + position + ", id=" + id, Toast.LENGTH_SHORT).show();
        Log.d(this.getClass().getName(), "messageClicked " + position + ", id=" + id);

        LentaMessageListRowItem item = (LentaMessageListRowItem) myListView.getAdapter().getItem(position);
        Bundle params = new Bundle(2);
        params.putInt(ITEM_ID, item.getBoardMsgId());
        params.putInt(AUTHOR_ID, item.getAuthorId());

        getStateHolder().setCurrentState(VIEW_MESSAGE, params);
        // load and view message
    }

    @Override
    public void refresh(Bundle params) {

        mainTabService.loadSubscribedMessages((ArrayAdapter) myListView.getAdapter());
    }
}