package com.ruanyun.chezhiyi.commonlib.view.ui.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.RefreshBaseFragment;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.model.JieSuanInfo;
import com.ruanyun.chezhiyi.commonlib.model.WorkOrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.MyWorkOrderParams;
import com.ruanyun.chezhiyi.commonlib.presenter.JieSuanPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleItemClicksListener;
import com.ruanyun.chezhiyi.commonlib.view.JieSuanMvpView;
import com.ruanyun.chezhiyi.commonlib.view.adapter.WorkOrderListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.greenrobot.event.Subscribe;
import retrofit2.Call;

/**
 * Description ：  我的工单的
 * <p/>
 * Created by ycw on 2016/10/10.
 */
public class WorkOrderListFragment extends RefreshBaseFragment implements
        /*LazyFragmentPagerAdapter.Laziable,*/ WorkOrderListAdapter.selectAction, View.OnClickListener, JieSuanMvpView {



    public static final int REQUEST_CODE_REFRESH = 1233;
    MyWorkOrderParams params = new MyWorkOrderParams();
    ListView list;
    WorkOrderListAdapter adapter;
    String workOrderStatusString = "";
    private LinearLayout llWorkAllPrice;
    private TextView tvWorkAllNumber, tvWorkAllPrice;
    private String workOrderNumString;
    private JieSuanPresenter jieSuanPresenter = new JieSuanPresenter();
    private List<WorkOrderInfo> datas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_settle_view, container, false);
        workOrderStatusString = getArguments().getString(C.IntentKey.WORK_ORDER_STATUS_STRING);
        initRefreshLayout();
        isFirstIn = true;
        isPrepared = true;
        return mContentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        jieSuanPresenter.attachView(this);
        initView();
        lazyLoad();
        registerBus();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    private void initView() {
        list = getView(R.id.list);
        datas = new ArrayList<>();
        adapter = new WorkOrderListAdapter(mContext, R.layout.list_item_work_order, datas);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new NoDoubleItemClicksListener() {
            @Override
            public void noDoubleClick(AdapterView<?> parent, View view, int position, long id) {
                WorkOrderInfo info = adapter.getItem(position);
                Intent intent = new Intent(mContext, WorkOrderDetailedActivity.class);
                intent.putExtra(C.IntentKey.WORKORDER_INFO, info);
                startActivityForResult(intent, REQUEST_CODE_REFRESH);
            }
        });
        if (app.isClient() && workOrderStatusString.equals("8")) //客户端待结算状态
        {
            adapter.showSelectBtn(true);
            adapter.setSelectAction(this);
            llWorkAllPrice = getView(R.id.ll_work_all_price);
            tvWorkAllPrice = getView(R.id.tv_work_all_price);
            tvWorkAllNumber = getView(R.id.tv_work_all_number);
            llWorkAllPrice.setVisibility(View.VISIBLE);
            tvWorkAllNumber.setOnClickListener(this);
//            refreshLayout.setPullDownRefreshEnable(false);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_REFRESH) {
            refreshWithLoading();
        }
    }

    @Subscribe
    public void refresh(Event even) {
        if (even.key.equals(C.EventKey.REFRESH_WORKORDER_LIST) && even.value.equals(C.EventKey.REFRESH_WORKORDER_LIST)) {
            lazyLoad();
//            allPrice = 0;
//            workOrderInfos.clear();
//            updataBottomUI();
        }
    }

    @Override
    protected void lazyLoad() {
        refreshWithLoading();
    }

    @Override
    public Call loadData() {
        if (app.isClient()){    //是客户端传ServiceUserNum
            params.setServiceUserNum(app.getCurrentUserNum());
        } else {        //是技师端
            params.setLeadingUserNum(app.getCurrentUserNum());
        }
        params.setWorkOrderStatusString(workOrderStatusString);
        params.setPageNum(currentPage);
        LogX.e("服务工单111",params.toString());
        return app.getApiService().getMyWorkOrderList(app.getCurrentUserNum(), params);
    }

    @Override
    public void showEmpty(String tag) {
        super.showEmpty(tag);
        resetBottomUI();
    }

    @Override
    public void onRefreshResult(PageInfoBase result, String tag) {
        resetBottomUI();
        datas = result.getResult();
        adapter.setDate(datas);
    }

    @Override
    public void onLoadMoreResult(PageInfoBase result, String tag) {
        datas.addAll(result.getResult());
        adapter.setDate(datas);
    }

    private double allPrice = 0;
    /**
     * 选中的工单的集合
     */
    private ArrayList<WorkOrderInfo> workOrderInfos = new ArrayList<>();  //选中的工单的集合

    @Override
    public void updateAllPriceUI(WorkOrderInfo item) {
        if (item.isSelected()) {  //选中
            allPrice = allPrice + item.getTotalAmount();
            workOrderInfos.add(item);
        } else { // 取消选中
            allPrice = allPrice - item.getTotalAmount();
            allPrice = allPrice < 0 ? 0 : allPrice;
            workOrderInfos.remove(item);
        }
        updataBottomUI();
    }

    /**
     * 更新底部的ui显示
     */
    private void updataBottomUI() {
        if (app.isClient() && workOrderStatusString.equals("8")) //客户端待结算状态
        {
            String text = String.format(Locale.CHINA, "合计：¥%1.2f", allPrice);
            tvWorkAllPrice.setText(AppUtility.getSpannerString(mContext, "¥", text, false));
            int size = workOrderInfos.size();// 选择的工单个数
            tvWorkAllNumber.setText(size <= 0 ? "结算" : new StringBuilder().append("结算").append
                    ("：（").append(size).append("）").toString());
        }
    }

    /**
     * 重置界面选择的工单
     */
    public void resetBottomUI() {
        if (!workOrderInfos.isEmpty()) {
            for (WorkOrderInfo info : workOrderInfos) {
                info.setSelected(false);
            }
        }
        workOrderInfos.clear();
        allPrice=0;
        updataBottomUI();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_work_all_number) {
            //  2016/11/1 工单结算
            if (workOrderInfos.size() > 0) {
                StringBuilder sb = new StringBuilder();
                sb.append(workOrderInfos.get(0).getWorkOrderNum());
                for (int i = 1; i < workOrderInfos.size(); i++) {
                    sb.append(",");
                    sb.append(workOrderInfos.get(i).getWorkOrderNum());
                }
                workOrderNumString = sb.toString();
                LogX.d(TAG, "---------------\n" + workOrderNumString);
                jieSuanPresenter.getJieSuan(app.getApiService().getJieSuan(app.getCurrentUserNum(), workOrderNumString));
            } else {
                AppUtility.showToastMsg("请选择结算工单");
            }
        }
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
    public void showTips(String msg) {
        AppUtility.showToastMsg(msg);
    }

    @Override
    public void getJieSuanSuccess(JieSuanInfo jieSuanInfo) {
        // 2016/11/1  跳转到工单结算界面
        Intent intent = new Intent();
        intent.setClassName(mContext, "com.ruanyun.czy.client.view.ui.my.SubmitWorkOrderActivity");
        intent.putExtra(C.IntentKey.WORK_ORDER_NUM_STRING, workOrderNumString);
        intent.putExtra(C.IntentKey.JIESUAN_INFO, jieSuanInfo);
        showActivity(intent);

    }

    @Override public void onDestroy() {
        super.onDestroy();
        unRegisterBus();
    }
}
