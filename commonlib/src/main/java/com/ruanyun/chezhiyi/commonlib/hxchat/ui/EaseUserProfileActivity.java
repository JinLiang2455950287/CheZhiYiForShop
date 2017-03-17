package com.ruanyun.chezhiyi.commonlib.hxchat.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.model.CarInfo;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.presenter.UserCarInfoListPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.UserCarInfoListMvpView;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.MyCaseLibActivity;
import com.ruanyun.chezhiyi.commonlib.view.widget.CircleImageView;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import java.util.List;

import retrofit2.Call;

/**
 * 个人资料页 <br/>
 * Created by ycw on 2016/8/24.
 */
@Deprecated
public class EaseUserProfileActivity
        extends AutoLayoutActivity
        implements View.OnClickListener, Topbar.onTopbarClickListener, UserCarInfoListMvpView {

    Topbar topbar;
    CircleImageView imgPhoto;       //头像
    TextView tvRemarkName;          //备注
    EditText editRemarks;           //设置备注
    LinearLayout llRemarks;         //是否显示设置备注
    TextView tvPhone;               // 手机号
    LinearLayout llPhone;           // 是否显示手机号
    TextView tvWorkState;           //工作状态
    LinearLayout llWorkState;       //  是否显示工作状态
    ImageView imgPic1;              // 案例库 或 车辆信息  第一张图片
    ImageView imgPic2;              // 案例库 或 车辆信息  第二张图片
    ImageView imgPic3;              // 案例库 或 车辆信息  第三张图片
    LinearLayout llInfo;            // 案例库 或 车辆信息  是否显示
    TextView tvCarInfo;             // 案例库 或 车辆信息
    Button btAddFriends;            //  加为好友
    Button btSendMessage;           // 发送消息
    Button btnTelPhone;             // 拨打电话
    TextView tvNickName;            // 昵称
    TextView tvIntroduction;        //个新签名  个人说明
    TextView tvIntroductionInfo;    //个新签名  个人说明  内容
    LinearLayout llIntroduction;    //个新签名  个人说明  是否显示

    private User user;//当前被查看的用户的资料
    UserCarInfoListPresenter userCarInfoListPresenter = new UserCarInfoListPresenter();

    /**
     * 司机查看司机
     */
    private static final int USER_RELATION_CLIENT_TO_CLIENT = 1;
    /**
     * 司机查看好友技师
     */
    private static final int USER_RELATION_CLIENT_TO_SHOP_FRIENT = 2;
    /**
     * 司机查看非好友技师
     */
    private static final int USER_RELATION_CLIENT_TO_SHOP_NOT_FRIENT = 3;
    /**
     * 技师查看技师
     */
    private static final int USER_RELATION_SHOP_TO_SHOP = 4;
    /**
     * 技师查看好友司机
     */
    private static final int USER_RELATION_SHOP_TO_CLIENT_FRIENT = 5;
    /**
     * 技师查看非好友司机
     */
    private static final int USER_RELATION_SHOP_TO_CLIENT_NOT_FRIENT = 6;
    /**
     * 司机查看自己资料
     */
    private static final int USER_RELATION_CLIENT_TO_SELF = 7;
    /**
     * 技师查看自己资料
     */
    private static final int USER_RELATION_SHOP_TO_SELF = 8;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.ease_activity_user_profile_info);
        userCarInfoListPresenter.attachView(this);
        initView();
    }

    private void initView() {
        topbar = getView(R.id.topbar);
        imgPhoto = getView(R.id.img_photo);
        tvRemarkName = getView(R.id.tv_remark_name);
        tvNickName = getView(R.id.tv_nickname);
        editRemarks = getView(R.id.edit_remarks);
        llRemarks = getView(R.id.ll_remarks);
        tvPhone = getView(R.id.tv_phone);
        llPhone = getView(R.id.ll_phone);
        tvWorkState = getView(R.id.tv_work_state);
        llWorkState = getView(R.id.ll_work_state);
        imgPic1 = getView(R.id.img_pic_1);
        imgPic2 = getView(R.id.img_pic_2);
        imgPic3 = getView(R.id.img_pic_3);
        llInfo = getView(R.id.ll_info);
        tvCarInfo = getView(R.id.tv_car_info);
        btAddFriends = getView(R.id.bt_add_friends);
        btSendMessage = getView(R.id.bt_send_message);
        btnTelPhone = getView(R.id.btn_tel_phone);
        tvIntroduction = getView(R.id.tv_introduction);
        tvIntroductionInfo = getView(R.id.tv_introduction_info);
        llIntroduction = getView(R.id.ll_introduction);

        llInfo.setOnClickListener(this);
        btAddFriends.setOnClickListener(this);
        btSendMessage.setOnClickListener(this);
        btnTelPhone.setOnClickListener(this);
        topbar.setBackBtnEnable(true)
                .setTttleText("个人资料")
                .onBackBtnClick()
                .setTopbarClickListener(this);
        user = getIntent().getParcelableExtra(C.IntentKey.USER_INFO);
        LogX.d(TAG, user.toString());
        showViewByUserRelation();
        getCarInfoList();
    }

    /**
     * 获取车辆信息
     */
    private void getCarInfoList() {
        Call call = app.getApiService().getCarinfoList(user.getUserNum());
        userCarInfoListPresenter.getCarInfoList(call);
    }

    /**
     * 根据用户的关系显示用户展示界面
     */
    private void showViewByUserRelation() {
        switch (getUserRelation()) {
            case USER_RELATION_CLIENT_TO_CLIENT:
                //AppUtility.showToastMsg("司机查看司机");
                showClientToClientView();
                break;
            case USER_RELATION_CLIENT_TO_SHOP_FRIENT:
                //AppUtility.showToastMsg("司机查看好友技师");
                showClientToShopFrientView();
                break;
            case USER_RELATION_CLIENT_TO_SHOP_NOT_FRIENT:
                //AppUtility.showToastMsg("司机查看非好友技师");
                showClientToShopNotFrientView();
                break;
            case USER_RELATION_CLIENT_TO_SELF:
                //AppUtility.showToastMsg("司机查看自己");
                showClientToSelfView();
                break;
            case USER_RELATION_SHOP_TO_CLIENT_FRIENT:
                //AppUtility.showToastMsg("技师查看好友司机");
                showShopToClientFrientView();
                break;
            case USER_RELATION_SHOP_TO_CLIENT_NOT_FRIENT:
                //AppUtility.showToastMsg("技师查看非好友司机");
                showShopToClientNotFrientView();
                break;
            case USER_RELATION_SHOP_TO_SHOP:        //技师看技师
                //AppUtility.showToastMsg("技师查看技师");
                showShopToShopView();
                break;
            case USER_RELATION_SHOP_TO_SELF:
                //AppUtility.showToastMsg("技师查看自己");
                showShopToSelfView();
                break;
        }
        if (user != null)
            updateUI();
    }

    /**
     * 显示技师查看自己的资料
     */
    private void showShopToSelfView() {
        btAddFriends.setVisibility(View.GONE);
        btSendMessage.setVisibility(View.GONE);
        btnTelPhone.setVisibility(View.GONE);
//        llRemarks.setVisibility(View.GONE);
//                llWorkState.setVisibility(View.VISIBLE);
//                llPhone.setVisibility(View.VISIBLE);
        tvIntroduction.setText("个人说明");
        tvCarInfo.setText("案例库");
        //查看自己资料只显示昵称
        tvRemarkName.setText(user.getNickName());
        tvRemarkName.setCompoundDrawablesWithIntrinsicBounds(null, null, user.getUserSexResId() == 0 ? null : ContextCompat.getDrawable(mContext, user.getUserSexResId()), null);
        tvNickName.setVisibility(View.INVISIBLE);
    }

    /**
     * 技师查看技师的资料
     */
    private void showShopToShopView() {
        btAddFriends.setVisibility(View.GONE);
//                btSendMessage.setVisibility(View.VISIBLE);
//                btnTelPhone.setVisibility(View.VISIBLE);
//                llRemarks.setVisibility(View.VISIBLE);
//                llWorkState.setVisibility(View.VISIBLE);
//                llPhone.setVisibility(View.VISIBLE);
        tvIntroduction.setText("个人说明");
        tvCarInfo.setText("案例库");

        tvRemarkName.setCompoundDrawablesWithIntrinsicBounds(null, null, user.getUserSexResId() == 0 ? null : ContextCompat.getDrawable(mContext, user.getUserSexResId()), null);
        // : 2016/9/1 如果有备注显示备注， 没有备注显示昵称
        tvRemarkName.setText(AppUtility.isNotEmpty(user.getFriendNickName()) ? user.getFriendNickName() : user.getNickName());
        tvNickName.setText("昵称：" + user.getNickName());
    }

    /**
     * 技师查看司机 非好友
     */
    private void showShopToClientNotFrientView() {
//                btAddFriends.setVisibility(View.VISIBLE);
        btSendMessage.setVisibility(View.GONE);
        btnTelPhone.setVisibility(View.GONE);
        llWorkState.setVisibility(View.GONE);
//        llRemarks.setVisibility(View.GONE);
        llPhone.setVisibility(View.GONE);
        tvIntroduction.setText("个性签名");
        tvCarInfo.setText("车辆信息");
        //非好友只显示昵称
        tvRemarkName.setText(user.getNickName());
        tvNickName.setVisibility(View.INVISIBLE);
    }

    /**
     * 技师查看司机 好友
     */
    private void showShopToClientFrientView() {
        btAddFriends.setVisibility(View.GONE);
//                btSendMessage.setVisibility(View.VISIBLE);
//                btnTelPhone.setVisibility(View.VISIBLE);
        llWorkState.setVisibility(View.GONE);
//        llRemarks.setVisibility(View.GONE);
        tvIntroduction.setText("个性签名");
        tvCarInfo.setText("车辆信息");

        tvRemarkName.setText(AppUtility.isNotEmpty(user.getFriendNickName()) ? user.getFriendNickName() : user.getNickName());
        tvRemarkName.setCompoundDrawablesWithIntrinsicBounds(null, null, user.getUserSexResId() == 0 ? null : ContextCompat.getDrawable(mContext, user.getUserSexResId()), null);
        tvNickName.setText("昵称：" + user.getNickName());
    }

    /**
     * 司机查看自己的资料
     */
    private void showClientToSelfView() {
        btAddFriends.setVisibility(View.GONE);
        btSendMessage.setVisibility(View.GONE);
        btnTelPhone.setVisibility(View.GONE);
        llWorkState.setVisibility(View.GONE);
//        llRemarks.setVisibility(View.GONE);
        tvIntroduction.setText("个性签名");
        tvCarInfo.setText("车辆信息");
        //查看自己资料只显示昵称
        tvRemarkName.setText(user.getNickName());
        tvPhone.setText(user.getLoginName());
        tvRemarkName.setCompoundDrawablesWithIntrinsicBounds(null, null, user.getUserSexResId() == 0 ? null : ContextCompat.getDrawable(mContext, user.getUserSexResId()), null);
        //tvNickName.setText("昵称：" + user.getNickName());
        tvNickName.setVisibility(View.INVISIBLE);
    }

    /**
     * 司机查看技师 非好友
     */
    private void showClientToShopNotFrientView() {
        btAddFriends.setVisibility(View.GONE);
        btSendMessage.setVisibility(View.GONE);
        btnTelPhone.setVisibility(View.GONE);
//        llRemarks.setVisibility(View.GONE);
//                llWorkState.setVisibility(View.VISIBLE);
//                llPhone.setVisibility(View.VISIBLE);
        tvIntroduction.setText("个人说明");
        tvCarInfo.setText("案例库");
        //非好友只显示昵称
        tvRemarkName.setText(user.getNickName());
        tvNickName.setVisibility(View.INVISIBLE);
    }

    /**
     * 司机查看技师 好友
     */
    private void showClientToShopFrientView() {
        btAddFriends.setVisibility(View.GONE);
//                btSendMessage.setVisibility(View.VISIBLE);
//                btnTelPhone.setVisibility(View.VISIBLE);
//                llRemarks.setVisibility(View.VISIBLE);
//                llPhone.setVisibility(View.VISIBLE);
//                llWorkState.setVisibility(View.VISIBLE);
        tvIntroduction.setText("个人说明");
        tvCarInfo.setText("案例库");

        tvRemarkName.setText(AppUtility.isNotEmpty(user.getFriendNickName()) ? user
                .getFriendNickName() : user.getNickName());
        tvRemarkName.setCompoundDrawablesWithIntrinsicBounds(null, null, user.getUserSexResId() == 0 ? null : ContextCompat.getDrawable(mContext, user.getUserSexResId()), null);
        tvNickName.setText("昵称：" + user.getNickName());
    }

    /**
     * 司机查看司机
     */
    private void showClientToClientView() {
        btAddFriends.setVisibility(View.GONE);
        btSendMessage.setVisibility(View.GONE);
        btnTelPhone.setVisibility(View.GONE);
        llWorkState.setVisibility(View.GONE);
        llRemarks.setVisibility(View.GONE);
        llPhone.setVisibility(View.GONE);
        tvIntroduction.setText("个性签名");
//        tvCarInfo.setText("车辆信息");
        llInfo.setVisibility(View.GONE);

        tvRemarkName.setText(user.getNickName());
//        tvRemarkName.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(mContext, R.drawable.icon_male), null);
        tvNickName.setVisibility(View.INVISIBLE);
    }

    /**
     * 获取用户关系
     */
    private int getUserRelation() {
        if (user.getUserNum().equals(app.getCurrentUserNum())) { //是否当前用户
            if (user.getUserType() == 2) {
                return USER_RELATION_SHOP_TO_SELF;
            } else {
                return USER_RELATION_CLIENT_TO_SELF;
            }
        } else if (app.isClient()) {  //不是当前用户的   --    司机用户
            if (user.getFriendStatus() == 1) {    //   是好友 一定是技师
                return USER_RELATION_CLIENT_TO_SHOP_FRIENT;
            } else if (user.getUserType() == 2) {  //   不是好友的  ---  技师
                return USER_RELATION_CLIENT_TO_SHOP_NOT_FRIENT;
            } else {    // 不是好友的    ----    司机
                return USER_RELATION_CLIENT_TO_CLIENT;
            }
        } else {     //不是当前用户的   --    技师用户
            if (user.getUserType() == 2) {    //   不是好友 一定是司机
                return USER_RELATION_SHOP_TO_SHOP;
            } else if (user.getFriendStatus() == 1) {  //   是好友的司机
                return USER_RELATION_SHOP_TO_CLIENT_FRIENT;
            } else {    // 不是好友的司机
                return USER_RELATION_SHOP_TO_CLIENT_NOT_FRIENT;
            }
        }
    }

    /**
     * 更新用户的界面
     */
    private void updateUI() {
        //用户头像
        //ImageUtil.loadImage(mContext, FileUtil.getImageUrl(user.getUserPhoto()), imgPhoto, R.drawable.em_default_avatar);
        Glide.with(mContext).load(FileUtil.getImageUrl(user.getUserPhoto())).error(R.drawable.em_default_avatar).into(imgPhoto);
        tvIntroductionInfo.setText(user.getUserType() == 2 ? user.getPersonalNote() : user.getPersonalSign());   //个性签名
        // : 2016/9/1 user信息没有手机号
        tvPhone.setText(user.getLoginName());
        // : 2016/9/1 user信息没有工作状态
        String workStatus = "";
        if (user.getWorkStatus() != null)
            workStatus = DbHelper.getInstance().getParentName(user.getWorkStatus(), C.ParentCode.WORK_STATUS);
        tvWorkState.setText(workStatus);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_add_friends) { //添加好友
            Intent intent = new Intent(mContext, EaseIdentityVerificationActivity.class);
            intent.putExtra(C.IntentKey.USER_INFO, user);
            showActivity(intent);
        } else if (id == R.id.bt_send_message) { // 发消息
            showActivity(AppUtility.getChatIntent(mContext, user.getUserNum()));
        } else if (id == R.id.btn_tel_phone) {   //一键拨号
            //showActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + user.getLoginName())));
            confirmDialPhone();

        } else if (id == R.id.ll_info) {
            if (user.getUserType() == 2) {
                Intent intent = new Intent(mContext, MyCaseLibActivity.class);
                intent.putExtra(C.IntentKey.USER_NUM, user.getUserNum());
                showActivity(intent);
            }
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
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == R.id.img_btn_left) {
            exit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userCarInfoListPresenter.detachView();
    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void dismissLoadingView() {

    }

    @Override
    public void onSuccess(List<CarInfo> carInfos) {
        int size = carInfos.size();
        if (size < 1) {

        } else if (size < 2) {
            imgPic1.setVisibility(View.VISIBLE);
            //ImageUtil.loadImage(mContext, FileUtil.getImageUrl(carInfos.get(0).getPicPath()), imgPic1, R.drawable.default_img);
            Glide.with(mContext).load(FileUtil.getImageUrl(carInfos.get(0).getPicPath())).error(R.drawable.default_img).into(imgPic1);
        } else if (size < 3) {
            imgPic1.setVisibility(View.VISIBLE);
            imgPic2.setVisibility(View.VISIBLE);
//            ImageUtil.loadImage(mContext, FileUtil.getImageUrl(carInfos.get(0).getPicPath()), imgPic1, R.drawable.default_img);
//            ImageUtil.loadImage(mContext, FileUtil.getImageUrl(carInfos.get(1).getPicPath()), imgPic2, R.drawable.default_img);
            Glide.with(mContext).load(FileUtil.getImageUrl(carInfos.get(0).getPicPath())).error(R.drawable.default_img).into(imgPic1);
            Glide.with(mContext).load(FileUtil.getImageUrl(carInfos.get(1).getPicPath())).error(R.drawable.default_img).into(imgPic2);
        } else {
            imgPic1.setVisibility(View.VISIBLE);
            imgPic2.setVisibility(View.VISIBLE);
            imgPic3.setVisibility(View.VISIBLE);
//            ImageUtil.loadImage(mContext, FileUtil.getImageUrl(carInfos.get(0).getPicPath()), imgPic1, R.drawable.default_img);
//            ImageUtil.loadImage(mContext, FileUtil.getImageUrl(carInfos.get(1).getPicPath()), imgPic2, R.drawable.default_img);
//            ImageUtil.loadImage(mContext, FileUtil.getImageUrl(carInfos.get(2).getPicPath()), imgPic3, R.drawable.default_img);
            Glide.with(mContext).load(FileUtil.getImageUrl(carInfos.get(0).getPicPath())).error(R.drawable.default_img).into(imgPic1);
            Glide.with(mContext).load(FileUtil.getImageUrl(carInfos.get(1).getPicPath())).error(R.drawable.default_img).into(imgPic2);
            Glide.with(mContext).load(FileUtil.getImageUrl(carInfos.get(2).getPicPath())).error(R.drawable.default_img).into(imgPic3);
        }
    }

    @Override
    public void onError(String msg, int errorCode) {

    }

    @Override
    public void onFail() {

    }

    @Override
    public Context getContext() {
        return null;
    }
}
