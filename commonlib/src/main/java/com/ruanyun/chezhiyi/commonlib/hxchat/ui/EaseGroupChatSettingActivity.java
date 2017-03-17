package com.ruanyun.chezhiyi.commonlib.hxchat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.widget.EaseAlertDialog;
import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.hxchat.Constant;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.model.HxUserGroup;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.params.QuitGroupParams;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleClicksListener;
import com.ruanyun.chezhiyi.commonlib.util.PrefUtility;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.HeaderPickerActivity;
import com.ruanyun.chezhiyi.commonlib.view.widget.CircleImageView;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.imagepicker.AndroidImagePicker;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Description ：群组聊天设置
 * <p/>
 * Created by ycw on 2016/8/11.
 */
public class EaseGroupChatSettingActivity extends HeaderPickerActivity implements View.OnClickListener, Topbar.onTopbarClickListener {
//    private static final int SELECT_PIC = 1213;
    private static final int MODIFY_GROUP_NAME = 1214;

    Topbar topbar;
    TextView tvGroupName;
    TextView tvGroupNumber;
    CircleImageView ivGroupHead1;
    CircleImageView ivGroupHead2;
    CircleImageView ivGroupHead3;
    CircleImageView ivGroupHead4;
    ImageView ivGroupHead5;
    TextView btnMessageAlertSettings;
    TextView btnMessageStickSettings;
    TextView tvFindChatRecord;
    TextView tvClearChatRecord;
    Button btnExitGroup;
    ArrayList<String> groupMemberNums = new ArrayList<String>();
    List<CircleImageView> groupIconList = new ArrayList<>();
    CircleImageView ivGroupHead;
    RelativeLayout rlGroupHead;
    LinearLayout llGroupName;

