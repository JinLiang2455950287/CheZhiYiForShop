package com.ruanyun.chezhiyi.commonlib.view.ui.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.RefreshBaseFragment;
import com.ruanyun.chezhiyi.commonlib.model.CaseInfo;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.model.params.GetCaseParams;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleItemClicksListener;
import com.ruanyun.chezhiyi.commonlib.view.CommentZanMvpView;
import com.ruanyun.chezhiyi.commonlib.view.adapter.CaseLibraryAdapter;
import com.ruanyun.chezhiyi.commonlib.view.widget.lazyviewpager.LazyFragmentPagerAdapter;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import retrofit2.Call;

/**
 * Description:  案例库列表
 * author: zhangsan on 16/9/12 上午10:55.
 */
public class CaseLibListFragment extends RefreshBaseFragment implements LazyFragmentPagerAdapter.Laziable {

    private ListView list;
    private String projectType = "";
    private GetCaseParams params = new GetCaseParams();
    private CaseLibraryAdapter adapter;
//    private String userNum;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_caselib_list, container, false);
        initRefreshLayout();
        list = getView(R.id.list);
        projectType = getArguments().getString(C.IntentKey.CASE_LIB_TYPE);
//        userNum = getArguments().getString(C.IntentKey.USER_NUM);
        return mContentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        refreshWithLoading();
    }

    private void initView() {
        adapter = new CaseLibraryAdapter(mContext, new ArrayList<CaseInfo>());
        list.setAdapter(adapter);
        list.setOnItemClickListener(new NoDoubleItemClicksListener() {
            @Override
            public void noDoubleClick(AdapterView<?> parent, View view, int position, long id) {
                CaseInfo caseInfo = adapter.getItem(position);
                if (null == caseInfo) {
                    return;
                }
                Intent intent = AppUtility.getArticleCaseLibDetailIntent(mContext, FileUtil.getFileUrl(String.format(C.ApiUrl.URL_CASELIB_DETAIL,
                        app.getCurrentUserNum(), caseInfo.getLibraryNum())), CommentZanMvpView.TYPE_CASE_LIBS);
                intent.putExtra(C.IntentKey.CASE_LIB_INFO, caseInfo);
                intent.putExtra(C.IntentKey.WEBVIEW_TITLE, "案例详情");
                showActivity(intent);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       registerBus();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegisterBus();
    }

    @Subscribe(threadMode = ThreadMode.MainThread,sticky =true)
    public void onRecieveSearchResult(Event<String> searchStr) {
        //if (result.key.equals(projectType)) {
        if(searchStr.key.equals(C.EventKey.KEY_SEARCH_CASELIB)) {
            params.setLibraryName(searchStr.value);
            refreshWithLoading();
            EventBus.getDefault().removeStickyEvent(searchStr);
        }
        //}
    }

    @Subscribe
    public void onReciveRefresh(Event<String> msg){
        if(msg.key.equals(C.EventKey.KEY_REFRESH_LIST)){
            refreshWithLoading();
        }
    }
   /* public void upDateSearchResult(String result){
        params.setLibraryName(result);
        refreshWithLoading();
    }
*/

    @Override
    public Call loadData() {
//        if (AppUtility.isNotEmpty(userNum))
//            params.setUserNum(userNum);
        params.setUserType(2);//查看企业案例
        params.setLibraryType(projectType);
//        params.setCurrentPage(currentPage);
        params.setPageNum(currentPage);
        return app.getApiService().getCaseInfo(app.getCurrentUserNum(), params);
    }

    @Override
    public void onRefreshResult(PageInfoBase result, String tag) {
        adapter.setData(result.getResult());
    }

    @Override
    public void onLoadMoreResult(PageInfoBase result, String tag) {
        adapter.addData(result.getResult());
    }
}
