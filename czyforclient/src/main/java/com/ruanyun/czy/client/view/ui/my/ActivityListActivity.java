package com.ruanyun.czy.client.view.ui.my;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.model.Tab;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.chezhiyi.commonlib.view.widget.lazyviewpager.LazyFragmentPagerAdapter;
import com.ruanyun.chezhiyi.commonlib.view.widget.lazyviewpager.LazyViewPager;
import com.ruanyun.czy.client.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 我的活动
 * Created by msq on 2016/9/10.
 */
public class ActivityListActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener {

    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.tab_title)
    TabLayout tabTitle;
    @BindView(R.id.content_panle)
    LazyViewPager contentPanle;

    List<Fragment> tabs = new ArrayList<>();
    List<Tab> tabTitles;
    TabViewPagerAdapter pageAdapter;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_activity_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        topbar.setTttleText("我的活动")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);

        initTabs();
    }

    private void initTabs() {

        tabTitles = new ArrayList<Tab>();
        tabTitles.add(new Tab("进行中","1"));
        tabTitles.add(new Tab("即将开始","2"));
        tabTitles.add(new Tab("已结束","3"));
//        tabTitles.add(new Tab("已取消","-1"));

        for (Tab info : tabTitles) {
            tabTitle.addTab(tabTitle.newTab().setText(info.getTabNum()));
            Bundle bundle = new Bundle();
            bundle.putString(C.IntentKey.ACTIVITY_TYPE, info.getType());
            ActivityListFragment activityListFragment = new ActivityListFragment();
            activityListFragment.setArguments(bundle);
            tabs.add(activityListFragment);
        }
      //  contentPanle.setOffscreenPageLimit(3);
        pageAdapter = new TabViewPagerAdapter(getSupportFragmentManager());
        contentPanle.setAdapter(pageAdapter);
        tabTitle.setupWithViewPager(contentPanle);
    }

    @Override
    public void onTobbarViewClick(View v) {
        int viewId = v.getId();
        if (viewId == com.ruanyun.chezhiyi.commonlib.R.id.img_btn_left) {
            finish();
        }
    }

    class TabViewPagerAdapter extends LazyFragmentPagerAdapter {

        public TabViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

    /*    @Override
        public Fragment getItem(int position) {
            return tabs.get(position);
        }*/

        @Override
        public int getCount() {
            return tabTitles.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles.get(position).getTabNum();
        }

        @Override
        protected Fragment getItem(ViewGroup container, int position) {
            return  tabs.get(position);
        }
    }
}
