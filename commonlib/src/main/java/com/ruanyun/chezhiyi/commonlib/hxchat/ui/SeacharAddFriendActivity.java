package com.ruanyun.chezhiyi.commonlib.hxchat.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.RefreshBaseActivity;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.hxchat.ui.adapter.EaseAddFirendListAdapter;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.params.SearchAddFirendParams;
import com.ruanyun.chezhiyi.commonlib.presenter.UserRelationShipPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.RelationShipMvpView;
import com.ruanyun.chezhiyi.commonlib.view.widget.CleanableEditText;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * @author wp
 * @ClassName: SeacharAddFriendActivity
 * @Description: 搜索陌生人
 * @date 2016/8/25 下午 6:44
 */
public class SeacharAddFriendActivity extends RefreshBaseActivity
        implements AdapterView.OnItemClickListener,
        TextView.OnEditorActionListener,
        Topbar.onTopbarClickListener, RelationShipMvpView {

    Topbar topbar;
    ListView list;
    private CleanableEditText searchContentEdittext;
    private SearchAddFirendParams searchAddFirendParams = new SearchAddFirendParams();
    private EaseAddFirendListAdapter adapter;
    private List<User> userList = new ArrayList<>();

    UserRelationShipPresenter userRelationShipPresenter = new UserRelationShipPresenter();

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        LogX.d(TAG, "-----------onCreate----------");
        setContentView(R.layout.ease_activity_search_chat_content);
        initRefreshLayout();
        initView();
        userRelationShipPresenter.attachView(this);
    }

    private void initView() {
        topbar = getView(R.id.topbar);
        list = getView(R.id.list);
        searchContentEdittext = getViewFromLayout(R.layout.ease_layout_search_edttext, topbar, false);
        searchContentEdittext.setOnEditorActionListener(this);
        searchContentEdittext.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        topbar.setTttleText("")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setRightText("取消")
                .addViewToTopbar(topbar.getTvTitleRight(), topbar.getLayoutParamsImgRight())
                .addViewToTopbar(searchContentEdittext, (AutoRelativeLayout.LayoutParams) searchContentEdittext.getLayoutParams())
                .onRightTextClick()
                .setTopbarClickListener(this);
        topbar.getTvTitle().setVisibility(View.GONE);
        adapter = new EaseAddFirendListAdapter(mContext, R.layout.ease_list_item_search_add_firend, userList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        LogX.d(TAG, "-----------onCreateView----------");
        return super.onCreateView(parent, name, context, attrs);
    }

    @Override
    public Call loadData() {
        searchAddFirendParams.setUserType("3");//类型 【默认传值 3 司机用户 】
        searchAddFirendParams.setWordkey(searchContentEdittext.getText().toString());//关键词【昵称与登录名称模糊查询】
        searchAddFirendParams.setPageNum(currentPage);
        return app.getHxApiService().searchAddFriend(app.getCurrentUserNum(), searchAddFirendParams);
    }

    @Override
    public void onRefreshResult(PageInfoBase result, String tag) {
        adapter.setData(result.getResult());
        adapter.notifyDataSetChanged();
        totalPage = result.getTotalPage();
        //关闭键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchContentEdittext.getWindowToken(), 0);
    }

    @Override
    public void onLoadMoreResult(PageInfoBase result, String tag) {
        adapter.addData(result.getResult());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //  调用获取用户关系
        Call<ResultBase<User>> call = app.getHxApiService().getFriendShipInfo(app.getCurrentUserNum(), adapter.getItem(position).getUserNum());
        userRelationShipPresenter.getFriendShipInfo(call);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String keyStr = searchContentEdittext.getText().toString();
            if (AppUtility.isNotEmpty(keyStr)) {
                adapter.setSearchStr(keyStr);
                onStartRefresh();
            }
            return true;
        }
        return false;
    }

    @Override
    public void onTobbarViewClick(View v) {
        int i = v.getId();
        if (i == R.id.img_btn_left || i == R.id.tv_title_right) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userRelationShipPresenter.detachView();
    }

    @Override
    public void showLoadingView() {
        showLoading();
    }

    @Override
    public void disMissLoadingView() {
        dissMissLoading();
    }

    @Override
    public void getFriendShipInfoSuccess(User user) {
        AppUtility.goToUserProfile(mContext, user);
    }

    @Override
    public void getFriendShipInfoError() {

    }

    @Override
    public void getFriendShipInfoFail() {

    }
}
