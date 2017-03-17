package com.ruanyun.chezhiyi.commonlib.view.ui.common;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.model.ProjectType;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.view.widget.CleanableEditText;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.chezhiyi.commonlib.view.widget.lazyviewpager.LazyFragmentPagerAdapter;
import com.ruanyun.chezhiyi.commonlib.view.widget.lazyviewpager.LazyViewPager;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Description:  案例库列表
 * author: zhangsan on 16/9/12 上午10:55.
 */
public class CaseLibActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener, TextView.OnEditorActionListener {

    private Topbar topbar;
    private TabLayout tablayout;
    private LazyViewPager contentPanle;
    private CleanableEditText seachText;
    private List<ProjectType> tabTitle;
    private List<Fragment> tabs = new ArrayList<>();
    private TabViewPagerAdapter pagerAdapter;
    private Event<String> searchResult = new Event<>();
//    private String userNum;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_caselib_list);
        initView();
        initTab();
    }

    private void initTab() {
        ProjectType emptyProjectType = new ProjectType();
        emptyProjectType.setProjectName("全部");
        emptyProjectType.setProjectNum("");
        tabTitle = DbHelper.getInstance().getSeviceTypes();
        tabTitle.add(0, emptyProjectType);
        for (ProjectType projectType : tabTitle) {
            tablayout.addTab(tablayout.newTab().setText(projectType.getProjectName()));
            Bundle bundle = new Bundle();
            bundle.putString(C.IntentKey.CASE_LIB_TYPE, projectType.getProjectNum());
//            bundle.putString(C.IntentKey.USER_NUM, userNum);
            CaseLibListFragment fragment = new CaseLibListFragment();
            fragment.setArguments(bundle);
            fragment.setRetainInstance(true);
            tabs.add(fragment);
        }
        pagerAdapter = new TabViewPagerAdapter(getSupportFragmentManager());
        //contentPanle.setOffscreenPageLimit(4);
        contentPanle.setAdapter(pagerAdapter);
        tablayout.setupWithViewPager(contentPanle);
    }

    private void initView() {
//        userNum = getIntent().getStringExtra(C.IntentKey.USER_NUM);
        topbar=getView(R.id.topbar);
        tablayout=getView(R.id.tablayout);
        contentPanle=getView(R.id.content_panle);
        seachText = getViewFromLayout(com.ruanyun.chezhiyi.commonlib.R.layout.ease_layout_search_edttext, topbar, false);
        seachText.setOnEditorActionListener(this);
        seachText.setVisibility(View.GONE);
        topbar.setTttleText("案例库")
                .addViewToTopbar(seachText, (AutoRelativeLayout.LayoutParams) seachText.getLayoutParams())
                .setBackBtnEnable(true)
                .enableRightImageBtn()
                .onBackBtnClick()
                .onRightImgBtnClick()
                .setRightText("取消")
                .enableRightText()
                .onRightTextClick()
                .setRightImgBtnBg(R.drawable.nav_search)
                .setTopbarClickListener(this);
        topbar.getTvTitleRight().setVisibility(View.GONE);
        searchResult.key = C.EventKey.KEY_SEARCH_CASELIB;

    }

    @Override
    public void onTobbarViewClick(View v) {
        int viewId=v.getId();
        if(viewId==R.id.img_btn_left){
            finish();
        }else if(viewId==R.id.img_btn_right){
            seachText.setVisibility(View.VISIBLE);
            topbar.getImgTitleRight().setVisibility(View.GONE);
            topbar.getTvTitleRight().setVisibility(View.VISIBLE);
        }else if(viewId==R.id.tv_title_right){
            seachText.setVisibility(View.GONE);
            topbar.getImgTitleRight().setVisibility(View.VISIBLE);
            topbar.getTvTitleRight().setVisibility(View.GONE);
          //  ((CaseLibListFragment)pagerAdapter.getCurrentItem()).upDateSearchResult("");
          //  EventBus.getDefault().postSticky("");
            postSearchReulst("");
        }

    }

    private void postSearchReulst(String searchStr){
        searchResult.value=searchStr;
        EventBus.getDefault().postSticky(searchResult);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {//点击键盘搜索
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
           // searchResult.key = tabTitle.get(contentPanle.getCurrentItem()).getProjectNum();

           // searchResult.value = seachText.getText().toString().trim();
            //EventBus.getDefault().postSticky(seachText.getText().toString().trim());
            postSearchReulst(seachText.getText().toString().trim());

            //((CaseLibListFragment)pagerAdapter.getCurrentItem()).upDateSearchResult(seachText.getText().toString().trim());
        }
        return false;
    }

    class TabViewPagerAdapter extends LazyFragmentPagerAdapter {

        public TabViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

       // @Override
     /*   public Fragment getItem(int position) {
            return tabs.get(position);
        }
*/
        @Override
        public int getCount() {
            return tabTitle.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitle.get(position).getProjectName();
        }

        @Override
        protected Fragment getItem(ViewGroup container, int position) {
            return tabs.get(position);
        }
    }
}
