package com.ruanyun.chezhiyi.view.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.RefreshBaseFragment;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.WorkBayInfo;
import com.ruanyun.chezhiyi.commonlib.model.WorkOrderInfo;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleClicksListener;
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleItemClicksListener;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.WorkOrderDetailedActivity;
import com.ruanyun.chezhiyi.view.adapter.StationStateAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import retrofit2.Call;

/**
 * 工位的fragment工位状态
 */
public class WorkBayFragment extends RefreshBaseFragment {

    private List<WorkBayInfo> workBayInfoList = new ArrayList<>();      //工位数据
    private StationStateAdapter stationAdapter;     //工位信息
    private ListView lvStation;

    public WorkBayFragment() {
        // Required empty public constructor
    }

    public static WorkBayFragment newInstance() {
        WorkBayFragment fragment = new WorkBayFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initRefreshLayout() {
        listPresenter.attachView(this);
        refreshLayout = getView(com.ruanyun.chezhiyi.commonlib.R.id.refreshlayout);
        emptyView = getView(com.ruanyun.chezhiyi.commonlib.R.id.emptyview);
        normalRefreshViewHolder = new BGANormalRefreshViewHolder(mContext, false);
        refreshLayout.setRefreshViewHolder(normalRefreshViewHolder);
        refreshLayout.setDelegate(this);
        if (isbindEmptyView) {
            emptyView.bind(refreshLayout);
            emptyView.setOnReloadListener(new NoDoubleClicksListener() {
                @Override
                public void noDoubleClick(View v) {
                    reLoad();
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_workbay, container, false);
        initView();
        return mContentView;
    }

    private void initView() {
        initRefreshLayout();
        lvStation = getView(R.id.list);
        stationAdapter = new StationStateAdapter(mContext, R.layout.list_item_station_state, workBayInfoList);
        lvStation.setAdapter(stationAdapter);
        lvStation.setOnItemClickListener(new NoDoubleItemClicksListener() {
            @Override
            public void noDoubleClick(AdapterView<?> parent, View view, int position, long id) {
                WorkBayInfo workBayInfo = stationAdapter.getItem(position);
                if (workBayInfo.getWorkbayStatus() != WorkBayInfo.LEISURE) {
                    WorkOrderInfo info = new WorkOrderInfo();
                    info.setWorkOrderNum(workBayInfo.getWorkOrderNum());
                    Intent workbayIntent = new Intent(mContext, WorkOrderDetailedActivity.class);
                    workbayIntent.putExtra(C.IntentKey.WORKORDER_INFO, info);
                    showActivity(workbayIntent);
                    LogX.e("工位状态", info.toString());
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refreshWithLoading();
    }


    @Override
    protected void onStartRefresh() {
        currentPage = 1;
        loadType = REFRESH;
        listPresenter.loadDataNoPage(loadData(), loadType);
    }

    @Override
    protected boolean onStartLoadMore() {
        currentPage++;
        if (currentPage <= totalPage) {
            loadType = LOADMORE;
            listPresenter.loadDataNoPage(loadData(), loadType);
            normalRefreshViewHolder.setLoadingMoreText("加载中...");
            return true;
        } else {
            refreshLayout.endLoadingMore();
            normalRefreshViewHolder.setLoadingMoreText("没有更多了");
            return false;
        }
    }

    @Override
    public void onRefreshNoPageResult(ResultBase resultBase, String tag) {
        workBayInfoList = (List<WorkBayInfo>) resultBase.getObj();
        stationAdapter.setDatas(workBayInfoList);
    }

    @Override
    public void onLoadMoreNoPageResult(ResultBase result, String tag) {
        super.onLoadMoreNoPageResult(result, tag);
    }

    @Override
    public Call loadData() {
        return app.getApiService().getStationList(app.getCurrentUserNum());
    }

    @Override
    public void onRefreshResult(PageInfoBase result, String tag) {
        // TODO: 2016/12/27 此处无用
    }

    @Override
    public void onLoadMoreResult(PageInfoBase result, String tag) {
        // TODO: 2016/12/27 此处无用
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
