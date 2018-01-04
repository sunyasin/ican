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
import org.ideacreation.can.app.adapter.ProfileListAdapter;
import org.ideacreation.can.app.adapter.StatePagerAdapter;

import java.util.ArrayList;

import static org.ideacreation.can.app.activity.fragment.LentaGroupedStateFragment.ITEM_ID;
import static org.ideacreation.can.app.activity.fragment.TabState.LENTA_GROUPED;
import static org.ideacreation.can.app.activity.fragment.TabState.LENTA_SUBSCRIBED_MESSAGES;

public class LentaSubscribedProfileStateFragment extends CommonStateFragment {

    private ListView myListView;

    public static LentaSubscribedProfileStateFragment instance(ContextHolder contextHolder, StatePagerAdapter stateHolder) {
        LentaSubscribedProfileStateFragment instance = new LentaSubscribedProfileStateFragment();
        instance.setContext(contextHolder);
        instance.setStateHolder(stateHolder);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lenta_group_content, container, false);
        myListView = view.findViewById(R.id.myListView);
        myListView.setAdapter(new ProfileListAdapter(getContext(), new ArrayList()));
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                profileClicked(position, id);
            }
        });
        Button btnMessages = view.findViewById(R.id.buttonMessages);
        Button btnGrouped = view.findViewById(R.id.buttonGrouped);
        btnGrouped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStateHolder().setCurrentState(LENTA_GROUPED, null);
            }
        });

        btnMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStateHolder().setCurrentState(LENTA_SUBSCRIBED_MESSAGES, null);
            }
        });

        Button btnProfiles = view.findViewById(R.id.buttonProfiles);
        btnProfiles.setVisibility(View.GONE); // скрыть кнопку профайлы для списка профайлов
        return view;
    }

    private void profileClicked(int position, long profileId) {
        Toast.makeText(getContext(), "profile list onItemClick pos=" + position + ", id=" + profileId, Toast.LENGTH_SHORT).show();
        Log.d(this.getClass().getName(), "profile list onItemClick " + position + ", id=" + profileId);

//        Bundle params = new Bundle(1);
//        params.putInt(ITEM_ID, (int) id);
        //getStateHolder().setCurrentState(LENTA_GROUP_PROFILES, params);
        mainTabService.profileItemSelected((int) profileId, (ArrayAdapter) myListView.getAdapter());
    }

    // загрузка профайлов для группы. id группы передается в параметрах
    @Override
    public void refresh(Bundle params) {
        if (params == null) { // нажали профили
            return;
        }
        Integer groupIndex = (Integer) params.get(ITEM_ID);
        if (groupIndex == null)
            throw new IllegalArgumentException("no ITEM_ID");

        mainTabService.loadSubscribedProfiles((ArrayAdapter) myListView.getAdapter());
    }
}
