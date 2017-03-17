package com.ruanyun.chezhiyi.commonlib.hxchat.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMClient;
import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.params.AddFriendParams;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.view.widget.CircleImageView;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import retrofit2.Call;

/**
 * @author wp
 * @ClassName: EaseIdentityVerificationActivity
 * @Description: 身份验证
 * @date 2016/8/24 下午 7:28
 */
public class EaseIdentityVerificationActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener {
    private Topbar topbar;
    private CircleImageView imgPhoto;
    private EditText editRemarks;
    private EditText editInfo;
    private TextView tvNickName;
    private AddFriendParams param = new AddFriendParams();
    private User user;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.ease_activity_identity_verification);
        initView();
    }

    private void initView() {
        topbar = getView(R.id.topbar);
        imgPhoto = getView(R.id.img_photo);
        tvNickName = getView(R.id.tv_nick_name);
        editRemarks = getView(R.id.edit_remarks);
        editInfo = getView(R.id.edit_info);//附加信息
        user = getIntent().getParcelableExtra(C.IntentKey.USER_INFO);
        if (user != null) {
            tvNickName.setText(user.getNickName());     //昵称
            Glide.with(mContext).load(FileUtil.getImageUrl(user.getUserPhoto())).into(imgPhoto);        //头像
        }
        topbar.setTttleText("身份验证")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setRightText("发送")
                .addViewToTopbar(topbar.getTvTitleRight(), topbar.getLayoutParamsImgRight())
                .onRightTextClick()
                .setTopbarClickListener(this);

    }

    public void sendOnclick() {
        // : 2016/8/30 添加好友的参数，好友的附加信息，设置备注
        param.setFriendNum(user.getUserNum());
        param.setNickName(/*editRemarks.getText().toString()*/"");
        param.setRemark(editInfo.getText().toString());
        param.setGroupNum("1");
        Call<ResultBase> call = App.getInstance().getHxApiService().addFriend(App.getInstance().getCurrentUserNum(), param);
        call.enqueue(new ResponseCallback<ResultBase>() {
            @Override
            public void onSuccess(Call call, ResultBase result) {
                notifyMsg(user.getUserNum());
                finish();
//                showActivity(SeacharAddFriendActivity.class);
            }

            @Override
            public void onError(Call call, ResultBase result, int errorCode) {
                AppUtility.showToastMsg(result.getMsg());
                showActivity(SeacharAddFriendActivity.class);
            }

            @Override
            public void onFail(Call call, String msg) {
                AppUtility.showToastMsg("添加好友消息发送失败");
            }

            @Override
            public void onResult() {

            }
        });

    }

    private void notifyMsg(final String toUserNum) {
        new Thread(new Runnable() {
            public void run() {

                try {
                    //demo use a hardcode reason here, you need let user to input if you like
//                    String s = getResources().getString(com.ruanyun.chezhiyi.commonlib.R.string.Add_a_friend);
                    String verification = editInfo.getText().toString();
                    EMClient.getInstance().contactManager().addContact(toUserNum, verification);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            //  progressDialog.dismiss();
                            String s1 = getResources().getString(com.ruanyun.chezhiyi.commonlib.R.string.send_successful);
                            AppUtility.showToastMsg(s1);
                        }
                    });
                } catch (final Exception e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            // progressDialog.dismiss();
                            String s2 = getResources().getString(com.ruanyun.chezhiyi.commonlib.R.string.Request_add_buddy_failure);
                            AppUtility.showToastMsg(s2 + e.getMessage());
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    public void onTobbarViewClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_title_right) {
            sendOnclick();
        } else if (i == R.id.img_btn_left) {
            finish();
        }
    }
}
