package com.ruanyun.chezhiyi.view.ui.mendian;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.model.Tab;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 2017/4/12
 * jin
 * 会员统计 当日/当月
 */
public class MemberCountActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener {

    @BindView(R.id.tab_title)
    TabLayout tabTitle;
    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.content_panle)
    ViewPager contentPanle;
    String[] clientTabName = {"当日", "本月"};
    String[] clientTabType = {"day", "month"};
    private ArrayList<Tab> tabTitles = new ArrayList<Tab>();
    private ArrayList<Fragment> tabs = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_count);
        ButterKnife.bind(this);
        initView();
        initTab();
    }


    private void initView() {
        topbar.setTttleText("会员统计")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);

    }

    private void initTab() {
        setTabTitles(clientTabName, clientTabType);
        for (Tab info : tabTitles) {
            tabTitle.addTab(tabTitle.newTab().setText(info.getTabNum()));
            MemberCountFragment memberCountFragment = new MemberCountFragment();
            Bundle bundle = new Bundle();
            bundle.putString(C.IntentKey.WORK_ORDER_STATUS_STRING, info.getType());
            memberCountFragment.setArguments(bundle);
            tabs.add(memberCountFragment);
        }
        contentPanle.setOffscreenPageLimit(2);
        contentPanle.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
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

        });
        tabTitle.setupWithViewPager(contentPanle);

//        setSelecteViewPage(getIntent().getIntExtra("item", 0));
        setSelecteViewPage(0);
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
        if (viewId == R.id.img_btn_left) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
