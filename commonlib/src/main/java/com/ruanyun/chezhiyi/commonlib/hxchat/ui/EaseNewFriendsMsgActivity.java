package com.ruanyun.chezhiyi.commonlib.hxchat.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hyphenate.chat.EMClient;
import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.hxchat.db.InviteMessgeDao;
import com.ruanyun.chezhiyi.commonlib.hxchat.domain.InviteMessage;
import com.ruanyun.chezhiyi.commonlib.hxchat.domain.InviteMessage.InviteMesageStatus;
import com.ruanyun.chezhiyi.commonlib.hxchat.ui.adapter.EaseNewFriendsMsgAdapter;
import com.ruanyun.chezhiyi.commonlib.hxchat.ui.adapter.ShopNewFriendsMsgAdapter;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.model.HxUser;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.params.AddFriendParams;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import retrofit2.Call;

/**
 * Description ：新的好友
 * <p/>
 * Created by ycw on 2016/8/10.
 */
public class EaseNewFriendsMsgActivity extends AutoLayoutActivity implements AdapterView.OnItemClickListener, EaseNewFriendsMsgAdapter.InvitationAction, Topbar.onTopbarClickListener {

    private static final int REQ_FRIEND_REQUEST = 1233;
    Topbar topbar;
    ListView listNewFriends;
    EaseNewFriendsMsgAdapter adapter;
    InviteMessgeDao dao;
    InviteMessage inviteMessage;
    AddFriendParams params = new AddFriendParams();
    private ShopNewFriendsMsgAdapter shopAdapter;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.ease_activity_new_friends_msg);
        initView();
        registerBus();
    }

    private void initView() {
        topbar = getView(R.id.topbar);
        listNewFriends = getView(R.id.list_new_friends);
        topbar.setTttleText("新的朋友")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
        if (app.isClient()) {  //    司机端
            dao = new InviteMessgeDao(this);
            List<InviteMessage> inviteMessages = dao.getMessagesList();
            setUserInfoToInviteMsg(inviteMessages);
            LogX.d(TAG, inviteMessages.toString());
            adapter = new EaseNewFriendsMsgAdapter(mContext, inviteMessages);
            adapter.setInvitationAction(this);
            listNewFriends.setAdapter(adapter);
            listNewFriends.setOnItemClickListener(this);
            dao.saveUnreadMessageCount(0);
        } else {  // 技师端  接口请求  好友列表
            shopAdapter = new ShopNewFriendsMsgAdapter(mContext, R.layout.ease_row_invite_msg, new ArrayList<User>());
            listNewFriends.setAdapter(shopAdapter);
            getNewFriendsList();
        }
    }


    /**
     * 获取技师端的新的好友列表
     */
    private void getNewFriendsList() {
        Call<ResultBase<List<User>>> call = app.getHxApiService().getUserList(app.getCurrentUserNum(), null, 1);
        call.enqueue(new ResponseCallback<ResultBase<List<User>>>() {
            @Override
            public void onSuccess(Call call, ResultBase<List<User>> userList) {
                shopAdapter.setData(userList.getObj());
                shopAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Call call, ResultBase<List<User>> hxUserResult, int erroCode) {
            }

            @Override
            public void onFail(Call call, String msg) {
            }

            @Override
            public void onResult() {
            }
        });
    }


    private void setUserInfoToInviteMsg(List<InviteMessage> msgs) {
        for (InviteMessage msg : msgs) {
            HxUser user = DbHelper.getInstance().getUserByNum(msg.getFrom());
            if (user != null) {
                msg.setUserNick(user.getUserNick());
                msg.setUserAvatar(user.getUserPhoto());
            }
        }
    }

    /**
     * decline invitation       拒绝邀请
     *
     * @param msg
     */
    private void refuseInvitation(final InviteMessage msg) {
        showLoading();
        final String str3 = getResources().getString(R.string.Refuse_with_failure);
        new Thread(new Runnable() {
            public void run() {
                // call api
                try {
                    if (msg.getStatus() == InviteMesageStatus.BEINVITEED) {//decline the invitation
                        EMClient.getInstance().contactManager().declineInvitation(msg.getFrom());
                    } /*else if (msg.getStatus() == InviteMesageStatus.BEAPPLYED) { //decline application to join group
                        EMClient.getInstance().groupManager().declineApplication(msg.getFrom(), msg.getGroupId(), "");
                    } else if (msg.getStatus() == InviteMesageStatus.GROUPINVITATION) {
                        EMClient.getInstance().groupManager().declineInvitation(msg.getGroupId(), msg.getGroupInviter(), "");
                    }*/
                    msg.setStatus(InviteMesageStatus.REFUSED);
                    // update database
                    ContentValues values = new ContentValues();
                    values.put(InviteMessgeDao.COLUMN_NAME_STATUS, msg.getStatus().ordinal());
                    dao.updateMessage(msg.getId(), values);
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            dissMissLoading();
                            adapter.notifyDataSetInvalidated();
                        }
                    });
                } catch (final Exception e) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            dissMissLoading();
                            AppUtility.showToastMsg(str3);
                        }
                    });
                }
            }
        }).start();
    }

    /**
     * accept invitation        接收邀请
     *
     * @param msg
     * @param groupNum
     */
    private void acceptInvite(final InviteMessage msg, String groupNum) {
        params.setGroupNum(groupNum);
        params.setFriendNum(msg.getFrom());
        Call<ResultBase> call = App.getInstance().getHxApiService().addFriendCallBack(App.getInstance().getCurrentUserNum(), params);
        call.enqueue(new ResponseCallback<ResultBase>() {
            @Override
            public void onSuccess(Call call, final ResultBase result) {
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            if (msg.getStatus() == InviteMesageStatus.BEINVITEED) {//accept be friends
                                EMClient.getInstance().contactManager().acceptInvitation(msg.getFrom());
                            } /*else if (msg.getStatus() == InviteMesageStatus.BEAPPLYED) { //accept application to join group
                                EMClient.getInstance().groupManager().acceptApplication(msg.getFrom(), msg.getGroupId());
                            } else if (msg.getStatus() == InviteMesageStatus.GROUPINVITATION) {
                                EMClient.getInstance().groupManager().acceptInvitation(msg.getGroupId(), msg.getGroupInviter());
                            }*/
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtility.showToastMsg(result.getMsg());
                                }
                            });

                            msg.setStatus(InviteMesageStatus.AGREED);
                            // update database
                            ContentValues values = new ContentValues();
                            values.put(InviteMessgeDao.COLUMN_NAME_STATUS, msg.getStatus().ordinal());
                            dao.updateMessage(msg.getId(), values);
                            adapter.notifyDataSetInvalidated();
                        } catch (Exception e) {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    AppUtility.showToastMsg("失败");
//                                }
//                            });
                        }
                    }
                }).start();
            }

            @Override
            public void onError(Call call, ResultBase result, int erroCode) {
                AppUtility.showToastMsg(result.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {
                AppUtility.showToastMsg("失败");
            }

            @Override
            public void onResult() {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // : 2016/8/11 新的好友， 好友的个人信息
        app.beanCacheHelper.getFriendShipInfo(adapter.getItem(position).getFrom(), this, C.EventKey.KEY_USER_PROFILE);
    }

    @Subscribe(threadMode = ThreadMode.MainThread, sticky = true)
    public void onUserResult(Event<User> userResult) {
        if (null != userResult && userResult.value != null) {
            if (userResult.key.equals(C.EventKey.KEY_USER_APPLY)) {
                Intent intent = new Intent(mContext, EaseFriendRequestActivity.class);
                intent.putExtra(C.IntentKey.USER_INFO, userResult.value);
                intent.putExtra("REASON", inviteMessage.getReason());  //附加消息
                startActivityForResult(intent, REQ_FRIEND_REQUEST);
            } else if (userResult.key.equals(C.EventKey.KEY_USER_PROFILE)) {
                AppUtility.goToUserProfile(this, userResult.value);
            }
            EventBus.getDefault().removeAllStickyEvents();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_FRIEND_REQUEST) {
            switch (resultCode) {
                case EaseFriendRequestActivity.RESULT_REFUSE://拒绝
                    refuseInvitation(inviteMessage);
                    break;
                case RESULT_OK://同意
                    acceptInvite(inviteMessage, "1");
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterBus();
    }

    @Override
    public void acceptInviteAction(InviteMessage inviteMessage) {
        this.inviteMessage = inviteMessage;
    }

    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == R.id.img_btn_left) {
            exit();
        }
    }
}
