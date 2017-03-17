package com.ruanyun.chezhiyi.commonlib.view.ui.common;


import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.MD5;
import com.ruanyun.chezhiyi.commonlib.view.widget.CleanableEditText;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

public class InputTradePwdDialog extends AutoLayoutActivity implements View.OnClickListener {

    CleanableEditText edt_inputpwd;
    Button btn_cancel;
    Button btn_confirm;
    private String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_input_pwd);
        initView();

    }

    private void initView(){
        edt_inputpwd = getView(R.id.edt_inputpwd);
        btn_cancel = getView(R.id.btn_cancel);
        btn_confirm = getView(R.id.btn_confirm);
        btn_cancel.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        if (viewId == R.id.btn_cancel) {
            finish();
        } else if (viewId == R.id.btn_confirm) {
            password = edt_inputpwd.getText().toString().trim();
            if (!TextUtils.isEmpty(password)) {
                setResult(RESULT_OK, new Intent().putExtra(C.IntentKey.PAY_PASSWORD, password));
                finish();
            } else {
                AppUtility.showToastMsg("你还没有填写密码");
            }
        }
    }
}
