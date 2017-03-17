package com.ruanyun.chezhiyi.commonlib.hxchat.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.AccountMoneyInfo;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.RecordActivity;
import com.ruanyun.chezhiyi.commonlib.view.widget.CircleImageView;
import com.ruanyun.chezhiyi.commonlib.view.widget.FlowLayout;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;


/**
 * 司机的  个人资料页
 * Created by ycw on 2016/8/24.
 */
public class PersonInfoActivity extends AutoLayoutActivity implements View.OnClickListener, Topbar.onTopbarClickListener {

    private static final int REQ_NICK_NAME = 1;
    Topbar topbar;
    CircleImageView imgPhoto;       //头像
    TextView tvRemarkName;          //备注
    TextView editRemarks;           //设置备注
    LinearLayout llRemarks;         //设置备注是否显示
    TextView tvPhone;               //手机号
    LinearLayout llPhone;           //是否显示手机号
    Button btnAddFriends;           //加好友
    Button btSendMessage;           //发送消息
    Button btnTelPhone;             //拨打电话
    TextView tvnickname;            //下面的昵称
    TextView tvIntroduction;        //个新签名  个人说明
    TextView tvIntroductionInfo;    //个新签名   内容
    LinearLayout llIntroduction;    //个新签名 是否显示
    LinearLayout llAccount;         //账户余额  是否显示
    TextView tvAccount;             //账户余额
    TextView tvHobby;               //个人爱好
    LinearLayout llHobby;           //个人爱好  是否显示
    FlowLayout flowHobbyInfo;       //个人爱好 标签
    TextView tvRecord;          //消费记录
    private User user; //被查看的用户

    /**
     * 技师查看非好友司机
     */
    private static final int USER_RELATION_SHOP_TO_CLIENT_NOT_FRIENT = 3;
    /**
     * 技师看司机  好友
     */
    private static final int USER_RELATION_SHOP_TO_CLIENT = 1;
    /**
     * 司机看自己 或者  看其他司机
     */
    private static final int USER_RELATION_CLIENT_TO_SELF = 2;
    /**
     * 技师接待时查看用户信息
     */
    private static final int USER_RELATION_SHOP_TO_JIEDAI = 4;

