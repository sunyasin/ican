package org.ideacreation.can.app.activity.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatImageButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.ideacreation.can.R;
import org.ideacreation.can.app.activity.ContextHolder;
import org.ideacreation.can.app.adapter.LentaMessageListRowItem;
import org.ideacreation.can.app.adapter.StatePagerAdapter;

import static org.ideacreation.can.app.activity.fragment.LentaGroupedStateFragment.ITEM_ID;

/**
 * просмотр сообщения
 */

public class ViewMessageFragment extends CommonStateFragment {

    private static final long MARK_AS_READ_AFTER = 5000;
    TextView headerText, bonusText, mainText;
    ImageView img;
    AppCompatImageButton btnSubscribe, btnBookmark, btnNotify, btnShare, btnNext, btnBack;

    LentaMessageListRowItem messageInfo;

    @Override
    public void refresh(Bundle params) {
        Integer boardMessageId = (Integer) params.get(ITEM_ID);
        if (boardMessageId == null)
            throw new IllegalArgumentException("no ITEM_ID");

        mainTabService.loadMessage(boardMessageId, this);
    }

    public void refresh(final LentaMessageListRowItem messageInfo) {
        this.messageInfo = messageInfo;
        headerText.setText(messageInfo.getAuthorName() + " " + String.valueOf(messageInfo.getPublishDate()));
        mainText.setText(messageInfo.getText());
        bonusText.setText("Бонусов: " + messageInfo.getBonusesCount());

        Handler readHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Toast.makeText(getContextHolder().getContext(), "Прочитано!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        readHandler.sendMessageDelayed(new Message(), MARK_AS_READ_AFTER);
    }

    public static ViewMessageFragment instance(ContextHolder context, StatePagerAdapter statePagerAdapter) {
        ViewMessageFragment fragment = new ViewMessageFragment();
        fragment.setContext(context);
        fragment.setStateHolder(statePagerAdapter);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_message_layout, container, false);
        headerText = rootView.findViewById(R.id.headerText);
        bonusText = rootView.findViewById(R.id.bonusText);
        mainText = rootView.findViewById(R.id.mainText);
        img = rootView.findViewById(R.id.messageImage);
/*
*/
        btnSubscribe = rootView.findViewById(R.id.btnSubscribe);
        btnBookmark = rootView.findViewById(R.id.btnBookmark);
        btnNotify = rootView.findViewById(R.id.btnNotify);
        btnShare = rootView.findViewById(R.id.btnShare);
        btnNext = rootView.findViewById(R.id.btnNext);

        btnSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (messageInfo.getSubscribed()) {
                    Toast.makeText(getContextHolder().getContext(), "Уже есть подписка", Toast.LENGTH_SHORT).show();
                } else {
                    getMainTabService().subscribeMessage(messageInfo.getBoardMsgId(), getContextHolder());
                }
            }
        });
        btnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (messageInfo.getBookmarked()) {
                    Toast.makeText(getContextHolder().getContext(), "Уже в закладках", Toast.LENGTH_SHORT).show();
                } else {
                    getMainTabService().bookmarkMessage(messageInfo.getBoardMsgId(), getContextHolder());//todo ошибка
                }
            }
        });
        btnNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMainTabService().notifyEventMessage(messageInfo.getBoardMsgId());
                Toast.makeText(getContextHolder().getContext(), "Напоминание создано", Toast.LENGTH_SHORT).show();
            }
        });
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (messageInfo.getShared()) {
                    Toast.makeText(getContextHolder().getContext(), "Уже было продублировано ранее", Toast.LENGTH_SHORT).show();
                } else {
                    getMainTabService().shareMessage(messageInfo.getBoardMsgId(), getContextHolder());
                }
            }
        });
        btnBack = rootView.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStateHolder().setCurrentState(TabState.LENTA_GROUPED, null);
            }
        });


        return rootView;
    }


}
