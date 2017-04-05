package com.ruanyun.chezhiyi.commonlib.view.ui.common;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

/**
 * 关于掌上汽服
 * Created by YCW on 2016/11/15.
 */
public class AboutActivity extends AutoLayoutActivity implements View.OnClickListener, Topbar.onTopbarClickListener {

    Topbar topbar;
    TextView tvVersions;
    TextView tvTechnicalSupport;
    TextView button;
    TextView tvCopRight;
    TextView tvCompany;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_about);
        initView();
    }

    private void initView() {
        topbar = getView(R.id.topbar);
        tvVersions = getView(R.id.tvVersions);
        tvTechnicalSupport = getView(R.id.tvTechnicalSupport);
        button = getView(R.id.button);
        tvCopRight = getView(R.id.tvCopRight);
        tvCompany = getView(R.id.tvCompany);

        tvVersions.setText(AppUtility.getVersion(mContext));
        button.setOnClickListener(this);

        topbar.setTttleText("关于我们")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            // TODO: 2016/11/15 关于界面的  检查更新

        }
    }

    @Override
    public void onTobbarViewClick(View v) {
        if (v.getId() == R.id.img_btn_left) {
            finish();
        }
    }
}
