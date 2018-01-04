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

import static org.ideacreation.can.app.activity.fragment.LentaGroupedStateFragment.ITEM_ID;
import static org.ideacreation.can.app.activity.fragment.TabState.LENTA_GROUPED;
import static org.ideacreation.can.app.activity.fragment.TabState.LENTA_GROUP_MESSAGES;
import static org.ideacreation.can.app.activity.fragment.TabState.LENTA_GROUP_PROFILES;

public class LentaGroupMessagesStateFragment extends CommonStateFragment {

    protected ListView myListView;
    protected Bundle selectedGroupParams;
    protected Button btnProfiles, btnMessages, btnGrouped;

    public static LentaGroupMessagesStateFragment instance(ContextHolder contextHolder, StatePagerAdapter stateHolder) {
        LentaGroupMessagesStateFragment instance = new LentaGroupMessagesStateFragment();
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
        btnProfiles = view.findViewById(R.id.buttonProfiles);
        btnMessages = view.findViewById(R.id.buttonMessages);
        btnGrouped = view.findViewById(R.id.buttonGrouped);
        initButtonListeners();

        return view;
    }

    protected void initButtonListeners() {

        btnGrouped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStateHolder().setCurrentState(LENTA_GROUPED, selectedGroupParams);
            }
        });

        btnProfiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStateHolder().setCurrentState(LENTA_GROUP_PROFILES, selectedGroupParams);
            }
        });
        btnMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStateHolder().setCurrentState(LENTA_GROUP_MESSAGES, selectedGroupParams);
            }
        });

    }

    private void messageClicked(int position, long id) {
        LentaMessageListRowItem msgItem = (LentaMessageListRowItem) myListView.getAdapter().getItem(position);
        if (msgItem != null) {
            Toast.makeText(getContext(), "messageClicked pos=" + position + ", msgItem id=" + msgItem.getBoardMsgId(), Toast.LENGTH_SHORT).show();
            Log.d(this.getClass().getName(), "messageClicked " + position + ", id=" + id);

            Bundle params = new Bundle(1);
            params.putInt(ITEM_ID, msgItem.getBoardMsgId()); // id на доске - однозначно определяет сообщение и стену автора
            getStateHolder().setCurrentState(TabState.VIEW_MESSAGE, params);
        }
    }

    @Override
    public void refresh(Bundle params) {
        selectedGroupParams = params; // припервом вызове запоминаем группу и передаем ее в случае нажатия доп.кнопок-переключателей
        Integer index = (Integer) params.get(ITEM_ID);
        if (index == null)
            throw new IllegalArgumentException("no ITEM_ID");

        mainTabService.loadGroupMessages(index, (ArrayAdapter) myListView.getAdapter());
    }
}