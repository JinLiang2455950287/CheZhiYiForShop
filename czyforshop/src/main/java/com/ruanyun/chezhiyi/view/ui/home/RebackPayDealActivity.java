package com.ruanyun.chezhiyi.view.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.presenter.RebackPayPrsenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.RebackPayMvpView;
import com.ruanyun.chezhiyi.commonlib.view.widget.EmojiFiltrationTextWatcher;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RebackPayDealActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener, RebackPayMvpView {

    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.btn_agree)
    Button btnAgree;
    @BindView(R.id.btn_refuse)
    Button btnRefuse;
    private RebackPayPrsenter payPrsenter = new RebackPayPrsenter();
    private String refundNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reback_pay_deal);
        ButterKnife.bind(this);
        initView();

        //监听EditText内容变化
        etContent.addTextChangedListener(new EmojiFiltrationTextWatcher(etContent) {
            @Override
            public void emojiFiltAfterTextChanged(Editable editable) {
                tvCount.setText(editable.length() + "/100");
                if (100 == editable.length()) {
                    AppUtility.showToastMsg("字数超出范围");
                }
            }
        });
    }

    private void initView() {
        refundNum = getIntent().getStringExtra("refundNum");
        payPrsenter.attachView(this);
        topbar.setTttleText("退款审核")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
    }

    @OnClick({R.id.btn_agree, R.id.btn_refuse})
    public void getOnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_agree:
                app.loadingDialogHelper.showLoading(this, "数据提交中...");
                payPrsenter.getRebackReason(app.getApiService().getRePayExanine(app.getCurrentUserNum(), refundNum, 1, etContent.getText().toString()));
                break;

            case R.id.btn_refuse:
                app.loadingDialogHelper.showLoading(this, "数据提交中...");
                payPrsenter.getRebackReason(app.getApiService().getRePayExanine(app.getCurrentUserNum(), refundNum, 3, etContent.getText().toString()));
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
    public void getRebackSccess(ResultBase resultBase) {
        app.loadingDialogHelper.dissMiss();
        LogX.e("退款审核1", resultBase.getObj().toString());
        LogX.e("退款审核1", resultBase.toString());
        if (resultBase.getResult() == 1) {
            finish();
        }
    }

    @Override
    public void getRebackFalse(ResultBase resultBase) {
        LogX.e("退款审核1getRebackFalse", resultBase.getObj().toString());
        LogX.e("退款审核1getRebackFalse", resultBase.toString());
        app.loadingDialogHelper.dissMiss();
        AppUtility.showToastMsg("退款审核失败");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        payPrsenter.detachView();
    }
}
