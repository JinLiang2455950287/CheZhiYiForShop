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
import com.ruanyun.chezhiyi.commonlib.model.WorkOrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.MyWorkOrderParams;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleItemClicksListener;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.WorkOrderDetailedActivity;
import com.ruanyun.chezhiyi.view.adapter.AwaitOrClearingAdapter;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.Subscribe;
import retrofit2.Call;

/**
 * Description ：
 * <p/>
 * Created by ycw on 2016/12/27.
 */

public class WorkOrderFragment extends RefreshBaseFragment {

    public static final String TAB_TYPE_WAIT_AREA = "2";//等候区
    public static final String TAB_TYPE_SETTLE_ACCOUNTS = "3";//结算区
    public static final String TAB_TYPE_QUALITY_CHECK = "6";//质检去

    private static final String arg_params_status = "ARG_PARAMS_STATUS";
    private static final String workorderstatus = "";
    public MyWorkOrderParams params = new MyWorkOrderParams();
    //    private Topbar topbar;
    private ListView lvStation;
    private AwaitOrClearingAdapter mAdapter;//等候区或结算中获取
    private List<WorkOrderInfo> workOrderInfoList = new ArrayList<>();
    private String paramsStatus;

    public WorkOrderFragment() {
    }

    public static WorkOrderFragment newInstance(String paramsStatus) {
        WorkOrderFragment fragment = new WorkOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString(arg_params_status, paramsStatus);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBus();
        paramsStatus = getArguments().getString(arg_params_status);
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
        mAdapter = new AwaitOrClearingAdapter(mContext, R.layout.list_item_await_car, workOrderInfoList, paramsStatus);
        lvStation.setAdapter(mAdapter);
        lvStation.setOnItemClickListener(new NoDoubleItemClicksListener() {
            @Override
            public void noDoubleClick(AdapterView<?> parent, View view, int position, long id) {
                switch (paramsStatus) {
                    case TAB_TYPE_QUALITY_CHECK://质检
                        Intent qualityCheckIntent = new Intent(mContext, WorkOrderDetailedActivity.class);
                        qualityCheckIntent.putExtra(C.IntentKey.WORKORDER_INFO, mAdapter.getItem(position));
                        showActivity(qualityCheckIntent);
                        break;
                    case TAB_TYPE_SETTLE_ACCOUNTS://结算
                        Intent intent = new Intent(mContext, WorkOrderDetailedActivity.class);
                        intent.putExtra(C.IntentKey.WORKORDER_INFO, mAdapter.getItem(position));
                        showActivity(intent);
                        break;
                    case TAB_TYPE_WAIT_AREA://等候区
                        Intent waitAreaIntent = new Intent(mContext, WaitingAreaActivity.class);
                        waitAreaIntent.putExtra(C.IntentKey.WORKORDER_INFO, mAdapter.getItem(position));
                        showActivity(waitAreaIntent);
                        break;
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
    public Call loadData() {
        Call call = null;
        switch (paramsStatus) {
            case TAB_TYPE_QUALITY_CHECK://质检
                //params.setWorkOrderStatusString(null);
                //工单状态(1工单等待确认 2已确认，等待进店 3等待施工 4即将开始施工 5施工中 6施工结束，质检中 7返修中，质检不合格 8等待结算，质检合格 9完成结算)等候中传值3 等待结算8 完成结算9
                params.setWorkOrderStatus(6);
                params.setPageNum(currentPage);
                listPresenter.setTag(TAB_TYPE_QUALITY_CHECK);
                call = app.getApiService().getMyWorkOrderList(app.getCurrentUserNum(), params);
                break;
            case TAB_TYPE_SETTLE_ACCOUNTS://结算
                //params.setWorkOrderStatusString(null);
                //工单状态(1工单等待确认 2已确认，等待进店 3等待施工 4即将开始施工 5施工中 6施工结束，质检中 7返修中，质检不合格 8等待结算，质检合格 9完成结算)等候中传值3 等待结算8 完成结算9
                params.setWorkOrderStatus(8);
                params.setPageNum(currentPage);
                listPresenter.setTag(TAB_TYPE_SETTLE_ACCOUNTS);
                call = app.getApiService().getMyWorkOrderList(app.getCurrentUserNum(), params);
                break;
            case TAB_TYPE_WAIT_AREA://等候区
                //工单状态(1工单等待确认 2已确认，等待进店 3等待施工 4即将开始施工 5施工中 6施工结束，质检中 7返修中，质检不合格 8等待结算，质检合格 9完成结算)等候中传值3 等待结算8 完成结算9
                params.setPageNum(currentPage);
                params.setWorkOrderStatus(3);
                listPresenter.setTag(TAB_TYPE_WAIT_AREA);
                call = app.getApiService().getMyWorkOrderList(app.getCurrentUserNum(), params);
                break;
        }
        return call;
    }

    @Override
    public void onRefreshResult(PageInfoBase result, String tag) {
        workOrderInfoList.clear();
        workOrderInfoList.addAll(result.getResult());
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
        if (evet.equals(C.EventKey.REFRESH_WAIT_AREA)) {
            if (paramsStatus.equals(TAB_TYPE_WAIT_AREA))
                refreshWithLoading();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegisterBus();
    }
}
