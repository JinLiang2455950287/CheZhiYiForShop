package com.ruanyun.chezhiyi.view.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.view.widget.EmojiFiltrationTextWatcher;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RebackPayDealActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener {

    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.et_content)
    EditText etContent;

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
        topbar.setTttleText("退款审核")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
    }

    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == com.ruanyun.chezhiyi.commonlib.R.id.img_btn_left) {
            finish();
        }
    }
}
