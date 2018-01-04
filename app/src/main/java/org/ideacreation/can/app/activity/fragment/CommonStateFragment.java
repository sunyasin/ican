package org.ideacreation.can.app.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.ideacreation.can.App;
import org.ideacreation.can.app.activity.ContextHolder;
import org.ideacreation.can.app.adapter.StatePagerAdapter;
import org.ideacreation.can.app.service.MainTabsService;

import javax.inject.Inject;

public class CommonStateFragment extends Fragment /*implements ContextHolder */ {
    private ContextHolder context;
    private StatePagerAdapter stateHolder;

    @Inject
    MainTabsService mainTabService;


    public CommonStateFragment() {
        App.getInjector().inject(this);
    }

    public static CommonStateFragment instance(ContextHolder contextHolder, StatePagerAdapter stateHolder) {
        CommonStateFragment instance = new CommonStateFragment();
        instance.setContext(contextHolder);
        instance.setStateHolder(stateHolder);
        return instance;
    }

    public MainTabsService getMainTabService() {
        return mainTabService;
    }

    //    @Override
    public ContextHolder getContextHolder() {
        return context;
    }

    public void setContext(ContextHolder context) {
        this.context = context;
    }


    public StatePagerAdapter getStateHolder() {
        return stateHolder;
    }

    public void setStateHolder(StatePagerAdapter stateHolder) {
        this.stateHolder = stateHolder;
    }

    public void refresh(Bundle params) {
    }
}
