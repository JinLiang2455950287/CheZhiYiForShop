package com.ruanyun.chezhiyi.view.ui.TiXing;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.BaseActivity;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 2017.4.17
 * jl
 * 会员提醒
 */
public class TiXingActivity extends BaseActivity implements Topbar.onTopbarClickListener {

    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.wait_project)
    RelativeLayout waitProject;
    @BindView(R.id.wait_huiyuan)
    RelativeLayout waitHuiyuan;
    @BindView(R.id.wait_birthday)
    RelativeLayout waitBirthday;
    @BindView(R.id.wait_remain)
    RelativeLayout waitRemain;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ti_xing);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        topbar.setTttleText("业务提醒")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
    }

    public void setIntent(String string) {
        Intent intent = new Intent(this, TiXingMangerActivity.class);
        intent.putExtra("waitType", string);
        startActivity(intent);
    }

    @OnClick({R.id.wait_project, R.id.wait_huiyuan, R.id.wait_birthday, R.id.wait_remain})
    public void OnClickView(View view) {
        switch (view.getId()) {
            case R.id.wait_project:
                setIntent("wait_project");
                break;
            case R.id.wait_huiyuan:
                setIntent("wait_huiyuan");
                break;
            case R.id.wait_birthday:
                setIntent("wait_birthday");
                break;
            case R.id.wait_remain:
                setIntent("wait_remain");
                break;
        }
    }

    @Override
    public void onTobbarViewClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.img_btn_left) {
            finish();
        }
    }
}
