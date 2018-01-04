package org.ideacreation.can.app.service;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.ideacreation.can.App;
import org.ideacreation.can.app.activity.ContextHolder;
import org.ideacreation.can.app.activity.fragment.ViewMessageFragment;
import org.ideacreation.can.app.adapter.GroupedListRowItem;
import org.ideacreation.can.app.adapter.LentaMessageListRowItem;
import org.ideacreation.can.app.adapter.ProfileListAdapter;
import org.ideacreation.can.app.adapter.ProfileListRowItem;
import org.ideacreation.can.app.provider.DataProvider;
import org.ideacreation.can.app.provider.OnResultCallback;
import org.ideacreation.can.app.provider.RestCallTask;
import org.ideacreation.can.common.model.enums.GroupType;
import org.ideacreation.can.common.model.json.ApiResponse;
import org.ideacreation.can.common.model.json.BoardMessageInfo;
import org.ideacreation.can.common.model.json.GroupInfo;
import org.ideacreation.can.common.model.json.PageableResponse;
import org.ideacreation.can.common.model.json.ProfileItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;

/**
 * Created by yas on 15.10.2017.
 */

public class MainTabsService {

    public final static String GROUPED_SELECTED_TYPE = "selected_block_type";
    public final static String GROUPED_SELECTED_ID = "selected_block_id";
    private DataProvider dataProvider;

