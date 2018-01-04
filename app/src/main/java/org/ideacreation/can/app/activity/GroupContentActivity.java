package org.ideacreation.can.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.ideacreation.can.App;
import org.ideacreation.can.R;
import org.ideacreation.can.app.adapter.ProfileListAdapter;
import org.ideacreation.can.app.service.MainTabsService;
import org.ideacreation.can.common.model.enums.GroupType;

import javax.inject.Inject;

public class GroupContentActivity extends AppCompatActivity {

    @Inject
    MainTabsService mainTabService;

    private ProfileListAdapter listAdapter;
    private GroupType selectedType;
    private int selectedId;

    public GroupContentActivity() {
        App.getInjector().inject(this);
    }


//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            =

    @Override
    protected void onCreate(Bundle params) {
        super.onCreate(params);
        setContentView(R.layout.lenta_group_content);

        params = getIntent().getExtras();
        selectedId = params.getInt(MainTabsService.GROUPED_SELECTED_ID, -1);
        selectedType = GroupType.getByInt(params.getInt(MainTabsService.GROUPED_SELECTED_TYPE, -1));
    }


/*    private void refreshCurrentTab() {
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        switch (navigation.getSelectedItemId()) {
            case 0: // profiles mode menu selected
                refreshProfileList();
                break;
            case 1: // messages mode menu selected
                break;
        }

    }

    private void refreshProfileList() {
//        mainTabService.privateGroupSelected(selectedId, selectedType, this);

    }

    private void refreshMessagesList() {

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //refreshCurrentTab();
    }*/

    public ProfileListAdapter getListAdapter() {
        return listAdapter;
    }

    public void setListAdapter(ProfileListAdapter listAdapter) {
        this.listAdapter = listAdapter;
    }
}
