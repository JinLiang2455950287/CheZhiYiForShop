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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.model.AttachInfo;
import com.ruanyun.chezhiyi.commonlib.model.CaseInfo;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.params.GetCaseParams;
import com.ruanyun.chezhiyi.commonlib.presenter.GetCasePresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.GetCaseMvpView;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.MyCaseLibActivity;
import com.ruanyun.chezhiyi.commonlib.view.widget.CircleImageView;
import com.ruanyun.chezhiyi.commonlib.view.widget.FlowLayout;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 技师的    个人资料页
 * Created by ycw on 2016/8/24.
 */
public class UserProfileActivity extends AutoLayoutActivity
        implements View.OnClickListener, Topbar.onTopbarClickListener,
//        UserCarInfoListMvpView,
        GetCaseMvpView {

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
    Button btSendMessage;           // 发送消息
    Button btnTelPhone;             // 拨打电话
    TextView tvNickName;            // 昵称
    TextView tvIntroduction;        //个新签名  个人说明
    TextView tvIntroductionInfo;        //个新签名  个人说明  内容
    LinearLayout llIntroduction;        //个新签名  个人说明  是否显示
    FlowLayout flowHobbyInfo;

    private User user;//当前被查看的用户的资料
    GetCasePresenter getCasePresenter = new GetCasePresenter();
    GetCaseParams params = new GetCaseParams();


    /**
     * 技师查看技师
     */
    private static final int USER_RELATION_SHOP_TO_SHOP = 4;

    /**
     * 技师查看自己资料
     */
    private static final int USER_RELATION_SHOP_TO_SELF = 8;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        getCasePresenter.attachView(this);
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
        btSendMessage = getView(R.id.bt_send_message);
        btnTelPhone = getView(R.id.btn_tel_phone);
        tvIntroduction = getView(R.id.tv_introduction);
        tvIntroductionInfo = getView(R.id.tv_introduction_info);
        llIntroduction = getView(R.id.ll_introduction);
        flowHobbyInfo = getView(R.id.flow_hobby_info);

        llInfo.setOnClickListener(this);
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
//        params.setCurrentPage(1);
        params.setPageNum(1);
        params.setUserType(1);
        params.setCreateUserNum(user.getUserNum());
        params.setStatus(1);
        getCasePresenter.getCaseList(app.getApiService().getCaseInfo(user.getUserNum(), params));
    }

    /**
     * 根据用户的关系显示用户展示界面
     */
    private void showViewByUserRelation() {
        switch (getUserRelation()) {
            case USER_RELATION_SHOP_TO_SHOP:        //技师看技师   或  司机看技师
                showShopToShopView();
                break;
            case USER_RELATION_SHOP_TO_SELF:   //技师看自己
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
        btSendMessage.setVisibility(View.GONE);
        btnTelPhone.setVisibility(View.GONE);
        tvIntroduction.setText("个人说明");
        tvCarInfo.setText("案例库");
        //查看自己资料只显示昵称
        tvRemarkName.setText(user.getNickName());
        tvRemarkName.setCompoundDrawablesWithIntrinsicBounds(null, null, user.getUserSexResId() == 0? null : ContextCompat.getDrawable(mContext, user.getUserSexResId()), null);
        tvNickName.setVisibility(View.INVISIBLE);
    }

    /**
     * 技师查看技师的资料
     */
    private void showShopToShopView() {
        tvIntroduction.setText("个人说明");
        tvCarInfo.setText("案例库");

        tvRemarkName.setCompoundDrawablesWithIntrinsicBounds(null, null, user.getUserSexResId() == 0 ? null : ContextCompat.getDrawable(mContext, user.getUserSexResId()), null);
        // : 2016/9/1 如果有备注显示备注， 没有备注显示昵称
        tvRemarkName.setText(AppUtility.isNotEmpty(user.getFriendNickName()) ? user
                .getFriendNickName() : user.getNickName());
        tvNickName.setText("昵称：" + user.getNickName());
    }


    /**
     * 获取用户关系
     */
    private int getUserRelation() {
        if (user.getUserNum().equals(app.getCurrentUserNum())) { //是否当前用户
            return USER_RELATION_SHOP_TO_SELF;
        } else {
            return USER_RELATION_SHOP_TO_SHOP;
        }

    }

    /**
     * 更新用户的界面
     */
    private void updateUI() {
        //用户头像
        Glide.with(mContext).load(FileUtil.getImageUrl(user.getUserPhoto())).error(R.drawable
                .em_default_avatar).into(imgPhoto);
        tvIntroductionInfo.setText(user.getUserType() == 2 ? user.getPersonalNote() : user
                .getPersonalSign());   //个性签名
        // : 2016/9/1 user信息没有手机号
        tvPhone.setText(user.getLinkTel());
        // : 2016/9/1 user信息没有工作状态
        String workStatus = "";
        if (user.getWorkStatus() != null)
            workStatus = DbHelper.getInstance().getParentName(user.getWorkStatus(), C.ParentCode
                    .WORK_STATUS);
        tvWorkState.setText(workStatus);
        // : 2016/10/21 技师的个人标签
        String userLabel = user.getLabelCode();
        if ( !TextUtils.isEmpty(userLabel) ) {
            List<String> label = Arrays.asList(userLabel.split(","));
            //            List<String> allLabels = new ArrayList<>();
            //            for (int i = 0; i < label.size(); i++) {
            //                allLabels.add(DbHelper.getInstance().getParentName(label.get(i), C.ParentCode.USER_LABEL));
            //            }
            creatServiceLables(label);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_send_message) { // 发消息
            showActivity(AppUtility.getChatIntent(mContext, user.getUserNum()));
        } else if (id == R.id.btn_tel_phone) {   //一键拨号
            /*showActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + user.getLinkTel())));*/
            confirmDialPhone();
        } else if (id == R.id.ll_info) {  // 点击案例信息
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
        getCasePresenter.detachView();
    }


    @Override
    public void showLodingView(String msg) {
        showLoading(msg);
    }

    @Override
    public void getCaseListOnSuccess(List<CaseInfo> caseInfoList) {
        if (caseInfoList == null || caseInfoList.size() == 0) return;
        List<AttachInfo> attachInfoList = caseInfoList.get(0).getAttachInfoList();
        int size = attachInfoList.size();
        if (size < 1) {

        } else if (size < 2) {
            imgPic1.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(FileUtil.getImageUrl(attachInfoList.get(0).getFilePath())).error(R
                    .drawable.default_img).into(imgPic1);
        } else if (size < 3) {
            imgPic1.setVisibility(View.VISIBLE);
            imgPic2.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(FileUtil.getImageUrl(attachInfoList.get(0).getFilePath())).error(R
                    .drawable.default_img).into(imgPic1);
            Glide.with(mContext).load(FileUtil.getImageUrl(attachInfoList.get(1).getFilePath())).error(R
                    .drawable.default_img).into(imgPic2);
        } else {
            imgPic1.setVisibility(View.VISIBLE);
            imgPic2.setVisibility(View.VISIBLE);
            imgPic3.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(FileUtil.getImageUrl(attachInfoList.get(0).getFilePath())).error(R
                    .drawable.default_img).into(imgPic1);
            Glide.with(mContext).load(FileUtil.getImageUrl(attachInfoList.get(1).getFilePath())).error(R
                    .drawable.default_img).into(imgPic2);
            Glide.with(mContext).load(FileUtil.getImageUrl(attachInfoList.get(2).getFilePath())).error(R
                    .drawable.default_img).into(imgPic3);
        }
    }

    @Override
    public void dismissLoadingView() {
        dissMissLoading();
    }


    /**
     * 技师的个人标签
     * @param stringList
     */
    @SuppressWarnings("ResourceType")
    private void creatServiceLables(List<String> stringList) {
        flowHobbyInfo.removeAllViews();
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 5, 8, 0);
        for (String info : stringList) {
            TextView view = new TextView(mContext);
            info = DbHelper.getInstance().getParentName(info, C.ParentCode.USER_LABEL);
            view.setText(info);
            view.setTextColor(Color.WHITE);
            view.setTextSize(12);
            view.setLayoutParams(lp);
            view.setBackgroundResource(R.drawable.shape_text_station_label);
            flowHobbyInfo.addView(view);
        }
    }

}