    @Inject
    public void setDataProvider(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    public MainTabsService() {
        App.getInjector().inject(this);
    }

    public void getLentaGrouped(final ArrayAdapter adapter) {
        Call call = dataProvider.grouped();
        RestCallTask<ApiResponse<List<GroupInfo>>> task = new RestCallTask<>(dataProvider, call, createLentaGroupedCallback(adapter));
        task.execute();
    }

    private OnResultCallback createLentaGroupedCallback(final ArrayAdapter adapter) {
        return new OnResultCallback<ApiResponse<List<GroupInfo>>>() {
            @Override
            public void onAsyncResult(ApiResponse<List<GroupInfo>> result) {
                if (result != null && result.isSuccess()) {
                    List<GroupedListRowItem> list = convertGroupInfoToRowItem(result.getResult());
                    Log.i("createLentaView", "new list = " + list + ", size=" + list.size());
                    adapter.clear();
                    adapter.addAll(list);
                    adapter.notifyDataSetChanged();
                } else {
                    //todo alert
                    Log.e(this.getClass().getName(), "createLentaGroupedCallback error = " + result == null ? "null result" : String.valueOf(result.getError()));
                }
            }
        };
    }

    public List<GroupedListRowItem> convertGroupInfoToRowItem(List<GroupInfo> list) {
        if (list == null)
            return null;
        List<GroupedListRowItem> result = new ArrayList<>(list.size());
        for (GroupInfo groupInfo : list) {
            GroupedListRowItem item = new GroupedListRowItem();
            item.setText(groupInfo.getName());
            item.setTotal(String.valueOf(groupInfo.getItemCount()));
            item.setUnread(String.valueOf(groupInfo.getUnreadCount()));
            item.setId(groupInfo.getId());
            item.setType(groupInfo.getType());
            result.add(item);
        }
        return result;
    }

    private OnResultCallback<ApiResponse<List<ProfileItem>>> createPrivateGroupProfilesCallback(final ArrayAdapter listAdapter) {

        OnResultCallback callback = new OnResultCallback<ApiResponse<List<ProfileItem>>>() {
            @Override
            public void onAsyncResult(ApiResponse<List<ProfileItem>> result) {
                Log.d(this.getClass().getName(), "createPrivateGroupProfilesCallback: result=" + result);
                if (result != null && result.isSuccess()) {
                    List<ProfileListRowItem> list = convertToProfileListRowItem(result.getResult());
                    Log.d(this.getClass().getName(), "new list = " + list + ", size=" + list.size());
                    listAdapter.clear();
                    listAdapter.addAll(list);
                    listAdapter.notifyDataSetChanged();
                } else {
                    //todo alert
                    Log.e(this.getClass().getName(), "createPrivateGroupProfilesCallback error = " + result == null ? "null result" : String.valueOf(result.getError()));
                }
            }
        };
        return callback;
    }

    public ArrayAdapter getAdpaterForGroupedType(ContextHolder contextHolder, GroupType selectedType) {
        switch (selectedType) {
            case PRIVATE:
            case SUBSCRIBED:
            case TAGGED:
            case BOOKMARKED:
            default:
                List<ProfileListRowItem> list = new ArrayList();
                list.add(new ProfileListRowItem());
                list.get(0).setName("blbloba");
                list.get(0).setUnreadCount(122);
                return new ProfileListAdapter(contextHolder.getContext(), list);
        }

    }

    private List<ProfileListRowItem> convertToProfileListRowItem(List<ProfileItem> list) {
        List<ProfileListRowItem> result = new ArrayList<>();
        if (result != null) {
            for (ProfileItem profileItem : list) {
                ProfileListRowItem item = new ProfileListRowItem();
                item.setName(profileItem.getName());
                item.setUnreadCount(profileItem.getUnreadCount());
                result.add(item);
            }
        }
        return result;
    }


    public void loadGroupProfiles(Integer selectedId, ArrayAdapter adapter) {
        Call call = dataProvider.getProfilesOfGroup(selectedId);
        RestCallTask<ApiResponse<List<ProfileItem>>> task = new RestCallTask<>(dataProvider, call, createPrivateGroupProfilesCallback(adapter));
        task.execute();

    }

    public void loadGroupMessages(Integer selectedGroupId, ArrayAdapter adapter) {
        Call call = dataProvider.getMessagesOfGroup(selectedGroupId);
        RestCallTask<ApiResponse<List<BoardMessageInfo>>> task = new RestCallTask<>(dataProvider, call, createPrivateGroupMessagesCallback(adapter));
        task.execute();

    }

    private OnResultCallback<ApiResponse<List<BoardMessageInfo>>> createPrivateGroupMessagesCallback(final ArrayAdapter adapter) {
        return new OnResultCallback<ApiResponse<List<BoardMessageInfo>>>() {
            @Override
            public void onAsyncResult(ApiResponse<List<BoardMessageInfo>> result) {
                if (result != null && result.isSuccess()) {
                    List<LentaMessageListRowItem> list = convertBoardMessageInfoToRowItem(result.getResult());
                    Log.i("createLentaView", "new list = " + list + ", size=" + list.size());
                    adapter.clear();
                    adapter.addAll(list);
                    adapter.notifyDataSetChanged();
                } else {
                    //todo alert
                    Log.e(this.getClass().getName(), "createPrivateGroupMessagesCallback error = " + (result == null ? "null result" : String.valueOf(result.getError())));
                }
            }
        };
    }

    private OnResultCallback<ApiResponse<BoardMessageInfo>> createLoadMessagesCallback(final ViewMessageFragment messageFragment) {
        return new OnResultCallback<ApiResponse<BoardMessageInfo>>() {
            @Override
            public void onAsyncResult(ApiResponse<BoardMessageInfo> result) {
                if (result != null && result.isSuccess()) {
                    LentaMessageListRowItem item = createLentaMessageListRowItem(result.getResult());
                    messageFragment.refresh(item);
                    Log.i("got message ", "item = " + item);
                } else {
                    //todo alert
                    Log.e(this.getClass().getName(), "createPrivateGroupMessagesCallback error = " + result == null ? "null result" : String.valueOf(result.getError()));
                }
            }
        };
    }

    private List<LentaMessageListRowItem> convertBoardMessageInfoToRowItem(List<BoardMessageInfo> msgList) {
        List<LentaMessageListRowItem> result = new ArrayList<>();
        if (result != null) {
            for (BoardMessageInfo msg : msgList) {
                LentaMessageListRowItem item = createLentaMessageListRowItem(msg);
                result.add(item);
                //todo item.setBookmarked(msg.get());
            }
        }
        return result;
    }

    private LentaMessageListRowItem createLentaMessageListRowItem(BoardMessageInfo msg) {
        LentaMessageListRowItem item = new LentaMessageListRowItem();
        item.setBoardMsgId(msg.getBoardId());
        item.setText(msg.getMessage());
        item.setAuthorId(msg.getSubjId());
        item.setAuthorName(msg.getSubjName());
        item.setBonusesForRepost(msg.getBonusTell());
        item.setMessageId(msg.getMsgId());
        item.setEventDate(msg.getEventDate());
        item.setImageFile(msg.getImageFile());
        item.setMessageType(msg.getType());
        item.setPublishDate(msg.getCreated());
        item.setBonusesCount(msg.getViewerBonuses());
        item.setBookmarked(msg.getBookmarked());
        item.setShared(msg.getShared());
        item.setSubscribed(msg.getSubscribed());
        item.setWasRead(msg.getWasRead());

        return item;
    }

    public void loadBookmarkedMessages(ArrayAdapter adapter) {
        Call call = dataProvider.getBookmarkedMessages();
        RestCallTask<ApiResponse<List<BoardMessageInfo>>> task =
                new RestCallTask<>(dataProvider, call, createPrivateGroupMessagesCallback(adapter));
        task.execute();
    }

    public void loadSubscribedProfiles(ArrayAdapter adapter) {
        Call call = dataProvider.getSubscribedProfiles();
        RestCallTask<ApiResponse<List<ProfileItem>>> task =
                new RestCallTask<>(dataProvider, call, createPrivateGroupProfilesCallback(adapter));
        task.execute();
    }

    public void loadSubscribedMessages(ArrayAdapter adapter) {
        Call call = dataProvider.getSubscribedMessages();
        RestCallTask<ApiResponse<List<BoardMessageInfo>>> task =
                new RestCallTask<>(dataProvider, call, createPrivateGroupMessagesCallback(adapter));
        task.execute();
    }

    public void profileItemSelected(int profileId, ArrayAdapter adapter) {
    }

    public void loadMessage(Integer boardMessageId, ViewMessageFragment messageFragment) {
        Call call = dataProvider.getMessageById(boardMessageId);
        RestCallTask<ApiResponse<BoardMessageInfo>> task =
                new RestCallTask<>(dataProvider, call, createLoadMessagesCallback(messageFragment));
        task.execute();
    }

    public void shareMessage(Integer boardMessageId, ContextHolder contextHolder) {
        Call call = dataProvider.shareMessage(boardMessageId);
        RestCallTask<ApiResponse> task =
                new RestCallTask<>(dataProvider, call, createVoidCallback(contextHolder));
        task.execute();
    }

    private OnResultCallback createVoidCallback(final ContextHolder contextHolder) {
        return new OnResultCallback<ApiResponse>() {
            @Override
            public void onAsyncResult(ApiResponse result) {
                if (result != null && result.isSuccess()) {
                } else {
                    Toast.makeText(contextHolder.getContext(), " createVoidCallback=", Toast.LENGTH_SHORT).show();
                    contextHolder.processErrorResponse(result);
                }
            }
        };
    }

    public void notifyEventMessage(Integer boardMessageId) {

    }

    public void bookmarkMessage(Integer boardMessageId, ContextHolder contextHolder) {
        Call call = dataProvider.bookmarkMessage(boardMessageId);
        RestCallTask<ApiResponse> task =
                new RestCallTask<>(dataProvider, call, createVoidCallback(contextHolder));
        task.execute();
    }

    public void subscribeMessage(int profileId, ContextHolder contextHolder) {
        Call call = dataProvider.subscribeOnProfile(profileId);
        RestCallTask<ApiResponse> task =
                new RestCallTask<>(dataProvider, call, createVoidCallback(contextHolder));
        task.execute();

    }

    public void loadTagMessages(Integer tagId, ArrayAdapter adapter) {
        Call call = dataProvider.getMessagesForTag(tagId);
        RestCallTask<ApiResponse<List<BoardMessageInfo>>> task =
                new RestCallTask<>(dataProvider, call, createPrivateGroupMessagesCallback(adapter));
        task.execute();
    }

    public void loadTagProfiles(Integer tagId, ArrayAdapter adapter) {
        Call call = dataProvider.getProfilesForTag(tagId);
        RestCallTask<ApiResponse<PageableResponse<ProfileItem>>> task =
                new RestCallTask<>(dataProvider, call, createPrivateTagProfilesCallback(adapter));
        task.execute();
    }

    private OnResultCallback createPrivateTagProfilesCallback(final ArrayAdapter listAdapter) {
        OnResultCallback callback = new OnResultCallback<ApiResponse<PageableResponse<ProfileItem>>>() {
            @Override
            public void onAsyncResult(ApiResponse<PageableResponse<ProfileItem>> result) {
                Log.d(this.getClass().getName(), "createPrivateTagProfilesCallback: result=" + result);
                if (result != null && result.isSuccess()) {
                    List<ProfileListRowItem> list = convertToProfileListRowItem(result.getResult().result);
                    Log.d(this.getClass().getName(), "new list = " + list + ", size=" + list.size());
                    listAdapter.clear();
                    listAdapter.addAll(list);
                    listAdapter.notifyDataSetChanged();
                } else {
                    //todo alert
                    Log.e(this.getClass().getName(), "createPrivateTagProfilesCallback error = " + (result == null ? "null result" : String.valueOf(result.getError())));
                }
            }
        };
        return callback;
    }
}
