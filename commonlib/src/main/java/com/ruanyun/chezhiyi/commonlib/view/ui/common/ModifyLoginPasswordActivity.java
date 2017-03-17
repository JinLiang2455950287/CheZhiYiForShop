package com.ruanyun.chezhiyi.commonlib.view.ui.common;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.view.widget.CleanableEditText;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**修改登录密码
 * Created by wp on 2016/10/12.
 */
public class ModifyLoginPasswordActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener, View.OnClickListener {
    Topbar topbar;
    CleanableEditText editOldPassword;
    //    @BindView(R.id.edit_news_password)
    CleanableEditText editNewsPassword;
    //    @BindView(R.id.edit_confirm_passsword)
    CleanableEditText editConfirmPasssword;
    //    @BindView(R.id.btn_update)
    Button btnUpdate;
    String oldPassword, newPassword, confirmPassword;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_update_password);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        topbar = getView(R.id.topbar);
        topbar.setTttleText("修改密码")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);

        editOldPassword = getView(R.id.edit_old_password);
        editNewsPassword = getView(R.id.edit_news_password);
        editConfirmPasssword = getView(R.id.edit_confirm_passsword);
        btnUpdate = getView(R.id.btn_update);
        btnUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        oldPassword = editOldPassword.getText().toString();
        newPassword = editNewsPassword.getText().toString();
        confirmPassword = editConfirmPasssword.getText().toString();
        if (TextUtils.isEmpty(oldPassword)) {
            AppUtility.showToastMsg("请输入旧密码");
            return;
        } else if (TextUtils.isEmpty(newPassword)) {
            AppUtility.showToastMsg("请输入新密码");
            return;
        } else if (TextUtils.isEmpty(confirmPassword)) {
            AppUtility.showToastMsg("请输入确认新密码");
            return;
        } else if (newPassword.equals(confirmPassword)) {
            AppUtility.showToastMsg("两次密码不一致，请重新输入新密码");
            return;
        }
        ModifyPassword();
    }

    //修改登录密码
    private void ModifyPassword() {

    }

    @Override
    public void onTobbarViewClick(View v) {
        int i = v.getId();
        if (i == R.id.img_btn_left) {
            finish();
        }
    }


}
