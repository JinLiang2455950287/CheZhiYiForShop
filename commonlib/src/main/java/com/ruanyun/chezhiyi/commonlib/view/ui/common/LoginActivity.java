package com.ruanyun.chezhiyi.commonlib.view.ui.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.model.AccountInfo;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.params.LoginParams;
import com.ruanyun.chezhiyi.commonlib.presenter.LoginPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.util.MD5;
import com.ruanyun.chezhiyi.commonlib.util.PrefUtility;
import com.ruanyun.chezhiyi.commonlib.view.LoginMvpView;
import com.ruanyun.chezhiyi.commonlib.view.widget.CleanableEditText;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import retrofit2.Call;

/**
 * Description ：登录界面
 * <p>
 * Created by ycw on 2016/8/9.
 */
public class LoginActivity extends AutoLayoutActivity implements LoginMvpView, View
        .OnClickListener, PlatformActionListener {

    ImageView imgLogo;
    CleanableEditText editPhoneInput;
    CleanableEditText editPasswordInput;
    Button btnLogin;
    TextView tvForgetPassword;
    TextView tvRegister;
    ImageView imgQq;
    ImageView imgWx;
    ImageView imgSina;
    /**  登录 */
    LoginPresenter loginPresenter = new LoginPresenter();

    /**
     * 登陆的用户类型  1 系统用户 2 技师用户 3 客户端用户
     */
    private String userType;
    private String thirdLoginName = "";
    int thirdType;
    Platform platform;//第三方的授权信息
    private LinearLayout llOtherText, llOtherType;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_login);