    private HxUserGroup groupInfo;
    private QuitGroupParams quitGroupParams = new QuitGroupParams();
    // private String userIcons;
    private HashMap<String, RequestBody> groupInfoMap = new HashMap<>();
    private File groupPath;
    private String groupName;
    private String groupRemark;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.ease_activity_group_chat_setting);
        ButterKnife.bind(this);
        initSelectPopView();
        initView();
        registerBus();
    }


    /**
     * 获取上传的头像文件
     * @param file
     */
    @Override
    protected void saveUserHeaderImage(File file) {
        groupPath = file;
        updateGroupInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterBus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
//                case SELECT_PIC:
//                    LogX.d(TAG, data.getData().toString());
//                    Uri uri = data.getData();
//                    try {
//                        //ivGroupHead.setImageBitmap(FileUtil.getBitmapFormUri(mContext, uri));
//                        groupPath = FileUtil.saveBitmapFile(FileUtil.getBitmapFormUri(mContext, uri), "head.jpg");
//                        updateGroupInfo();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    break;
                case AndroidImagePicker.REQ_CAMERA:  // 拍照后 回调
                    if (!TextUtils.isEmpty(androidImagePicker.getCurrentPhotoPath())) {
                        AndroidImagePicker.galleryAddPic(mContext, androidImagePicker.getCurrentPhotoPath());
                        androidImagePicker.notifyPictureTaken();
                    }
                    break;

                case MODIFY_GROUP_NAME:
                    groupName = data.getStringExtra(C.IntentKey.GROUP_NAME);
                    updateGroupInfo();
                    break;
            }
        }
    }

    /**
     * 邀请群聊成功
     * @param result
     */
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onInviteMemberSuccess(String result) {
        if (result.equals(C.EventKey.KEY_INVITE_SUCCESS)) {
            getGroupMembers(groupInfo.getGroupInfoNum());
        }
    }

    private void initView() {
        groupInfo = getIntent().getParcelableExtra(C.IntentKey.GROUP_INFO);
        topbar = getView(R.id.topbar);
        tvGroupName = getView(R.id.tv_group_name);
        llGroupName = getView(R.id.ll_group_name);
        ivGroupHead = getView(R.id.iv_group_head);
        rlGroupHead = getView(R.id.rl_group_head);
        tvGroupNumber = getView(R.id.tv_group_number);
        ivGroupHead1 = getView(R.id.iv_group_head_1);
        ivGroupHead2 = getView(R.id.iv_group_head_2);
        ivGroupHead3 = getView(R.id.iv_group_head_3);
        ivGroupHead4 = getView(R.id.iv_group_head_4);
        ivGroupHead5 = getView(R.id.iv_group_head_5);
        btnMessageAlertSettings = getView(R.id.btn_message_alert_settings);
        btnMessageStickSettings = getView(R.id.btn_message_stick_settings);
        tvFindChatRecord = getView(R.id.tv_find_chat_record);
        tvClearChatRecord = getView(R.id.tv_clear_chat_record);
        btnExitGroup = getView(R.id.btn_exit_group);
        //如果当前群为会议室隐藏加人按钮
        if(app.getStoreInfo()!=null&&groupInfo.getHuanxinNum().equals(app.getStoreInfo().getHuanxinNum())){
            ivGroupHead5.setVisibility(View.GONE);

        }
        ivGroupHead5.setOnClickListener(this);
        btnExitGroup.setOnClickListener(this);
        btnMessageStickSettings.setOnClickListener(this);
        btnMessageAlertSettings.setOnClickListener(this);
        tvGroupNumber.setOnClickListener(this);
        tvClearChatRecord.setOnClickListener(this);
        tvFindChatRecord.setOnClickListener(this);
        rlGroupHead.setOnClickListener(this);
        llGroupName.setOnClickListener(this);
        groupIconList.add(ivGroupHead1);
        groupIconList.add(ivGroupHead2);
        groupIconList.add(ivGroupHead3);
        groupIconList.add(ivGroupHead4);
        topbar.setBackBtnEnable(true)
                .onBackBtnClick()
                .setTttleText("群聊天设置")
                .setTopbarClickListener(this);
        updateUI(groupInfo);
       // if (groupInfo.getRelationType() == 1) {//群主
        if (groupInfo.getUserNum().equals(app.getCurrentUserNum())) {
            btnExitGroup.setText("解散群");
        } else {//群员
            btnExitGroup.setText("退出群");
        }
        if (PrefUtility.get(C.PrefName.PREF_TOP_CHAT, "").equals(groupInfo.getHuanxinNum())) {
            btnMessageStickSettings.setSelected(true);
        }
        //是否消息免打扰
        btnMessageAlertSettings.setSelected(PrefUtility.getBoolean(groupInfo.getGroupInfoNum(), false));
        getGroupMembers(groupInfo.getGroupInfoNum());
    }

    private void updateUI(HxUserGroup groupInfo) {
        tvGroupName.setText(groupInfo.getGroupName());
        //显示群图片
        //ImageUtil.loadImage(this, FileUtil.getImageUrl(groupInfo.getGroupPath()), ivGroupHead, R.drawable.default_imge_big);
        Glide.with(mContext)
                .load(FileUtil.getImageUrl(groupInfo.getGroupPath()))
                .error(R.drawable.default_img)
                .into(ivGroupHead);
        DbHelper.getInstance().saveGroup(groupInfo);
        LogX.d(TAG, "------------image--------->" + FileUtil.getImageUrl(groupInfo.getGroupPath()));
    }

    /**
     * 显示群中 前四个头像
     *
     * @author zhangsan
     * @date 16/9/6 下午1:57
     */
    private void setupMembersAvavtar(List<User> users) {
        int count = 0;
        for (final User user : users) {
            if (count <= 3) {
                CircleImageView imageView = groupIconList.get(count);
                imageView.setVisibility(View.VISIBLE);
                imageView.setOnClickListener(new NoDoubleClicksListener() {
                    @Override public void noDoubleClick(View v) {
                        getFriendShipInfo(user.getUserNum());
                    }
                });
                if (mContext != null)
                    Glide.with(mContext)
                            .load(FileUtil.getFileUrl(user.getUserPhoto()))
                            .error(R.drawable.icon_new_friends)
                            .into(imageView);
                count++;
            }
        }
    }

    private void getGroupMemberNums(List<User> users) {
        for (User user : users) {
            groupMemberNums.add(user.getUserNum());
        }
    }

    private void getGroupMembers(String groupNum) {
        app.getHxApiService().getGroupMember(app.getCurrentUserNum(), groupNum).enqueue(new ResponseCallback<ResultBase<List<User>>>() {
            @Override
            public void onSuccess(Call call, ResultBase<List<User>> listResult) {
                List<User> users = listResult.getObj();
                tvGroupNumber.setText(new StringBuilder().append(users.size()).append("人").toString());
                setupMembersAvavtar(users);
                getGroupMemberNums(users);
            }

            @Override
            public void onError(Call call, ResultBase<List<User>> listResultBase, int errorCode) {

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
     * 根据用户编号获取  含有朋友的关系 的用户信息
     * @param userNum   用户编号
     */
    private void getFriendShipInfo(String userNum) {
        Call<ResultBase<User>> call = App.getInstance().getHxApiService().getFriendShipInfo(
                        App.getInstance().getCurrentUserNum(), userNum);
        call.enqueue(new ResponseCallback<ResultBase<User>>() {
            @Override
            public void onSuccess(Call call, ResultBase<User> userResult) {
                AppUtility.goToUserProfile(mContext, userResult.getObj());
            }

            @Override
            public void onError(Call call, ResultBase<User> userResult, int errorCode) {
                AppUtility.showToastMsg(userResult.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {
                AppUtility.showToastMsg(msg);
            }

            @Override
            public void onResult() {
            }
        });
    }

    /**
     * 退出群
     *
     * @author zhangsan
     * @date 16/8/23 下午3:34
     */
    private void exitGroup() {
        showLoading("处理中...");
        quitGroupParams.setGroupNum(groupInfo.getGroupInfoNum());
        quitGroupParams.setFriendNum(app.getCurrentUserNum());
        app.getHxApiService().quitGroup(app.getCurrentUserNum(), quitGroupParams).enqueue(new ResponseCallback<ResultBase>() {
            @Override
            public void onSuccess(Call call, ResultBase resultBase) {
                EventBus.getDefault().postSticky(C.EventKey.EXIT_GROUP);
                EventBus.getDefault().post(new Event<HxUserGroup>(C.EventKey.EXIT_GROUP,groupInfo));
                finish();
            }

            @Override
            public void onError(Call call, ResultBase resultBase, int errorCode) {

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

    /**
     * 群主解散群
     *
     * @author zhangsan
     * @date 16/8/23 下午3:34
     */
    private void deleteGroup(String groupNum) {
        showLoading("处理中...");
        app.getHxApiService().deleteGroup(app.getCurrentUserNum(), groupNum).enqueue(new ResponseCallback<ResultBase>() {
            @Override
            public void onSuccess(Call call, ResultBase resultBase) {
                EventBus.getDefault().postSticky(C.EventKey.EXIT_GROUP);
              //  EventBus.getDefault().post(new Event<HxUserGroup>(C.EventKey.EXIT_GROUP,groupInfo));
                finish();
            }

            @Override
            public void onError(Call call, ResultBase resultBase, int errorCode) {

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

    /**
     * 清空聊天记录
     */
    public void emptyHistory() {
        String msg = getResources().getString(com.hyphenate.easeui.R.string.Whether_to_empty_all_chats);
        new EaseAlertDialog(mContext, null, msg, null, new EaseAlertDialog.AlertDialogUser() {

            @Override
            public void onResult(boolean confirmed, Bundle bundle) {
                if (confirmed) {
                    EMClient.getInstance().chatManager().deleteConversation(groupInfo.getHuanxinNum(), true);

                }
            }
        }, true).show();
    }


    /**
     * 修改群信息
     */
    private void updateGroupInfo() {
        showLoading();
        //群编号
        groupInfoMap.put("groupNum", RequestBody.create(MediaType.parse("text/plain"), groupInfo.getGroupInfoNum()));
        //群头像 图片流 【可为空】
        if (groupPath != null)
            groupInfoMap.put("groupPathPic\"; filename=\"" + groupPath.getName(), RequestBody.create(MediaType.parse("image/jpeg"), groupPath));
        //群名称【可为空】
        if (AppUtility.isNotEmpty(groupName))
            groupInfoMap.put("groupName", RequestBody.create(MediaType.parse("text/plain"), groupName));
        //群备注 【可为空】
        if (AppUtility.isNotEmpty(groupRemark))
            groupInfoMap.put("remark", RequestBody.create(MediaType.parse("text/plain"), groupRemark));
        Call<ResultBase<HxUserGroup>> call = app.getHxApiService().updateGroupInfo(app.getCurrentUserNum(), groupInfoMap);
        call.enqueue(new ResponseCallback<ResultBase<HxUserGroup>>() {
            @Override
            public void onSuccess(Call call, ResultBase<HxUserGroup> hxUserGroupResultBase) {

                HxUserGroup group = hxUserGroupResultBase.getObj();
                if (group == null  || TextUtils.isEmpty(group.getHuanxinNum())) return;
                groupInfo = group;
                updateUI(groupInfo);
                Intent intent = new Intent();
                intent.putExtra(C.IntentKey.GROUP_INFO, groupInfo);
                setResult(RESULT_FIRST_USER, intent); // 修改群组名称后  返回chatFragment  刷新topbar  回调标志
            }

            @Override
            public void onError(Call call, ResultBase<HxUserGroup> hxUserGroupResultBase, int errorCode) {
                AppUtility.showToastMsg(hxUserGroupResultBase.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {
//                AppUtility.showToastMsg("失败");
            }

            @Override
            public void onResult() {
                dissMissLoading();
            }
        });
    }


    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.btn_exit_group) {//退出群
           // if (groupInfo.getRelationType() == 1) {//群主
            if (groupInfo.getUserNum().equals(app.getCurrentUserNum())){
                deleteGroup(groupInfo.getGroupInfoNum());
            } else {
                exitGroup();
            }
        } else if (viewId == R.id.tv_find_chat_record) {//查找聊天记录
            Intent intent = new Intent(mContext, SearchMsgActivity.class);
            intent.putExtra(C.IntentKey.SEARCH_MSG_TYPE, Constant.CHATTYPE_GROUP);
            intent.putExtra(C.IntentKey.SEARCH_MSG_NUM, groupInfo.getGroupInfoNum());
            showActivity(intent);
        } else if (viewId == R.id.tv_clear_chat_record) {//清空聊天记录
            emptyHistory();
        } else if (viewId == R.id.btn_message_alert_settings) {//消息免打扰
            btnMessageAlertSettings.setSelected(!btnMessageAlertSettings.isSelected());
            PrefUtility.put(groupInfo.getGroupInfoNum(), btnMessageAlertSettings.isSelected());
        } else if (viewId == R.id.btn_message_stick_settings) {//置顶
            btnMessageStickSettings.setSelected(!btnMessageStickSettings.isSelected());
            if (btnMessageStickSettings.isSelected()) {
                PrefUtility.put(C.PrefName.PREF_TOP_CHAT, groupInfo.getHuanxinNum());
            } else {
                PrefUtility.put(C.PrefName.PREF_TOP_CHAT, "");
            }
        } else if (viewId == R.id.iv_group_head_5) {//添加群成员
            Intent intent = new Intent(mContext, PickContactActivity.class);
            intent.putExtra(C.IntentKey.PICK_CONTACT_PAGE_TYPE, PickContactActivity.TYPE_INVITE);
            intent.putExtra(C.IntentKey.GROUP_INFO, groupInfo);
            intent.putStringArrayListExtra(C.IntentKey.GROUP_MEMBERS, groupMemberNums);
            startActivityForResult(intent, 1);
        } else if (viewId == R.id.tv_group_number) {
            Intent intent = new Intent(mContext, GroupMembersActivity.class);
            intent.putExtra(C.IntentKey.GROUP_NUM, groupInfo.getGroupInfoNum());
            showActivity(intent);
        } else if (viewId == R.id.rl_group_head) {   //  修改群头像
           // if (groupInfo.getRelationType() == 1) {//群主
            if (groupInfo.getUserNum().equals(app.getCurrentUserNum())){
                // TODO: 2016/11/26 ImagerPick 选择图片
//                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(i, SELECT_PIC);
                showSelectPopView(rlGroupHead);

            } else {
                AppUtility.showToastMsg("群主才能修改群头像");
            }
        } else if (viewId == R.id.ll_group_name) {   //  修改群名称
          //  if (groupInfo.getRelationType() == 1) {//群主
            if (groupInfo.getUserNum().equals(app.getCurrentUserNum())){
                Intent i = new Intent(mContext, EaseModifyGroupNameActivity.class);
                i.putExtra(C.IntentKey.UPDATE_TYPE, EaseModifyGroupNameActivity.TYPE_UPDATE_GROUP_NAME);
                i.putExtra(C.IntentKey.GROUP_NAME, groupInfo.getGroupName());
                startActivityForResult(i, MODIFY_GROUP_NAME);
            } else {
                AppUtility.showToastMsg("群主才能修改名称");
            }
        }
    }

    @Override
    public void onTobbarViewClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.img_btn_left) {
            finish();
        }
    }
}
