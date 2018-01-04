package org.ideacreation.can.app.activity.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import org.ideacreation.can.app.activity.ContextHolder;
import org.ideacreation.can.app.adapter.StatePagerAdapter;

import static org.ideacreation.can.app.activity.fragment.LentaGroupedStateFragment.ITEM_ID;
import static org.ideacreation.can.app.activity.fragment.TabState.LENTA_GROUPED;
import static org.ideacreation.can.app.activity.fragment.TabState.LENTA_TAG_PROFILES;

public class LentaTagMessagesStateFragment extends LentaGroupMessagesStateFragment {

    public static LentaTagMessagesStateFragment instance(ContextHolder contextHolder, StatePagerAdapter stateHolder) {
        LentaTagMessagesStateFragment instance = new LentaTagMessagesStateFragment();
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

        btnMessages.setVisibility(View.GONE);
        btnProfiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStateHolder().setCurrentState(LENTA_TAG_PROFILES, selectedGroupParams);
            }
        });
    }

    @Override
    public void refresh(Bundle params) {
        selectedGroupParams = params; // припервом вызове запоминаем группу и передаем ее в случае нажатия доп.кнопок-переключателей
        Integer itemId = (Integer) params.get(ITEM_ID);
        if (itemId == null)
            throw new IllegalArgumentException("no ITEM_ID");

        mainTabService.loadTagMessages(itemId, (ArrayAdapter) myListView.getAdapter());
    }
}