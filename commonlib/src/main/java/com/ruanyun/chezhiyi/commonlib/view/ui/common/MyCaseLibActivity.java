package com.ruanyun.chezhiyi.commonlib.view.ui.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.RefreshBaseActivity;
import com.ruanyun.chezhiyi.commonlib.model.CaseInfo;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.model.params.GetCaseParams;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleItemClicksListener;
import com.ruanyun.chezhiyi.commonlib.view.CommentZanMvpView;
import com.ruanyun.chezhiyi.commonlib.view.adapter.CaseLibraryAdapter;
import com.ruanyun.chezhiyi.commonlib.view.widget.CleanableEditText;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;

import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import retrofit2.Call;

/**
 * Description:我的案例   或者    对应  userNum  的案例库信息
 * author: zhangsan on 16/9/1下午3:03.
 */
public class MyCaseLibActivity extends RefreshBaseActivity implements Topbar.onTopbarClickListener, View.OnClickListener, TextView.OnEditorActionListener/*,
        SearchPopupWindow.SearchPopupWindowAction */{


//    public static final String IS_SEARCH = "isSearch";
//    public static final String NOT_SEARCH = "notSearch";
    private Topbar topbar;
    private FrameLayout llSearchCommon;
    private ListView list;
    private CleanableEditText seachText;
    private GetCaseParams params = new GetCaseParams();
    private CaseLibraryAdapter adapter;
//    private CaseLibraryAdapter searchAdapter;
//    private SearchPopupWindow searchPopupWindow;
    private String  createUserNum = "";  //案例用户编号 传值 获取该技师的案例
    private NoDoubleItemClicksListener noDoubleItemClicksListener = new NoDoubleItemClicksListener() {
        @Override
        public void noDoubleClick(AdapterView<?> parent, View view, int position, long id) {
            CaseInfo caseInfo = /*adapter.getItem(position)*/(CaseInfo) parent.getAdapter().getItem(position);
            if (null == caseInfo) {
                return;
            }
            Intent intent = AppUtility.getArticleCaseLibDetailIntent(mContext, FileUtil.getFileUrl(String.format(C.ApiUrl.URL_CASELIB_DETAIL,
                    app.getCurrentUserNum(), caseInfo.getLibraryNum())), CommentZanMvpView.TYPE_CASE_LIBS);
            intent.putExtra(C.IntentKey.CASE_LIB_INFO, caseInfo);
            intent.putExtra(C.IntentKey.WEBVIEW_TITLE, "案例详情");
            showActivity(intent);
        }
    };
//    private String searchType = NOT_SEARCH;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_my_caselib);
        initView();
        registerBus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterBus();
    }

    @Subscribe(threadMode =ThreadMode.MainThread)
    public void onRecieveRefresh(Event<String> msg){
        if(msg.key.equals(C.EventKey.KEY_REFRESH_LIST)){
//            searchType = NOT_SEARCH;
            refreshWithLoading();
        }
    }

    private void initView() {
        createUserNum = getIntent().getStringExtra(C.IntentKey.USER_NUM);
        topbar = getView(R.id.topbar);
        llSearchCommon = getView(R.id.ll_search_common);
        llSearchCommon.setOnClickListener(this);
        list = getView(R.id.list);
        initRefreshLayout();
        if (AppUtility.isNotEmpty(createUserNum)) {
            topbar.setTttleText("案例库")
                    .setBackBtnEnable(true)
                    .onBackBtnClick()
                    .setTopbarClickListener(this);
        } else {
            topbar.setTttleText("我的案例")
                    .setBackBtnEnable(true)
                    .onBackBtnClick()
                    .enableRightImageBtn()
                    .setRightImgBtnBg(R.drawable.nav_add)
                    .onRightImgBtnClick()
                    .setTopbarClickListener(this);
        }
        seachText = getViewFromLayout(com.ruanyun.chezhiyi.commonlib.R.layout.ease_layout_search_edttext, topbar, false);
        seachText.setOnEditorActionListener(this);
        seachText.setVisibility(View.GONE);
        topbar.setRightText("取消")
                .enableRightText()
                .onRightTextClick();
        topbar.getTvTitleRight().setVisibility(View.GONE);
        topbar.addViewToTopbar(seachText, (AutoRelativeLayout.LayoutParams) seachText.getLayoutParams());

        adapter = new CaseLibraryAdapter(mContext, new ArrayList<CaseInfo>());
        list.setAdapter(adapter);
        list.setOnItemClickListener(noDoubleItemClicksListener);
//        searchAdapter = new CaseLibraryAdapter(mContext, new ArrayList<CaseInfo>());
//        searchPopupWindow = new SearchPopupWindow(mContext, llSearchCommon, this, searchAdapter , noDoubleItemClicksListener);
        refreshWithLoading();
    }

    @Override
    public Call loadData() {
//        params.setCurrentPage(currentPage);
//        listPresenter.setTag(searchType);
        params.setPageNum(currentPage);
        params.setUserType(1);
        if (AppUtility.isNotEmpty(createUserNum)) {
            params.setCreateUserNum(createUserNum);
            params.setStatus(1);  // 审核通过
        } else {
            params.setCreateUserNum(app.getCurrentUserNum());
            adapter.showCheckStatus(true);
        }
        return app.getApiService().getCaseInfo(app.getCurrentUserNum(), params);
    }