//        ButterKnife.bind(this);
        loginPresenter.attachView(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.detachView();
    }

    private void initView() {
        imgLogo = (ImageView) findViewById(R.id.img_logo);
        editPhoneInput = (CleanableEditText) findViewById(R.id.edit_phone_input);
        editPasswordInput = (CleanableEditText) findViewById(R.id.edit_password_input);
        btnLogin = (Button) findViewById(R.id.btn_login);
        tvForgetPassword = (TextView) findViewById(R.id.tv_forget_password);
        tvRegister = (TextView) findViewById(R.id.tv_register);
        imgQq = getView(R.id.img_qq);
        imgWx = getView(R.id.img_wx);
        imgSina = getView(R.id.img_sina);
        llOtherText = getView(R.id.ll_other_text);
        llOtherType = getView(R.id.ll_other_type);
        initListener();//控件设置监听
        initEditTextShow();
        if (app.isClient()) {
            editPhoneInput.setInputType(EditorInfo.TYPE_CLASS_PHONE);
            editPhoneInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
            userType = "3";
            tvForgetPassword.setVisibility(View.VISIBLE);
            tvRegister.setVisibility(View.VISIBLE);
            
            llOtherText.setVisibility(View.GONE);
            llOtherType.setVisibility(View.GONE);
        } else {
            userType = "2";
            editPhoneInput.setHint("请输入登录名");
            tvRegister.setVisibility(View.GONE);
            llOtherText.setVisibility(View.GONE);
            llOtherType.setVisibility(View.GONE);
            tvForgetPassword.setVisibility(View.GONE);// 不显示忘记密码
        }
    }

    /**
     * 设置已存在账号的情况下，显示登录账号在编辑框里
     */
    private void initEditTextShow() {
        AccountInfo accountInfo = DbHelper.getInstance().getAccountInfoByName(PrefUtility.get(C
                .PrefName.PREF_LOGIN_NAME, ""));
        if (accountInfo != null) {
            String loginName = accountInfo.getLoginName();
            editPhoneInput.setText(loginName);
            editPhoneInput.setSelection(loginName.length());
            editPasswordInput.requestFocus();
        }
    }

    /**
     * 设置监听
     */
    private void initListener() {
        btnLogin.setOnClickListener(this);
        tvForgetPassword.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        imgQq.setOnClickListener(this);
        imgWx.setOnClickListener(this);
        imgSina.setOnClickListener(this);
    }

    @Override
    public void onHxLoginSuccess() {
//        if (app.isClient()) {
//           // showActivity("com.ruanyun.czy.client.MainActivity");
//            loginPresenter.getUserCarInfos();
//        } else {
            showActivity("com.ruanyun.chezhiyi.MainActivity");
            finish();
//        }
    }



    @Override
    public void onHxLoginFail(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AppUtility.showToastMsg(message);
            }
        });
        LogX.d("ycw", "---message---"+message);
    }


    /**
     * 后台根据第三方信息返回用户信息  信息为空 -- 去绑定手机号注册用户    信息为不为空 -- 返回的user 登录
     * @param user
     */
    @Override
    public void thirdBindLogin(User user) {
        if (AppUtility.isNotEmpty(user.getUserNum())) {
            //登录
            loginPresenter.loginWithUser(user);
        } else {
            bindWithPhone();
        }
    }

    @Override public void userNoCar() {
        showActivity("com.ruanyun.czy.client.view.ui.my.AddCarInformationActivity");
    }

    @Override public void userHaseCar() {
        showActivity("com.ruanyun.czy.client.MainActivity");
        finish();
    }

    @Override public void getCarError() {
        finish();
    }

    private void bindWithPhone() {
        //第三方 授权并绑定手机号
        Intent intent = new Intent(mContext, VerifyPhoneActivity.class);
        intent.putExtra(C.IntentKey.THIRD_TYPE, thirdType);
        intent.putExtra(C.IntentKey.THIRD_NUM, this.platform.getDb().getUserId());
        intent.putExtra(C.IntentKey.THIRD_ICON, this.platform.getDb().getUserIcon());
        intent.putExtra(C.IntentKey.THIRD_NAME, this.platform.getDb().getUserName());
        intent.putExtra(C.IntentKey.VERIFY_PH_ONE_TYPE, VerifyPhoneActivity.TYPE_RIGESTE_THRID);
        showActivity(intent);
        finish();
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

    @Override
    public void onUserLoginFail() {

    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_login) { // 登录的处理
            disposeInputMessage();
        } else if (id == R.id.tv_forget_password) {    // 忘记密码
            Intent intent = new Intent(mContext, VerifyPhoneActivity.class);
            intent.putExtra(C.IntentKey.VERIFY_PH_ONE_TYPE, VerifyPhoneActivity
                    .TYPE_FORGET_PASSWORD);
            showActivity(intent);
        } else if (id == R.id.tv_register) {  // 注册
            Intent intent = new Intent(mContext, VerifyPhoneActivity.class);
            intent.putExtra(C.IntentKey.VERIFY_PH_ONE_TYPE, VerifyPhoneActivity.TYPE_RIGESTE);
            startActivity(intent);
        } else if (id == R.id.img_qq) {  // QQ
            //thirdLoginName = LoginPresenter.QQ_LOGIN;
            authorizeLogin(LoginPresenter.QQ_LOGIN);
            thirdType = C.ThirdType.TYPE_QQ;
        } else if (id == R.id.img_wx) {  // 微信
            //thirdLoginName = LoginPresenter.WEIXIN_LOGIN;
            authorizeLogin(LoginPresenter.WEIXIN_LOGIN);
            thirdType =  C.ThirdType.TYPE_WECHAT;
        } else if (id == R.id.img_sina) {  // 微博
            //thirdLoginName = LoginPresenter.SINAWEIBO_LOGIN;
            authorizeLogin(LoginPresenter.SINAWEIBO_LOGIN);
            thirdType = C.ThirdType.TYPE_SINA;
        }
    }



    /**
     * 处理输入的信息
     */
    private void disposeInputMessage() {
        String password = editPasswordInput.getText().toString();
        String phone = editPhoneInput.getText().toString();
        if (AppUtility.isNotEmpty(phone) && AppUtility.isNotEmpty(password)) {
            Call call = loginCall(phone, MD5.md5(password), userType);
            loginPresenter.userLogin(call);
        } else {
            AppUtility.showToastMsg("请将信息填写完整！");
        }
    }

    /**
     * 登陆的参数
     */
    private LoginParams loginParams = new LoginParams();

    /**
     * 登录接口
     */
    private Call loginCall(String loginName, String loginPass, String userType) {
        loginParams.setLoginPass(loginPass);
        loginParams.setUserType(userType);
        return app.getApiService().userLogin(loginName, loginParams);
    }

    /**
     * 授权登陆
     *
     * @param platFormName
     */
    public void authorizeLogin(String platFormName) {
        Platform platform = ShareSDK.getPlatform(platFormName);
        platform.setPlatformActionListener(this);
        platform.authorize();
    }

    /**
     * 第三方授权回调   获取到授权信息后
     * @param platform
     * @param i
     * @param hashMap
     */
    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        this.platform = platform;
        LogX.d(TAG, "============  onComplete  ===========");
        /**  根据第三方信息 获取用户信息  */
        loginPresenter.loginThird(app.getApiService().loginThird(platform.getDb().getUserId(), thirdType));
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        LogX.d(TAG, "============  onError  ===========");

    }

    @Override
    public void onCancel(Platform platform, int i) {
        LogX.d(TAG, "============  onCancel  ==========");

    }
}
