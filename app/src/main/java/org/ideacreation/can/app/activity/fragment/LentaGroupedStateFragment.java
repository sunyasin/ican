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

import org.ideacreation.can.App;
import org.ideacreation.can.R;
import org.ideacreation.can.app.activity.ContextHolder;
import org.ideacreation.can.app.adapter.CustomListAdapter;
import org.ideacreation.can.app.adapter.GroupedListRowItem;
import org.ideacreation.can.app.adapter.StatePagerAdapter;
import org.ideacreation.can.app.service.MainTabsService;

import java.util.ArrayList;

import javax.inject.Inject;

import static org.ideacreation.can.app.activity.fragment.TabState.LENTA_BOOKMARKED;
import static org.ideacreation.can.app.activity.fragment.TabState.LENTA_GROUP_PROFILES;
import static org.ideacreation.can.app.activity.fragment.TabState.LENTA_SUBSCRIBED_PROFILES;
import static org.ideacreation.can.app.activity.fragment.TabState.LENTA_TAG_PROFILES;


public class LentaGroupedStateFragment extends CommonStateFragment {
    public static final String ITEM_ID = "block_id";
    public static final String POSITION = "position";
    public static final String AUTHOR_ID = "author_id";

    private ListView myListView;
    private boolean viewCreated = false;

    private ContextHolder context;
    private StatePagerAdapter stateHolder;

    @Inject
    MainTabsService mainTabService;


    public MainTabsService getMainTabService() {
        return mainTabService;
    }

    public LentaGroupedStateFragment() {
        App.getInjector().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_main_tab, container, false);
        myListView = view.findViewById(R.id.myListView);
        myListView.setAdapter(new CustomListAdapter(getContext(), new ArrayList()));
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "" + position + ", id=" + id, Toast.LENGTH_SHORT).show();
                Log.d(this.getClass().getName(), "onItemClick " + position + ", id=" + id);
                groupedBlockClicked(position);
            }
        });

        viewCreated = true;
        refresh(null);
        return view;
    }

    //    @Override
    public void refresh(Bundle params) {
        if (viewCreated) {
            getMainTabService().getLentaGrouped((ArrayAdapter) myListView.getAdapter());
        }
    }

    public StatePagerAdapter getStateHolder() {
        return stateHolder;
    }

    private void groupedBlockClicked(int pos) {
        Log.d(this.getClass().getName(), "onItemClick pos=" + pos);
        Bundle params = new Bundle(1);
        GroupedListRowItem item = (GroupedListRowItem) myListView.getAdapter().getItem(pos);
        params.putInt(ITEM_ID, item.getId());
        params.putInt(POSITION, pos);

        switch (item.getType()) {
            case PRIVATE:
                getStateHolder().setCurrentState(LENTA_GROUP_PROFILES, params);
                break;
            case BOOKMARKED:
                getStateHolder().setCurrentState(LENTA_BOOKMARKED, params);
                break;
            case SUBSCRIBED:
                getStateHolder().setCurrentState(LENTA_SUBSCRIBED_PROFILES, params);
                break;
            case TAGGED:
                getStateHolder().setCurrentState(LENTA_TAG_PROFILES, params);
                break;
        }
    }

    public static LentaGroupedStateFragment instance(ContextHolder contextHolder, StatePagerAdapter stateHolder) {
        LentaGroupedStateFragment instance = new LentaGroupedStateFragment();
        instance.setContext(contextHolder);
        instance.setStateHolder(stateHolder);
        return instance;
    }

    public void setContext(ContextHolder context) {
        this.context = context;
    }

    public void setStateHolder(StatePagerAdapter stateHolder) {
        this.stateHolder = stateHolder;
    }
}