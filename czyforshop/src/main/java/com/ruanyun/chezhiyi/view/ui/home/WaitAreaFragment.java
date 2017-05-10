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
import com.ruanyun.chezhiyi.commonlib.model.JieSuanParm;
import com.ruanyun.chezhiyi.commonlib.model.WorkOrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.MyWorkOrderParams;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleItemClicksListener;
import com.ruanyun.chezhiyi.view.adapter.AwaitOrClearingAdapter;
import com.ruanyun.chezhiyi.view.adapter.WaitAreaAdapter;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.Subscribe;
import retrofit2.Call;

/**
 * home等候区fragment_wait_area
 * 2017.4.10
 */
public class WaitAreaFragment extends RefreshBaseFragment {

    public static final String TAB_TYPE_WAIT_AREA = "2";//等候区

    public MyWorkOrderParams params = new MyWorkOrderParams();
    private ListView lvStation;
    private WaitAreaAdapter mAdapter;//等候区或结算中获取
    private List<WorkOrderInfo> workOrderInfoList = new ArrayList<>();

    public WaitAreaFragment() {
    }

    public static WaitAreaFragment newInstance() {
        WaitAreaFragment fragment = new WaitAreaFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString(arg_params_status, paramsStatus);
//        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBus();
//        paramsStatus = getArguments().getString(arg_params_status);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_wait_area, container, false);
        initView();

        return mContentView;
    }

    private void initView() {
        initRefreshLayout();
        lvStation = getView(R.id.list);
        mAdapter = new WaitAreaAdapter(mContext, R.layout.list_item_await_quality, workOrderInfoList);
        lvStation.setAdapter(mAdapter);
        lvStation.setOnItemClickListener(new NoDoubleItemClicksListener() {
            @Override
            public void noDoubleClick(AdapterView<?> parent, View view, int position, long id) {

                Intent waitAreaIntent = new Intent(mContext, WaitingAreaActivity.class);
                waitAreaIntent.putExtra(C.IntentKey.WORKORDER_INFO, mAdapter.getItem(position));
                showActivity(waitAreaIntent);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refreshWithLoading();
    }

    @Override
    public Call loadData() {
        Call call = null;
        //工单状态(1工单等待确认 2已确认，等待进店 3等待施工 4即将开始施工 5施工中 6施工结束，质检中 7返修中，质检不合格 8等待结算，质检合格 9完成结算)等候中传值3 等待结算8 完成结算9
        params.setPageNum(currentPage);
        params.setWorkOrderStatus(3);
        listPresenter.setTag(TAB_TYPE_WAIT_AREA);
        call = app.getApiService().getMyWorkOrderList(app.getCurrentUserNum(), params);

        return call;
    }

    @Override
    public void onRefreshResult(PageInfoBase result, String tag) {
        workOrderInfoList.clear();
        workOrderInfoList.addAll(result.getResult());
        LogX.e("workOrderInfoListgetResult()", result.getResult().toString());
        LogX.e("workOrderInfoList", workOrderInfoList.toString());
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onLoadMoreResult(PageInfoBase result, String tag) {
        workOrderInfoList.addAll(result.getResult());
        mAdapter.setDatas(workOrderInfoList);
    }

    /**
     * 收到刷新列表通知
     *
     * @param evet
     */
    @Subscribe
    public void onReciverefresh(String evet) {
//        if (evet.equals(C.EventKey.REFRESH_WAIT_AREA)) {
//            if (paramsStatus.equals(TAB_TYPE_WAIT_AREA))
//                refreshWithLoading();
//        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegisterBus();
    }


}
