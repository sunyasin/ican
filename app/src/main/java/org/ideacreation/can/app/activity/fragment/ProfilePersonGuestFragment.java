package org.ideacreation.can.app.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.ideacreation.can.R;
import org.ideacreation.can.app.activity.ContextHolder;
import org.ideacreation.can.app.adapter.StatePagerAdapter;

public class ProfilePersonGuestFragment extends CommonStateFragment {

    public static ProfilePersonGuestFragment newInstance(ContextHolder context, StatePagerAdapter statePagerAdapter) {
        ProfilePersonGuestFragment fragment = new ProfilePersonGuestFragment();
        fragment.setContext(context);
        fragment.setStateHolder(statePagerAdapter);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.profile_person_self, container, false);
        return rootView;
    }
}