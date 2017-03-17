package com.ruanyun.chezhiyi.view.ui.home;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RelativeLayout;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.view.widget.TopTabButton;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.zhy.autolayout.AutoRelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by msq on 2016/9/26.
 * 当天预约Activity
 */
public class DayAppointmentActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener {

    @BindView(R.id.topbar)
    Topbar topbar;
    TopTabButton topTabButton;
    DayAppointmentComeFragment dayAppointmentComeFragment;//未到店
    DayAppointmentGoFragment dayAppointmentGoFragment;//已接待

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_day_appointment);
        ButterKnife.bind(this);
        init();

    }

    private void init() {
        dayAppointmentComeFragment = new DayAppointmentComeFragment();
        dayAppointmentGoFragment = new DayAppointmentGoFragment();
        topTabButton = getViewFromLayout(R.layout.layout_toptabbar, topbar, false);
        topTabButton.setRightText("已接待");
        topTabButton.setLeftText("未到店");
        topTabButton.setLeftTabStatus(true);
        topTabButton.onLeftTabClick(this, "onLeftTabClick");
        topTabButton.onRightTabClick(this, "onRightTabClick");
        topbar.getTvTitle().setVisibility(View.GONE);
        topbar.addViewToTopbar(topTabButton, (AutoRelativeLayout.LayoutParams) topTabButton.getLayoutParams())
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
        //默认加载第一个fragment
        showFragmentAtIndex(1);
    }

    //点击未到店按钮
    public void onLeftTabClick() {
        showFragmentAtIndex(1);
    }

    //点击已接待按钮
    public void onRightTabClick() {
        showFragmentAtIndex(2);
    }

    @Override
    public void onTobbarViewClick(View v) {
        int viewId = v.getId();
        if (viewId == com.ruanyun.chezhiyi.commonlib.R.id.img_btn_left) {
            finish();
        }
    }

    /**
     * 显示指定的fragment
     * @param index
     */
    protected void showFragmentAtIndex(int index) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (index){
            case 1:
                //判断fragment是否已加载
                if (!dayAppointmentComeFragment.isAdded()) {
                    fragmentTransaction.add(R.id.frag_container, dayAppointmentComeFragment);
                    fragmentTransaction.show(dayAppointmentComeFragment).hide(dayAppointmentGoFragment);
                } else {
                    fragmentTransaction.show(dayAppointmentComeFragment).hide(dayAppointmentGoFragment);
                }
                break;
            case 2:
                if (!dayAppointmentGoFragment.isAdded()) {
                    fragmentTransaction.add(R.id.frag_container, dayAppointmentGoFragment);
                    fragmentTransaction.show(dayAppointmentGoFragment).hide(dayAppointmentComeFragment);
                } else {
                    fragmentTransaction.show(dayAppointmentGoFragment).hide(dayAppointmentComeFragment);
                }
                break;
            default:
                break;
        }
        //提交
        fragmentTransaction.commit();
    }

}
