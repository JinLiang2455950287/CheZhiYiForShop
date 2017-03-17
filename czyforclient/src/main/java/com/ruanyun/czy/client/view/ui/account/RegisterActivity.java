package com.ruanyun.czy.client.view.ui.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.AgreementContentInfo;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.params.LoginParams;
import com.ruanyun.chezhiyi.commonlib.model.params.RegisterParams;
import com.ruanyun.chezhiyi.commonlib.presenter.LoginPresenter;
import com.ruanyun.chezhiyi.commonlib.presenter.RegisterPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.MD5;
import com.ruanyun.chezhiyi.commonlib.view.LoginMvpView;
import com.ruanyun.chezhiyi.commonlib.view.RegisterMvpView;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.LoginActivity;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.ShowContentActivity;
import com.ruanyun.chezhiyi.commonlib.view.widget.CleanableEditText;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.czy.client.MainActivity;
import com.ruanyun.czy.client.R;
import com.ruanyun.czy.client.view.ui.my.AddCarInformationActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Description ：客户端的注册界面
 * <p>
 * Created by ycw on 2016/8/9.
 */
public class RegisterActivity extends AutoLayoutActivity
        implements RegisterMvpView, Topbar.onTopbarClickListener, LoginMvpView {

    public static final String USER_TYPE = "3";
    /**
     * 注册参数
     */
    RegisterParams registerParam = new RegisterParams();
    /**
     * 注册  Presenter
     */
    RegisterPresenter registerPresenter = new RegisterPresenter();
    LoginPresenter loginPresenter = new LoginPresenter();
    String phone;

    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.edit_nick_name)
    CleanableEditText editNickName;
    @BindView(R.id.edit_password)
    CleanableEditText editPassword;
    @BindView(R.id.edit_confirm_passsword)
    CleanableEditText editConfirmPasssword;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.tv_user_agreement)
    TextView tvUserAgreement;
    private String loginPass;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        phone = getIntent().getStringExtra(C.IntentKey.PHONE);
        initView();
        registerPresenter.attachView(this);
        loginPresenter.attachView(this);
    }

    private void initView() {
        topbar.setTttleText("注册")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
        topbar.setBackgroundColor(ContextCompat.getColor(mContext, com.ruanyun.chezhiyi.commonlib.R.color.transparent));
        Spannable span = new SpannableString(getString(com.ruanyun.chezhiyi.commonlib.R.string.user_agreement));
        span.setSpan(new ForegroundColorSpan(getResources().getColor(com.ruanyun.chezhiyi.commonlib.R.color.theme_color_default)), 7, span.length(), Spannable
                .SPAN_EXCLUSIVE_INCLUSIVE);
        span.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                //获取用户协议接口
                registerPresenter.agreement();
            }
        }, 7, span.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tvUserAgreement.setMovementMethod(LinkMovementMethod.getInstance());
        tvUserAgreement.setText(span);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        registerPresenter.detachView();
    }

    @Override
    public void onRegisterShowLoading() {
        showLoading();
    }

    @Override
    public void onRegisterSuccess(ResultBase resultBase) {
        // TODO: 2016/12/18 跳转到主界面
        LoginParams params = new LoginParams();
        params.setUserType(USER_TYPE);
        params.setLoginPass(loginPass);
        loginPresenter.userLogin(app.getApiService().userLogin(phone, params));
//        finish();
    }

    @Override
    public void onRegisterError(ResultBase resultBase, int errorCode) {
        AppUtility.showToastMsg(resultBase.getMsg());
    }

    @Override
    public void onRegisterFail(String msg) {
        AppUtility.showToastMsg(msg);
    }

    @Override
    public void onRegisterResponse() {
        dissMissLoading();
    }

    @Override
    public void onAgreementSuccess(AgreementContentInfo agreementContentInfo) {
        Intent intent = new Intent(mContext, ShowContentActivity.class);
        intent.putExtra(C.IntentKey.EDIT_CONTEXT, agreementContentInfo.getContent());
        intent.putExtra(C.IntentKey.TOPBAR_TITLE, ShowContentActivity.STRING_YHXY);
        showActivity(intent);
    }

    @OnClick(R.id.btn_register)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                String nickName = editNickName.getText().toString();
                String password = editPassword.getText().toString();
                String confirmPassword = editConfirmPasssword.getText().toString();
                if (!(AppUtility.isNotEmpty(phone) &&
                        AppUtility.isNotEmpty(password) &&
                        AppUtility.isNotEmpty(confirmPassword) &&
                        AppUtility.isNotEmpty(nickName))) {
                    AppUtility.showToastMsg("请将信息填写完整！");
                } else if (!password.equals(confirmPassword)) {
                    AppUtility.showToastMsg("请确认2次密码输入一致！");
                } else if (password.length() < 6) {
                    AppUtility.showToastMsg("密码位数不能低于6位");
                }else {
                    registerParam.setNickName(nickName);
                    registerParam.setUserName(nickName);
                    loginPass = MD5.md5(password);
                    registerParam.setLoginPass(loginPass);
                    registerParam.setUserType(USER_TYPE);
                    Call call = app.getApiService().userRegister(phone, registerParam);
                    registerPresenter.userRegister(call);
                }
                break;
        }
    }

    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == com.ruanyun.chezhiyi.commonlib.R.id.img_btn_left) {
            finish();
        }
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void onHxLoginSuccess() {
        // TODO: 2016/12/18 注册成功 后并且登陆成功
        loginPresenter.getUserCarInfos();
    }

    @Override
    public void onUserLoginError() {
        skipActivity(LoginActivity.class);
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
        skipActivity(LoginActivity.class);
    }

    @Override
    public void onHxLoginFail(String message) {
        skipActivity(LoginActivity.class);
    }

    @Override
    public void thirdBindLogin(User user) {

    }

    @Override
    public void userNoCar() {
//        showActivity("com.ruanyun.czy.client.view.ui.my.AddCarInformationActivity");
        showActivity(AddCarInformationActivity.class);
        finish();
    }

    @Override
    public void userHaseCar() {
        showActivity(MainActivity.class);
        finish();
    }

    @Override
    public void getCarError() {
        finish();
    }
}