//    @Override
//    public void showEmpty(String tag) {
//        if (NOT_SEARCH.equals(tag)) {
//            super.showEmpty(tag);
//        } else {
//            searchAdapter.clearData();
//        }
//        searchType = NOT_SEARCH;
//        params.setLibraryName(null);
//    }

    @Override
    public void onRefreshResult(PageInfoBase result, String tag) {
//        if (NOT_SEARCH.equals(tag)) {
            adapter.setData(result.getResult());
//        } else {
//            searchAdapter.setData(result.getResult());
//        }
//        searchType = NOT_SEARCH;
//        params.setLibraryName(null);
    }

    @Override
    public void onLoadMoreResult(PageInfoBase result, String tag) {
//        if (NOT_SEARCH.equals(tag)) {
            adapter.addData(result.getResult());
//        } else {
//            searchAdapter.addData(result.getResult());
//        }
//        searchType = NOT_SEARCH;
//        params.setLibraryName(null);
    }

    @Override
    public void onTobbarViewClick(View v) {
        if(v.getId() == com.ruanyun.chezhiyi.commonlib.R.id.img_btn_left) {
            finish();
        } else if (v.getId() == com.ruanyun.chezhiyi.commonlib.R.id.img_btn_right) {
            showActivity("com.ruanyun.chezhiyi.view.ui.mine.NewCaseActivity");
        } else if (v.getId() == R.id.tv_title_right) {  //     搜索的取消
            llSearchCommon.setVisibility(View.VISIBLE);
            topbar.getTvTitleRight().setVisibility(View.GONE);
            topbar.getImgTitleRight().setVisibility(View.VISIBLE);
            seachText.setVisibility(View.GONE);

            seachText.setText("");
            params.setLibraryName(null);
            refreshWithLoading();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_search_common) {     // 打开搜索
            llSearchCommon.setVisibility(View.GONE);
            topbar.getTvTitleRight().setVisibility(View.VISIBLE);
            topbar.getImgTitleRight().setVisibility(View.GONE);
            seachText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String search = seachText.getText().toString().trim();
            params.setLibraryName(search);
            refreshWithLoading();
        }
        return false;
    }

//    @Override
//    public void startSearchString(String searchStr) {
//        params.setLibraryName(searchStr);
//        searchType = IS_SEARCH;
//        refreshWithLoading();
//    }

//    @Override
//    public void clearData() {
//        searchAdapter.clearData();
//    }
}
