package com.ruanyun.chezhiyi.commonlib.hxchat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.hyphenate.util.EasyUtils;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.model.HxUser;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * chat activity，EaseChatFragment was used {@link #}
 */
public class ChatActivity
        extends BaseActivity {
    public static ChatActivity activityInstance;
    String toChatUsername;
    private ChatFragment chatFragment;
    // private HxUserGroup groupInfo;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.em_activity_chat);
        activityInstance = this;
        //get user id or group id
        ///  groupInfo=getIntent().getParcelableExtra(C.IntentKey.GROUP_INFO);
        toChatUsername = getIntent().getExtras().getString("userId");
        //use EaseChatFratFragment
        chatFragment = new ChatFragment();
        //pass parameters to chat fragment
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        AppUtility.fixInputMethodManagerLeak(this);
        activityInstance = null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // make sure only one chat activity is opened
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username)) super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }

    private void saveUser(User user) {
        HxUser hxUser = new HxUser();
        hxUser.setUserNum(user.getUserNum());
        if (!TextUtils.isEmpty(user.getFriendNickName())) {
            hxUser.setUserNick(user.getFriendNickName());
        } else {
            hxUser.setUserNick(user.getNickName());
        }
        hxUser.setUserType(user.getUserType());
        hxUser.setUserPhoto(FileUtil.getFileUrl(user.getUserPhoto()));
        DbHelper.getInstance().insertHxUser(hxUser);
    }

    @Subscribe(threadMode = ThreadMode.MainThread, sticky = true, priority = 110)
    public void onUserResult(Event<User> userResult) {
        if (null != userResult && userResult.value != null) {
            if (userResult.key.equals(C.EventKey.KEY_USER_SETTING)) {
                Intent intent = new Intent(this, ChatSettingActivity.class);
                intent.putExtra(C.IntentKey.USER_INFO, userResult.value);
                startActivity(intent);
                saveUser(userResult.value);
            } else if (userResult.key.equals(C.EventKey.KEY_USER_PROFILE)) {
                AppUtility.goToUserProfile(this, userResult.value);
                saveUser(userResult.value);
            }

            EventBus.getDefault().removeStickyEvent(userResult);
        }
    }


    @Subscribe(threadMode = ThreadMode.MainThread, sticky = true, priority = 100)
    public void onGroupExit(String content) {
        if (C.EventKey.EXIT_GROUP.equals(content)) {
            EventBus.getDefault().removeStickyEvent(content);
            finish();

        } else if (!("select".equals(content) || "deselect".equals(content) || content.equals(C.EventKey.KEY_REFRESH_LIST) || content.equals(C.EventKey.KEY_INVITE_SUCCESS))) {
            chatFragment.setTitle(content);
            EventBus.getDefault().removeStickyEvent(content);
        }
    }


    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
        if (EasyUtils.isSingleActivity(this)) {
          /*  Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);*/
        }

    }

    public String getToChatUsername() {
        return toChatUsername;
    }

}
