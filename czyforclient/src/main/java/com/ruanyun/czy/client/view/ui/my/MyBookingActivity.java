package com.ruanyun.czy.client.view.ui.my;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.model.Tab;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.czy.client.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import de.greenrobot.event.Subscribe;

/**
 * Description ：我的预约
 * <p/>
 * Created by hdl on 2016/9/18.
 */
public class MyBookingActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener {


    Topbar topbar;
    TabLayout tabTitle;
    /*Lazy*/ViewPager contentPanle;
    List<Fragment> tabs = new ArrayList<>();
    List<Tab> tabTitles;
    TabViewPagerAdapter pageAdapter;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_tablayout_viewpager);
        ButterKnife.bind(this);
        initView();
        initData();
        registerBus();
    }

    private void initView() {
        topbar = getView(com.ruanyun.chezhiyi.commonlib.R.id.topbar);
        tabTitle = getView(com.ruanyun.chezhiyi.commonlib.R.id.tab_title);
        contentPanle = getView(com.ruanyun.chezhiyi.commonlib.R.id.content_panle);
        topbar.setTttleText("我的预约")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
    }

    private void initData() {
        tabTitles = new ArrayList<Tab>();
        tabTitles.add(new Tab("全部",""));
        tabTitles.add(new Tab("待确认","1,2"));
        tabTitles.add(new Tab("待服务","3"));
        tabTitles.add(new Tab("已完成","4"));

        for (Tab info : tabTitles) {
            tabTitle.addTab(tabTitle.newTab().setText(info.getTabNum()));
            Bundle bundle = new Bundle();
            bundle.putString(C.IntentKey.MY_BOOKING_TYPE, info.getType());
            MyBookingFragment myBookingFragment = new MyBookingFragment();
            myBookingFragment.setArguments(bundle);
            tabs.add(myBookingFragment);
        }
        pageAdapter = new TabViewPagerAdapter(getSupportFragmentManager());
        contentPanle.setAdapter(pageAdapter);
        tabTitle.setupWithViewPager(contentPanle);
    }

    /**
     * topbar监听
     * @param v
     */
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

//        @Override
//        protected Fragment getItem(ViewGroup container, int position) {
//            return tabs.get(position);
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterBus();
    }

    @Subscribe
    public void selectPage(Event<String> event){
        if (event.key.equals(C.EventKey.TO_BOOKINR_WATIE) && event.value.equals(C.EventKey.TO_BOOKINR_WATIE)) {
            contentPanle.setCurrentItem(2);
        }
    }
}
