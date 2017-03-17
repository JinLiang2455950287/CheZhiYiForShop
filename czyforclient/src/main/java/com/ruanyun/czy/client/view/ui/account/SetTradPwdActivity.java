package com.ruanyun.czy.client.view.ui.account;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.model.params.SetTradePwdParams;
import com.ruanyun.chezhiyi.commonlib.presenter.SetTradePwdPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.MD5;
import com.ruanyun.chezhiyi.commonlib.view.SetTradePwdMvpView;
import com.ruanyun.chezhiyi.commonlib.view.widget.CleanableEditText;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.czy.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Description:
 * author: zhangsan on 16/10/19 下午5:17.
 */
public class SetTradPwdActivity extends AutoLayoutActivity implements SetTradePwdMvpView, Topbar.onTopbarClickListener {

    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.input_login_pwd)
    CleanableEditText inputLoginPwd;
    @BindView(R.id.edit_input_pwd)
    CleanableEditText editInputPwd;
    @BindView(R.id.edit_confirm_passsword)
    CleanableEditText editConfirmPasssword;
    @BindView(R.id.btn_reset_password)
    Button btnResetPassword;
    SetTradePwdParams params=new SetTradePwdParams();
    SetTradePwdPresenter presenter=new SetTradePwdPresenter();
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_set_tradpwd);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    private void initView() {
        presenter.attachView(this);
        topbar.setBackBtnEnable(true)
              .setTttleText("设置交易密码")
              .onBackBtnClick()
              .setTopbarClickListener(this)  ;
    }

    @OnClick(R.id.btn_reset_password)
    public void onClick() {
        String loginPwd = inputLoginPwd.getText().toString().trim();
        String tradePwd = editInputPwd.getText().toString().trim();
        String confirmPwd = editConfirmPasssword.getText().toString().trim();
        if (TextUtils.isEmpty(loginPwd)) {
            AppUtility.showToastMsg("请输入登录密码");
            return;
        } else if (TextUtils.isEmpty(tradePwd)) {
            AppUtility.showToastMsg("请输入支付密码");
            return;
        } else if (TextUtils.isEmpty(confirmPwd)) {
            AppUtility.showToastMsg("请确认支付密码");
            return;
        } else if (!tradePwd.equals(confirmPwd)) {
            AppUtility.showToastMsg("两次密码不一致,请重新输入");
            return;
        } else if (tradePwd.length() != 6) {
            AppUtility.showToastMsg("支付密码必须是6位");
            return;
        }
        params.setLoginPassword(MD5.md5(loginPwd));
        params.setPayPassword(MD5.md5(tradePwd));
        presenter.submitSetting(params);
    }

    @Override
    public void showToast(String msg) {
        AppUtility.showToastMsg(msg);
    }

    @Override
    public void showLoadingView(String msg) {
        showLoading(msg);
    }

    @Override
    public void disMissLoadingView() {
        dissMissLoading();
    }

    @Override
    public void setSuccess() {
        app.beanCacheHelper.getAccountMoney();
        finish();
    }

    @Override
    public void setFail() {

    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void onTobbarViewClick(View v) {
        switch (v.getId()){
            case com.ruanyun.chezhiyi.commonlib.R.id.img_btn_left:
                finish();
                break;
        }
    }
}
