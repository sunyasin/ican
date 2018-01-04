package org.ideacreation.can.app.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.ideacreation.can.R;
import org.ideacreation.can.app.activity.fragment.LentaBookmarkedStateFragment;
import org.ideacreation.can.app.activity.fragment.LentaGroupMessagesStateFragment;
import org.ideacreation.can.app.activity.fragment.LentaGroupProfilesStateFragment;
import org.ideacreation.can.app.activity.fragment.LentaGroupedStateFragment;
import org.ideacreation.can.app.activity.fragment.LentaSubscribedMessagesStateFragment;
import org.ideacreation.can.app.activity.fragment.LentaSubscribedProfileStateFragment;
import org.ideacreation.can.app.activity.fragment.LentaTagMessagesStateFragment;
import org.ideacreation.can.app.activity.fragment.LentaTagProfilesStateFragment;
import org.ideacreation.can.app.activity.fragment.ProfileBusinessGuestFragment;
import org.ideacreation.can.app.activity.fragment.ProfileBusinessSelfTabFragment;
import org.ideacreation.can.app.activity.fragment.TabState;
import org.ideacreation.can.app.activity.fragment.ViewMessageFragment;
import org.ideacreation.can.app.adapter.SectionsPagerAdapter;
import org.ideacreation.can.app.adapter.StatePagerAdapter;
import org.ideacreation.can.common.model.json.ApiResponse;

import static org.ideacreation.can.R.id.container;

public class MainTabActivity extends AppCompatActivity implements ContextHolder {


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private ViewPager mTabViewPager;
    private SectionsPagerAdapter mTabPagerAdapter;
    private StatePagerAdapter statePagerAdapter;
    ViewPager lentaPager;

    public ViewPager getLentaPager() {
        return lentaPager;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void stateChanged(TabState currentState) {
        if (lentaPager != null) { // init in process ?
            lentaPager.setCurrentItem(currentState.ordinal());
        }
    }

    @Override
    public void processErrorResponse(ApiResponse result) {

    }

    public StatePagerAdapter getStateHolder() {
        return statePagerAdapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTabPagerAdapter = new SectionsPagerAdapter(statePagerAdapter, getSupportFragmentManager());
        mTabViewPager = (ViewPager) findViewById(container);
        mTabViewPager.setAdapter(mTabPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mTabViewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_tab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            refreshCurrentTab();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void refreshCurrentTab() {
//        MainTabEnum tabEnum = MainTabEnum.values()[mTabViewPager.getCurrentItem()];
////        statePagerAdapter.refreshAfterInit(tabEnum);
//        mPagerAdapter.refreshAfterInit(tabEnum);
    }

    public void setupPagerAdapter(FragmentManager fragmentManager, ViewPager viewPager) {
        if (statePagerAdapter == null) {
            this.statePagerAdapter = new StatePagerAdapter(this, fragmentManager, TabState.LENTA_GROUPED);

            this.statePagerAdapter.addStateFragment(TabState.LENTA_GROUPED, LentaGroupedStateFragment.instance(this, statePagerAdapter));
            this.statePagerAdapter.addStateFragment(TabState.LENTA_GROUP_PROFILES, LentaGroupProfilesStateFragment.instance(this, statePagerAdapter));
            this.statePagerAdapter.addStateFragment(TabState.LENTA_GROUP_MESSAGES, LentaGroupMessagesStateFragment.instance(this, statePagerAdapter));
            this.statePagerAdapter.addStateFragment(TabState.LENTA_BOOKMARKED, LentaBookmarkedStateFragment.instance(this, statePagerAdapter));
            this.statePagerAdapter.addStateFragment(TabState.LENTA_SUBSCRIBED_PROFILES, LentaSubscribedProfileStateFragment.instance(this, statePagerAdapter));
            this.statePagerAdapter.addStateFragment(TabState.LENTA_SUBSCRIBED_MESSAGES, LentaSubscribedMessagesStateFragment.instance(this, statePagerAdapter));
            this.statePagerAdapter.addStateFragment(TabState.LENTA_TAG_PROFILES, LentaTagProfilesStateFragment.instance(this, statePagerAdapter));
            this.statePagerAdapter.addStateFragment(TabState.LENTA_TAG_MESSAGES, LentaTagMessagesStateFragment.instance(this, statePagerAdapter));
            this.statePagerAdapter.addStateFragment(TabState.VIEW_MESSAGE, ViewMessageFragment.instance(this, statePagerAdapter));
            this.statePagerAdapter.addStateFragment(TabState.PROFILE_BUSSINESS_SELF, ProfileBusinessSelfTabFragment.instance(this, statePagerAdapter));
            this.statePagerAdapter.addStateFragment(TabState.PROFILE_BUSSINESS_GUEST, ProfileBusinessGuestFragment.instance(this, statePagerAdapter));

            this.statePagerAdapter.addStateFragment(TabState.PROFILE_PERSON_GUEST, ProfileBusinessGuestFragment.instance(this, statePagerAdapter));
            this.statePagerAdapter.addStateFragment(TabState.PROFILE_PERSON_SELF, ProfileBusinessGuestFragment.instance(this, statePagerAdapter));

            this.lentaPager = viewPager;
            this.lentaPager.setAdapter(this.statePagerAdapter);
        }
    }

    public void setActivePage(TabState page) {
        lentaPager.setCurrentItem(page.ordinal());
    }


}
