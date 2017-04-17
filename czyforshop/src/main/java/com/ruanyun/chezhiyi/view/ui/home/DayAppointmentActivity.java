package com.ruanyun.chezhiyi.view.ui.home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by msq on 2016/9/26.
 * 当天预约Activity
 */
public class DayAppointmentActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener {

    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.tab_title)
    TabLayout tabTitle;
    DayAppointmentComeFragment dayAppointmentComeFragment;//未到店
    DayAppointmentGoFragment dayAppointmentGoFragment;//已接待
    @BindView(R.id.content_panle)
    ViewPager contentPanle;
    @BindView(R.id.tv_numindex)
    TextView tvNumindex;
    private ArrayList<Fragment> tabs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_day_appointment);
        ButterKnife.bind(this);
        init();

    }

    private void init() {
        topbar.setTttleText("当天预约")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
        dayAppointmentComeFragment = new DayAppointmentComeFragment();
        dayAppointmentGoFragment = new DayAppointmentGoFragment();
        tabTitle.addTab(tabTitle.newTab().setText("未到店"));
        tabs.add(dayAppointmentComeFragment);
        tabTitle.addTab(tabTitle.newTab().setText("已到店"));
        tabs.add(dayAppointmentGoFragment);
        contentPanle.setOffscreenPageLimit(2);
        contentPanle.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return tabs.get(position);
            }

            @Override
            public int getCount() {
                return tabs.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                if (position == 0) {
                    return "未到店";
                }
                return "已到店";
            }

        });
        tabTitle.setupWithViewPager(contentPanle);
        setSelecteViewPage(0);

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
    public void onTobbarViewClick(View v) {
        int viewId = v.getId();
        if (viewId == com.ruanyun.chezhiyi.commonlib.R.id.img_btn_left) {
            finish();
        }
    }

    /**
     * 显示指定的fragment
     *
     * @param index
     */
    protected void showFragmentAtIndex(int index) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (index) {
            case 1:
                //判断fragment是否已加载
                if (!dayAppointmentComeFragment.isAdded()) {
                    fragmentTransaction.add(R.id.frag_container, dayAppointmentComeFragment);
                    fragmentTransaction.show(dayAppointmentComeFragment).hide(dayAppointmentGoFragment);
                } else {
                    fragmentTransaction.show(dayAppointmentComeFragment).hide(dayAppointmentGoFragment);
                }
                break;
            case 2:
                if (!dayAppointmentGoFragment.isAdded()) {
                    fragmentTransaction.add(R.id.frag_container, dayAppointmentGoFragment);
                    fragmentTransaction.show(dayAppointmentGoFragment).hide(dayAppointmentComeFragment);
                } else {
                    fragmentTransaction.show(dayAppointmentGoFragment).hide(dayAppointmentComeFragment);
                }
                break;
            default:
                break;
        }
        //提交
        fragmentTransaction.commit();
    }

}
