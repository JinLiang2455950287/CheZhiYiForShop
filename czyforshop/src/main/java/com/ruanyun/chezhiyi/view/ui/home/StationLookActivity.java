
package com.ruanyun.chezhiyi.view.ui.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.BaseActivity;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.view.widget.StationTopTabButton;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Description ：查看工位 等候区 结算中 切换列表页面
 * <p/>
 * Created by hdl on 2016/9/26.
 */

public class StationLookActivity extends BaseActivity implements Topbar.onTopbarClickListener, StationTopTabButton.onTabClickListener {

    private Topbar topbar;
    private StationTopTabButton topTabButton;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_station_look);
        initView();
    }

    private void initView() {
        topbar = getView(R.id.topbar);
        topTabButton = getViewFromLayout(R.layout.layout_station_toptabbar, topbar, false);
        topTabButton.setLeftText("工位");
        topTabButton.setMiddleText("等候区");
        topTabButton.setMiddle1Text("质检区");
        topTabButton.setRightText("结算中");
        topTabButton.setLeftTabStatus(true);
        topTabButton.setUpTabClickListener(this);
        topbar.getTvTitle().setVisibility(View.INVISIBLE);
        topbar.addViewToTopbar(topTabButton, (AutoRelativeLayout.LayoutParams) topTabButton.getLayoutParams())
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .addViewToTopbar(topbar.getTvTitleRight(), topbar.getLayoutParamsImgRight())
//                .setRightText("工位图") // TODO 暂时不显示
//                .onRightTextClick()
                .setTopbarClickListener(this);

        fragments.add(WorkBayFragment.newInstance());//工位区
        fragments.add(WorkOrderFragment.newInstance(WorkOrderFragment.TAB_TYPE_WAIT_AREA));////等候区
        fragments.add(WorkOrderFragment.newInstance(WorkOrderFragment.TAB_TYPE_QUALITY_CHECK));//质检去
        fragments.add(WorkOrderFragment.newInstance(WorkOrderFragment.TAB_TYPE_SETTLE_ACCOUNTS));//结算区

        changeFragment(0);
    }

    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == com.ruanyun.chezhiyi.commonlib.R.id.img_btn_left) {
            finish();
        } else if (id == com.ruanyun.chezhiyi.commonlib.R.id.tv_title_right) {
            AppUtility.showToastMsg("工位图");
        }
    }


    @Override
    public void onTabClick(View v) {
        switch (v.getId()) {
            case com.ruanyun.chezhiyi.commonlib.R.id.toptab_left:
                topTabButton.setLeftTabStatus(true);
                changeFragment(0);
                break;
            case com.ruanyun.chezhiyi.commonlib.R.id.toptab_middle:
                topTabButton.setMiddleTabStatus(true);
                changeFragment(1);
                break;
            case com.ruanyun.chezhiyi.commonlib.R.id.toptab_middle1:
                topTabButton.setTabMiddle1TabStatus(true);
                changeFragment(2);
                break;
            case com.ruanyun.chezhiyi.commonlib.R.id.toptab_right:
                topTabButton.setRightTabStatus(true);
                changeFragment(3);
                break;
        }
    }


    /**
     * =============================================================================
     */

    private boolean[] isFragmentsAdded = {false, false, false, false};

    private List<Fragment> fragments = new ArrayList<>();


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

