package com.ruanyun.czy.client.view.ui.my;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.model.CrowdFundingInfo;
import com.ruanyun.chezhiyi.commonlib.model.Tab;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.chezhiyi.commonlib.view.widget.lazyviewpager.LazyFragmentPagerAdapter;
import com.ruanyun.chezhiyi.commonlib.view.widget.lazyviewpager.LazyViewPager;
import com.ruanyun.czy.client.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Description ：我的众筹
 * <p/>
 * Created by hdl on 2016/9/21.
 */
public class MyCrowdFundingActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener{
    Topbar topbar;
    TabLayout tabTitle;
    LazyViewPager contentPanle;
    List<Fragment> tabs = new ArrayList<>();
    List<Tab> tabTitles;
    TabViewPagerAdapter pageAdapter;
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
        topbar.setTttleText("我的众筹")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
    }

    private void initData() {
        tabTitles = new ArrayList<Tab>();
        tabTitles.add(new Tab("进行中", CrowdFundingInfo.UNDERWAY));
        tabTitles.add(new Tab("已结束", CrowdFundingInfo.ENDED));

        for (Tab info : tabTitles) {
            tabTitle.addTab(tabTitle.newTab().setText(info.getTabNum()));
            Bundle bundle = new Bundle();
            bundle.putString(C.IntentKey.MY_CROWD_FUNDING_TYPE, info.getType());
            MyCrowdFundingFragment myCrowdFundingFragment = new MyCrowdFundingFragment();
            myCrowdFundingFragment.setArguments(bundle);
            tabs.add(myCrowdFundingFragment);
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
     * 滑动Viewpager  Adapter
     */
    class TabViewPagerAdapter extends LazyFragmentPagerAdapter {

        public TabViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

      /*  @Override
        public Fragment getItem(int position) {
            //return tabs.get(position);
            return ArticleListFragment.newInstance(tabTitles.get(position).getItemCode());
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
            return tabs.get(position);
        }
    }
}
