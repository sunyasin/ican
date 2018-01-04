package org.ideacreation.can.app.provider;

import org.ideacreation.can.common.model.json.ApiResponse;
import org.ideacreation.can.common.model.json.BoardMessageInfo;
import org.ideacreation.can.common.model.json.GroupInfo;
import org.ideacreation.can.common.model.json.PageableResponse;
import org.ideacreation.can.common.model.json.ProfileItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DataProvider {

    @GET("/api/grouped")
    Call<ApiResponse<List<GroupInfo>>> grouped();

    @GET("/api/profiles/group/{groupId}")
    Call<ApiResponse<List<ProfileItem>>> getProfilesOfGroup(@Path("groupId") int groupId);

    @GET("/api/profiles/subscribed")
    Call<ApiResponse<List<ProfileItem>>> getSubscribedProfiles();

    @GET("/api/messages/group/{groupId}")
    Call<ApiResponse<List<BoardMessageInfo>>> getMessagesOfGroup(@Path("groupId") int groupId);

    @GET("/api/messages/bookmarked")
    Call<ApiResponse<List<BoardMessageInfo>>> getBookmarkedMessages();

    @GET("/api/messages/subscribed")
    Call<ApiResponse<List<BoardMessageInfo>>> getSubscribedMessages();

    @GET("/api/message/{messageId}")
    Call<ApiResponse<BoardMessageInfo>> getMessageById(@Path("messageId") int messageId);

    @PUT("/api/message/{messageId}/share")
    Call<ApiResponse> shareMessage(@Path("messageId") int messageId);

    @PUT("/api/message/{messageId}/bookmark")
    Call<ApiResponse> bookmarkMessage(@Path("messageId") int messageId);

    @POST("/api/profile/{profileId}/subscribe-toggle")
    Call<ApiResponse> subscribeOnProfile(@Path("profileId") int profileId);

    @GET("/api/messages/tag/{tagId}")
    Call<ApiResponse<List<BoardMessageInfo>>> getMessagesForTag(@Path("tagId") int tagId);

    @GET("/api/profiles/tag/{tagId}")
    Call<ApiResponse<PageableResponse<ProfileItem>>> getProfilesForTag(@Path("tagId") int tagId);
}

