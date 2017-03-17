package com.ruanyun.chezhiyi.commonlib.view.ui.common;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.params.LoginParams;
import com.ruanyun.chezhiyi.commonlib.presenter.ForgetPasswordPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.MD5;
import com.ruanyun.chezhiyi.commonlib.view.ForgetPasswordMvpView;
import com.ruanyun.chezhiyi.commonlib.view.widget.CleanableEditText;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * 忘记密码
 * Created by Sxq on 2016/8/27.
 */
public class ForgetPasswordActivity extends AutoLayoutActivity implements View.OnClickListener, Topbar.onTopbarClickListener, ForgetPasswordMvpView {
    Topbar topbar;
    CleanableEditText editConfirmPasssword;
    Button btnResetPassword;
    CleanableEditText editPassword;
    private LoginParams params = new LoginParams();
    private String password, confirmPassword, phone;
    private ForgetPasswordPresenter forgetPasswordPresenter = new ForgetPasswordPresenter();
    private Call call;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        initView();
        forgetPasswordPresenter.attachView(this);
    }

    private void initView() {
        topbar = getView(R.id.topbar);
        topbar.setTttleText(R.string.retrieve_password).setBackBtnEnable(true).setTopbarClickListener(this);
        topbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
        editPassword = getView(R.id.edit_password);
        editConfirmPasssword = getView(R.id.edit_confirm_passsword);
        btnResetPassword = getView(R.id.btn_reset_password);
        btnResetPassword.setOnClickListener(this);
        phone = getIntent().getStringExtra(C.IntentKey.PHONE);//手机号
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_reset_password) {
            resetPassword();
        }

    }

    /**
     * 重置密码
     */
    private void resetPassword() {
        password = editPassword.getText().toString();
        confirmPassword = editConfirmPasssword.getText().toString();

        if (!AppUtility.isNotEmpty(password) || !AppUtility.isNotEmpty(confirmPassword)) {
            AppUtility.showToastMsg(R.string.please_fill_in_the_information);
        } else if (!password.equals(confirmPassword)) {
            AppUtility.showToastMsg(R.string.password_not_same);
        } else {
            params.setUserType(app.isClient() ? "3" : "2");    //1 系统用户 2 技师用户 3 客户端用户
            params.setLoginPass(MD5.md5(password));
            call = app.getApiService().forgetPassword(phone, params);
            forgetPasswordPresenter.forgetPassword(call);
        }
    }


    @Override
    public void onTobbarViewClick(View v) {
        int i = v.getId();
        if (i == R.id.left_image) {
            finish();
        }
    }

    @Override
    public void onResetSuccess(ResultBase<User> userResult) {
        AppUtility.showToastMsg(userResult.getMsg());
        finish();
    }

    @Override
    public void onResetMsg(String msg) {
        AppUtility.showToastMsg(msg);
    }

    @Override
    public void showLoadingView() {
        showLoading("处理中");
    }

    @Override
    public void onResetResponse() {
        dissMissLoading();
    }

    @Override
    public Context getContext() {
        return null;
    }
}
