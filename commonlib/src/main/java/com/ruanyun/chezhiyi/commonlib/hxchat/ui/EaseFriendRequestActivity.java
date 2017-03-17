package com.ruanyun.chezhiyi.commonlib.hxchat.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.view.widget.CircleImageView;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import butterknife.ButterKnife;

/**
 * 好友申请
 * Created by ycw on 2016/8/24.
 */
public class EaseFriendRequestActivity extends AutoLayoutActivity implements View.OnClickListener, Topbar.onTopbarClickListener {

    public static final int RESULT_REFUSE = 1083;
    Topbar topbar;
    LinearLayout llUserProfile;
    CircleImageView imgPhoto;
    TextView tvUserName;
    TextView tvOtherInfo;
    Button btnRefuse;
    Button btnAgree;

    private User user;
    private String otherMessage = "";

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.ease_activity_friend_application);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        user = getIntent().getParcelableExtra(C.IntentKey.USER_INFO);
        otherMessage = getIntent().getStringExtra("REASON");

        topbar = getView(R.id.topbar);
        llUserProfile = getView(R.id.ll_user_profile);
        imgPhoto = getView(R.id.img_photo);
        tvUserName = getView(R.id.tv_user_name);
        tvOtherInfo = getView(R.id.tv_other_info);
        btnRefuse = getView(R.id.btn_refuse);
        btnAgree = getView(R.id.btn_agree);

        llUserProfile.setOnClickListener(this);
        btnRefuse.setOnClickListener(this);
        btnAgree.setOnClickListener(this);

        topbar.setTttleText("好友申请")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
        if (user != null) {
            Glide.with(this)
                    .load(FileUtil.getFileUrl(user.getUserPhoto()))
                    .error(R.drawable.em_default_avatar)
                    .into(imgPhoto);
            tvUserName.setText(user.getNickName());
        }
        tvOtherInfo.setText(otherMessage);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_refuse) {            //拒绝
            setResult(RESULT_REFUSE);
            finish();
        } else if (id == R.id.btn_agree) {      //同意
            setResult(RESULT_OK);
            finish();
        } else if (id == R.id.ll_user_profile) {
            AppUtility.goToUserProfile(mContext, user);
        }
    }

    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == R.id.img_btn_left) {
            exit();
        }
    }
}
