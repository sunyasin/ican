package org.ideacreation.can.app.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.ideacreation.can.R;
import org.ideacreation.can.app.activity.ContextHolder;
import org.ideacreation.can.app.adapter.LentaMessageListRowItem;
import org.ideacreation.can.app.adapter.MessagesListAdapter;
import org.ideacreation.can.app.adapter.StatePagerAdapter;

import java.util.ArrayList;

import static org.ideacreation.can.app.activity.fragment.LentaGroupedStateFragment.ITEM_ID;

public class LentaBookmarkedStateFragment extends CommonStateFragment {

    private ListView myListView;

    public static LentaBookmarkedStateFragment instance(ContextHolder contextHolder, StatePagerAdapter stateHolder) {
        LentaBookmarkedStateFragment instance = new LentaBookmarkedStateFragment();
        instance.setContext(contextHolder);
        instance.setStateHolder(stateHolder);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_tab, container, false);
        myListView = view.findViewById(R.id.myListView);
        myListView.setAdapter(new MessagesListAdapter(getContext(), new ArrayList()));
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                messageClicked(position, id);
            }
        });

        return view;
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
        // load and view message
    }

    @Override
    public void refresh(Bundle params) {
        mainTabService.loadBookmarkedMessages((ArrayAdapter) myListView.getAdapter());
    }
}