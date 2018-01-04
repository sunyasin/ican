package org.ideacreation.can.app.activity.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.ideacreation.can.R;
import org.ideacreation.can.app.activity.MainTabActivity;


public class Fragment1 extends Fragment {
    private Button btn1;
    private Button btn2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        btn1 = view.findViewById(R.id.button);
        btn2 = view.findViewById(R.id.button2);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainTabActivity) getActivity()).setActivePage(TabState.LENTA_GROUPED);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainTabActivity) getActivity()).setActivePage(TabState.LENTA_GROUP_MESSAGES);
            }
        });

        return view;
    }
}
