package com.ruanyun.chezhiyi.view.ui.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import java.util.List;

import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Description ：优惠券管理界面
 * <p/>
 * Created by hdl on 2016/9/19.
 */
public class DiscountCouponActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener  {
    Topbar topbar;
    TabLayout tabTitle;
    ViewPager contentPanle;
    List<Fragment> tabs = new ArrayList<>();
    List<Tab> tabTitles;
    TabViewPagerAdapter pageAdapter;
    private String[] tabstr = new String[]{"未送出", "已送出", "已过期"};

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_tablayout_viewpager);
        registerBus();
        initView();
        initdata();
    }
    public static final String UNUSED = "1";//未送出
    public static final String SEND = "2";//已送出
    public static final String EXCEED_TIME_LIMIT = "3";//已过期
    public static final String REFRESH_ACTIVITY = "4";//更新界面

    /**
     * DiscountCouponFragment 167
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onCreatGroup(Event<String[]> event){
        if(event!=null&&event.key.equals(C.EventKey.DISCOUNT_COUPON_NUMBER)){
            String[] strs = event.value;
//            if(UNUSED.equals(strs[0])){
//                tabTitle.getTabAt(0).setText("未送出("+strs[1]+")");
//            }else if(SEND.equals(strs[0])){
//                tabTitle.getTabAt(1).setText("已送出("+strs[1]+")");
//            }else if(EXCEED_TIME_LIMIT.equals(strs[0])){
//                tabTitle.getTabAt(2).setText("已过期("+strs[1]+")");
//            }

            int postion = getPostion(strs[0]);
            if (postion != -1) {
                tabTitle.getTabAt(postion).setText(tabstr[postion] + "(" + strs[1] + ")");
            }
        }
    }


    private int getPostion(String type) {
        for (int i = 0; i < tabTitles.size(); i++) {
            if (tabTitles.get(i).getType().equals(type)) {
                return i;  // 返回找到的位置
            }
        }
        return -1;// 没有找到  返回-1
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterBus();
    }

    private void initView() {
        topbar = getView(R.id.topbar);
        tabTitle = getView(R.id.tab_title);
        contentPanle = getView(R.id.content_panle);
        topbar.setTttleText("优惠券管理")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
    }

    private void initdata() {
        tabTitles = new ArrayList<Tab>();
        tabTitles.add(new Tab("未送出",UNUSED));
        tabTitles.add(new Tab("已送出",SEND));
        tabTitles.add(new Tab("已过期",EXCEED_TIME_LIMIT));

        for (Tab info : tabTitles) {
            tabTitle.addTab(tabTitle.newTab().setText(info.getTabNum()));
            Bundle bundle = new Bundle();
            bundle.putString(C.IntentKey.DISCOUNT_COUPON_TYPE, info.getType());
            DiscountCouponFragment discountCouponFragment = new DiscountCouponFragment();
            discountCouponFragment.setArguments(bundle);
            tabs.add(discountCouponFragment);
        }
        pageAdapter = new TabViewPagerAdapter(getSupportFragmentManager());
        contentPanle.setAdapter(pageAdapter);
        contentPanle.setOffscreenPageLimit(3);
        tabTitle.setupWithViewPager(contentPanle);
    }

    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == R.id.img_btn_left) {
            finish();
        }
    }

    /**
     * 滑动 ViewPager Adapter
     */
    class TabViewPagerAdapter extends FragmentPagerAdapter {

        public TabViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            return tabs.get(position);
//            return ArticleListFragment.newInstance(tabTitles.get(position).getItemCode());
        }
        @Override
        public int getCount() {
            return tabTitles.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles.get(position).getTabNum();
        }

    }
}
