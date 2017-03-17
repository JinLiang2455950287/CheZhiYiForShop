package com.ruanyun.chezhiyi.commonlib.view.ui.common;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.model.ParentCodeInfo;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.chezhiyi.commonlib.view.widget.lazyviewpager.LazyViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


/**
 * Description:  文章资讯列表
 * author: zhangsan on 16/9/9 下午1:41.
 */
public class ArticleListActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener {

    Topbar topbar;
    TabLayout tabTitle;
    LazyViewPager contentPanle;
    List<Fragment> tabs = new ArrayList<>();
    List<ParentCodeInfo> tabTitles;
    TabViewPagerAdapter pageAdapter;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_article_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        topbar = getView(R.id.topbar);
        tabTitle = getView(R.id.tab_title);
        contentPanle = getView(R.id.content_panle);
         topbar.setTttleText("文章资讯")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);

        initTabs();
    }

    private void initTabs() {
        tabTitles = app.beanCacheHelper.getLocalParentCodes(C.ParentCode.ARTICLE_TYPE);
        ParentCodeInfo parentCodeInfo=new ParentCodeInfo();
        parentCodeInfo.setItemCode("");
        tabTitles.add(0,parentCodeInfo);
        parentCodeInfo.setItemName("全部");
        for (ParentCodeInfo info : tabTitles) {
            tabTitle.addTab(tabTitle.newTab().setText(info.getItemName()));
            Bundle bundle = new Bundle();
            bundle.putString(C.IntentKey.ARTICLE_TYPE, info.getItemCode());
            ArticleListFragment articleListFragment = new ArticleListFragment();
            articleListFragment.setArguments(bundle);
            tabs.add(articleListFragment);
        }
      //  contentPanle.setOffscreenPageLimit(3);
        pageAdapter = new TabViewPagerAdapter(getSupportFragmentManager());
        contentPanle.setAdapter(pageAdapter);
        tabTitle.setupWithViewPager(contentPanle);
    }

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
//            return ArticleListFragment.newInstance(tabTitles.get(position).getItemCode());
        }

        @Override
        public int getCount() {
            return tabTitles.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles.get(position).getItemName();
        }

        /*@Override
        protected Fragment getItem(ViewGroup container, int position) {
            return tabs.get(position);
        }*/
    }

}
