package com.ruanyun.czy.client.view.ui.my;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.model.Tab;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.czy.client.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的订单
 * Created by Sxq on 2016/9/19.
 */
public class OrderListActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener {
    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.tab_title)
    TabLayout tabTitle;
    @BindView(R.id.content_panle)
    /*Lazy*/ViewPager contentPanle;
    TabViewPagerAdapter pageAdapter;
    List<Tab> tabTitles;
    List<Fragment> tabs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_order_list);
        ButterKnife.bind(this);
        initView();
        initTabs();
    }

    private void initView() {
        topbar.setTttleText("我的订单")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
    }

    private void initTabs() {
        tabTitles = new ArrayList<Tab>();
        tabTitles.add(new Tab("全部",""));
        tabTitles.add(new Tab("待付款","1"));
        tabTitles.add(new Tab("待消费","2"));
        tabTitles.add(new Tab("待评价","3"));
        tabTitles.add(new Tab("退款","-2,4"));
        tabTitles.add(new Tab("已完成","5"));
        for (Tab info : tabTitles) {
            tabTitle.addTab(tabTitle.newTab().setText(info.getTabNum()));
            Bundle bundle = new Bundle();
            bundle.putString(C.IntentKey.ORDER_TYPE, info.getType());
            OrderListFragment orderListFragment = new OrderListFragment();
            orderListFragment.setArguments(bundle);
            tabs.add(orderListFragment);
        }
        pageAdapter = new TabViewPagerAdapter(getSupportFragmentManager());
        contentPanle.setAdapter(pageAdapter);
        contentPanle.setOffscreenPageLimit(5);
//        contentPanle.setInitLazyItemOffset(0.2f);
        tabTitle.setupWithViewPager(contentPanle);
    }

    /**
     * topbar 点击事件
     * @param v
     */
    @Override
    public void onTobbarViewClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.img_btn_left) {
            finish();
        }
    }

    class TabViewPagerAdapter extends /*Lazy*/FragmentPagerAdapter {

        public TabViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return tabs.get(position);
        }

        @Override
        public int getCount() {
            return tabTitles.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles.get(position).getTabNum();
        }

        /*@Override
        protected Fragment getItem(ViewGroup container, int position) {
            return tabs.get(position);
        }*/
    }

}
