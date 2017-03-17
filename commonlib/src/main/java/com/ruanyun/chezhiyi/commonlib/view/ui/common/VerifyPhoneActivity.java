package com.ruanyun.chezhiyi.commonlib.view.ui.common;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hyphenate.easeui.utils.FileDownloader;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.model.AgreementContentInfo;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.params.VerifyCodyParams;
import com.ruanyun.chezhiyi.commonlib.presenter.LoginPresenter;
import com.ruanyun.chezhiyi.commonlib.presenter.VerifyCodyPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.util.StringUtil;
import com.ruanyun.chezhiyi.commonlib.view.LoginMvpView;
import com.ruanyun.chezhiyi.commonlib.view.VerifyCodyMvpView;
import com.ruanyun.chezhiyi.commonlib.view.widget.CleanableEditText;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.chezhiyi.commonlib.view.widget.ValidCodeButton;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;


/**
 * 验证手机号
 * Created by ycw on 2016/8/27.
 */
public class VerifyPhoneActivity extends AutoLayoutActivity implements
        Topbar.onTopbarClickListener, View.OnClickListener, VerifyCodyMvpView, LoginMvpView {
    public static final int TYPE_RIGESTE = 2;//  2 注册
    public static final int TYPE_FORGET_PASSWORD = 1;//1 忘记密码
    public static final int TYPE_RIGESTE_THRID = 3;//3 第三方验证登录
    public static final String TYPE_EXIST = "2";// 用户存在发送验证码
    public static final String TYPE_NO_EXIST = "1";// 用户不存在发送验证码
    Topbar topbar;
    CleanableEditText editPhone;
    CleanableEditText editVerificationCode;
    ValidCodeButton btGetVerifyCode;
    Button btnNext;
    TextView tvUserAgreement;
    private int activityType; // 验证手机号的类型 -1  忘记密码  - 注册
    private String mVerifyCode, phone; // 验证码
    private String verifyType; // 验证码type

    private VerifyCodyParams params = new VerifyCodyParams();//获取短信验证码的参数
    private LoginPresenter loginPresenter = new LoginPresenter();
    private VerifyCodyPresenter verifyCodyPresenter = new VerifyCodyPresenter();
    private String userType = "";//用户类型1 系统用户 2 技师用户 3 客户端用户

    private String thirdName,thirdIcon,thirdNum;
    private int thirdType;
    private HashMap<String, RequestBody> map;
    private boolean isGetVerifyCody = false;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        verifyCodyPresenter.attachView(this);
        loginPresenter.attachView(this);
        setContentView(R.layout.layout_get_verify);
        activityType = getIntent().getIntExtra(C.IntentKey.VERIFY_PH_ONE_TYPE, TYPE_RIGESTE);
        if (activityType == TYPE_RIGESTE_THRID) {
            thirdType = getIntent().getIntExtra(C.IntentKey.THIRD_TYPE, 0);
            thirdIcon = getIntent().getStringExtra(C.IntentKey.THIRD_ICON);
            thirdNum = getIntent().getStringExtra(C.IntentKey.THIRD_NUM);
            thirdName = getIntent().getStringExtra(C.IntentKey.THIRD_NAME);
        }
        initView();
    }

    private void initView() {
        topbar = getView(R.id.topbar);
        editPhone = getView(R.id.edit_phone);
        editVerificationCode = getView(R.id.edit_verification_code);
        btGetVerifyCode = getView(R.id.bt_get_verify_code);
        btnNext = getView(R.id.btn_next);
        tvUserAgreement = getView(R.id.tv_user_agreement);

        topbar.setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this)
                .setTttleText(getTitleText());
        topbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
        btGetVerifyCode.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        userType = isClient() ? "3" : "2";
        Spannable span = new SpannableString(getString(R.string.user_agreement));
        span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.theme_color_default)), 7, span.length(), Spannable
                .SPAN_EXCLUSIVE_INCLUSIVE);
        span.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                //获取用户协议接口
                verifyCodyPresenter.agreement();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);
            }
        }, 7, span.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tvUserAgreement.setMovementMethod(LinkMovementMethod.getInstance());
        tvUserAgreement.setText(span);

    }

    /**
     * topbar标题
     *
     * @return
     */
    private String getTitleText() {
        String str;
        if (activityType == TYPE_RIGESTE) {
            str = "注册";
            verifyType = TYPE_NO_EXIST;
        } else if (activityType == TYPE_RIGESTE_THRID) {
            str = "手机号验证";
            btnNext.setText("验证登录");
            verifyType = TYPE_NO_EXIST;
            map = new HashMap<>();
        } else {
            str = getIntent().getStringExtra(C.IntentKey.TOPBAR_TITLE);
            str = AppUtility.isNotEmpty(str)?str:"找回密码";
            tvUserAgreement.setVisibility(View.GONE);
            verifyType =  TYPE_EXIST;
        }
        return str;
    }

    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == R.id.img_btn_left) {
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        phone = editPhone.getText().toString();
        //mVerifyCode = editVerificationCode.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            AppUtility.showToastMsg("请输入手机号");
            return;
        }
        int id = v.getId();
        if (id == R.id.bt_get_verify_code) {    //获取验证码

            if (StringUtil.isPhone(phone)) {
                getVerifyCody();
                isGetVerifyCody = true;
            } else {
                AppUtility.showToastMsg("手机号码格式不正确");
            }
        } else if (id == R.id.btn_next) {       //下一步
            mVerifyCode = editVerificationCode.getText().toString();

            if (!isGetVerifyCody) {
                AppUtility.showToastMsg("请先获取验证码");
                return;
            }
            if (TextUtils.isEmpty(mVerifyCode)) {
                AppUtility.showToastMsg("请先填写验证码");
                return;
            }
//            else if (btGetVerifyCode.isClickable()) {
//                AppUtility.showToastMsg("请重新获取验证码");
//                return;
//            } else if (TextUtils.isEmpty(verifyCode)) {
//                AppUtility.showToastMsg("请填写验证码");
//                return;
//            } else if (!mVerifyCode.equals(verifyCode)) {
//                AppUtility.showToastMsg("验证码错误,确认后输入");
//                return;
//            }
            // TODO: 2016/8/27 验证完手机跳转到对应界面
            showNextActivityByType();

        }
    }

    /**
     * 调用获取验证码接口
     */
    private void getVerifyCody() {
        params.setType(verifyType);
        params.setValidate(null);
        params.setLoginName(null);
        params.setUserType(userType);//用户类型1 系统用户 2 技师用户 3 客户端用户
        Call call = app.getApiService().getVerifyCody(phone, params);
        btnNext.setOnClickListener(null);
        verifyCodyPresenter.getVerifyCody(call);
    }

    /**
     * 2016/8/27 验证完手机跳转到对应界面
     */
    private void showNextActivityByType() {
        switch (activityType) {
            case TYPE_FORGET_PASSWORD: //忘记密码
//                Intent intent = new Intent();
//                intent.setClass(mContext, ForgetPasswordActivity.class);
//                intent.putExtra(C.IntentKey.PHONE, phone);
//                showActivity(intent);
//                finish();
//                break;
            case TYPE_RIGESTE:  //注册
                //校验短信验证码
                params.setType(verifyType);
                params.setValidate(mVerifyCode);
                params.setUserType(null);
                params.setLoginName(phone);
                verifyCodyPresenter.validate(app.getApiService().validate(verifyCodyPresenter
                        .Cookie, phone, params));
                break;

            case TYPE_RIGESTE_THRID: //第三方登录
                new AsyncDownladPicTask().execute();
                break;
        }
    }

    @Override
    public void verifyCodySuccess() {
        btGetVerifyCode.startCountDownTimer();
//        AppUtility.showToastMsg(msg);
//        mVerifyCode = msg;
    }

    @Override
    public void showVerifyCodyMsg(String msg) {
        AppUtility.showToastMsg(msg);
    }

    /**
     * 第三方绑定 登陆
     * @param user
     */
    @Override
    public void addThirdSuccess(User user) {
        LogX.d(TAG, "----userNum-->" + user.getUserNum());
        loginPresenter.loginWithUser(user);
    }


    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void onHxLoginSuccess() {
        if (app.isClient()) {
            //获取用户车辆信息
            loginPresenter.getUserCarInfos();
//            showActivity("com.ruanyun.czy.client.MainActivity");
        } else {
            showActivity("com.ruanyun.chezhiyi.MainActivity");
            finish();
        }
    }

    @Override
    public void onUserLoginError() {

    }

    @Override
    public void showTip(String msg) {
        AppUtility.showToastMsg(msg);
    }

    @Override
    public void showLoadingView() {
        showLoading();
    }

    @Override
    public void dismissLoadingView() {
        dissMissLoading();
    }

    /**
     * 校验验证码成功后
     */
    @Override
    public void validateSuccess() {
        Intent intent = new Intent();
        switch (activityType) {
            case TYPE_RIGESTE:  //注册
                //校验短信验证码
                intent.setClassName(mContext,
                        "com.ruanyun.czy.client.view.ui.account.RegisterActivity");
                intent.putExtra(C.IntentKey.PHONE, phone);
                showActivity(intent);
                finish();
                break;
            case TYPE_FORGET_PASSWORD: //忘记密码
                intent.setClass(mContext, ForgetPasswordActivity.class);
                intent.putExtra(C.IntentKey.PHONE, phone);
                showActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void verifyCodyResult() {
        btnNext.setOnClickListener(this);
    }


    @Override
    public void onAgreementSuccess(AgreementContentInfo agreementContentInfo) {
        Intent intent = new Intent(mContext, ShowContentActivity.class);
        intent.putExtra(C.IntentKey.EDIT_CONTEXT, agreementContentInfo.getContent());
        intent.putExtra(C.IntentKey.TOPBAR_TITLE, ShowContentActivity.STRING_YHXY);
        showActivity(intent);
    }

    @Override
    public void onUserLoginFail() {

    }

    @Override
    public void onHxLoginFail(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showTip(message);
            }
        });
    }

    @Override
    public void thirdBindLogin(User user) {
        // 此处无用

    }

    @Override public void userNoCar() {
        showActivity("com.ruanyun.czy.client.view.ui.my.AddCarInformationActivity");
        finish();
    }

    @Override public void userHaseCar() {
        showActivity("com.ruanyun.czy.client.MainActivity");
        finish();
    }

    @Override public void getCarError() {

    }


    /**
     * 下载第三方头像
     */
    class AsyncDownladPicTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            return FileDownloader.getFilePathString(thirdIcon, FileDownloader.getCacheFilePath(getSubFileName()));
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            LogX.d("ycw", "-----third Head------》" + s);
            addThird(s);
        }
    }

    private String getSubFileName() {
        if (TextUtils.isEmpty(thirdIcon)) return "";
        if (thirdIcon.lastIndexOf("/") == -1) return thirdIcon;
        return thirdIcon.substring(thirdIcon.lastIndexOf("/")+1, thirdIcon.length());
    }

    /**
     * 第三方用户登录并注册用户
     *
     * @param userPhoto
     */
    private void addThird(String userPhoto) {
        File pic = new File(userPhoto);
        map.put("userType", RequestBody.create(MediaType.parse("text/plain"), userType));
        map.put("nickName", RequestBody.create(MediaType.parse("text/plain"), thirdName));
        map.put("thirdType", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(thirdType)));
        map.put("thirdNum", RequestBody.create(MediaType.parse("text/plain"), thirdNum));
        map.put("userPhotoPic\"; filename=\"" + pic.getName() , RequestBody.create(MediaType.parse("image/jpeg"), pic));
        verifyCodyPresenter.addThird(app.getApiService().addThird(phone, map));
    }


}