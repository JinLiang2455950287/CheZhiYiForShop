package com.ruanyun.chezhiyi.commonlib.hxchat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ListView;

import com.hyphenate.easeui.widget.EaseSidebar;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.hxchat.Constant;
import com.ruanyun.chezhiyi.commonlib.hxchat.ui.adapter.ClientContactAdapter;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.model.HxUser;
import com.ruanyun.chezhiyi.commonlib.model.HxUserGroup;
import com.ruanyun.chezhiyi.commonlib.model.params.InviteGroupParams;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.view.widget.CleanableEditText;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import retrofit2.Call;


/**
 * Description:
 * author: zhangsan on 16/8/12 下午1:35.
 */
public class PickContactActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener {
    public static final int TYPE_INVITE = 123;//邀请好友加群
    public static final int TYPE_NEW_GROUP = 321;//新建讨论组

    static final String STR_FINISH = "完成(%s)";
    static final String STR_INVITE = "发起(%s)";

    Topbar topbar;
    CleanableEditText query;
    ListView list;
    EaseSidebar sidebar;
    ClientContactAdapter clientContactAdapter;
    HxUserGroup groupInfo;
    InviteGroupParams params = new InviteGroupParams();
    int pageType, selectCount;
    ArrayList<String> groupMemberNums = new ArrayList<>();
    //ArrayList<HxUser> groupMembers=new ArrayList<>();
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.ease_activity_pick_contact);
        registerBus();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterBus();
    }

    private void initView() {
        pageType = getIntent().getIntExtra(C.IntentKey.PICK_CONTACT_PAGE_TYPE, 0);
        groupInfo = getIntent().getParcelableExtra(C.IntentKey.GROUP_INFO);
        if (pageType == TYPE_INVITE) {
            groupMemberNums = getIntent().getStringArrayListExtra(C.IntentKey.GROUP_MEMBERS);
           // groupMembers=getIntent().getParcelableArrayListExtra(C.IntentKey.GROUP_MEMBERS);
        }
        topbar = getView(R.id.topbar);
        query = getView(R.id.query);
        list = getView(R.id.list);
        sidebar = getView(R.id.sidebar);
        clientContactAdapter = new ClientContactAdapter(mContext, new ArrayList<HxUser>());
        clientContactAdapter.isShowChooser(true);
        clientContactAdapter.setupDataFromDb(getLocalUser());
        list.setAdapter(clientContactAdapter);
        sidebar.setListView(list);
        // TextView tvRight=new TextView(mContext);
        query.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //

            }

            @Override
            public void afterTextChanged(Editable s) {
                clientContactAdapter.getFilter().filter(s);
            }
        });
        topbar.setBackBtnEnable(true)
                .onBackBtnClick()
                .addViewToTopbar(topbar.getTvTitleRight(), topbar.getLayoutParamsImgRight())
                .onRightTextClick()
                .setTopbarClickListener(this);
        if (pageType == TYPE_INVITE) {
            topbar.setTttleText("添加新成员").setRightText("完成");
        } else {
            topbar.setTttleText("选择联系人").setRightText("发起");
        }
        topbar.getTvTitleRight().setEnabled(false);
        topbar.getTvTitleRight().setTextColor(getResources().getColor(R.color.white));
        topbar.getTvTitleRight().setAlpha(0.5f);

    }


    private void onContactPick() {
        if (selectCount > 0) {
            topbar.getTvTitleRight().setEnabled(true);
            topbar.getTvTitleRight().setAlpha(1f);
            topbar.getTvTitleRight().setTextColor(getResources().getColor(R.color.white));
            if (pageType == TYPE_INVITE) {
                topbar.setRightText(String.format(STR_FINISH, selectCount));
            } else {
                topbar.setRightText(String.format(STR_INVITE, selectCount));
            }
        } else if (selectCount == 0) {
            if (pageType == TYPE_INVITE) {
                topbar.setRightText("完成");
            } else {
                topbar.setRightText("发起");
            }
            topbar.getTvTitleRight().setEnabled(false);
            topbar.getTvTitleRight().setAlpha(0.5f);
            topbar.getTvTitleRight().setTextColor(getResources().getColor(R.color.white));
        }

    }

    /**
     * link ClientContactDelegate 62
     *
     * @author zhangsan
     * @date 16/9/6 下午3:54
     * 点击联系人触发
     */
    @Subscribe(threadMode = ThreadMode.MainThread, sticky = true, priority = 100)
    public void onUserSelect(String event) {
        if (!TextUtils.isEmpty(event)) {
            if (event.equals("select")) {
                selectCount++;
            } else {
                selectCount--;
                if (selectCount < 0)
                    selectCount = 0;
            }
            EventBus.getDefault().removeStickyEvent(event);
            onContactPick();
        }
    }

    private List<HxUser> getLocalUser() {
        List<HxUser> users = DbHelper.getInstance().getContactList();
        if (pageType == TYPE_INVITE) {
            for (String num : groupMemberNums) {
                for (HxUser hxUser : users) {
                    if (num.equals(hxUser.getUserNum())) {
                        hxUser.setInvited(true);
                    }
                }
            }
        }
        return users;
    }

    @Override
    public void onTobbarViewClick(View v) {
        if (v.getId() == R.id.img_btn_left) {
            finish();
        } else if (v.getId() == R.id.tv_title_right) {//topbar完成
            String selectUserNum = clientContactAdapter.getSelectUserNum();
            if (!TextUtils.isEmpty(selectUserNum)) {
                if (pageType == TYPE_NEW_GROUP) {
                    creatGroup(clientContactAdapter.getSelectUserNick(), selectUserNum);
                } else if (pageType == TYPE_INVITE) {
                    joinGroup(groupInfo, selectUserNum);
                }
            }

        }

    }

    /**
     * 创建群组
     *
     * @param groupName
     * @param userNums
     */
    private void creatGroup(String groupName, String userNums) {
        showLoading("处理中...");
        app.getHxApiService().addGroup(app.getCurrentUserNum(), groupName, userNums).enqueue(new ResponseCallback<ResultBase<HxUserGroup>>() {
            @Override
            public void onSuccess(Call call, ResultBase<HxUserGroup> hxUserGroupResultBase) {
                groupInfo = hxUserGroupResultBase.getObj();
                //joinGroup(groupInfo, clientContactAdapter.getSelectUserNum());
                if (!TextUtils.isEmpty(groupInfo.getHuanxinNum())) {
                    Event<String> event = new Event<String>();
                    event.key = C.EventKey.KEY_CREATE_GROUP;
                    event.value = clientContactAdapter.getSelectAllUserNick();
                    EventBus.getDefault().postSticky(event);
                    Intent groupChatIntent = AppUtility.getChatIntent(mContext, groupInfo.getHuanxinNum());
                    groupChatIntent.putExtra("chatType", Constant.CHATTYPE_GROUP);
                    groupChatIntent.putExtra(C.IntentKey.GROUP_INFO, groupInfo);
                    showActivity(groupChatIntent);
                    DbHelper.getInstance().saveGroup(groupInfo);
                    //finish();
                }
//                AppUtility.showToastMsg(hxUserGroupResultBase.getMsg());
                finish();
            }

            @Override
            public void onError(Call call, ResultBase<HxUserGroup> hxUserGroupResultBase, int errorCode) {

            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {

            }
        });
    }

    /**
     * 加入群组
     *
     * @param groupsInfo
     * @param userNums
     */
    private void joinGroup(final HxUserGroup groupsInfo, String userNums) {
        if (pageType == TYPE_INVITE) {
            showLoading("正在邀请..");
        }
        params.setFriendNum(userNums);
        params.setGroupNum(groupsInfo.getGroupInfoNum());
        app.getHxApiService().inviteToGroup(app.getCurrentUserNum(), params).enqueue(new ResponseCallback<ResultBase>() {
            @Override
            public void onSuccess(Call call, ResultBase resultBase) {
                if (pageType == TYPE_NEW_GROUP) {
                    if (!TextUtils.isEmpty(groupsInfo.getHuanxinNum())) {
                        Event<String> event = new Event<String>();
                        event.key = C.EventKey.KEY_CREATE_GROUP;
                        event.value = clientContactAdapter.getSelectAllUserNick();
                        EventBus.getDefault().postSticky(event);
                        Intent groupChatIntent = AppUtility.getChatIntent(mContext, groupsInfo.getHuanxinNum());
                        groupChatIntent.putExtra("chatType", Constant.CHATTYPE_GROUP);
                        groupChatIntent.putExtra(C.IntentKey.GROUP_INFO, groupsInfo);
                        showActivity(groupChatIntent);
                        //finish();
                    }
                } else {
                    EventBus.getDefault().postSticky(C.EventKey.KEY_INVITE_SUCCESS);
                }
                AppUtility.showToastMsg(resultBase.getMsg());
                finish();
            }

            @Override
            public void onError(Call call, ResultBase resultBase, int errorCode) {
                AppUtility.showToastMsg(resultBase.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {
                dissMissLoading();
            }
        });
    }

}
