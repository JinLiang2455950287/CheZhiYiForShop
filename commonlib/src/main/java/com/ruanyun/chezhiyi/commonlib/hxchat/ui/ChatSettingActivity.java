package com.ruanyun.chezhiyi.commonlib.hxchat.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.widget.EaseAlertDialog;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.hxchat.Constant;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.PrefUtility;
import com.ruanyun.chezhiyi.commonlib.view.widget.CircleImageView;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import butterknife.ButterKnife;

/**
 * Description: 个人聊天设置
 * author: zhangsan on 16/8/24 下午11:23.
 */
public class ChatSettingActivity extends AutoLayoutActivity implements View.OnClickListener, Topbar.onTopbarClickListener {

    Topbar topbar;
    LinearLayout llUserInfo;
    CircleImageView imgPhoto;
    TextView tvUsername;
    ImageView imgSex;
    TextView tvUserNick;
    TextView tvSetRemarks;
    Button btnNews;// 消息免打扰
    Button btnTopChat;
    TextView tvFindChatContent;
    TextView tvEmptyChat;

    private User user;
    private static final int REQ_NICK_NAME = 1223;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.ease_activity_chat_settings);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        topbar = getView(R.id.topbar);
        llUserInfo = getView(R.id.ll_user_info);
        imgPhoto = getView(R.id.img_photo);
        tvUsername = getView(R.id.tv_username);
        imgSex = getView(R.id.img_sex);
        tvUserNick = getView(R.id.tv_user_nick);
        tvSetRemarks = getView(R.id.tv_set_remarks);
        btnNews = getView(R.id.btn_news);
        btnTopChat = getView(R.id.btn_top_chat);
        tvFindChatContent = getView(R.id.tv_find_chat_content);
        tvEmptyChat = getView(R.id.tv_empty_chat);

        tvSetRemarks.setOnClickListener(this);
        btnNews.setOnClickListener(this);
        btnTopChat.setOnClickListener(this);
        tvFindChatContent.setOnClickListener(this);
        tvEmptyChat.setOnClickListener(this);
        llUserInfo.setOnClickListener(this);

        user = getIntent().getParcelableExtra(C.IntentKey.USER_INFO);
        topbar.setTttleText("聊天设置")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
        updateUI();
    }

    /**
     * 界面数据
     */
    private void updateUI() {
        if (user == null || TextUtils.isEmpty(user.getUserNum())) return;
        tvUsername.setText(AppUtility.isNotEmpty(user.getFriendNickName()) ? user.getFriendNickName() : user.getNickName());
        if (user.getUserSexResId() != 0) {
            imgSex.setImageResource(user.getUserSexResId());
        }
        tvUserNick.setText(String.format("昵称：%s", user.getNickName()));
        Glide.with(mContext)
                .load(FileUtil.getImageUrl(user.getUserPhoto()))
                .error(R.drawable.em_default_avatar)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        imgPhoto.setImageDrawable(resource);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        imgPhoto.setImageDrawable(errorDrawable);
                    }
                });
        if(user.getUserNum().equals(PrefUtility.get(C.PrefName.PREF_TOP_CHAT,""))){
            btnTopChat.setSelected(true);
        }
        btnNews.setSelected(PrefUtility.getBoolean(user.getUserNum(),false));
        if (user.getFriendStatus() == 1) {  //是好友显示设置备注
            tvSetRemarks.setVisibility(View.VISIBLE);
        } else {
            tvSetRemarks.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQ_NICK_NAME && data != null) {
            String nickName = data.getStringExtra(C.IntentKey.UPDATE_NICKNAME);
            tvUsername.setText(nickName);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_set_remarks) {
            // : 2016/8/25 设置备注昵称
            Intent i = new Intent(mContext, EaseModifyGroupNameActivity.class);
            i.putExtra(C.IntentKey.UPDATE_TYPE, EaseModifyGroupNameActivity.TYPE_UPDATE_PERSONAL_NAME);
            i.putExtra(C.IntentKey.USER_INFO, user);
            startActivityForResult(i, REQ_NICK_NAME);
//            showActivity(i);

        } else if (id == R.id.btn_news) {
            // TODO: 2016/8/25 消息免打扰
            btnNews.setSelected(!btnNews.isSelected());
            // 被查看的用户  是否设置  消息免打扰
            PrefUtility.put(user.getUserNum(), btnNews.isSelected());
        } else if (id == R.id.btn_top_chat) {
            // TODO: 2016/8/25 置顶聊天
            btnTopChat.setSelected(!btnTopChat.isSelected());
            if(btnTopChat.isSelected()){
                PrefUtility.put(C.PrefName.PREF_TOP_CHAT,user.getUserNum());
            }else {
                PrefUtility.put(C.PrefName.PREF_TOP_CHAT, "");
            }
        } else if (id == R.id.tv_find_chat_content) {
            // : 2016/8/25 查找个人聊天记录
            Intent intent=new Intent(mContext,SearchMsgActivity.class);
            intent.putExtra(C.IntentKey.SEARCH_MSG_TYPE, Constant.CHATTYPE_SINGLE);
            intent.putExtra(C.IntentKey.SEARCH_MSG_NUM,user.getUserNum());
            showActivity(intent);
        } else if (id == R.id.tv_empty_chat) {
            // : 2016/8/25 清空聊天记录
            emptyHistory();
        } else if (id == R.id.ll_user_info) {
//            Intent intent = new Intent(mContext, EaseUserProfileActivity.class);
//            intent.putExtra(C.IntentKey.USER_INFO, user);
//            showActivity(intent);
            AppUtility.goToUserProfile(mContext, user);
        }
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
                    EMClient.getInstance().chatManager().deleteConversation(user.getUserNum(), true);
                }
            }
        }, true).show();
    }

    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == R.id.img_btn_left) {
            finish();
        }
    }
}
