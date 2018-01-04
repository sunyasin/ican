package org.ideacreation.can.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.ideacreation.can.app.activity.fragment.LentaTabFragment;
import org.ideacreation.can.app.activity.fragment.MainTabEnum;
import org.ideacreation.can.app.activity.fragment.PlaceholderFragment2;
import org.ideacreation.can.app.activity.fragment.ProfileBusinessSelfTabFragment;

import java.util.HashMap;
import java.util.Map;

/*
    ===============   main tabs adapter ===============
     */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private Map<Integer, Fragment> fragmentList = new HashMap<>();
    private StatePagerAdapter statePagerAdapter;

    public SectionsPagerAdapter(StatePagerAdapter statePagerAdapter, FragmentManager fm) {
        super(fm);
        this.statePagerAdapter = statePagerAdapter;
    }

    @Override
    public Fragment getItem(int position) {
        MainTabEnum tabEnum = MainTabEnum.values()[Math.max(0, position)];
        Fragment tab;
        switch (tabEnum) {
            case PROFILE:
                tab = ProfileBusinessSelfTabFragment.newInstance(null, statePagerAdapter);
                break;
            case LENTA:
                tab = LentaTabFragment.newInstance(null, tabEnum, null);
                break;
            default:
                tab = PlaceholderFragment2.newInstance(1);

        }
        fragmentList.put(position, tab);
        return tab;
    }

    @Override
    public int getCount() {
        return MainTabEnum.values().length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return MainTabEnum.values()[position].getLabel();
    }

    public void refreshAfterInit() {
//            MainTabEnum tabEnum = MainTabEnum.values()[mTabViewPager.getCurrentItem()];
//            statePagerAdapter.refreshAfterInit(tabEnum);

    }
}