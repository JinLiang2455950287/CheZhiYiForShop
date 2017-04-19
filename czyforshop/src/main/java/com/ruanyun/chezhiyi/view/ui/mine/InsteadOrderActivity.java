package com.ruanyun.chezhiyi.view.ui.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.model.Tab;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.chezhiyi.commonlib.view.widget.lazyviewpager.LazyFragmentPagerAdapter;
import com.ruanyun.chezhiyi.commonlib.view.widget.lazyviewpager.LazyViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Description ：代下单管理
 * <p/>
 * Created by hdl on 2016/9/30.
 */

public class InsteadOrderActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener {

    Topbar topbar;
    TabLayout tabTitle;
    LazyViewPager contentPanle;
    List<Fragment> tabs = new ArrayList<>();
    List<Tab> tabTitles;
    TabViewPagerAdapter pageAdapter;

    public static final String ALL = "";
    public static final String UNDERWAY = "4,5,6,7";
    public static final String END = "9";

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_tablayout_lazy_viewpager);
        initView();
        initData();
    }

    private void initView() {
        topbar = getView(com.ruanyun.chezhiyi.commonlib.R.id.topbar);
        tabTitle = getView(com.ruanyun.chezhiyi.commonlib.R.id.tab_title);
        contentPanle = getView(com.ruanyun.chezhiyi.commonlib.R.id.content_panle);
        topbar.setTttleText("代下单管理")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
    }

    private void initData() {
        tabTitles = new ArrayList<Tab>();
        tabTitles.add(new Tab("全部", ALL));
        tabTitles.add(new Tab("服务中", UNDERWAY));
        tabTitles.add(new Tab("已结束", END));

        for (Tab info : tabTitles) {
            tabTitle.addTab(tabTitle.newTab().setText(info.getTabNum()));
            Bundle bundle = new Bundle();
            bundle.putString(C.IntentKey.INSTEAD_ORDER_TYPE, info.getType());
            InsteadOrderFragment insteadOrderFragment = new InsteadOrderFragment();
            insteadOrderFragment.setArguments(bundle);
            tabs.add(insteadOrderFragment);
        }
        pageAdapter = new TabViewPagerAdapter(getSupportFragmentManager());
        contentPanle.setAdapter(pageAdapter);
        tabTitle.setupWithViewPager(contentPanle);
    }

    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == com.ruanyun.chezhiyi.commonlib.R.id.img_btn_left) {
            finish();
        }
    }

    /**
     * 滑动 ViewPager Adapter
     */
    class TabViewPagerAdapter extends LazyFragmentPagerAdapter {

        public TabViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

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
            return tabs.get(position);
        }
    }

}
