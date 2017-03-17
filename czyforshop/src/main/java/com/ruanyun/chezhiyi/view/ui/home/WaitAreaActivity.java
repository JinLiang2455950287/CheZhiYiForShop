package com.ruanyun.chezhiyi.view.ui.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.BaseActivity;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import java.util.ArrayList;
import java.util.List;

/*
* 等候去页面
* 2017.3.8 setContentView(R.layout.activity_wait_area);
* jin
* */
public class WaitAreaActivity extends BaseActivity implements Topbar.onTopbarClickListener {

    private Topbar topbar;
    private String AreaType;
    private List<Fragment> fragments;
    private boolean[] isFragmentsAdded = {false, false, false, false};

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_wait_area);
        initView();
    }

    private void initView() {
        topbar = getView(R.id.topbar);
        topbar.setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
        fragments = new ArrayList<>();
        fragments.add(WorkBayFragment.newInstance());//工位
        fragments.add(WorkOrderFragment.newInstance(WorkOrderFragment.TAB_TYPE_WAIT_AREA));//等侯区"2"
        fragments.add(WorkOrderFragment.newInstance(WorkOrderFragment.TAB_TYPE_QUALITY_CHECK));//质检区"6"
        fragments.add(WorkOrderFragment.newInstance(WorkOrderFragment.TAB_TYPE_SETTLE_ACCOUNTS));//结算区"3"
        AreaType = getIntent().getStringExtra("AreaType");
        if (AreaType.endsWith("2")) {
            topbar.setTttleText("等侯区");
            changeFragment(1);
        } else if (AreaType.endsWith("6")) {
            topbar.setTttleText("质检区");
            changeFragment(2);
        } else if (AreaType.endsWith("3")) {
            topbar.setTttleText("结算区");
            changeFragment(3);
        } else {
            topbar.setTttleText("工位状态");
            changeFragment(0);
        }
    }

    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == com.ruanyun.chezhiyi.commonlib.R.id.img_btn_left) {
            finish();
        }
    }

    /**
     * 显示指定的  fragment
     *
     * @param index
     */
    private void showFragmentAtIndex(int index) {
        Fragment fragment = fragments.get(index);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (!isFragmentsAdded[index]) {//未加入系统fragment堆栈
            ft.add(R.id.frag_container, fragment).show(fragment).commit();
            isFragmentsAdded[index] = true;
        } else {
            ft.show(fragments.get(index)).commit();
        }
    }

    private void hideAllFragments() {
        if (fragments.size() > 0)
            for (Fragment fragment : fragments) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.hide(fragment).commitAllowingStateLoss();
            }
    }

    private void changeFragment(int index) {
        hideAllFragments();
        showFragmentAtIndex(index);
    }

}
