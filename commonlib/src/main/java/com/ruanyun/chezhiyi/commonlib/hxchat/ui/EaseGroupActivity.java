package com.ruanyun.chezhiyi.commonlib.hxchat.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.hxchat.Constant;
import com.ruanyun.chezhiyi.commonlib.hxchat.ui.adapter.EaseGroupAdapter;
import com.ruanyun.chezhiyi.commonlib.model.HxUserGroup;
import com.ruanyun.chezhiyi.commonlib.presenter.HXUserGroupPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleItemClicksListener;
import com.ruanyun.chezhiyi.commonlib.view.HXUserGroupMvpView;
import com.ruanyun.chezhiyi.commonlib.view.widget.CleanableEditText;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout.BGARefreshLayoutDelegate;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import retrofit2.Call;

/**
 * Description ：讨论组
 * <p>
 * Created by ycw on 2016/8/10.
 */
public class EaseGroupActivity extends AutoLayoutActivity implements BGARefreshLayoutDelegate, HXUserGroupMvpView, Topbar.onTopbarClickListener {

    Topbar topbar;
    CleanableEditText query;
    ImageButton searchClear;
    ListView lvGroup;
    BGARefreshLayout refreshlayout;
    EaseGroupAdapter adapter;
    List<HxUserGroup> groupList = new ArrayList<>();
    HXUserGroupPresenter hxUserGroupPresenter = new HXUserGroupPresenter();

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.ease_activity_group_list);
        hxUserGroupPresenter.attachView(this);
        initView();
        registerBus();
    }


    private void initView() {
        topbar = getView(R.id.topbar);
        query = getView(R.id.query);
        searchClear = getView(R.id.search_clear);
        lvGroup = getView(R.id.lv_group);
        refreshlayout = getView(R.id.refreshlayout);
        query.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtils.isEmpty(s)){
                    adapter.setData(groupList);
                    adapter.notifyDataSetChanged();
                }else {
                    adapter.setData(adapter.getSearchResult(s.toString().trim()));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        initRefreshLayout();
        initListView();
        initTopbar();
    }

    private void initTopbar() {
         topbar.setTttleText("我的讨论组")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
    }

    @Subscribe(threadMode= ThreadMode.MainThread,sticky = true,priority = 100)
    public void onGroupExit(String content) {
        if(C.EventKey.EXIT_GROUP.equals(content)) {
            finish();
            EventBus.getDefault().removeStickyEvent(content);
        }
    }

    private void initListView() {
        adapter = new EaseGroupAdapter(mContext,R.layout.ease_item_group, groupList);
        lvGroup.setAdapter(adapter);
        lvGroup.setOnItemClickListener(new NoDoubleItemClicksListener() {
            @Override
            public void noDoubleClick(AdapterView<?> parent, View view, int position, long id) {
                HxUserGroup hxUserGroup=adapter.getItem(position);
                if(hxUserGroup!=null){
                    Intent intent = new Intent(mContext, ChatActivity.class);
                    // it is group chat
                    intent.putExtra("chatType", Constant.CHATTYPE_GROUP);
                    intent.putExtra("userId", hxUserGroup.getHuanxinNum());
                    intent.putExtra(C.IntentKey.GROUP_INFO,hxUserGroup);
                    showActivity(intent);
                }
            }
        });
    }

    /**
     * 刷新控件初始化
     */
    private void initRefreshLayout() {
        /** 只准刷新  */
        refreshlayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(this, false));
        refreshlayout.setDelegate(this);
        refreshlayout.beginRefreshing();
    }


    @Override
    protected void onStop() {
        super.onStop();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(query.getWindowToken(),0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hxUserGroupPresenter.detachView();
        unRegisterBus();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        /**   "sys45610000010125"  */
        Call call = app.getHxApiService().getUserGroupList(app.getCurrentUserNum());
        hxUserGroupPresenter.getUserGroupList(call);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    @Override
    public void onSuccess(ResultBase<List<HxUserGroup>> resultBase) {
        groupList = resultBase.getObj();
        adapter.setData(groupList);
        adapter.notifyDataSetChanged();
        DbHelper.getInstance().saveGroups(groupList);
    }

    @Override
    public void onError(ResultBase<List<HxUserGroup>> resultBase, int errorCode) {
        AppUtility.showToastMsg(resultBase.getMsg());
    }

    @Override
    public void onFail(String msg) {
        AppUtility.showToastMsg(msg);
    }

    @Override
    public void onResult() {
        refreshlayout.endRefreshing();
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void onTobbarViewClick(View v) {
        int id =  v.getId();
        if (id == R.id.img_btn_left){
            exit();
        }
    }
}
