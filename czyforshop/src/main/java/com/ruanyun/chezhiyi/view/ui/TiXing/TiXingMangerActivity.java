package com.ruanyun.chezhiyi.view.ui.TiXing;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.BaseActivity;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 2017.4.17
 * jl
 * 会员提醒管理界面
 */
public class TiXingMangerActivity extends BaseActivity implements Topbar.onTopbarClickListener {
    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.fl_managerActivity)
    FrameLayout flManagerActivity;
    private TiXingFragment tiXingFragment;
    String waitType;
    String toparTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ti_xing_manger);
        ButterKnife.bind(this);
        waitType = getIntent().getStringExtra("waitType");
        if (waitType.equals("wait_project")) {
            toparTitle = "项目保养提醒";
        } else if (waitType.equals("wait_huiyuan")) {
            toparTitle = "会员流失提醒";
        } else if (waitType.equals("wait_birthday")) {
            toparTitle = "会员生日提醒";
        } else {
            toparTitle = "余额不足提醒";
        }

        topbar.setTttleText(toparTitle)
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
        setFragment(waitType);
    }

    public void setFragment(String type) {
        tiXingFragment = new TiXingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        tiXingFragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_managerActivity, tiXingFragment);
        transaction.commit();
    }

    @Override
    public void onTobbarViewClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.img_btn_left) {
            finish();
        }
    }
}
