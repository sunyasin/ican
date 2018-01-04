package org.ideacreation.can.app.activity.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.ideacreation.can.App;
import org.ideacreation.can.R;
import org.ideacreation.can.app.activity.ContextHolder;
import org.ideacreation.can.app.activity.MainTabActivity;
import org.ideacreation.can.app.adapter.StatePagerAdapter;

/**
 * A placeholder fragment containing a simple view.
 */

public class LentaTabFragment extends CommonStateFragment {


    private static final String ARG_SECTION_NUMBER = "sn";
    private MainTabEnum tab;

    public LentaTabFragment() {
        App.getInjector().inject(this);
    }

    @Override
    public void refresh(Bundle params) {
//            statePagerAdapter.refresh();
    }

    public MainTabEnum getTab() {
        return tab;
    }

    public void setTab(MainTabEnum tab) {
        this.tab = tab;
    }

    public static LentaTabFragment newInstance(ContextHolder context, MainTabEnum section, StatePagerAdapter statePagerAdapter) {
        LentaTabFragment fragment = new LentaTabFragment();
        fragment.setContext(context);
        fragment.setStateHolder(statePagerAdapter);
        fragment.setTab(section);

        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, section.ordinal());
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        switch (getTab()) {
            case LENTA:
            case PROFILE:
            case MESSAGES:
                return createLentaView(inflater, container);
//                    break;
        }
        return null;
    }


    // создание вида для главного таба "лента" - список групп
    public View createLentaView(final LayoutInflater inflater, final ViewGroup container) {
        View rootView = inflater.inflate(R.layout.lenta_tab, container, false);
        ViewPager viewPager = rootView.findViewById(R.id.container1);

        ((MainTabActivity) getActivity()).setupPagerAdapter(getChildFragmentManager(), viewPager);

        return rootView;
    }
}
