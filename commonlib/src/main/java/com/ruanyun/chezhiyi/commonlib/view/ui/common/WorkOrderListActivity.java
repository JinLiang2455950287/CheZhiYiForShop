package com.ruanyun.chezhiyi.commonlib.view.ui.common;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.model.Tab;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import java.util.ArrayList;

import de.greenrobot.event.Subscribe;

/**
 * Description ：我的工单列表
 * <p/>
 * Created by ycw on 2016/10/10.
 */
public class WorkOrderListActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener {


    Topbar topbar;
    TabLayout tabTitleLayout;
    /*Lazy*/ ViewPager contentPanle;
    private ArrayList<Tab> tabTitles;
    private ArrayList<Fragment> tabs = new ArrayList<>();
    String[] clientTabName = {"全部", "等候中", "进行中", "待结算", "已完成"};
    String[] clientTabType = {"2,3,4,5,6,7,8,9", "3", "4,5,6,7", "8", "9"};
    String[] shopTabName = {"全部", "待服务", "服务中", "结算中", "已完成"};
    String[] shopTabType = {"4,5,6,7,8,9", "3,4,7", "5"/*,6,7*/, "8", "9"};


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_work_order);
        initView(app.isClient());
        initTab(app.isClient());
        setResult(RESULT_OK);
        registerBus();
    }

    private void initTab(boolean client) {
        tabTitles = new ArrayList<Tab>();
        if (client) {
            setTabTitles(clientTabName, clientTabType);
        } else {
            setTabTitles(shopTabName, shopTabType);
        }

        for (Tab info : tabTitles) {
            tabTitleLayout.addTab(tabTitleLayout.newTab().setText(info.getTabNum()));
            Bundle bundle = new Bundle();
            bundle.putString(C.IntentKey.WORK_ORDER_STATUS_STRING, info.getType());
            WorkOrderListFragment workOrderListFragment = new WorkOrderListFragment();
            workOrderListFragment.setArguments(bundle);
            tabs.add(workOrderListFragment);
        }
        contentPanle.setOffscreenPageLimit(5);
//        getSupportFragmentManager().enableDebugLogging(true);
        contentPanle.setAdapter(new /*Lazy*/FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return tabs.get(position);
            }
           /* @Override
            protected Fragment getItem(ViewGroup container, int position) {
                return tabs.get(position);
            }*/

            @Override
            public CharSequence getPageTitle(int position) {
                return tabTitles.get(position).getTabNum();
            }

            @Override
            public int getCount() {
                return tabTitles.size();
            }
        });
        tabTitleLayout.setupWithViewPager(contentPanle);

        setSelecteViewPage(getIntent().getIntExtra("item", 0));
    }

    /**
     * 设置名称  和对应 type
     *
     * @param tabName
     * @param tabType
     */
    private void setTabTitles(String[] tabName, String[] tabType) {
        for (int i = 0; i < tabName.length; i++) {
            tabTitles.add(new Tab(tabName[i], tabType[i]));
        }
    }

    private void initView(boolean client) {
        topbar = getView(R.id.topbar);
        tabTitleLayout = getView(R.id.tab_title);
        contentPanle = getView(R.id.content_panle);
        topbar.setTttleText(client ? "我的工单" : "服务工单")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
    }


    @Override
    public void onTobbarViewClick(View v) {
        int viewId = v.getId();
        if (viewId == com.ruanyun.chezhiyi.commonlib.R.id.img_btn_left) {
            finish();
        }
    }

    /**
     * 选中viewpage Item 项
     *
     * @param item
     */
    public void setSelecteViewPage(int item) {
        contentPanle.setCurrentItem(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterBus();
    }

    /**
     * 工单结算完成
     */
    @Subscribe
    public void upViewToFinish(Event<String> event) {
        if (event.key.equals(C.EventKey.WORK_ORDER_LIST_FINISH)) {
            setSelecteViewPage(4);// 最后一个
        }
    }
}
