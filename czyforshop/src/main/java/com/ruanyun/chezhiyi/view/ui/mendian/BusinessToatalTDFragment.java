package com.ruanyun.chezhiyi.view.ui.mendian;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.BaseFragment;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.LogX;

/**
 * 2017/4/12
 * jin
 * 营业额统计 当日/当月
 */
public class BusinessToatalTDFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private TextView tvBanka;
    private SwipeRefreshLayout refreshlayout;
    private String workOrderStatusString;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_business_toatal_td, container, false);
        workOrderStatusString = getArguments().getString(C.IntentKey.WORK_ORDER_STATUS_STRING);


        initRefreshView();
        initView();
        return mContentView;
    }

    private void initView() {
        tvBanka = (TextView) mContentView.findViewById(R.id.tv_banka);
    }

    /**
     * 初始化下拉刷新
     */
    private void initRefreshView() {
        refreshlayout = (SwipeRefreshLayout) mContentView.findViewById(R.id.refreshlayout);
        refreshlayout.setColorSchemeResources(com.ruanyun.chezhiyi.commonlib.R.color.holo_blue_bright, com.ruanyun.chezhiyi.commonlib.R.color.holo_green_light,
                com.ruanyun.chezhiyi.commonlib.R.color.holo_orange_light, com.ruanyun.chezhiyi.commonlib.R.color.holo_red_light);
        refreshlayout.setOnRefreshListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onRefresh() {
        if (workOrderStatusString.equals("2")) {
            Toast.makeText(mContext, "当日", 2).show();
            LogX.e("营业统计","当日");
        } else {
            LogX.e("营业统计","当月");
        }
        try {
            Thread.sleep(1000);
            if (refreshlayout != null) {
                refreshlayout.setRefreshing(false);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
