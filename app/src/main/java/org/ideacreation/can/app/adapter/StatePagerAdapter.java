package org.ideacreation.can.app.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.ideacreation.can.app.activity.ContextHolder;
import org.ideacreation.can.app.activity.fragment.CommonStateFragment;
import org.ideacreation.can.app.activity.fragment.LentaTabFragment;
import org.ideacreation.can.app.activity.fragment.MainTabEnum;
import org.ideacreation.can.app.activity.fragment.TabState;

import java.util.HashMap;
import java.util.Map;

/*
    ===============================   STATE fragments provider =================
     */
public class StatePagerAdapter extends FragmentStatePagerAdapter {

    private Map<TabState, CommonStateFragment> fragmentList = new HashMap<>();
    private ContextHolder contextHolder;
    private TabState currentState;
    private boolean afterInit = false;


    public StatePagerAdapter(ContextHolder context, FragmentManager fm, TabState initialState) {
        super(fm);
        this.contextHolder = context;
        currentState = initialState;
        //getItem(currentState.ordinal()); // create and store state object for state
    }

    @Override
    public Fragment getItem(int position) {
        if (position >= TabState.values().length)
            return null;
        TabState state = TabState.values()[position];
        Fragment tab = fragmentList.get(state);

        return tab;
    }

    @Override
    public int getCount() {
        return TabState.values().length;
    }

    public void addStateFragment(TabState state, CommonStateFragment fragment) {
        if (fragment != null) {
            fragmentList.put(state, fragment);
//                fragment.setContext(contextHolder);
//                fragment.setStateHolder(this);
        }
    }

    public void refresh() {
//            if (afterInit) {
//                getCurrentStateFragment().refresh();
//            }
    }

    public void setCurrentState(TabState currentState, Bundle params) {
        this.currentState = currentState;

        contextHolder.stateChanged(currentState);
        getCurrentStateFragment().refresh(params);
    }

    private CommonStateFragment getCurrentStateFragment() {
        CommonStateFragment state = fragmentList.get(currentState);
        return state;
    }

    public Fragment getTabFragment(MainTabEnum tabEnum) {
        CommonStateFragment tab = null;

        switch (tabEnum) {
            case LENTA:
            case MESSAGES:
            case PROFILE:
            case SEARCH:
            default:
                tab = LentaTabFragment.newInstance(contextHolder, tabEnum, this);
                break;

        }
        return tab;
    }

    public void refreshAfterInit(MainTabEnum tabEnum) {
        afterInit = true;
        refresh();
    }

    public ContextHolder getContextHolder() {
        return contextHolder;
    }
}
