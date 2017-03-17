package com.ruanyun.czy.client.view.ui.my;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.model.Tab;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Description ：司机端 优惠券界面
 * <p/>
 * Created by hdl on 2016/9/19.
 */
public class DiscountCouponActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener  {
    private Topbar topbar;
    private TabLayout tabTitle;
    private /*Lazy*/ViewPager contentPanle;
    private List<Fragment> tabs = new ArrayList<>();
    private List<Tab> tabTitles;
    private TabViewPagerAdapter pageAdapter;
    private String[] tabstr = new String[] {"未使用","已使用","已过期"};

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

    /**
     * 显示优惠券的数量
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onCreatGroup(Event<String[]> event) {
        if (event != null && event.key.equals(C.EventKey.DISCOUNT_COUPON_NUMBER)) {
            String[] strs = event.value;
//            if (UNUSED.equals(strs[0])) {
//                tabTitle.getTabAt(0).setText("未使用(" + strs[1] + ")");
//            } else if (EXCEED_TIME_LIMIT.equals(strs[0])) {
//                tabTitle.getTabAt(1).setText("已过期(" + strs[1] + ")");
//            } else if (SEND.equals(strs[0])) {
//                tabTitle.getTabAt(2).setText("已使用(" + strs[1] + ")");
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
        topbar = getView(com.ruanyun.chezhiyi.commonlib.R.id.topbar);
        tabTitle = getView(com.ruanyun.chezhiyi.commonlib.R.id.tab_title);
        contentPanle = getView(com.ruanyun.chezhiyi.commonlib.R.id.content_panle);
        topbar.setTttleText("优惠券")
                .setBackBtnEnable(true)
                .onBackBtnClick()
//                topbar  右边的 “使用说明”
//                .setRightText("使用说明")
//                .addViewToTopbar(topbar.getTvTitleRight(), topbar.getLayoutParamsImgRight())
//                .onRightTextClick()
                .setTopbarClickListener(this);
    }

    private void initdata() {
        tabTitles = new ArrayList<Tab>();
        tabTitles.add(new Tab(tabstr[0],UNUSED));
        tabTitles.add(new Tab(tabstr[1],SEND));
        tabTitles.add(new Tab(tabstr[2],EXCEED_TIME_LIMIT));
        //加载fragment
        for (Tab info : tabTitles) {
            tabTitle.addTab(tabTitle.newTab().setText(info.getTabNum()));
            Bundle bundle = new Bundle();
            bundle.putString(C.IntentKey.DISCOUNT_COUPON_TYPE, info.getType());
            DiscountCouponFragment discountCouponFragment = new DiscountCouponFragment();
            discountCouponFragment.setArguments(bundle);
            tabs.add(discountCouponFragment);
        }
        contentPanle.setOffscreenPageLimit(3);
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
        }else if (id == com.ruanyun.chezhiyi.commonlib.R.id.tv_title_right) {
            AppUtility.showToastMsg("使用说明");
        }
    }

    /**
     * 滑动Viewpager  Adapter
     */
    class TabViewPagerAdapter extends FragmentPagerAdapter {

        public TabViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return tabs.get(position);
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
        public Object instantiateItem(ViewGroup container, int position) {
            LogX.d("ycw", "container id ====>  " + container.getId());
            return super.instantiateItem(container, position);
        }
    }
}
