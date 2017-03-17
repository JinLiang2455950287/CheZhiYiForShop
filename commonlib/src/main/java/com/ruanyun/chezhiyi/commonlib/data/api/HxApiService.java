package com.ruanyun.chezhiyi.commonlib.data.api;


import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.HxGroup;
import com.ruanyun.chezhiyi.commonlib.model.ChatMessage;
import com.ruanyun.chezhiyi.commonlib.model.HxUserGroup;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.params.AddFriendParams;
import com.ruanyun.chezhiyi.commonlib.model.params.GroupDetailParams;
import com.ruanyun.chezhiyi.commonlib.model.params.InviteGroupParams;
import com.ruanyun.chezhiyi.commonlib.model.params.MessageListParams;
import com.ruanyun.chezhiyi.commonlib.model.params.ModifyBlackParams;
import com.ruanyun.chezhiyi.commonlib.model.params.QuitGroupParams;
import com.ruanyun.chezhiyi.commonlib.model.params.SearchAddFirendParams;
import com.ruanyun.chezhiyi.commonlib.model.params.UpdateGroupNameCardParams;
import com.ruanyun.chezhiyi.commonlib.model.params.UpdateUserMarkParams;
import com.ruanyun.chezhiyi.commonlib.util.C;

import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Description:后台环信接口 author: zhangsan on 16/7/28 上午9:41.
 */
public interface HxApiService {

    /**friendStatus 1—好友  3-黑名单   【传值为1 获取好友列表 传值3 获取黑名单列表】
     * 1.4.1	好友列表
     **/
    @POST(C.HxApiUrl.URL_GET_CONTACT_LIST)
    Call<ResultBase<List<User>>> getUserList(@Path("userNum") String userNum, @Query("friendStatus") Integer friendStatus, @Query("groupNum") Integer groupNum);

    /** 1.3.2 获取用户所有分组 **/
    @POST(C.HxApiUrl.URL_GET_CONTACT_GROUP)
    Call<ResultBase<List<HxGroup>>> getGroupList(@Path("userNum") String userNum);

    /** 1.3.3	添加分组 **/
    @FormUrlEncoded
    @POST(C.HxApiUrl.URL_ADD_CONTACT_GROUP)
    Call<ResultBase<HxGroup>> addContactGroup(@Path("userNum") String userNum, @Field("groupName") String groupName);

    /** 1.4.2	添加好友【通知】 **/
    @POST(C.HxApiUrl.URL_ADD_CONTACT_FRIEND)
    Call<ResultBase> addFriend(@Path("userNum") String userNum, @Body AddFriendParams param);

    /** 1.4.3	添加好友【回调】 **/
    @POST(C.HxApiUrl.URL_ADD_CONTACT_FRIEND_CALLBACK)
    Call<ResultBase> addFriendCallBack(@Path("userNum") String userNum, @Body AddFriendParams param);

    /** 1.4.4	删除好友 **/
    @FormUrlEncoded
    @POST(C.HxApiUrl.URL_DELETE_CONTACT_FRIEND)
    Call<ResultBase> deleteFriend(@Path("userNum") String userNum, @Field("friendNum") String friendNum);

    /** 1.5.2	获取用户群组 **/
    @POST(C.HxApiUrl.URL_GET_USER_GROUP_LIST)
    Call<ResultBase<List<HxUserGroup>>> getUserGroupList(@Path("userNum") String userNum);

    /** 1.5.3	创建群 **/
    @FormUrlEncoded
    @POST(C.HxApiUrl.URL_ADD_GROUP)
    Call<ResultBase<HxUserGroup>> addGroup(@Path("userNum") String userNum, @Field("groupName") String groupName,@Field("userNums") String userNums);

    /** 1.5.4	删除群 **/
    @FormUrlEncoded
    @POST(C.HxApiUrl.URL_GROUP_DELETE)
    Call<ResultBase> deleteGroup(@Path("userNum") String userNum, @Field("groupNum") String groupNum);

