package com.ruanyun.chezhiyi.view.ui.home;

import android.os.Bundle;
import android.view.View;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

/**
 * Description ：预约处理详情页面
 * <p/>jin
 * Created by hdl on 2017/3/9.
 */
public class AppointMentDealDetailActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener {
    private Topbar topbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint_ment_deal_detail);
        initView();
    }

    private void initView() {
        topbar = (Topbar) findViewById(R.id.topbar);
        topbar.setTttleText("预约处理")
                .setBackBtnEnable(true)
                .addViewToTopbar(topbar.getTvTitleRight(), topbar.getLayoutParamsImgRight())
                .onBackBtnClick()
                .setRightText("确定")
                .onRightTextClick()
                .setTopbarClickListener(this);
    }

    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == com.ruanyun.chezhiyi.commonlib.R.id.img_btn_left) {
            finish();
        } else if (id == R.id.tv_title_right) {
            AppUtility.showToastMsg("你点击了确定");
        }
    }
}