    /**
     * 技师接查看预约未到店用户信息   支持打电话  发消息
     */
    private static final int USER_RELATION_SHOP_TO_YUYUE = 5;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_user_profile);
        initView();
    }

    private void initView() {
        topbar = getView(R.id.topbar);
        imgPhoto = getView(R.id.img_photo);
        tvRemarkName = getView(R.id.tv_remark_name);
        tvnickname = getView(R.id.tv_nickname);
        editRemarks = getView(R.id.edit_remarks);
        llRemarks = getView(R.id.ll_remarks);
        tvPhone = getView(R.id.tv_phone);
        llPhone = getView(R.id.ll_phone);
        btnAddFriends = getView(R.id.bt_add_friends);
        btSendMessage = getView(R.id.bt_send_message);
        btnTelPhone = getView(R.id.btn_tel_phone);
        tvIntroduction = getView(R.id.tv_introduction);
        tvIntroductionInfo = getView(R.id.tv_introduction_info);
        llIntroduction = getView(R.id.ll_introduction);
        llAccount = getView(R.id.ll_account);
        tvAccount = getView(R.id.tv_account);
        tvHobby = getView(R.id.tv_hobby);
        llHobby = getView(R.id.ll_hobby);
        flowHobbyInfo = getView(R.id.flow_hobby_info);
        tvRecord = getView(R.id.tv_record);
        topbar.setBackBtnEnable(true)
                .setTttleText("个人资料")
                .onBackBtnClick()
                .setTopbarClickListener(this);

        user = getIntent().getParcelableExtra(C.IntentKey.USER_INFO);
        showViewByUserRelation();
        getAccountMoney();
    }

    /**
     * 显示不同用户查看司机的的个人资料
     */
    private void showViewByUserRelation() {

        //  默认 昵称显示
        String remarkName = AppUtility.isNotEmpty(user.getFriendNickName()) ? user.getFriendNickName() : user.getNickName();
        //   默认 昵称显示
        String nickname = new StringBuilder().append("昵称：").append(user.getNickName()).toString();

        switch (getUserRelation()) {
            case USER_RELATION_CLIENT_TO_SELF:      // 除技师之外的 其他人查看  司机个人信息
                btnAddFriends.setVisibility(View.GONE);
                btSendMessage.setVisibility(View.GONE);
                btnTelPhone.setVisibility(View.GONE);
                llRemarks.setVisibility(View.GONE);
                llAccount.setVisibility(View.GONE);
                tvRecord.setVisibility(View.GONE);
                remarkName = user.getNickName();
                nickname = "";
                break;
            case USER_RELATION_SHOP_TO_CLIENT:   //技师  查看  司机个人信息
                tvAccount.setText("¥0.0");
                btnAddFriends.setVisibility(View.GONE);
                btSendMessage.setOnClickListener(this);
                btnTelPhone.setOnClickListener(this);
                llRemarks.setVisibility(View.GONE);
                llRemarks.setOnClickListener(this);
                tvRecord.setVisibility(View.VISIBLE);
                tvRecord.setOnClickListener(this);
                break;
            case USER_RELATION_SHOP_TO_CLIENT_NOT_FRIENT:   //   技师非好友
                tvAccount.setText("¥0.0");
                btnAddFriends.setVisibility(View.VISIBLE);
                btnAddFriends.setOnClickListener(this);
                btSendMessage.setVisibility(View.GONE);
//                btSendMessage.setOnClickListener(this);
                btnTelPhone.setVisibility(View.GONE);
//                btnTelPhone.setOnClickListener(this);
                llRemarks.setVisibility(View.GONE);
                tvRecord.setVisibility(View.VISIBLE);
                tvRecord.setOnClickListener(this);
                break;
            case USER_RELATION_SHOP_TO_JIEDAI:    //  仅显示  信息  没有操作
                tvAccount.setText("¥0.0");
                btnAddFriends.setVisibility(View.GONE);
//                btnAddFriends.setOnClickListener(this);
                btSendMessage.setVisibility(View.GONE);
//                btSendMessage.setOnClickListener(this);
                btnTelPhone.setVisibility(View.GONE);
//                btnTelPhone.setOnClickListener(this);
                llRemarks.setVisibility(View.GONE);
                tvRecord.setVisibility(View.VISIBLE);
                tvRecord.setOnClickListener(this);
                break;
            case USER_RELATION_SHOP_TO_YUYUE:
            default:
                tvAccount.setText("¥0.0");
                llRemarks.setVisibility(View.GONE);
                btnAddFriends.setVisibility(View.GONE);
                btSendMessage.setVisibility(View.VISIBLE);
                btSendMessage.setOnClickListener(this);
                btnTelPhone.setVisibility(View.VISIBLE);
                btnTelPhone.setOnClickListener(this);
                tvRecord.setVisibility(View.VISIBLE);
                tvRecord.setOnClickListener(this);
                break;
        }

        // 备注名
        tvRemarkName.setText(remarkName);
        //  昵称
        tvnickname.setText(nickname);
        //性别
        tvRemarkName.setCompoundDrawablesWithIntrinsicBounds(null, null, user.getUserSexResId() == 0 ? null : ContextCompat.getDrawable(mContext, user.getUserSexResId()), null);
        //用户头像
        Glide.with(mContext).load(FileUtil.getImageUrl(user.getUserPhoto())).error(R.drawable.em_default_avatar).into(imgPhoto);
        //手机号
        tvPhone.setText(user.getLinkTel());
        //  个人爱好
        String userInterest = user.getUserInterest();
        if (!TextUtils.isEmpty(userInterest))
            creatServiceLables(Arrays.asList(userInterest.split(",")));
        // 个性签名
        tvIntroductionInfo.setText(user.getPersonalNote());

    }

    /**
     * 获取用户关系
     *
     * @return
     */
    private int getUserRelation() {
        if (app.getUser() == null) {
            return USER_RELATION_CLIENT_TO_SELF;
        }
        if (app.getUser().getUserType() == 2) { //  当前用户是 技师
            if (user.getFriendStatus() == 1) {  //  技师查看司机 好友
                return USER_RELATION_SHOP_TO_CLIENT;
            } else if (user.getFriendStatus() == 0) {    // 技师查看司机  没有关系按钮  接待时的客户信息进入
                return USER_RELATION_SHOP_TO_JIEDAI;
            } else if (user.getFriendStatus() == 10) {    // 技师接查看预约未到店用户信息   支持打电话  发消息
                return USER_RELATION_SHOP_TO_YUYUE;
            }
            else {
                return USER_RELATION_SHOP_TO_CLIENT_NOT_FRIENT;  //技师查看司机 非好友
            }
        } else {    // 司机看司机  或看自己
            return USER_RELATION_CLIENT_TO_SELF;
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_remarks) {                        // 备注
            Intent i = new Intent(mContext, EaseModifyGroupNameActivity.class);
            i.putExtra(C.IntentKey.UPDATE_TYPE, EaseModifyGroupNameActivity.TYPE_UPDATE_PERSONAL_NAME);
            i.putExtra(C.IntentKey.USER_INFO, user);
            startActivityForResult(i, REQ_NICK_NAME);
        } else if (id == R.id.bt_send_message) {            // 发消息
            showActivity(AppUtility.getChatIntent(mContext, user.getUserNum()));
        } else if (id == R.id.btn_tel_phone) {              //一键拨号
            /*showActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + user.getLinkTel())));*/
            confirmDialPhone();
        } else if (id == R.id.bt_add_friends) {             //加好友
            Intent intent = new Intent(mContext, EaseIdentityVerificationActivity.class);
            intent.putExtra(C.IntentKey.USER_INFO, user);
            showActivity(intent);
        } else if (id == R.id.tv_record) {                     // 消费记录
            LogX.e("ycw", "----消费记录");
            // TODO: 2016/12/27 消费记录
            Intent intent = new Intent(mContext, RecordActivity.class);
            intent.putExtra(C.IntentKey.USER_INFO, user);
            showActivity(intent);

        }
    }


    /**
     * 确认拨打电话提示
     */
    public void confirmDialPhone() {
        AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                .setMessage(user.getLinkTel())
//                .setTitle("确认拨打电话？")
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + user.getLinkTel())));
                        dialog.dismiss();
                    }
                }).create();
        alertDialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQ_NICK_NAME && data != null) {
            String nickName = data.getStringExtra(C.IntentKey.UPDATE_NICKNAME);
            editRemarks.setText(nickName);
        }
    }

    /**
     * 获取账户信息
     */
    public void getAccountMoney() {
        Call<ResultBase<AccountMoneyInfo>> call = App.getInstance().getApiService()
                .getUserAccountInfo(user.getUserNum());
        call.enqueue(new ResponseCallback<ResultBase<AccountMoneyInfo>>() {
            @Override
            public void onSuccess(Call call, ResultBase<AccountMoneyInfo>
                    accountMoneyInfoResultBase) {
                tvAccount.setText(String.format("¥%s", new BigDecimal(accountMoneyInfoResultBase
                        .getObj().getAccountBalance()).setScale(1, BigDecimal.ROUND_HALF_UP).toString()));
            }

            @Override
            public void onError(Call call, ResultBase<AccountMoneyInfo>
                    accountMoneyInfoResultBase, int errorCode) {

            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {

            }
        });
    }

    @SuppressWarnings("ResourceType")
    private void creatServiceLables(List<String> stringList) {
        flowHobbyInfo.removeAllViews();
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams
                .WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(8, 5, 0, 0);
        for (String info : stringList) {
            TextView view = new TextView(mContext);
            view.setText(info);
            view.setTextColor(Color.WHITE);
            view.setTextSize(12);
            view.setLayoutParams(lp);
            view.setBackgroundResource(R.drawable.shape_text_station_label);
            flowHobbyInfo.addView(view);
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