    /** 1.5.5	获取群详情[单个] **/
    @POST(C.HxApiUrl.URL_GET_GROUP_DETAILS)
    Call<ResultBase<HxUserGroup>> getGroupDetails(@Path("userNum") String userNum, @Body GroupDetailParams params);

    /** 1.5.6	修改群信息 **/
    @Multipart
    @POST(C.HxApiUrl.URL_UPDATE_GROUP_DETAILS)
    Call<ResultBase<HxUserGroup>> updateGroupInfo(@Path("userNum") String userNum, @PartMap HashMap<String, RequestBody> map);

    /** 1.6.2	获取群组成员 **/
    @FormUrlEncoded
    @POST(C.HxApiUrl.URL_GET_GROUP_MEMBER)
    Call<ResultBase<List<User>>> getGroupMember(@Path("userNum") String userNum, @Field("groupNum") String groupNum);

    /** 1.6.3	修改群名片 **/
    @POST(C.HxApiUrl.URL_UPDATE_GROUP_NAME_CARD)
    Call<ResultBase> updateGroupNameCard(@Path("userNum") String userNum, @Body UpdateGroupNameCardParams params);

    /** 1.6.4	获取群名片 **/
    @FormUrlEncoded
    @POST(C.HxApiUrl.URL_GET_GROUP_NAME_CARD)
    Call<ResultBase<HxUserGroup>> getGroupNameCard(@Path("userNum") String userNum, @Field("groupNum") String groupNum);

    /** 1.6.5	退出群 **/
    @POST(C.HxApiUrl.URL_QUIT_GROUP)
    Call<ResultBase> quitGroup(@Path("userNum") String userNum, @Body QuitGroupParams params);

    /** 1.6.6	加入群 **/
    @POST(C.HxApiUrl.URL_GROUP_INVITE)
    Call<ResultBase> inviteToGroup(@Path("userNum") String userNum, @Body InviteGroupParams params);

    /** 1.4.5	加入或移除黑名单**/
    @POST(C.HxApiUrl.URL_MODIFY_BLACK)
    Call<ResultBase> modifyBlack(@Path("userNum") String userNum, @Body ModifyBlackParams params);

    /** 1.4.6查找陌生人添加好友 **/
  @POST(C.HxApiUrl.URL_NO_FRIEND_LIST)
    Call<ResultBase<PageInfoBase<User>>> searchAddFriend(@Path("userNum") String userNum, @Body SearchAddFirendParams params);

    /**1.7.2	获取聊天记录
     **/
    @POST(C.HxApiUrl.URL_GET_MESSAGE_LIST)
    Call<ResultBase<PageInfoBase<ChatMessage>>> getMessageList(@Path("userNum") String userNum, @Body MessageListParams params);
   /** 1.7.3获取聊天记录的上下文 **/
   @POST(C.HxApiUrl.URL_GET_MESSAGE_CONTEXT)
   Call<ResultBase<List<ChatMessage>>> getMessageContext(@Path("userNum") String userNum, @Body MessageListParams params);
    /** 1.9.2	根据用户编号获取用户详细信息
     **/
    @POST(C.HxApiUrl.URL_GET_USER_BY_NUM)
    Call<ResultBase<User>> getUserByNum(@Path("userNum") String userNum,@Query("friendNum") String friendNum);

    /** 1.4.8	好友详细信息
     **/
    @POST(C.HxApiUrl.URL_GET_FRIEND_INFO_BY_NUM)
    Call<ResultBase<User>> getFriendShipInfo(@Path("userNum") String userNum,@Query("friendNum") String friendNum);
   /**  **/
    @POST(C.HxApiUrl.URL_UPDATE_USER_MARK)
    Call<ResultBase> updateUserMark(@Path("userNum") String userNum, @Body UpdateUserMarkParams params);

}
