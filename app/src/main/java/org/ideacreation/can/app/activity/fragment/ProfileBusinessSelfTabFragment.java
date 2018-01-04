package org.ideacreation.can.app.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.ideacreation.can.R;
import org.ideacreation.can.app.activity.ContextHolder;
import org.ideacreation.can.app.adapter.StatePagerAdapter;

public class ProfileBusinessSelfTabFragment extends CommonStateFragment {

    public static ProfileBusinessSelfTabFragment newInstance(ContextHolder context, StatePagerAdapter statePagerAdapter) {
        ProfileBusinessSelfTabFragment fragment = new ProfileBusinessSelfTabFragment();
        fragment.setContext(context);
        fragment.setStateHolder(statePagerAdapter);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.profile_business_self_tab, container, false);
        return rootView;
    }
}