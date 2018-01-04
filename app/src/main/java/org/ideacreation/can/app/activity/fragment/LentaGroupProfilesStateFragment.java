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
import static org.ideacreation.can.app.activity.fragment.TabState.LENTA_GROUP_MESSAGES;
import static org.ideacreation.can.app.activity.fragment.TabState.LENTA_GROUP_PROFILES;
import static org.ideacreation.can.app.activity.fragment.TabState.PROFILE_BUSSINESS_GUEST;

public class LentaGroupProfilesStateFragment extends CommonStateFragment {

    protected ListView myListView;
    protected Bundle selectedGroupParams;
    Button btnProfiles, btnMessages, btnGrouped;

    public static LentaGroupProfilesStateFragment instance(ContextHolder contextHolder, StatePagerAdapter stateHolder) {
        LentaGroupProfilesStateFragment instance = new LentaGroupProfilesStateFragment();
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
        btnProfiles = view.findViewById(R.id.buttonProfiles);
        btnMessages = view.findViewById(R.id.buttonMessages);
        btnGrouped = view.findViewById(R.id.buttonGrouped);
        initButtonListeners();
//        BottomNavigationView navigation = (BottomNavigationView) view.findViewById(R.id.navigation);
//        navigation.setSelectedItemId(0);
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

    private void profileClicked(int position, long profileId) {
        Toast.makeText(getContext(), "profile list onItemClick pos=" + position + ", id=" + profileId, Toast.LENGTH_SHORT).show();
        Log.d(this.getClass().getName(), "profile list onItemClick " + position + ", id=" + profileId);

        Bundle params = new Bundle(1);
        params.putInt(ITEM_ID, (int) profileId);
        //getStateHolder().setCurrentState(LENTA_GROUP_PROFILES, params);
        mainTabService.profileItemSelected((int) profileId, (ArrayAdapter) myListView.getAdapter());
        getStateHolder().setCurrentState(PROFILE_BUSSINESS_GUEST, params);
    }

    // загрузка сообщений  для группы. id группы передается в параметрах
    @Override
    public void refresh(Bundle params) {
        if (params == null) {
            return; // еще ничего не выбирали
        }
        selectedGroupParams = params; // припервом вызове запоминаем группу и передаем ее в случае нажатия доп.кнопок-переключателей
        Integer groupId = (Integer) params.get(ITEM_ID);
        if (groupId == null)
            throw new IllegalArgumentException("no ITEM_ID");

        mainTabService.loadGroupProfiles(groupId, (ArrayAdapter) myListView.getAdapter());
    }
}
