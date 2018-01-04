package org.ideacreation.can.app.activity.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import org.ideacreation.can.app.activity.ContextHolder;
import org.ideacreation.can.app.adapter.StatePagerAdapter;

import static org.ideacreation.can.app.activity.fragment.LentaGroupedStateFragment.ITEM_ID;
import static org.ideacreation.can.app.activity.fragment.TabState.LENTA_GROUPED;
import static org.ideacreation.can.app.activity.fragment.TabState.LENTA_TAG_MESSAGES;

public class LentaTagProfilesStateFragment extends LentaGroupProfilesStateFragment {

    public static LentaTagProfilesStateFragment instance(ContextHolder contextHolder, StatePagerAdapter stateHolder) {
        LentaTagProfilesStateFragment instance = new LentaTagProfilesStateFragment();
        instance.setContext(contextHolder);
        instance.setStateHolder(stateHolder);
        return instance;
    }

    @Override
    protected void initButtonListeners() {

        btnGrouped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStateHolder().setCurrentState(LENTA_GROUPED, null);
            }
        });

        btnProfiles.setVisibility(View.GONE);
        btnMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStateHolder().setCurrentState(LENTA_TAG_MESSAGES, selectedGroupParams);
            }
        });
    }

    // загрузка профайлов для группы. id группы передается в параметрах
    @Override
    public void refresh(Bundle params) {
        selectedGroupParams = params; // при первом вызове запоминаем группу и передаем ее в случае нажатия доп.кнопок-переключателей
        Integer groupIndex = (Integer) params.get(ITEM_ID);
        if (groupIndex == null)
            throw new IllegalArgumentException("no ITEM_ID");

        mainTabService.loadTagProfiles(groupIndex, (ArrayAdapter) myListView.getAdapter());
    }
}
